package pl.arturekdev.mineEconomy.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
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

        if (player == null) {
            return "";
        }


        return null;
    }
}
