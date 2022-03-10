package pl.minecodes.mineeconomy.hook.placeholderapi;

import eu.okaeri.injector.annotation.Inject;
import me.clip.placeholderapi.PlaceholderHook;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import pl.minecodes.mineeconomy.data.configuration.Configuration;
import pl.minecodes.mineeconomy.profile.Profile;
import pl.minecodes.mineeconomy.profile.ProfileService;

import java.util.logging.Logger;

public class PlaceholderAPIHook extends PlaceholderExpansion {

    @Inject private Logger logger;
    @Inject private JavaPlugin plugin;
    @Inject private Configuration configuration;
    @Inject private ProfileService profileService;

    @Override
    public String getIdentifier() {
        return this.plugin.getDescription().getName();
    }

    @Override
    public String getAuthor() {
        return this.plugin.getDescription().getAuthors().toString();
    }

    @Override
    public String getVersion() {
        return this.plugin.getDescription().getVersion();
    }


    @Override
    public String onRequest(OfflinePlayer player, String parameter) {
        Profile profile = this.profileService.getProfile(player.getUniqueId());
        if (parameter.equals("balance")) {
            switch (this.configuration.getCurrencyPositionVault()) {
                case AHEAD:
                    return this.configuration.getCurrency(profile.getBalance()) + profile.getBalance();
                case BEHIND:
                    return profile.getBalance() + this.configuration.getCurrency(profile.getBalance());
            }
        }
        return "";
    }

    public void registerHook() {
        this.register();
        this.logger.info("Successfully register PlaceholderAPI hook!");
    }
}
