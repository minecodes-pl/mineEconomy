package pl.arturekdev.mineEconomy.config;

import space.arim.dazzleconf.*;
import space.arim.dazzleconf.error.*;
import space.arim.dazzleconf.ext.snakeyaml.*;
import space.arim.dazzleconf.helper.*;
import space.arim.dazzleconf.sorter.*;

import java.io.*;
import java.nio.file.*;

public class ConfigurationLoader<C> extends ConfigurationHelper<C> {

    private volatile C configData;

    private ConfigurationLoader(Path configFolder, String fileName, ConfigurationFactory<C> factory) {
        super(configFolder, fileName, factory);
    }

    public static <C> ConfigurationLoader<C> create(Path configFolder, String fileName, Class<C> configClass) {
        SnakeYamlOptions yamlOptions = new SnakeYamlOptions.Builder()
                .useCommentingWriter(true)
                .build();
        ConfigurationOptions options = new ConfigurationOptions.Builder().sorter(new AnnotationBasedSorter()).build();
        return new ConfigurationLoader<>(configFolder, fileName,
                new SnakeYamlConfigurationFactory<>(configClass, options, yamlOptions));
    }

    public void reloadConfig() {
        try {
            configData = reloadConfigData();
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);

        } catch (ConfigFormatSyntaxException ex) {
            configData = getFactory().loadDefaults();
            System.err.println("Uh-oh! The syntax of your configuration are invalid. "
                    + "Check your YAML syntax with a tool such as https://yaml-online-parser.appspot.com/");
            ex.printStackTrace();

        } catch (InvalidConfigException ex) {
            configData = getFactory().loadDefaults();
            System.err.println("Uh-oh! The values in your configuration are invalid. "
                    + "Check to make sure you have specified the right data types.");
            ex.printStackTrace();
        }
    }

    public C getConfigData() {
        C configData = this.configData;
        if (configData == null) {
            throw new IllegalStateException("Configuration has not been loaded yet");
        }
        return configData;
    }
}
