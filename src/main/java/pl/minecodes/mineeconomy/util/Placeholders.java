package pl.minecodes.mineeconomy.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class Placeholders {

    public static String replace(String string, Map<String, Object> placeholders) {
        AtomicReference<String> atomicString = new AtomicReference<>(string);
        placeholders.forEach((key, value) -> atomicString.set(atomicString.get().replace("{" + key + "}", String.valueOf(value))));
        return atomicString.get();
    }

    public static List<String> replace(List<String> list, Map<String, Object> placeholders) {
        List<String> replacedList = new ArrayList<>();
        list.forEach(string -> replacedList.add(replace(string, placeholders)));
        return replacedList;
    }
}
