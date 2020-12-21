package pl.arturekdev.mineEconomy.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import pl.arturekdev.mineEconomy.comparator.RankingSort;
import pl.arturekdev.mineEconomy.managers.UserManager;
import pl.arturekdev.mineEconomy.objects.User;
import pl.arturekdev.mineEconomy.utils.MUtil;

import java.util.List;

public class PlaceholderExpansionEco extends PlaceholderExpansion {

    @Override
    public String getIdentifier() {
        return "mineEco";
    }

    @Override
    public String getAuthor() {
        return "_arturek";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    public String onPlaceholderRequest(Player player, String identifier) {

        if (identifier.equalsIgnoreCase("money")) {
            User u = UserManager.getUser(player.getName());
            return String.valueOf(u.getMoney());
        }

        if (identifier.contains("top_name_")) {

            String[] strings = identifier.split("top_name_");
            int integer = Integer.parseInt(strings[1]) - 1;

            UserManager.getUsers().sort(new RankingSort());

            if (integer >= UserManager.getUsers().size()) {
                return "Brak";
            }

            return String.valueOf(UserManager.getUsers().get(integer).getName());

        }

        if (identifier.contains("top_value_")) {

            String[] strings = identifier.split("top_value_");
            int integer = Integer.parseInt(strings[1]) - 1;

            UserManager.getUsers().sort(new RankingSort());

            if (integer >= UserManager.getUsers().size()) {
                return "Brak";
            }

            return String.valueOf(UserManager.getUsers().get(integer).getMoney());

        }

        if (identifier.contains("format_")) {
            String[] strings = identifier.split("format_");
            int integer = Integer.parseInt(strings[1]) - 1;

            List<User> users = UserManager.getUsers();

            if (integer >= users.size()) {
                return MUtil.fix("&cBrak");
            }

            users.sort(new RankingSort());

            User user = users.get(integer);

            return MUtil.fix("&e%name% &8⇒ &f%money% &eIskier").replace("%name%", user.getName()).replace("%money%", String.valueOf(user.getMoney()));
        }


        if (player == null) {
            return "";
        }


        return null;
    }
}
