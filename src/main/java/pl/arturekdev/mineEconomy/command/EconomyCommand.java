package pl.arturekdev.mineEconomy.command;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import pl.arturekdev.mineEconomy.*;
import pl.arturekdev.mineEconomy.command.util.*;
import pl.arturekdev.mineEconomy.config.*;
import pl.arturekdev.mineEconomy.user.*;
import pl.arturekdev.mineEconomy.util.*;

import java.util.*;

public class EconomyCommand implements CommandExecutor {

    private final EcoMessages ecoMessages;
    private final List<SubCommand> subCommands;

    public EconomyCommand(EcoMessages ecoMessages) {
        this.ecoMessages = ecoMessages;
        this.subCommands = new ArrayList<>();
        this.subCommands.add(new RankingCommand("ranking", Collections.singletonList("top"), null, this.ecoMessages));
        EconomyService economyService = new EconomyService();
        this.subCommands.add(new PayCommand("pay", Collections.singletonList("przelew"), this.ecoMessages, economyService));
        this.subCommands.add(new CheckCommand("check", Collections.singletonList("sprwadz"), "mineEconomy.check", this.ecoMessages));
        this.subCommands.add(new SetCommand("set", Collections.singletonList("ustaw"), "mineEconomy.set", this.ecoMessages, economyService));
        this.subCommands.add(new GiveCommand("give", Collections.singletonList("daj"), "mineEconomy.give", this.ecoMessages, economyService));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            if (sender instanceof Player) {
                User user = UserService.getUser(sender.getName());
                MessageUtil.sendMessage(sender, ecoMessages.playerWalletInfo().replace("%value%", String.valueOf(user.getMoney())));
            } else {
                for (String string : ecoMessages.commandsList()) {
                    MessageUtil.sendMessage(sender, string);
                }
            }
        } else {
            SubCommand subCommand = getSubCommand(args[0]);
            if (subCommand == null) {
                for (String string : ecoMessages.commandsList()) {
                    MessageUtil.sendMessage(sender, string);
                }
                return false;
            }
            subCommand.handleCommand(sender, Arrays.copyOfRange(args, 1, args.length));
        }

        return false;
    }

    private SubCommand getSubCommand(String name) {
        for (SubCommand subCommand : subCommands) {
            if (subCommand.getName().equalsIgnoreCase(name) || subCommand.getAliases().contains(name)) {
                return subCommand;
            }
        }
        return null;
    }

}
