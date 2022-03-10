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

@Section(route = "economy", aliases = {"eco", "ecoadmin"}, priority = 2)
@Permission("economy.admin")
public class EconomyDepositCommand {

    @Inject private Messages messages;
    @Inject private ProfileService profileService;
    @Inject private Configuration configuration;

    @Execute(route = "deposit", required = 2)
    public void executeDeposit(CommandSender sender, @Arg(0) OfflinePlayer offlinePlayer, @Arg(1) Double value) {
        AtomicDouble atomicValue = new AtomicDouble(value);

        Profile profile = this.profileService.getProfile(offlinePlayer.getUniqueId());
        profile.deposit(atomicValue.get(), new BalanceOperationCallback() {
            @Override
            public void done() {
                MessageUtil.sendMessage(sender, Placeholders.replace(messages.getBalanceSuccessfullyDeposit(),
                        ImmutableMap.of(
                                "player", Objects.requireNonNull(offlinePlayer.getName(), "OfflinePlayer name is null."),
                                "value", atomicValue.get(),
                                "currency", configuration.getCurrency(atomicValue.get()))));
            }

            @Override
            public void cancel(CancelReason reason) {
                MessageUtil.sendMessage(sender, messages.getBalanceOperationParameterIsNegative());
            }
        }, this.configuration.getRoundedScale());
    }
}
