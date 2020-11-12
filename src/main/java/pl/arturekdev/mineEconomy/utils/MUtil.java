package pl.arturekdev.mineEconomy.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class MUtil {
    public static String fix(final String text) {
        return ChatColor.translateAlternateColorCodes('&',
                text.replace("<<", "«").replace(">>", "»").replace("*", "\u0fc3"));
    }

    public static void sendMsg(Player p, String msg) {
        p.sendMessage(fix(msg));
    }

    public static void sendMsg(CommandSender p, String msg) {
        p.sendMessage(fix(msg));
    }

}
