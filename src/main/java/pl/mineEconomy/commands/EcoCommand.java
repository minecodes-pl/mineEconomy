package pl.mineEconomy.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.mineEconomy.comparator.Sort;
import pl.mineEconomy.managers.GlobalManager;
import pl.mineEconomy.managers.UserManager;
import pl.mineEconomy.objects.User;
import pl.mineEconomy.utils.Util;

public class EcoCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (!(s instanceof Player)) {
            Util.sendMsg(s, " &8>> &cKomenda jest przeznaczona dla graczy!");
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("ekonomia")) {
            Util.sendMsg(s, " &8>> &7Walutą serwer są &eIskry &7zdobywać je możesz poprzez zakupienie ich na &ewww.campfire.pl &7lub za wykonywanie zadań!");
            Util.sendMsg(s, " &8>> &7Wzamian za &eIskry &7możesz robić zakupy pod &e/sklep");
            Util.sendMsg(s, " &8>> &7Komendy dostępne w ekonomii:");
            Util.sendMsg(s, " &8>> &7/konto - &fSprawdzanie stanu konta wirtualnego!");
            Util.sendMsg(s, " &8>> &7/przelew - &fPrzelewanie wirtualnej waluty!");
            Util.sendMsg(s, " &8>> &7/wyplac - &fWypłacanie wirtualnej waluty z konta!");
            Util.sendMsg(s, " &8>> &7/wplac - &fWplacanie waluty na wirtualne konto!");
            Util.sendMsg(s, " &8>> &7/bogacze - &fRanking 10 najbogatszych osób na serwerze!");
        }

        if (cmd.getName().equalsIgnoreCase("bogacze")) {
            UserManager.getUsers().sort(new Sort());
            Util.sendMsg(s, " ");
            Util.sendMsg(s, " &eNajbogatsze osoby:");
            Util.sendMsg(s, " ");
            int top = 0;
            for (int i = 0; i < 10; i++) {
                Util.sendMsg(s, " &8>> &7Top " + (top + 1) + ": &e" + UserManager.getUsers().get(top).getName() + " &7posiada &e" + UserManager.getUsers().get(top).getMoney() + " Iskier");
                top++;
            }
        }
        if (cmd.getName().equalsIgnoreCase("konto")) {
            Player p = (Player) s;
            User u = UserManager.getUser(p.getName());
            if (args.length != 0) {
                Util.sendMsg(p, "Poprawne użycie /konto");
                return true;
            }
            Util.sendMsg(p, " &8>> &7Stan twojego konta: &e" + u.getMoney() + " Iskier");
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("przelew")) {
            Player p = (Player) s;
            User u = UserManager.getUser(p.getName());
            if (args.length != 2) {
                Util.sendMsg(p, " &8>> &cPoprawne użycie &e/przelew <nick> <kwota>");
                return true;
            }
            Player pa = Bukkit.getPlayer(args[0]);

            if (pa == p) {
                Util.sendMsg(p, " &8>> &cTen przelew jest niewykonalny!");
                return true;
            }

            if (pa == null) {
                Util.sendMsg(p, " &8>> &cPodany gracz jest offline!");
                return true;
            }

            if (!GlobalManager.isNumber(args[1])) {
                Util.sendMsg(p, " &8>> &cPodana kwota nie jest liczbą!");
                return true;
            }

            int sendMoney = Integer.parseInt(args[1]);
            User ua = UserManager.getUser(pa.getName());

            if (!GlobalManager.haveMoney(u, sendMoney)) {
                Util.sendMsg(p, " &8>> &cNie posiadasz wystarczjąco środków aby wykonać ten przelew!");
                return true;
            }

            u.removeMoney(sendMoney);
            ua.addMoney(sendMoney);
            u.setUpdate(true);
            ua.setUpdate(true);

            Util.sendMsg(p, " &8>> &aPrzlew do gracza &e" + ua.getName() + " &aw wysokości &e" + sendMoney + " Iskier &azostał pomyślnie zlecony!");
            Util.sendMsg(pa, " &8>> &aOtrzymałeś przelew od gracza &e" + u.getName() + " &aw wysokości &e" + sendMoney + " Iskier");
            return true;

        }
        if (cmd.getName().equalsIgnoreCase("wyplac")) {
            Player p = (Player) s;
            User u = UserManager.getUser(p.getName());
            if (args.length != 1) {
                Util.sendMsg(p, " &8>> &cPoprawne użycie &e/wyplac <kwota>");
                return true;
            }

            if (!GlobalManager.isNumber(args[0])) {
                Util.sendMsg(p, " &8>> &cPodana kwota nie jest liczbą!");
                return true;
            }

            int sendMoney = Integer.valueOf(args[0]);

            if (!GlobalManager.haveMoney(u, sendMoney)) {
                Util.sendMsg(p, " &8>> &cNie posiadasz wystarczjąco środków aby wykonać ten przelew!");
                return true;
            }

            u.removeMoney(sendMoney);
            u.setUpdate(true);
            GlobalManager.giveItem(p, GlobalManager.getItemStack(sendMoney));
            Util.sendMsg(p, " &8>> &aWypłata &e" + sendMoney + " Iskier &azostała zrealizowana pomyślnie!");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("wplac")) {
            Player p = (Player) s;
            User u = UserManager.getUser(p.getName());

            if (args.length != 0) {
                Util.sendMsg(p, " &8>> &cPoprawne użycie &e/wplac");
                return true;
            }

            if (!GlobalManager.isSimilar(p.getInventory().getItemInMainHand(), GlobalManager.getItemStack())) {
                Util.sendMsg(p, " &8>> &cPrzedmiot który trzymasz w ręcę nie jest walutą!");
                return true;
            }

            int sendMoney = p.getInventory().getItemInMainHand().getAmount();

            u.addMoney(sendMoney);
            u.setUpdate(true);
            p.getInventory().removeItem(GlobalManager.getItemStack(sendMoney));
            Util.sendMsg(p, " &8>> &aWpłata &e" + sendMoney + " Iskier &azostała zrealizowana pomyślnie!");
            return true;
        }


        return false;
    }

}
