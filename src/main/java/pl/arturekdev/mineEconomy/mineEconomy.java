package pl.arturekdev.mineEconomy;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import pl.arturekdev.mineEconomy.database.DatabaseConnector;
import pl.arturekdev.mineEconomy.listeners.PlayerInteractListener;
import pl.arturekdev.mineEconomy.managers.CommandManager;
import pl.arturekdev.mineEconomy.managers.UserManager;
import pl.arturekdev.mineEconomy.placeholders.PlaceholderExpansionEco;
import pl.arturekdev.mineEconomy.runnable.UserUpdateRunnable;
import pl.arturekdev.mineEconomy.utils.ItemBuilder;

import java.util.Random;

public final class mineEconomy extends JavaPlugin {

    private static mineEconomy instance;
    @Getter
    private DatabaseConnector databaseConnector;

    public static mineEconomy getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        databaseConnector = new DatabaseConnector();
        databaseConnector.prepareCollection();

        UserManager userManager = new UserManager(databaseConnector);
        userManager.loadUsers();

        CommandManager commandManager = new CommandManager();
        commandManager.registerCommands();

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new UserUpdateRunnable(userManager), 60, 60);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new PlaceholderExpansionEco().register();
        }
    }

    @Override
    public void onDisable() {

        UserManager userManager = new UserManager(databaseConnector);
        UserManager.getUsers().forEach(user -> user.update(userManager));

    }

    public ItemStack getPurse(int value) {

        ItemBuilder itemBuilder = new ItemBuilder(Material.GLOWSTONE_DUST);

        itemBuilder.setAmount(1);
        itemBuilder.setTitle(" &8>> &6&lSakiewka Iskier &8<<");
        itemBuilder.addLore(" ");
        itemBuilder.addLore(" &8>> &7W sakiewce znajduje się: &e" + value + " Iskier");
        itemBuilder.addLore(" ");
        itemBuilder.addLore("&e&nKliknij prawym trzymając aby wpłacić!");

        NBTItem nbtItem = new NBTItem(itemBuilder.build());


        nbtItem.setInteger("RANDOM", new Random().nextInt());
        nbtItem.setInteger("VALUE", value);
        nbtItem.setString("PURSE", "SPARKS");

        return nbtItem.getItem();

    }
}
