package pl.minecodes.mineeconomy.command.player;

import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.AtomicDouble;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Section;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.entity.Player;
import pl.minecodes.mineeconomy.EconomyPlugin;
import pl.minecodes.mineeconomy.data.configuration.Configuration;
import pl.minecodes.mineeconomy.data.configuration.Messages;
import pl.minecodes.mineeconomy.profile.Profile;
import pl.minecodes.mineeconomy.profile.ProfileService;
import pl.minecodes.mineeconomy.profile.helper.BalanceOperationCallback;
import pl.minecodes.mineeconomy.util.MessageUtil;
import pl.minecodes.mineeconomy.util.Placeholders;

import java.util.Objects;

@Section(route = "transfer", aliases = {"pay", "sendmoney"})
public class TransferCommand {

    @Inject private Messages messages;
    @Inject private Configuration configuration;
    @Inject private ProfileService profileService;

    @Execute(required = 2)
    public void executeTransfer(Player sender, @Arg(0) Player target, @Arg(1) Double value) {
        AtomicDouble atomicValue = new AtomicDouble(Double.parseDouble(EconomyPlugin.FORMAT.format(value)));

        Profile senderProfile = this.profileService.getProfile(sender.getUniqueId());
        if (!(senderProfile.has(atomicValue.get()))) {
            MessageUtil.sendMessage(sender, this.messages.getBalanceNoFounds());
            return;
        }

        Profile targetProfile = this.profileService.getProfile(target.getPlayer().getUniqueId());
        senderProfile.withdraw(atomicValue.get(), new BalanceOperationCallback() {
            @Override
            public void done() {
                //ignore
            }

            @Override
            public void cancel(CancelReason reason) {
                //ignore
            }
        });

        targetProfile.deposit(atomicValue.get(), new BalanceOperationCallback() {
            @Override
            public void done() {
                MessageUtil.sendMessage(sender, Placeholders.replace(messages.getBalanceSuccessfullyTransferToSender(),
                        ImmutableMap.of(
                                "target", target.getPlayer().getName(),
                                "value", atomicValue.get(),
                                "currency", configuration.getCurrency(atomicValue.get())
                        )));

                MessageUtil.sendMessage(Objects.requireNonNull(target.getPlayer().getPlayer()), Placeholders.replace(messages.getBalanceSuccessfullyTransferToTarget(),
                        ImmutableMap.of(
                                "sender", sender.getName(),
                                "value", atomicValue.get(),
                                "currency", configuration.getCurrency(atomicValue.get())
                        )));
            }

            @Override
            public void cancel(CancelReason reason) {
                MessageUtil.sendMessage(sender, messages.getBalanceOperationParameterIsNegative());
            }
        });
    }

}
