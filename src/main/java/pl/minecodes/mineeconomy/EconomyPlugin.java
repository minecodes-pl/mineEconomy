package pl.minecodes.mineeconomy;

import co.aikar.commands.BukkitCommandManager;
import co.aikar.commands.BukkitLocales;
import eu.okaeri.injector.Injector;
import eu.okaeri.injector.OkaeriInjector;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pl.minecodes.mineeconomy.command.admin.EconomyCommand;
import pl.minecodes.mineeconomy.command.player.BalanceCommand;
import pl.minecodes.mineeconomy.command.player.TransferCommand;
import pl.minecodes.mineeconomy.data.configuration.Configuration;
import pl.minecodes.mineeconomy.data.configuration.Messages;
import pl.minecodes.mineeconomy.data.configuration.helper.ConfigurationFactory;
import pl.minecodes.mineeconomy.hook.placeholderapi.PlaceholderAPIHook;
import pl.minecodes.mineeconomy.hook.vault.VaultHook;
import pl.minecodes.mineeconomy.hook.vault.VaultManager;
import pl.minecodes.mineeconomy.profile.ProfileService;

import java.util.Locale;

public class EconomyPlugin extends JavaPlugin {

    private Injector injector;
    private Messages messages;
    private Configuration configuration;
    private ProfileService profileService;
    private VaultHook vaultHook;

    @Override
    public void onEnable() {
        this.injector = OkaeriInjector.create()
                .registerInjectable(this)
                .registerInjectable(this.getLogger());

        this.loadConfiguration();

        this.profileService = this.injector.createInstance(ProfileService.class);
        this.injector.registerInjectable(profileService);

        this.registerCommands();

        VaultManager vaultManager = this.injector.createInstance(VaultManager.class);
        this.injector.registerInjectable(vaultManager);

        vaultHook = this.injector.createInstance(VaultHook.class);
        vaultHook.registerHook();

        PlaceholderAPIHook papiHook = this.injector.createInstance(PlaceholderAPIHook.class);
        papiHook.register();
    }

    @Override
    public void onDisable() {
        vaultHook.unregisterHook();
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
        commandManager.registerCommand(this.injector.createInstance(TransferCommand.class));

        commandManager.getLocales().setDefaultLocale(Locale.ENGLISH);
        BukkitLocales locales = commandManager.getLocales();
        try {
            locales.loadYamlLanguageFile(messages.getBindFile().toFile(), Locale.ENGLISH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
