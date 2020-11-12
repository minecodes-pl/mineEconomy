package pl.arturekdev.mineEconomy;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pl.arturekdev.mineEconomy.database.DatabaseConnector;
import pl.arturekdev.mineEconomy.managers.CommandManager;
import pl.arturekdev.mineEconomy.managers.UserManager;
import pl.arturekdev.mineEconomy.placeholders.PlaceholderExpansionEco;
import pl.arturekdev.mineEconomy.runnable.UserUpdateRunnable;

public final class mineEconomy extends JavaPlugin {

    private static mineEconomy plugin;
    @Getter
    private DatabaseConnector databaseConnector;

    public static mineEconomy getInst() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;

        databaseConnector = new DatabaseConnector();
        databaseConnector.prepareCollection();

        UserManager userManager = new UserManager(databaseConnector);
        userManager.loadUsers();

        CommandManager commandManager = new CommandManager();
        commandManager.registerCommands();

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new UserUpdateRunnable(userManager), 60, 60);

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new PlaceholderExpansionEco().register();
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
