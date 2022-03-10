package pl.minecodes.mineeconomy.command.argument;

import dev.rollczi.litecommands.LiteInvocation;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.SingleArgumentHandler;
import dev.rollczi.litecommands.valid.ValidationCommandException;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.HumanEntity;
import pl.minecodes.mineeconomy.data.configuration.Messages;

import java.util.List;
import java.util.stream.Collectors;

@ArgumentName("username")
public class OfflinePlayerArgument implements SingleArgumentHandler<OfflinePlayer> {

    @Inject private Messages messages;

    @Override
    public OfflinePlayer parse(LiteInvocation invocation, String argument) throws ValidationCommandException {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(argument);
        if (offlinePlayer == null) {
            throw new ValidationCommandException(this.messages.getPlayerIsNotExistsInCache());
        }

        return offlinePlayer;
    }

    @Override
    public List<String> tabulation(String command, String[] args) {
        return Bukkit.getOnlinePlayers()
                .stream()
                .map(HumanEntity::getName)
                .collect(Collectors.toList());
    }
}
