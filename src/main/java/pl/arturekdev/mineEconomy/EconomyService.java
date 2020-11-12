package pl.arturekdev.mineEconomy;

import org.bukkit.entity.Player;
import pl.arturekdev.mineEconomy.managers.UserManager;
import pl.arturekdev.mineEconomy.objects.User;

public class EconomyService {

    public boolean has(Player target, int value) {

        User user = UserManager.getUser(target.getName());

        if (value < 0) {
            return false;
        }

        return user.getMoney() >= value;

    }

    public void transfer(Player sender, Player target, int value) {

        User senderUser = UserManager.getUser(sender.getName());
        User targetUser = UserManager.getUser(target.getName());

        senderUser.setMoney(senderUser.getMoney() - value);
        targetUser.setMoney(targetUser.getMoney() + value);

        senderUser.setUpdate(true);
        targetUser.setUpdate(true);

    }

    public void giveMoney(Player target, int value) {

        User user = UserManager.getUser(target.getName());
        user.setMoney(user.getMoney() + value);
        user.setUpdate(true);

    }

    public void giveMoney(String target, int value) {

        User user = UserManager.getUser(target);
        user.setMoney(user.getMoney() + value);
        user.setUpdate(true);

    }

    public void takeMoney(Player target, int value) {

        User user = UserManager.getUser(target.getName());
        user.setMoney(user.getMoney() - value);
        user.setUpdate(true);

    }

    public void takeMoney(String target, int value) {

        User user = UserManager.getUser(target);
        user.setMoney(user.getMoney() - value);
        user.setUpdate(true);

    }

    public void setMoney(Player target, int value) {

        User user = UserManager.getUser(target.getName());
        user.setMoney(value);
        user.setUpdate(true);

    }
    public void setMoney(String target, int value) {

        User user = UserManager.getUser(target);
        user.setMoney(value);
        user.setUpdate(true);

    }


}
