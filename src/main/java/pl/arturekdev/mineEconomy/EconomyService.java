package pl.arturekdev.mineEconomy;

import org.bukkit.entity.*;
import pl.arturekdev.mineEconomy.user.*;

import java.text.*;

public class EconomyService {

    public static boolean hasStatic(Player target, double value) {

        User user = UserService.getUser(target.getName());

        if (value < 0) {
            return false;
        }

        return user.getMoney() >= value;
    }

    public static boolean hasStatic(String target, double value) {

        User user = UserService.getUser(target);

        if (value < 0) {
            return false;
        }

        return user.getMoney() >= value;

    }

    public boolean has(Player target, double value) {

        User user = UserService.getUser(target.getName());

        if (value < 0) {
            return false;
        }

        return user.getMoney() >= value;

    }

    public boolean has(String target, double value) {

        User user = UserService.getUser(target);

        if (value < 0) {
            return false;
        }

        return user.getMoney() >= value;
    }

    public double getMoney(Player target) {

        User user = UserService.getUser(target.getName());

        return user.getMoney();

    }

    public void transfer(Player sender, Player target, double value) {

        User senderUser = UserService.getUser(sender.getName());
        User targetUser = UserService.getUser(target.getName());

        DecimalFormat decimalFormat = new DecimalFormat(mineEconomy.getEcoConfiguration().format());
        value = Double.parseDouble(decimalFormat.format(value));

        senderUser.setMoney(senderUser.getMoney() - value);
        targetUser.setMoney(targetUser.getMoney() + value);

        senderUser.setUpdate(true);
        targetUser.setUpdate(true);

    }

    public void giveMoney(Player target, double value) {

        User user = UserService.getUser(target.getName());

        DecimalFormat decimalFormat = new DecimalFormat(mineEconomy.getEcoConfiguration().format());
        value = Double.parseDouble(decimalFormat.format(value));

        user.setMoney(user.getMoney() + value);
        user.setUpdate(true);

    }

    public void giveMoney(String target, double value) {

        User user = UserService.getUser(target);

        DecimalFormat decimalFormat = new DecimalFormat(mineEconomy.getEcoConfiguration().format());
        value = Double.parseDouble(decimalFormat.format(value));

        user.setMoney(user.getMoney() + value);
        user.setUpdate(true);

    }

    public void takeMoney(Player target, double value) {

        User user = UserService.getUser(target.getName());

        DecimalFormat decimalFormat = new DecimalFormat(mineEconomy.getEcoConfiguration().format());
        value = Double.parseDouble(decimalFormat.format(value));

        if (value < 0) {
            user.setMoney(0);
            user.setUpdate(true);
            return;
        }

        user.setMoney(user.getMoney() - value);
        user.setUpdate(true);

    }

    public void takeMoney(String target, double value) {

        User user = UserService.getUser(target);

        DecimalFormat decimalFormat = new DecimalFormat(mineEconomy.getEcoConfiguration().format());
        value = Double.parseDouble(decimalFormat.format(value));

        if (value < 0) {
            user.setMoney(0);
            user.setUpdate(true);
            return;
        }

        user.setMoney(user.getMoney() - value);
        user.setUpdate(true);

    }

    public void setMoney(Player target, double value) {

        User user = UserService.getUser(target.getName());

        DecimalFormat decimalFormat = new DecimalFormat(mineEconomy.getEcoConfiguration().format());
        value = Double.parseDouble(decimalFormat.format(value));

        user.setMoney(value);
        user.setUpdate(true);

    }

    public void setMoney(String target, double value) {

        User user = UserService.getUser(target);

        DecimalFormat decimalFormat = new DecimalFormat(mineEconomy.getEcoConfiguration().format());
        value = Double.parseDouble(decimalFormat.format(value));

        user.setMoney(value);
        user.setUpdate(true);

    }


}
