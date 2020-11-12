package pl.arturekdev.mineEconomy.commands;

import org.bukkit.command.CommandSender;
import pl.arturekdev.mineEconomy.commands.util.Command;
import pl.arturekdev.mineEconomy.utils.MUtil;

public class InformationCommand extends Command {

    public InformationCommand() {
        super("ekonomia", "Informacj o ekonomii", "/ekonomia", "mineEconomy.info", "ecoinfo");
    }

    @Override
    public boolean onExecute(CommandSender sender, String[] args) {
        MUtil.sendMsg(sender, " &8>> &7Walutą serwer są &eIskry &7zdobywać je możesz poprzez zakupienie ich na &ewww.campfire.pl &7lub za wykonywanie zadań!");
        MUtil.sendMsg(sender, " &8>> &7Wzamian za &eIskry &7możesz robić zakupy pod &e/sklep");
        MUtil.sendMsg(sender, " &8>> &7Komendy dostępne w ekonomii:");
        MUtil.sendMsg(sender, " &8>> &7/konto - &fSprawdzanie stanu konta wirtualnego!");
        MUtil.sendMsg(sender, " &8>> &7/przelew - &fPrzelewanie wirtualnej waluty!");
        MUtil.sendMsg(sender, " &8>> &7/wyplac - &fWypłacanie wirtualnej waluty z konta!");
        MUtil.sendMsg(sender, " &8>> &7/bogacze - &fRanking 10 najbogatszych osób na serwerze!");
        return false;
    }
}
