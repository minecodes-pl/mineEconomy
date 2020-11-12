// 
// Decompiled by Procyon v0.5.30
// 

package pl.arturekdev.mineEconomy.managers;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;
import pl.arturekdev.mineEconomy.commands.*;
import pl.arturekdev.mineEconomy.commands.util.Command;
import pl.arturekdev.mineEconomy.utils.Reflection;

import java.util.HashMap;

public class CommandManager {

    public static final HashMap<String, Command> commands;
    private static final Reflection.FieldAccessor<SimpleCommandMap> f;
    private static CommandMap cmdMap;

    static {
        commands = new HashMap<>();
        f = Reflection.getField(SimplePluginManager.class, "commandMap", SimpleCommandMap.class);
        CommandManager.cmdMap = CommandManager.f.get(Bukkit.getServer().getPluginManager());
    }

    private void register(final Command cmd) {
        if (CommandManager.cmdMap == null) {
            CommandManager.cmdMap = CommandManager.f.get(Bukkit.getServer().getPluginManager());
        }
        CommandManager.cmdMap.register(cmd.getName(), cmd);
        CommandManager.commands.put(cmd.getName(), cmd);
    }

    public Command getCommand(String command) {
        return commands.get(command);
    }

    public void unregisterAll() {
        for (String cmd : commands.keySet()) {
            getCommand(cmd).unregister(CommandManager.f.get(Bukkit.getServer().getPluginManager()));
        }
    }

    public void registerCommands() {

        unregisterAll();

        register(new AccountCommand());
        register(new AdminCommand());
        register(new InformationCommand());
        register(new PayCommand());
        register(new RankingCommand());
        register(new WithdrawCommand());
    }
}
