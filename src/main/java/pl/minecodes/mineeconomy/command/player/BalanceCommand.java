package pl.minecodes.mineeconomy.command.player;

import com.google.common.collect.ImmutableMap;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Section;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.entity.Player;
import pl.minecodes.mineeconomy.data.configuration.Configuration;
import pl.minecodes.mineeconomy.data.configuration.Messages;
import pl.minecodes.mineeconomy.profile.Profile;
import pl.minecodes.mineeconomy.profile.ProfileService;
import pl.minecodes.mineeconomy.util.MessageUtil;
import pl.minecodes.mineeconomy.util.Placeholders;

@Section(route = "balance", aliases = {"bal", "money"})
public class BalanceCommand {

    @Inject private Messages messages;
    @Inject private Configuration configuration;
    @Inject private ProfileService profileService;

    @Execute()
    public void executeBalanceCheck(Player player) {
        Profile profile = this.profileService.getProfile(player.getUniqueId());
        MessageUtil.sendMessage(player, Placeholders.replace(this.messages.getBalanceCheck(),
                ImmutableMap.of(
                        "balance", profile.getBalance(),
                        "currency", this.configuration.getCurrency(profile.getBalance()))));
    }

}
