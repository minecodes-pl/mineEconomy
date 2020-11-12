package pl.arturekdev.mineEconomy.commands;

import org.bukkit.command.CommandSender;
import pl.arturekdev.mineEconomy.commands.util.Command;
import pl.arturekdev.mineEconomy.comparator.Sort;
import pl.arturekdev.mineEconomy.managers.UserManager;
import pl.arturekdev.mineEconomy.utils.MUtil;

public class RankingCommand extends Command {


    public RankingCommand() {
        super("baltop", "Top najbogadszych", "/baltop", "mineEconomy.ranking", "bogacze", "najbogadsi");
    }

    @Override
    public boolean onExecute(CommandSender sender, String[] args) {
        UserManager.getUsers().sort(new Sort());
        MUtil.sendMsg(sender, " ");
        MUtil.sendMsg(sender, " &eNajbogatsze osoby:");
        MUtil.sendMsg(sender, " ");
        int top = 0;
        for (int i = 0; i < 10; i++) {
            MUtil.sendMsg(sender, " &8>> &7Top " + (top + 1) + ": &e" + UserManager.getUsers().get(top).getName() + " &7posiada &e" + UserManager.getUsers().get(top).getMoney() + " Iskier");
            top++;
        }
        return false;
    }
}
