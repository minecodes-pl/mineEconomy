package pl.arturekdev.mineEconomy.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import pl.arturekdev.mineEconomy.comparator.RankingSort;
import pl.arturekdev.mineEconomy.user.UserService;
import pl.arturekdev.mineEconomy.user.User;
import pl.arturekdev.mineEconomy.util.MessageUtil;

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
            User u = UserService.getUser(player.getName());
            return String.valueOf(u.getMoney());
        }

        if (identifier.contains("top_name_")) {

            String[] strings = identifier.split("top_name_");
            int integer = Integer.parseInt(strings[1]) - 1;

            UserService.getUsers().sort(new RankingSort());

            if (integer >= UserService.getUsers().size()) {
                return "Brak";
            }

            return String.valueOf(UserService.getUsers().get(integer).getName());

        }

        if (identifier.contains("top_value_")) {

            String[] strings = identifier.split("top_value_");
            int integer = Integer.parseInt(strings[1]) - 1;

            UserService.getUsers().sort(new RankingSort());

            if (integer >= UserService.getUsers().size()) {
                return "Brak";
            }

            return String.valueOf(UserService.getUsers().get(integer).getMoney());

        }

        if (player == null) {
            return "";
        }

        return null;
    }
}
