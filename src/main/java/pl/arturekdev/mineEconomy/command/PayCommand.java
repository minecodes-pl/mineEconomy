package pl.arturekdev.mineEconomy.command;

import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import pl.arturekdev.mineEconomy.*;
import pl.arturekdev.mineEconomy.command.util.*;
import pl.arturekdev.mineEconomy.config.*;
import pl.arturekdev.mineEconomy.util.*;

import java.text.*;
import java.util.*;

public class PayCommand extends SubCommand {

    private final EcoMessages ecoMessages;
    private final EconomyService economyService;

    public PayCommand(String name, List<String> aliases, EcoMessages ecoMessages, EconomyService economyService) {
        super(name, aliases);
        this.ecoMessages = ecoMessages;
        this.economyService = economyService;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] arguments) {
        Player player = (Player) sender;

        if (arguments.length != 2) {
            MessageUtil.sendMessage(player, ecoMessages.payCommandUsage());
            return;
        }

        Player target = Bukkit.getPlayer(arguments[0]);

        if (target == player) {
            MessageUtil.sendMessage(player, ecoMessages.thisPayIsNotPossible());
            return;
        }

        if (target == null) {
            MessageUtil.sendMessage(player, ecoMessages.targetIsOffline());
            return;
        }

        double value;
        try {
            value = Double.parseDouble(arguments[1]);
        } catch (IllegalArgumentException e) {
            MessageUtil.sendMessage(sender, ecoMessages.valueIsNotNumber());
            return;
        }

        DecimalFormat decimalFormat = new DecimalFormat(mineEconomy.getEcoConfiguration().format());
        value = Double.parseDouble(decimalFormat.format(value));
        if (!economyService.has(player, value)) {
            MessageUtil.sendMessage(sender, ecoMessages.dontHaveFunds());
            return;
        }

        economyService.transfer(player, target, value);

        MessageUtil.sendMessage(player, ecoMessages.successSendPay().replace("%target%", target.getName()).replace("%value%", String.valueOf(value)));
        MessageUtil.sendMessage(target, ecoMessages.successReceivedPay().replace("%sender%", player.getName()).replace("%value%", String.valueOf(value)));
    }
}
