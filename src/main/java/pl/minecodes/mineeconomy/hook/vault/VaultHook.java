package pl.minecodes.mineeconomy.hook.vault;

import eu.okaeri.injector.annotation.Inject;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class VaultHook {

    @Inject private Logger logger;
    @Inject private JavaPlugin plugin;
    @Inject private VaultManager provider;

    public void registerHook() {
        Bukkit.getServicesManager().register(Economy.class, this.provider, plugin, ServicePriority.Highest);
        this.logger.info("Successfully register Vault hook!");
    }

    public void unregisterHook() {
        Bukkit.getServicesManager().unregister(Economy.class, this.provider);
        this.logger.info("Successfully unregister Vault hook!");
    }
}
