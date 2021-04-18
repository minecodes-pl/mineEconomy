package pl.arturekdev.mineEconomy;

import net.milkbowl.vault.economy.*;
import org.bukkit.*;
import org.bukkit.plugin.*;
import org.bukkit.plugin.java.*;
import pl.arturekdev.mineEconomy.command.*;
import pl.arturekdev.mineEconomy.config.*;
import pl.arturekdev.mineEconomy.database.*;
import pl.arturekdev.mineEconomy.listener.PlayerQuitListener;
import pl.arturekdev.mineEconomy.placeholder.*;
import pl.arturekdev.mineEconomy.task.*;
import pl.arturekdev.mineEconomy.user.*;
import pl.arturekdev.mineEconomy.vault.*;

public final class mineEconomy extends JavaPlugin {

    private static mineEconomy instance;
    private static Economy economy;
    private static EcoConfiguration ecoConfiguration;
    private static EcoMessages ecoMessages;
    private static VaultHook vaultHook;
    private VaultManager vaultManager;
    private UserService userService;

    public static mineEconomy getInstance() {
        return instance;
    }

    public static EcoConfiguration getEcoConfiguration() {
        return ecoConfiguration;
    }

    public static EcoMessages getEcoMessages() {
        return ecoMessages;
    }

    public VaultManager getVaultManager() {
        return vaultManager;
    }

    @Override
    public void onEnable() {
        instance = this;

        ConfigurationLoader<EcoConfiguration> ecoConfigurationLoader = ConfigurationLoader.create(this.getDataFolder().toPath(), "configuration.yml", EcoConfiguration.class);
        ecoConfigurationLoader.reloadConfig();
        ecoConfiguration = ecoConfigurationLoader.getConfigData();

        ConfigurationLoader<EcoMessages> ecoMessagesLoader = ConfigurationLoader.create(this.getDataFolder().toPath(), "messages.yml", EcoMessages.class);
        ecoMessagesLoader.reloadConfig();
        ecoMessages = ecoMessagesLoader.getConfigData();

        vaultManager = new VaultManager(ecoConfiguration);
        vaultHook = new VaultHook(vaultManager);
        vaultHook.hook();

        DatabaseConnector databaseConnector = new DatabaseConnector();
        databaseConnector.prepareCollection();

        userService = new UserService(databaseConnector);
        userService.loadUsers();

        setupEconomy();

        getCommand("money").setExecutor(new EconomyCommand(ecoMessages));
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(userService), this);

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new UserUpdateTask(userService), 60, 60);
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new PlayTimePrizeTask(ecoConfiguration, ecoMessages), ecoConfiguration.playTimePrizeTask(), ecoConfiguration.playTimePrizeTask());

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new PlaceholderExpansionEco().register();
        }
    }

    @Override
    public void onDisable() {

        vaultHook.unhook();
        UserService.getUsers().listIterator().forEachRemaining(user -> user.update(userService));
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }
}
