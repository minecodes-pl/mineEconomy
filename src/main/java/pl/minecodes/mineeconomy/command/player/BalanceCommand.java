package pl.minecodes.mineeconomy.command.player;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import com.google.common.collect.ImmutableMap;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.entity.Player;
import pl.minecodes.mineeconomy.data.configuration.Configuration;
import pl.minecodes.mineeconomy.data.configuration.Messages;
import pl.minecodes.mineeconomy.profile.Profile;
import pl.minecodes.mineeconomy.profile.ProfileService;
import pl.minecodes.mineeconomy.util.MessageUtil;
import pl.minecodes.mineeconomy.util.Placeholders;

@CommandAlias("balance|money|bal|account")
public class BalanceCommand extends BaseCommand {

    @Inject private Messages messages;
    @Inject private Configuration configuration;
    @Inject private ProfileService profileService;

    @Default
    public void onPlayerBalanceCheck(Player player) {
        Profile profile = this.profileService.getProfile(player.getUniqueId());
        MessageUtil.sendMessage(player, Placeholders.replace(this.messages.getBalanceCheck(),
                ImmutableMap.of(
                        "balance", profile.getBalance(),
                        "currency", this.configuration.getCurrency(profile.getBalance()))));
    }

}
