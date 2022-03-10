package pl.minecodes.mineeconomy.command.admin.sub;

import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.AtomicDouble;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import pl.minecodes.mineeconomy.EconomyPlugin;
import pl.minecodes.mineeconomy.data.configuration.Configuration;
import pl.minecodes.mineeconomy.data.configuration.Messages;
import pl.minecodes.mineeconomy.profile.Profile;
import pl.minecodes.mineeconomy.profile.ProfileService;
import pl.minecodes.mineeconomy.profile.helper.BalanceOperationCallback;
import pl.minecodes.mineeconomy.util.MessageUtil;
import pl.minecodes.mineeconomy.util.Placeholders;

import java.util.Objects;

@Section(route = "economy", aliases = {"eco", "ecoadmin"}, priority = 3)
@Permission("economy.admin")
public class EconomyWithdrawCommand {

    @Inject private Messages messages;
    @Inject private ProfileService profileService;
    @Inject private Configuration configuration;

    @Execute(route = "withdraw", required = 2)
    public void executeWithdraw(CommandSender sender, @Arg(0) OfflinePlayer offlinePlayer, @Arg(1) Double value) {
        AtomicDouble atomicValue = new AtomicDouble(Double.parseDouble(EconomyPlugin.FORMAT.format(value)));

        Profile profile = this.profileService.getProfile(offlinePlayer.getUniqueId());
        profile.withdraw(atomicValue.get(), new BalanceOperationCallback() {
            @Override
            public void done() {
                MessageUtil.sendMessage(sender, Placeholders.replace(messages.getBalanceSuccessfullyWithdraw(),
                        ImmutableMap.of(
                                "player", Objects.requireNonNull(offlinePlayer.getName(), "OfflinePlayer name is null."),
                                "value", atomicValue.get(),
                                "currency", configuration.getCurrency(atomicValue.get()))));
            }

            @Override
            public void cancel(CancelReason reason) {
                switch (reason) {
                    case NO_FOUNDS:
                        MessageUtil.sendMessage(sender, messages.getBalanceWithdrawNoFounds());
                        break;
                    case NEGATIVE_BALANCE:
                        MessageUtil.sendMessage(sender, messages.getBalanceIsNegative());
                        break;
                    case NEGATIVE_PARAMETER:
                        MessageUtil.sendMessage(sender, messages.getBalanceOperationParameterIsNegative());
                        break;
                }
            }
        }, this.configuration.getRoundedScale());
    }
}
