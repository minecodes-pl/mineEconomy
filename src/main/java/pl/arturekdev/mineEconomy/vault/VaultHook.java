package pl.arturekdev.mineEconomy.vault;

import net.milkbowl.vault.economy.*;
import org.bukkit.*;
import org.bukkit.plugin.*;
import pl.arturekdev.mineEconomy.*;

import java.util.logging.*;

public class VaultHook {

    private VaultManager provider;

    public VaultHook(VaultManager provider) {
        this.provider = provider;
    }


    public void hook() {
        Bukkit.getServicesManager().register(Economy.class, this.provider, EconomyPlugin.getInstance(), ServicePriority.Highest);
        Bukkit.getLogger().log(Level.INFO, ChatColor.GREEN + "Hooked into Vault!");
    }

    public void unhook() {
        provider = EconomyPlugin.getInstance().getVaultManager();
        Bukkit.getServicesManager().unregister(Economy.class, this.provider);
    }

}
