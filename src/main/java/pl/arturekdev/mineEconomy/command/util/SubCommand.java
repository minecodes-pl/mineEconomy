package pl.arturekdev.mineEconomy.command.util;

import lombok.*;
import org.bukkit.command.*;

import java.util.*;

@Data
public abstract class SubCommand {

    private final String name;
    private final List<String> aliases;
    private final String permission;

    public SubCommand(String name, List<String> aliases, String permission) {
        this.name = name;
        this.aliases = aliases;
        this.permission = permission;
    }

    public SubCommand(String name, List<String> aliases) {
        this.name = name;
        this.aliases = aliases;
        this.permission = null;
    }

    public SubCommand(String name) {
        this(name, Collections.emptyList(), null);
    }

    public abstract void handleCommand(CommandSender sender, String[] arguments);
}
