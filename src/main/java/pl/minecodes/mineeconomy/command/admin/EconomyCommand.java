package pl.minecodes.mineeconomy.command.admin;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.AtomicDouble;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.Bukkit;
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

import java.util.Collections;
import java.util.Objects;

@CommandAlias("economy|eco|ecoadmin")
@CommandPermission("economy.admin")
public class EconomyCommand extends BaseCommand {

    @Inject
    private ProfileService profileService;
    @Inject
    private Configuration configuration;
    @Inject
    private Messages messages;


    @Default
    @HelpCommand
    public void help(CommandSender sender) {
        this.messages.getEconomyAdminCommands().forEach(string -> MessageUtil.sendMessage(sender, string));
    }


    @Syntax("<username> <balance>")
    @Subcommand("set")
    @Description("Setup player balance.")
    @CommandCompletion("@players 10|100|1000|10000")
    public void onAdministratorSetBalance(CommandSender sender, String username, double balance) {
        AtomicDouble atomicBalance = new AtomicDouble(Double.parseDouble(EconomyPlugin.FORMAT.format(balance)));

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayerIfCached(username);
        if (offlinePlayer == null) {
            MessageUtil.sendMessage(sender, this.messages.getPlayerIsNotExistsInCache());
            return;
        }

        Profile profile = this.profileService.getProfile(offlinePlayer.getUniqueId());
        profile.setupBalance(atomicBalance.get(), new BalanceOperationCallback() {
            @Override
            public void done() {
                MessageUtil.sendMessage(sender, Placeholders.replace(messages.getBalanceSuccessfullySet(),
                        ImmutableMap.of(
                                "player", Objects.requireNonNull(offlinePlayer.getName(), "OfflinePlayer name is null."),
                                "balance", atomicBalance.get(),
                                "currency", configuration.getCurrency(atomicBalance.get()))));
            }

            @Override
            public void cancel(CancelReason reason) {
                MessageUtil.sendMessage(sender, messages.getBalanceOperationParameterIsNegative());
            }
        });
    }


    @Syntax("<username> <value>")
    @Subcommand("deposit")
    @Description("Deposit money to player balance.")
    @CommandCompletion("@players 10|100|1000|10000")
    public void onAdministratorDepositMoney(CommandSender sender, String username, double value) {
        AtomicDouble atomicValue = new AtomicDouble(Double.parseDouble(EconomyPlugin.FORMAT.format(value)));

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayerIfCached(username);
        if (offlinePlayer == null) {
            MessageUtil.sendMessage(sender, this.messages.getPlayerIsNotExistsInCache());
            return;
        }

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
        });
    }


    @Syntax("<username> <value>")
    @Subcommand("withdraw")
    @Description("Withdraw money from player balance.")
    @CommandCompletion("@players 10|100|1000|10000")
    public void onAdministratorWithdrawMoney(CommandSender sender, String username, double value) {
        AtomicDouble atomicValue = new AtomicDouble(Double.parseDouble(EconomyPlugin.FORMAT.format(value)));

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayerIfCached(username);
        if (offlinePlayer == null) {
            MessageUtil.sendMessage(sender, this.messages.getPlayerIsNotExistsInCache());
            return;
        }

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
        });
    }


    @Syntax("<username>")
    @Subcommand("clear")
    @Description("Clear player balance.")
    @CommandCompletion("@players")
    public void onAdministratorClearBalance(CommandSender sender, String username) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayerIfCached(username);
        if (offlinePlayer == null) {
            MessageUtil.sendMessage(sender, this.messages.getPlayerIsNotExistsInCache());
            return;
        }

        Profile profile = this.profileService.getProfile(offlinePlayer.getUniqueId());
        profile.setupBalance(0, new BalanceOperationCallback() {
            @Override
            public void done() {
                MessageUtil.sendMessage(sender, Placeholders.replace(messages.getBalanceSuccessfullyClear(),
                        Collections.singletonMap("player", Objects.requireNonNull(offlinePlayer.getName(), "OfflinePlayer name is null."))));
            }

            @Override
            public void cancel(CancelReason reason) {
                MessageUtil.sendMessage(sender, messages.getBalanceOperationParameterIsNegative());
            }
        });
    }


    @Syntax("<username>")
    @Subcommand("check")
    @Description("Check player balance.")
    @CommandCompletion("@players")
    public void onAdministratorCheckBalance(CommandSender sender, String username) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayerIfCached(username);
        if (offlinePlayer == null) {
            MessageUtil.sendMessage(sender, this.messages.getPlayerIsNotExistsInCache());
            return;
        }

        Profile profile = this.profileService.getProfile(offlinePlayer.getUniqueId());
        MessageUtil.sendMessage(sender, Placeholders.replace(this.messages.getBalanceAdministratorCheck(),
                ImmutableMap.of(
                        "player", Objects.requireNonNull(offlinePlayer.getName(), "OfflinePlayer name is null."),
                        "balance", profile.getBalance(),
                        "currency", this.configuration.getCurrency(profile.getBalance()))));
    }
}
