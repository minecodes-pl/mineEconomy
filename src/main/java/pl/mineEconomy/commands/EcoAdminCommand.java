package pl.mineEconomy.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import pl.mineEconomy.managers.GlobalManager;
import pl.mineEconomy.managers.UserManager;
import pl.mineEconomy.objects.User;
import pl.mineEconomy.utils.Util;

public class EcoAdminCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {

        if (!s.hasPermission("mineEconomy.admin")){
            Util.sendMsg(s, " &8>> &cNie posiadasz uprawnień do tej komendy!");
            return true;
        }

        if (args.length == 0) {
            Util.sendMsg(s, " &8>> &c/ecoa give <nick> <kwota> &7- Dawanie Iskier!");
            Util.sendMsg(s, " &8>> &c/ecoa set <nick> <kwota> &7- Ustawianie Iskier!");
            Util.sendMsg(s, " &8>> &c/ecoa info <nick> &7- Informacje i koncie gracza!");
            return true;
        }

        if (args[0].equalsIgnoreCase("give")) {
            if (args.length != 3) {
                Util.sendMsg(s, " &8>> &c/ecoa give <nick> <kwota> &7- Dawanie Iskier!");
                return true;
            }

            if (!GlobalManager.isNumber(args[2])) {
                Util.sendMsg(s, " &8>> &cKwota nie jest liczbą!");
                return true;
            }

            int i = Integer.parseInt(args[2]);
            User u = UserManager.getUser(args[1]);

            u.addMoney(i);
            Util.sendMsg(s, " &8>> &aPomyślnie dodano do konta gracza &e" + u.getName() + " " + i + " Iskier!");
            return true;
        }

        if (args[0].equalsIgnoreCase("set")) {
            if (args.length != 3) {
                Util.sendMsg(s, " &8>> &c/ecoa set <nick> <kwota> &7- Ustawianie Iskier!");
                return true;
            }

            if (!GlobalManager.isNumber(args[2])) {
                Util.sendMsg(s, " &8>> &cKwota nie jest liczbą!");
                return true;
            }

            int i = Integer.parseInt(args[2]);
            User u = UserManager.getUser(args[1]);

            u.setMoney(i);
            Util.sendMsg(s, " &8>> &aPomyślnie ustawiono wartość konta gracza &e" + u.getName() + " &ana &e" + i + " Iskier!");
            return true;
        }

        if (args[0].equalsIgnoreCase("info")) {
            if (args.length != 2) {
                Util.sendMsg(s, " &8>> &c/ecoa info <nick> &7- Informacje i koncie gracza!");
                return true;
            }

            User u = UserManager.getUser(args[1]);

            Util.sendMsg(s, " &8>> &aKonto gracza &e" + u.getName() + ": " + u.getMoney() + " Iskier!");
            return true;
        }


        return false;
    }

}
