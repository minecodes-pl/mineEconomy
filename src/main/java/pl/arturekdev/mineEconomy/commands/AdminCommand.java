package pl.arturekdev.mineEconomy.commands;


import org.bukkit.command.CommandSender;
import pl.arturekdev.mineEconomy.EconomyService;
import pl.arturekdev.mineEconomy.commands.util.Command;
import pl.arturekdev.mineEconomy.managers.UserManager;
import pl.arturekdev.mineEconomy.objects.User;
import pl.arturekdev.mineEconomy.utils.MUtil;

public class AdminCommand extends Command {


    public AdminCommand() {
        super("ecoa", "Admin Economii", "/ecoa", "mineEconomy.admin", "ecoadmin");
    }

    @Override
    public boolean onExecute(CommandSender sender, String[] args) {

        if (!sender.hasPermission("mineEconomy.admin")) {
            MUtil.sendMsg(sender, " &8>> &cNie posiadasz uprawnień do tej komendy!");
            return true;
        }

        if (args.length == 0) {
            MUtil.sendMsg(sender, " &8>> &c/ecoa give <nick> <kwota> &7- Dawanie Iskier!");
            MUtil.sendMsg(sender, " &8>> &c/ecoa set <nick> <kwota> &7- Ustawianie Iskier!");
            MUtil.sendMsg(sender, " &8>> &c/ecoa info <nick> &7- Informacje i koncie gracza!");
            return true;
        }

        if (args[0].equalsIgnoreCase("give")) {
            if (args.length != 3) {
                MUtil.sendMsg(sender, " &8>> &c/ecoa give <nick> <kwota> &7- Dawanie Iskier!");
                return true;
            }


            int value;

            try {
                value = Integer.parseInt(args[2]);
            } catch (Exception e) {
                MUtil.sendMsg(sender, " &8>> &cKwota nie jest liczbą!");
                return false;
            }

            EconomyService economyService = new EconomyService();
            economyService.giveMoney(args[1], value);

            MUtil.sendMsg(sender, " &8>> &aPomyślnie dodano do konta gracza &e" + args[1] + " " + value + " Iskier!");
            return true;
        }

        if (args[0].equalsIgnoreCase("set")) {
            if (args.length != 3) {
                MUtil.sendMsg(sender, " &8>> &c/ecoa set <nick> <kwota> &7- Ustawianie Iskier!");
                return true;
            }

            int value;

            try {
                value = Integer.parseInt(args[2]);
            } catch (Exception e) {
                MUtil.sendMsg(sender, " &8>> &cKwota nie jest liczbą!");
                return false;
            }

            EconomyService economyService = new EconomyService();
            economyService.setMoney(args[1], value);

            MUtil.sendMsg(sender, " &8>> &aPomyślnie ustawiono wartość konta gracza &e" + args[1] + " &ana &e" + value + " Iskier!");
            return true;
        }

        if (args[0].equalsIgnoreCase("info")) {
            if (args.length != 2) {
                MUtil.sendMsg(sender, " &8>> &c/ecoa info <nick> &7- Informacje i koncie gracza!");
                return true;
            }

            User u = UserManager.getUser(args[1]);

            MUtil.sendMsg(sender, " &8>> &aKonto gracza &e" + u.getName() + ": " + u.getMoney() + " Iskier!");
            return true;
        }


        return false;
    }
}
