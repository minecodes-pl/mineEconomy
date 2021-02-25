package pl.arturekdev.mineEconomy.task;

import org.bukkit.*;
import org.bukkit.entity.*;
import pl.arturekdev.mineEconomy.*;
import pl.arturekdev.mineEconomy.config.*;
import pl.arturekdev.mineEconomy.user.*;
import pl.arturekdev.mineEconomy.util.*;

public class PlayTimePrizeTask implements Runnable {

    private final EcoConfiguration ecoConfiguration;
    private final EcoMessages ecoMessages;
    private final EconomyService economyService = new EconomyService();

    public PlayTimePrizeTask(EcoConfiguration ecoConfiguration, EcoMessages ecoMessages) {
        this.ecoConfiguration = ecoConfiguration;
        this.ecoMessages = ecoMessages;
    }

    @Override
    public void run() {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            User user = UserService.getUser(onlinePlayer.getName());

            if (user.getLastLimitAward() + 86400000 > System.currentTimeMillis()) {
                onlinePlayer.sendActionBar(MessageUtil.fix(ecoMessages.actionBarPlayTimePrizeLimit()));
                continue;
            }

            onlinePlayer.sendActionBar(MessageUtil.fix(ecoMessages.actionBarPlayTimePrize().replace("%value%", String.valueOf(ecoConfiguration.playTimePrize()))));
            economyService.giveMoney(onlinePlayer, ecoConfiguration.playTimePrize());
            user.setMoneyFromAwards(user.getMoneyFromAwards() + ecoConfiguration.playTimePrize());

            if (user.getMoneyFromAwards() == ecoConfiguration.limitMoneyPerDay()) {
                user.setLastLimitAward(System.currentTimeMillis());
            }
        }
    }
}
