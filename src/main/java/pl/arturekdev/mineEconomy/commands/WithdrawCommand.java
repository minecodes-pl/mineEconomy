package pl.arturekdev.mineEconomy.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.arturekdev.mineEconomy.EconomyService;
import pl.arturekdev.mineEconomy.commands.util.Command;
import pl.arturekdev.mineEconomy.utils.ItemUtil;
import pl.arturekdev.mineEconomy.mineEconomy;
import pl.arturekdev.mineEconomy.utils.MUtil;

public class WithdrawCommand extends Command {


    public WithdrawCommand() {
        super("withdraw", "Wypłacanie sakiewki iskier", "/wyplac <ilość>", "mineEconomy.withdraw", "wyplac");
    }

    @Override
    public boolean onExecute(CommandSender sender, String[] args) {
        Player p = (Player) sender;

        if (args.length != 1) {
            MUtil.sendMsg(p, " &8>> &cPoprawne użycie &e/wyplac <kwota>");
            return false;
        }

        int value;

        try {
            value = Integer.parseInt(args[0]);
        } catch (Exception e) {
            MUtil.sendMsg(p, " &8>> &cPodana kwota nie jest liczbą!");
            return false;
        }

        EconomyService economyService = new EconomyService();

        if (!economyService.has(p, value)) {
            MUtil.sendMsg(p, " &8>> &cNie posiadasz wystarczjąco środków aby wykonać ten przelew!");
            return false;
        }

        economyService.takeMoney(p, value);

        ItemUtil.giveItem(p, mineEconomy.getInstance().getPurse(value));

        MUtil.sendMsg(p, " &8>> &aWypłata sakiewki o wartości &e" + value + " Iskier &azostała zrealizowana pomyślnie!");
        return false;
    }
}
