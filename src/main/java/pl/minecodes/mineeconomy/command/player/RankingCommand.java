package pl.minecodes.mineeconomy.command.player;

import com.google.common.collect.ImmutableMap;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Section;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.entity.Player;
import pl.minecodes.mineeconomy.data.configuration.Configuration;
import pl.minecodes.mineeconomy.data.configuration.Messages;
import pl.minecodes.mineeconomy.data.database.element.model.DataService;
import pl.minecodes.mineeconomy.profile.Profile;
import pl.minecodes.mineeconomy.util.MessageUtil;
import pl.minecodes.mineeconomy.util.Placeholders;

import java.util.Objects;

@Section(route = "ranking", aliases = {"ecotop"})
public class RankingCommand {

    @Inject private Messages messages;
    @Inject private DataService dataService;
    @Inject private Configuration configuration;

    @Execute
    public void executeRanking(Player player) {
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
