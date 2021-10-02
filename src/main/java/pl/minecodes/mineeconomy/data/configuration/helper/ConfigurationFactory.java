package pl.minecodes.mineeconomy.data.configuration.helper;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.postprocessor.SectionSeparator;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class ConfigurationFactory {

    private final File defaultDir;

    public ConfigurationFactory(File defaultDir) {
        this.defaultDir = defaultDir;
    }

    public <T extends OkaeriConfig> T produce(Class<T> clazz, String file, ObjectSerializer<?>... serializers) {
        return produce(clazz, new File(this.defaultDir, file), serializers);
    }

    public <T extends OkaeriConfig> T produce(Class<T> clazz, File file, ObjectSerializer<?>... serializers) {
        return ConfigManager.create(clazz, it -> it
                .withConfigurer(new YamlBukkitConfigurer("#", SectionSeparator.NEW_LINE), registry -> {
                    registry.register(new SerdesBukkit());
                    for (ObjectSerializer<?> serializer : serializers) {
                        registry.register(serializer);
                    }
                })
                .withBindFile(file)
                .saveDefaults()
                .load(true)
        );
    }

}