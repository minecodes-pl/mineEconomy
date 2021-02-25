package pl.arturekdev.mineEconomy.util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class MessageUtil {
    public static String fix(final String text) {
        return ChatColor.translateAlternateColorCodes('&',
                text.replace("<<", "«").replace(">>", "»").replace("*", "\u0fc3"));
    }

    public static void sendMessage(Player p, String msg) {
        p.sendMessage(fix(msg));
    }

    public static void sendMessage(CommandSender p, String msg) {
        p.sendMessage(fix(msg));
    }

}
