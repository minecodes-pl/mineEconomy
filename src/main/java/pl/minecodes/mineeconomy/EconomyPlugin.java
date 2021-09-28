package pl.minecodes.mineeconomy;

import co.aikar.commands.BukkitCommandManager;
import co.aikar.commands.BukkitLocales;
import eu.okaeri.injector.Injector;
import eu.okaeri.injector.OkaeriInjector;
import org.bukkit.plugin.java.JavaPlugin;
import pl.minecodes.mineeconomy.command.admin.EconomyCommand;
import pl.minecodes.mineeconomy.command.player.BalanceCommand;
import pl.minecodes.mineeconomy.data.configuration.Configuration;
import pl.minecodes.mineeconomy.data.configuration.Messages;
import pl.minecodes.mineeconomy.data.configuration.helper.ConfigurationFactory;
import pl.minecodes.mineeconomy.profile.ProfileService;

import java.util.Locale;

public class EconomyPlugin extends JavaPlugin {

    private Injector injector;
    private Messages messages;
    private Configuration configuration;
    private ProfileService profileService;

    @Override
    public void onEnable() {
        this.profileService = new ProfileService();
        this.injector = OkaeriInjector.create()
                .registerInjectable(this)
                .registerInjectable(this.getLogger())
                .registerInjectable(profileService);

        this.loadConfiguration();
        this.registerCommands();
    }

    @Override
    public void onDisable() {
    }

    private void loadConfiguration() {
        ConfigurationFactory configurationFactory = new ConfigurationFactory(this.getDataFolder());
        configuration = configurationFactory.produce(Configuration.class, "configuration.yml");
        messages = configurationFactory.produce(Messages.class, "messages.yml");
        this.injector.registerInjectable(configuration);
        this.injector.registerInjectable(messages);
    }

    private void registerCommands() {
        BukkitCommandManager commandManager = new BukkitCommandManager(this);
        commandManager.registerCommand(this.injector.createInstance(BalanceCommand.class));
        commandManager.registerCommand(this.injector.createInstance(EconomyCommand.class));

        commandManager.getLocales().setDefaultLocale(Locale.ENGLISH);
        BukkitLocales locales = commandManager.getLocales();
        try {
            locales.loadYamlLanguageFile(messages.getBindFile().toFile(), Locale.ENGLISH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
