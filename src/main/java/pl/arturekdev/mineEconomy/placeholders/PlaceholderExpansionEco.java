package pl.arturekdev.mineEconomy.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import pl.arturekdev.mineEconomy.comparator.RankingSort;
import pl.arturekdev.mineEconomy.managers.UserManager;
import pl.arturekdev.mineEconomy.objects.User;

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

            User user = UserManager.getUsers().get(integer);

            if (user == null) {
                return "Brak";
            }

            return String.valueOf(user.getName());

        }

        if (identifier.contains("top_value_")) {

            String[] strings = identifier.split("top_value_");
            int integer = Integer.parseInt(strings[1]) - 1;

            UserManager.getUsers().sort(new RankingSort());

            User user = UserManager.getUsers().get(integer);

            if (user == null) {
                return "Brak";
            }

            return String.valueOf(user.getMoney());

        }


        if (player == null) {
            return "";
        }


        return null;
    }
}
