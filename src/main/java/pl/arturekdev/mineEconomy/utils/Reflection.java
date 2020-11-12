package pl.arturekdev.mineEconomy.utils;

import org.bukkit.Bukkit;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Reflection {
    private static String OBC_PREFIX;
    private static String NMS_PREFIX;
    private static String VERSION;
    private static Pattern MATCH_VARIABLE;

    static {
        Reflection.OBC_PREFIX = Bukkit.getServer().getClass().getPackage().getName();
        Reflection.NMS_PREFIX = Reflection.OBC_PREFIX.replace("org.bukkit.craftbukkit", "net.minecraft.server");
        Reflection.VERSION = Reflection.OBC_PREFIX.replace("org.bukkit.craftbukkit", "").replace(".", "");
        Reflection.MATCH_VARIABLE = Pattern.compile("\\{([^\\}]+)\\}");
    }

    @SuppressWarnings("unchecked")
    public static <T> FieldAccessor<T> getSimpleField(final Class<?> target, final String name) {
        return getField(target, name);
    }

    public static <T> FieldAccessor<T> getField(final Class<?> target, final String name, final Class<T> fieldType) {
        return getField(target, name, fieldType, 0);
    }

    public static <T> FieldAccessor<T> getField(final String className, final String name, final Class<T> fieldType) {
        return getField(getClass(className), name, fieldType, 0);
    }

    public static <T> FieldAccessor<T> getField(final Class<?> target, final Class<T> fieldType, final int index) {
        return getField(target, null, fieldType, index);
    }

    public static <T> FieldAccessor<T> getField(final String className, final Class<T> fieldType, final int index) {
        return getField(getClass(className), fieldType, index);
    }

    @SuppressWarnings("unchecked")
    private static <T> FieldAccessor<T> getField(final Class<?> target, final String name, final Class<T> fieldType, int index) {
        Field[] declaredFields;
        for (int length = (declaredFields = target.getDeclaredFields()).length, i = 0; i < length; ++i) {
            final Field field = declaredFields[i];
            if ((name == null || field.getName().equals(name)) && fieldType.isAssignableFrom(field.getType()) && index-- <= 0) {
                field.setAccessible(true);
                return new FieldAccessor<T>() {
                    @Override
                    public T get(final Object target) {
                        try {
                            return (T) field.get(target);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException("Cannot access reflection.", e);
                        }
                    }

                    @Override
                    public void set(final Object target, final Object value) {
                        try {
                            field.set(target, value);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException("Cannot access reflection.", e);
                        }
                    }

                    @Override
                    public boolean hasField(final Object target) {
                        return field.getDeclaringClass().isAssignableFrom(target.getClass());
                    }
                };
            }
        }
        if (target.getSuperclass() != null) {
            return (FieldAccessor<T>) getField(target.getSuperclass(), name, (Class<Object>) fieldType, index);
        }
        throw new IllegalArgumentException("Cannot find field with type " + fieldType);
    }

    @SuppressWarnings("unchecked")
    private static <T> FieldAccessor<T> getField(final Class<?> target, final String name) {
        Field[] declaredFields;
        for (int length = (declaredFields = target.getDeclaredFields()).length, i = 0; i < length; ++i) {
            final Field field = declaredFields[i];
            if (name == null || field.getName().equals(name)) {
                field.setAccessible(true);
                return new FieldAccessor<T>() {
                    @Override
                    public T get(final Object target) {
                        try {
                            return (T) field.get(target);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException("Cannot access reflection.", e);
                        }
                    }

                    @Override
                    public void set(final Object target, final Object value) {
                        try {
                            field.set(target, value);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException("Cannot access reflection.", e);
                        }
                    }

                    @Override
                    public boolean hasField(final Object target) {
                        return field.getDeclaringClass().isAssignableFrom(target.getClass());
                    }
                };
            }
        }
        if (target.getSuperclass() != null) {
            return getField(target.getSuperclass(), name);
        }
        throw new IllegalArgumentException("Cannot find field with type");
    }

    public static MethodInvoker getMethod(final String className, final String methodName, final Class<?>... params) {
        return getTypedMethod(getClass(className), methodName, null, params);
    }

    public static MethodInvoker getMethod(final Class<?> clazz, final String methodName, final Class<?>... params) {
        return getTypedMethod(clazz, methodName, null, params);
    }

    public static MethodInvoker getTypedMethod(final Class<?> clazz, final String methodName, final Class<?> returnType, final Class<?>... params) {
        Method[] declaredMethods;
        for (int length = (declaredMethods = clazz.getDeclaredMethods()).length, i = 0; i < length; ++i) {
            final Method method = declaredMethods[i];
            if (((methodName == null || method.getName().equals(methodName)) && returnType == null) || (method.getReturnType().equals(returnType) && Arrays.equals(method.getParameterTypes(), params))) {
                method.setAccessible(true);
                return new MethodInvoker() {
                    @Override
                    public Object invoke(final Object target, final Object... arguments) {
                        try {
                            return method.invoke(target, arguments);
                        } catch (Exception e) {
                            throw new RuntimeException("Cannot invoke method " + method, e);
                        }
                    }
                };
            }
        }
        if (clazz.getSuperclass() != null) {
            return getMethod(clazz.getSuperclass(), methodName, params);
        }
        throw new IllegalStateException(String.format("Unable to find method %s (%s).", methodName, Arrays.asList(params)));
    }

    public static ConstructorInvoker getConstructor(final String className, final Class<?>... params) {
        return getConstructor(getClass(className), params);
    }

    public static ConstructorInvoker getConstructor(final Class<?> clazz, final Class<?>... params) {
        @SuppressWarnings("rawtypes")
        Constructor[] declaredConstructors;
        for (int length = (declaredConstructors = clazz.getDeclaredConstructors()).length, i = 0; i < length; ++i) {
            final Constructor<?> constructor = (Constructor<?>) declaredConstructors[i];
            if (Arrays.equals(constructor.getParameterTypes(), params)) {
                constructor.setAccessible(true);
                return new ConstructorInvoker() {
                    @Override
                    public Object invoke(final Object... arguments) {
                        try {
                            return constructor.newInstance(arguments);
                        } catch (Exception e) {
                            throw new RuntimeException("Cannot invoke constructor " + constructor, e);
                        }
                    }
                };
            }
        }
        throw new IllegalStateException(String.format("Unable to find constructor for %s (%s).", clazz, Arrays.asList(params)));
    }

    public static Class<Object> getUntypedClass(final String lookupName) {
        @SuppressWarnings("unchecked") final Class<Object> clazz = (Class<Object>) getClass(lookupName);
        return clazz;
    }

    public static Class<?> getClass(final String lookupName) {
        return getCanonicalClass(expandVariables(lookupName));
    }

    public static Class<?> getMinecraftClass(final String name) {
        return getCanonicalClass(String.valueOf(String.valueOf(String.valueOf(Reflection.NMS_PREFIX))) + "." + name);
    }

    public static Class<?> getCraftBukkitClass(final String name) {
        return getCanonicalClass(String.valueOf(String.valueOf(String.valueOf(Reflection.OBC_PREFIX))) + "." + name);
    }

    private static Class<?> getCanonicalClass(final String canonicalName) {
        try {
            return Class.forName(canonicalName);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Cannot find " + canonicalName, e);
        }
    }

    private static String expandVariables(final String name) {
        final StringBuffer output = new StringBuffer();
        final Matcher matcher = Reflection.MATCH_VARIABLE.matcher(name);
        while (matcher.find()) {
            final String variable = matcher.group(1);
            String replacement = "";
            if ("nms".equalsIgnoreCase(variable)) {
                replacement = Reflection.NMS_PREFIX;
            } else if ("obc".equalsIgnoreCase(variable)) {
                replacement = Reflection.OBC_PREFIX;
            } else {
                if (!"version".equalsIgnoreCase(variable)) {
                    throw new IllegalArgumentException("Unknown variable: " + variable);
                }
                replacement = Reflection.VERSION;
            }
            if (replacement.length() > 0 && matcher.end() < name.length() && name.charAt(matcher.end()) != '.') {
                replacement = String.valueOf(String.valueOf(replacement)) + ".";
            }
            matcher.appendReplacement(output, Matcher.quoteReplacement(replacement));
        }
        matcher.appendTail(output);
        return output.toString();
    }

    public interface ConstructorInvoker {
        Object invoke(final Object... p0);
    }

    public interface FieldAccessor<T> {
        T get(final Object p0);

        void set(final Object p0, final Object p1);

        boolean hasField(final Object p0);
    }

    public interface MethodInvoker {
        Object invoke(final Object p0, final Object... p1);
    }
}
