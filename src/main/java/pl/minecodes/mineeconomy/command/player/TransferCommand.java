package pl.minecodes.mineeconomy.command.player;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import com.google.common.collect.ImmutableMap;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.entity.Player;
import pl.minecodes.mineeconomy.data.configuration.Configuration;
import pl.minecodes.mineeconomy.data.configuration.Messages;
import pl.minecodes.mineeconomy.profile.Profile;
import pl.minecodes.mineeconomy.profile.ProfileService;
import pl.minecodes.mineeconomy.profile.helper.BalanceOperationCallback;
import pl.minecodes.mineeconomy.util.MessageUtil;
import pl.minecodes.mineeconomy.util.Placeholders;

@CommandAlias("transfer|pay")
public class TransferCommand extends BaseCommand {

    @Inject
    private Messages messages;
    @Inject
    private Configuration configuration;
    @Inject
    private ProfileService profileService;

    @Default
    @Syntax("<username> <value>")
    @CommandCompletion("@players 10|100|1000|10000")
    public void onPlayerTransfer(Player sender, OnlinePlayer target, double value) {

        Profile senderProfile = this.profileService.getProfile(sender.getUniqueId());
        if (!(senderProfile.has(value))) {
            MessageUtil.sendMessage(sender, this.messages.getBalanceNoFounds());
            return;
        }

        Profile targetProfile = this.profileService.getProfile(target.getPlayer().getUniqueId());
        targetProfile.deposit(value, new BalanceOperationCallback() {
            @Override
            public void done() {
                MessageUtil.sendMessage(sender, Placeholders.replace(messages.getBalanceSuccessfullyTransferToSender(),
                        ImmutableMap.of(
                                "target", target.getPlayer().getName(),
                                "value", value,
                                "currency", configuration.getCurrency(value)
                        )));

                MessageUtil.sendMessage(sender, Placeholders.replace(messages.getBalanceSuccessfullyTransferToTarget(),
                        ImmutableMap.of(
                                "sender", sender.getName(),
                                "value", value,
                                "currency", configuration.getCurrency(value)
                        )));
            }

            @Override
            public void cancel(CancelReason reason) {
                MessageUtil.sendMessage(sender, messages.getBalanceOperationParameterIsNegative());
            }
        });
    }

}
