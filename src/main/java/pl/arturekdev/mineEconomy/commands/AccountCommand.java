package pl.arturekdev.mineEconomy.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.arturekdev.mineEconomy.commands.util.Command;
import pl.arturekdev.mineEconomy.managers.UserManager;
import pl.arturekdev.mineEconomy.objects.User;
import pl.arturekdev.mineEconomy.utils.MUtil;

public class AccountCommand extends Command {

    public AccountCommand() {
        super("konto", "Stan konta", "", "mineEconomy.bal", "money", "iskry", "saldo");
    }

    @Override
    public boolean onExecute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        User u = UserManager.getUser(p.getName());
        MUtil.sendMsg(p, " &8>> &7Stan twojego konta: &e" + u.getMoney() + " Iskier");
        return false;
    }
}
