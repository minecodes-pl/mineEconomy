package pl.minecodes.mineeconomy.util;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Collections;

public class MessageUtil {

    private static final Pattern HEX_REGEX = Pattern.compile("#[a-fA-F0-9]{6}");

    private MessageUtil() {
    }

    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(implementColors(message));
    }

    public static void sendMessage(Player player, String message) {
        player.sendMessage(implementColors(message));
    }

    public static String implementColors(String message) {
        if (message == null || message.isEmpty()) {
            return "";
        }
        for (Matcher matcher = HEX_REGEX.matcher(message); matcher.find(); matcher = HEX_REGEX.matcher(message)) {
            String color = message.substring(matcher.start(), matcher.end());
            message = message.replace(color, ChatColor.of(color) + "");
        }

        return ChatColor.translateAlternateColorCodes('&', message.replace("<<", "«").replace(">>", "»"));
    }

    public static List<String> implementColors(List<String> message) {
        if (message == null || message.isEmpty()) {
            return Collections.emptyList();
        }
        
        //TODO 06/11/2021: Naprawić syf kod
        List<String> messages = new ArrayList<>();
        message.forEach(s -> messages.add(implementColors(s)));
        return messages;
    }

    public static String removeColor(String message) {
        return ChatColor.stripColor(message);
    }

}
