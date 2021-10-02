package pl.minecodes.mineeconomy.command.player;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import com.google.common.collect.ImmutableMap;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.entity.Player;
import pl.minecodes.mineeconomy.data.configuration.Configuration;
import pl.minecodes.mineeconomy.data.configuration.Messages;
import pl.minecodes.mineeconomy.data.database.element.model.DataService;
import pl.minecodes.mineeconomy.profile.Profile;
import pl.minecodes.mineeconomy.profile.ProfileService;
import pl.minecodes.mineeconomy.util.MessageUtil;
import pl.minecodes.mineeconomy.util.Placeholders;

import java.util.Objects;

@CommandAlias("ranking")
public class RankingCommand extends BaseCommand {

    @Inject
    private Messages messages;
    @Inject
    private DataService dataService;
    @Inject
    private Configuration configuration;

    @Default
    public void onCheckRanking(Player player) {
        int rankingOrder = 1;
        for (String string : this.messages.getBalanceRanking()) {
            if (!string.contains("username")) MessageUtil.sendMessage(player, string);

            Profile order = this.dataService.order(rankingOrder);
            if (order == null) {
                MessageUtil.sendMessage(player, this.messages.getBalanceRankingNullObject());
            } else {
                MessageUtil.sendMessage(player, Placeholders.replace(string,
                        ImmutableMap.of(
                                "username", Objects.requireNonNull(order.getPlayer().getName(), "OfflinePlayer name is null."),
                                "balance", order.getBalance(),
                                "currency", this.configuration.getCurrency(order.getBalance())
                        )));
            }
            rankingOrder++;
        }
    }

}
