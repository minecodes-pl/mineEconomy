package pl.minecodes.mineeconomy.command.admin.sub;

import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import pl.minecodes.mineeconomy.data.configuration.Configuration;
import pl.minecodes.mineeconomy.data.configuration.Messages;
import pl.minecodes.mineeconomy.profile.Profile;
import pl.minecodes.mineeconomy.profile.ProfileService;
import pl.minecodes.mineeconomy.profile.helper.BalanceOperationCallback;
import pl.minecodes.mineeconomy.util.MessageUtil;
import pl.minecodes.mineeconomy.util.Placeholders;

import java.util.Collections;
import java.util.Objects;

@Section(route = "economy", aliases = {"eco", "ecoadmin"}, priority = 4)
@Permission("economy.admin")
public class EconomyClearCommand {

    @Inject private Messages messages;
    @Inject private ProfileService profileService;
    @Inject private Configuration configuration;

    @Execute(route = "clear", required = 1)
    public void executeClear(CommandSender sender, @Arg(0) OfflinePlayer offlinePlayer) {
        Profile profile = this.profileService.getProfile(offlinePlayer.getUniqueId());
        profile.setupBalance(0, new BalanceOperationCallback() {
            @Override
            public void done() {
                MessageUtil.sendMessage(sender, Placeholders.replace(messages.getBalanceSuccessfullyClear(),
                        Collections.singletonMap("player", Objects.requireNonNull(offlinePlayer.getName(), "OfflinePlayer name is null."))));
            }

            @Override
            public void cancel(CancelReason reason) {
                MessageUtil.sendMessage(sender, messages.getBalanceOperationParameterIsNegative());
            }
        });
    }
}
