package pl.minecodes.mineeconomy.command.admin;

import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.command.CommandSender;
import pl.minecodes.mineeconomy.data.configuration.Configuration;
import pl.minecodes.mineeconomy.data.configuration.Messages;
import pl.minecodes.mineeconomy.profile.ProfileService;
import pl.minecodes.mineeconomy.util.MessageUtil;

@Section(route = "economy", aliases = {"eco", "ecoadmin"}, priority = 0)
@Permission("economy.admin")
public class EconomyCommand {

    @Inject private ProfileService profileService;
    @Inject private Configuration configuration;
    @Inject private Messages messages;

    @Execute
    public void executeHelpCommand(CommandSender sender) {
        this.messages.getEconomyAdminCommands().forEach(string -> MessageUtil.sendMessage(sender, string));
    }
}
