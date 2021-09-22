package pl.arturekdev.mineEconomy.command;

import org.bukkit.command.*;
import pl.arturekdev.mineEconomy.*;
import pl.arturekdev.mineEconomy.command.util.*;
import pl.arturekdev.mineEconomy.config.*;
import pl.arturekdev.mineEconomy.user.*;
import pl.arturekdev.mineEconomy.util.*;

import java.text.*;
import java.util.*;

public class GiveCommand extends SubCommand {

    private final EcoMessages ecoMessages;
    private final EconomyService economyService;

    public GiveCommand(String name, List<String> aliases, String permission, EcoMessages ecoMessages, EconomyService economyService) {
        super(name, aliases, permission);
        this.ecoMessages = ecoMessages;
        this.economyService = economyService;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] arguments) {

        if (!sender.hasPermission(getPermission())) {
            MessageUtil.sendMessage(sender, ecoMessages.dontHavePermission().replace("%permission%", getPermission()));
            return;
        }

        if (arguments.length != 2) {
            MessageUtil.sendMessage(sender, ecoMessages.giveCommandUsage());
            return;
        }

        User user = UserService.getUserIsPossibleNull(arguments[0]);
        if (user == null) {
            MessageUtil.sendMessage(sender, ecoMessages.userIsNull());
            return;
        }

        double value;
        try {
            value = Double.parseDouble(arguments[1]);
        } catch (IllegalArgumentException e) {
            MessageUtil.sendMessage(sender, ecoMessages.valueIsNotNumber());
            return;
        }

        DecimalFormat decimalFormat = new DecimalFormat(EconomyPlugin.getEcoConfiguration().format());
        value = Double.parseDouble(decimalFormat.format(value));
        economyService.giveMoney(user.getName(), value);

        MessageUtil.sendMessage(sender, ecoMessages.successGiveMoney().replace("%player%", user.getName()).replace("%value%", String.valueOf(value)));
    }
}
