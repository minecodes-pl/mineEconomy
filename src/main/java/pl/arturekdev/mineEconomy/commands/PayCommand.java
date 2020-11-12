package pl.arturekdev.mineEconomy.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.arturekdev.mineEconomy.EconomyService;
import pl.arturekdev.mineEconomy.commands.util.Command;
import pl.arturekdev.mineEconomy.managers.UserManager;
import pl.arturekdev.mineEconomy.objects.User;
import pl.arturekdev.mineEconomy.utils.MUtil;

public class PayCommand extends Command {


    public PayCommand() {
        super("pay", "przelewy", "/przelew <ilość> <nick>", "mineEconomy.pay", "przelew", "przelej");
    }

    @Override
    public boolean onExecute(CommandSender sender, String[] args) {

        Player p = (Player) sender;

        if (args.length != 2) {
            MUtil.sendMsg(p, " &8>> &cPoprawne użycie &e/przelew <nick> <kwota>");
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == p) {
            MUtil.sendMsg(p, " &8>> &cTen przelew jest niewykonalny!");
            return false;
        }

        if (target == null) {
            MUtil.sendMsg(p, " &8>> &cPodany gracz jest offline!");
            return false;
        }

        int value;

        try {
            value = Integer.parseInt(args[1]);
        } catch (Exception e) {
            MUtil.sendMsg(p, " &8>> &cPodana kwota nie jest liczbą!");
            return false;
        }

        EconomyService economyService = new EconomyService();

        if (!economyService.has(p, value)) {
            MUtil.sendMsg(sender, " &8>> &cNie posiadasz wystarczjąco środków aby wykonać ten przelew!");
            return false;
        }

        economyService.transfer(p, target, value);

        MUtil.sendMsg(p, " &8>> &aPrzlew do gracza &e" + target.getName() + " &aw wysokości &e" + value + " Iskier &azostał pomyślnie zlecony!");
        MUtil.sendMsg(target, " &8>> &aOtrzymałeś przelew od gracza &e" + p.getName() + " &aw wysokości &e" + value + " Iskier");
        return false;
    }
}
