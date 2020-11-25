package pl.arturekdev.mineEconomy.commands;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.arturekdev.mineEconomy.commands.util.Command;
import pl.arturekdev.mineEconomy.comparator.RankingSort;
import pl.arturekdev.mineEconomy.managers.UserManager;
import pl.arturekdev.mineEconomy.utils.MUtil;

public class RankingCommand extends Command {


    public RankingCommand() {
        super("baltop", "Top najbogadszych", "/baltop", "mineEconomy.ranking", "bogacze", "najbogadsi");
    }

    @Override
    public boolean onExecute(CommandSender sender, String[] args) {
        UserManager.getUsers().sort(new RankingSort());
        MUtil.sendMsg(sender, " ");
        MUtil.sendMsg(sender, " &eNajbogatsze osoby:");
        MUtil.sendMsg(sender, " ");
        int top = 0;
        Player player = (Player) sender;
        for (int i = 0; i < 10; i++) {
            PlaceholderAPI.setPlaceholders(player, "&8#{top} &e%mineEco_top_name_{top}% &6>> %mineEco_top_value_{top}% &eIskier".replace("{top}", String.valueOf(i)));
            top++;
        }
        return false;
    }
}
