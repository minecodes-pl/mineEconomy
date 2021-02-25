package pl.arturekdev.mineEconomy.command;

import me.clip.placeholderapi.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import pl.arturekdev.mineEconomy.command.util.*;
import pl.arturekdev.mineEconomy.comparator.*;
import pl.arturekdev.mineEconomy.config.*;
import pl.arturekdev.mineEconomy.user.*;
import pl.arturekdev.mineEconomy.util.*;

import java.util.*;

public class RankingCommand extends SubCommand {

    private final EcoMessages ecoMessages;

    public RankingCommand(String name, List<String> aliases, String permission, EcoMessages ecoMessages) {
        super(name, aliases, permission);
        this.ecoMessages = ecoMessages;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] arguments) {
        UserService.getUsers().sort(new RankingSort());
        Player player = (Player) sender;

        for (String string : ecoMessages.rankingMessage()) {
            string = PlaceholderAPI.setPlaceholders(player, string);
            MessageUtil.sendMessage(player, string);
        }
    }
}
