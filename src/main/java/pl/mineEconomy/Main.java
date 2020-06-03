package pl.mineEconomy;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pl.mineEconomy.commands.EcoAdminCommand;
import pl.mineEconomy.commands.EcoCommand;
import pl.mineEconomy.managers.DatabaseManager;
import pl.mineEconomy.managers.UserManager;
import pl.mineEconomy.placeholders.PlaceholderExpansionEco;
import pl.mineEconomy.runnable.SaveUsersRunnable;

public final class Main extends JavaPlugin {

    private static Main plugin;

    public static Main getInst() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        createUsersTable();
        UserManager.load();
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")){
            new PlaceholderExpansionEco().register();
        }
        this.getCommand("przelew").setExecutor(new EcoCommand());
        this.getCommand("konto").setExecutor(new EcoCommand());
        this.getCommand("wyplac").setExecutor(new EcoCommand());
        this.getCommand("wplac").setExecutor(new EcoCommand());
        this.getCommand("ekonomia").setExecutor(new EcoCommand());
        this.getCommand("bogacze").setExecutor(new EcoCommand());
        this.getCommand("ecoa").setExecutor(new EcoAdminCommand());
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new SaveUsersRunnable(), 20, 20);
    }

    public void createUsersTable() {
        final StringBuilder sb = new StringBuilder();
        sb.append("create table if not exists `economyUser`(");
        sb.append("`nick` varchar(16) not null,");
        sb.append("`balance` int(255),");
        sb.append("primary key (nick));");
        DatabaseManager.updateQuery(sb.toString());
    }
}
