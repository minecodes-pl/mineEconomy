package pl.arturekdev.mineEconomy.command;

import org.bukkit.command.*;
import pl.arturekdev.mineEconomy.command.util.*;
import pl.arturekdev.mineEconomy.config.*;
import pl.arturekdev.mineEconomy.user.*;
import pl.arturekdev.mineEconomy.util.*;

import java.util.*;

public class CheckCommand extends SubCommand {

    private final EcoMessages ecoMessages;

    public CheckCommand(String name, List<String> aliases, String permission, EcoMessages ecoMessages) {
        super(name, aliases, permission);
        this.ecoMessages = ecoMessages;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] arguments) {

        if (!sender.hasPermission(getPermission())) {
            MessageUtil.sendMessage(sender, ecoMessages.dontHavePermission().replace("%permission%", getPermission()));
            return;
        }

        if (arguments.length != 1) {
            MessageUtil.sendMessage(sender, ecoMessages.checkCommandUsage());
            return;
        }

        User user = UserService.getUserIsPossibleNull(arguments[0]);
        if (user == null) {
            MessageUtil.sendMessage(sender, ecoMessages.userIsNull());
            return;
        }

        MessageUtil.sendMessage(sender, ecoMessages.userMoneyCheck().replace("%player%", user.getName()).replace("%value%", String.valueOf(user.getMoney())));
    }
}
