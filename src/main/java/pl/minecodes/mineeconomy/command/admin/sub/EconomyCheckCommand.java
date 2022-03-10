package pl.minecodes.mineeconomy.command.admin.sub;

import com.google.common.collect.ImmutableMap;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import pl.minecodes.mineeconomy.data.configuration.Configuration;
import pl.minecodes.mineeconomy.data.configuration.Messages;
import pl.minecodes.mineeconomy.profile.Profile;
import pl.minecodes.mineeconomy.profile.ProfileService;
import pl.minecodes.mineeconomy.util.MessageUtil;
import pl.minecodes.mineeconomy.util.Placeholders;

import java.util.Objects;

@Section(route = "economy", aliases = {"eco", "ecoadmin"}, priority = 5)
@Permission("economy.admin")
public class EconomyCheckCommand {

    @Inject private Messages messages;
    @Inject private ProfileService profileService;
    @Inject private Configuration configuration;

    @Execute(route = "check", required = 1)
    public void executeCheck(CommandSender sender, @Arg(0) OfflinePlayer offlinePlayer) {
        Profile profile = this.profileService.getProfile(offlinePlayer.getUniqueId());
        MessageUtil.sendMessage(sender, Placeholders.replace(this.messages.getBalanceAdministratorCheck(),
                ImmutableMap.of(
                        "player", Objects.requireNonNull(offlinePlayer.getName(), "OfflinePlayer name is null."),
                        "balance", profile.getBalance(),
                        "currency", this.configuration.getCurrency(profile.getBalance()))));
    }
}
