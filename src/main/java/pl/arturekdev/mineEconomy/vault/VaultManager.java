package pl.arturekdev.mineEconomy.vault;

import net.milkbowl.vault.economy.*;
import org.bukkit.*;
import pl.arturekdev.mineEconomy.config.*;
import pl.arturekdev.mineEconomy.user.*;

import java.text.DecimalFormat;
import java.util.*;

public class VaultManager implements Economy {

    private final EcoConfiguration ecoConfiguration;
    private DecimalFormat format = new DecimalFormat("###,###,###,###.##");

    public VaultManager(EcoConfiguration ecoConfiguration) {
        this.ecoConfiguration = ecoConfiguration;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return "mineEconomy";
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return 0;
    }

    @Override
    public String format(double amount) {
        return ecoConfiguration.currencyFormat().replace("{amount}", this.format.format(amount)).replace("{currencyName}", currencyNameSingular());
    }

    @Override
    public String currencyNamePlural() {
        return ecoConfiguration.currencyName();
    }

    @Override
    public String currencyNameSingular() {
        return ecoConfiguration.currencyName();
    }

    @Override
    public boolean hasAccount(String playerName) {
        for (User user : UserService.getUsers()) {
            if (user.getName().equals(playerName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasAccount(OfflinePlayer player) {
        for (User user : UserService.getUsers()) {
            if (user.getName().equals(player.getName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasAccount(String playerName, String worldName) {
        for (User user : UserService.getUsers()) {
            if (user.getName().equals(playerName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasAccount(OfflinePlayer player, String worldName) {
        for (User user : UserService.getUsers()) {
            if (user.getName().equals(player.getName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public double getBalance(String playerName) {
        return UserService.getUser(playerName).getMoney();
    }

    @Override
    public double getBalance(OfflinePlayer player) {
        return UserService.getUser(player.getName()).getMoney();
    }

    @Override
    public double getBalance(String playerName, String world) {
        return UserService.getUser(playerName).getMoney();
    }

    @Override
    public double getBalance(OfflinePlayer player, String world) {
        return UserService.getUser(player.getName()).getMoney();
    }

    @Override
    public boolean has(String playerName, double amount) {
        User user = UserService.getUser(playerName);
        if (user.getMoney() < 0){
            user.setMoney(0);
            user.setUpdate(true);
            return false;
        }
        return user.getMoney() >= amount;
    }

    @Override
    public boolean has(OfflinePlayer player, double amount) {
        User user = UserService.getUser(player.getName());
        if (user.getMoney() < 0){
            user.setMoney(0);
            user.setUpdate(true);
            return false;
        }
        return user.getMoney() >= amount;
    }

    @Override
    public boolean has(String playerName, String worldName, double amount) {
        User user = UserService.getUser(playerName);
        if (user.getMoney() < 0){
            user.setMoney(0);
            user.setUpdate(true);
            return false;
        }
        return user.getMoney() >= amount;
    }

    @Override
    public boolean has(OfflinePlayer player, String worldName, double amount) {
        User user = UserService.getUser(player.getName());
        if (user.getMoney() < 0){
            user.setMoney(0);
            user.setUpdate(true);
            return false;
        }
        return user.getMoney() >= amount;
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, double amount) {
        User user = UserService.getUser(playerName);
        double balance = user.getMoney();
        if (amount > balance) {
            return new EconomyResponse(amount, balance, EconomyResponse.ResponseType.FAILURE, "Could not withdraw player!");
        } else {
            balance -= amount;
        }
        user.setMoney(balance);
        return new EconomyResponse(amount, balance, EconomyResponse.ResponseType.SUCCESS, "Could not withdraw player!");
    }


    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
        User user = UserService.getUser(player.getName());
        double balance = user.getMoney();
        if (amount > balance) {
            return new EconomyResponse(amount, balance, EconomyResponse.ResponseType.FAILURE, "Could not withdraw player!");
        } else {
            balance -= amount;
        }
        user.setMoney(balance);
        return new EconomyResponse(amount, balance, EconomyResponse.ResponseType.SUCCESS, "Could not withdraw player!");
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
        User user = UserService.getUser(playerName);
        double balance = user.getMoney();
        if (amount > balance) {
            return new EconomyResponse(amount, balance, EconomyResponse.ResponseType.FAILURE, "Could not withdraw player!");
        } else {
            balance -= amount;
        }
        user.setMoney(balance);
        return new EconomyResponse(amount, balance, EconomyResponse.ResponseType.SUCCESS, "Could not withdraw player!");
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount) {
        User user = UserService.getUser(player.getName());
        double balance = user.getMoney();
        if (amount > balance) {
            return new EconomyResponse(amount, balance, EconomyResponse.ResponseType.FAILURE, "Could not withdraw player!");
        } else {
            balance -= amount;
        }
        user.setMoney(balance);
        return new EconomyResponse(amount, balance, EconomyResponse.ResponseType.SUCCESS, "Could not withdraw player!");
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, double amount) {

        User user = UserService.getUser(playerName);
        double balance = user.getMoney();
        balance += amount;
        user.setMoney(balance);

        return new EconomyResponse(amount, balance, EconomyResponse.ResponseType.SUCCESS, "Could not deposit player!");
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
        User user = UserService.getUser(player.getName());
        double balance = user.getMoney();
        balance += amount;
        user.setMoney(balance);

        return new EconomyResponse(amount, balance, EconomyResponse.ResponseType.SUCCESS, "Could not deposit player!");
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
        User user = UserService.getUser(playerName);
        double balance = user.getMoney();
        balance += amount;
        user.setMoney(balance);

        return new EconomyResponse(amount, balance, EconomyResponse.ResponseType.SUCCESS, "Could not deposit player!");
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount) {
        User user = UserService.getUser(player.getName());
        double balance = user.getMoney();
        balance += amount;
        user.setMoney(balance);

        return new EconomyResponse(amount, balance, EconomyResponse.ResponseType.SUCCESS, "Could not deposit player!");
    }

    @Override
    public EconomyResponse createBank(String name, String player) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented!");
    }

    @Override
    public EconomyResponse createBank(String name, OfflinePlayer player) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented!");
    }

    @Override
    public EconomyResponse deleteBank(String name) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented!");
    }

    @Override
    public EconomyResponse bankBalance(String name) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented!");
    }

    @Override
    public EconomyResponse bankHas(String name, double amount) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented!");
    }

    @Override
    public EconomyResponse bankWithdraw(String name, double amount) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented!");
    }

    @Override
    public EconomyResponse bankDeposit(String name, double amount) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented!");
    }

    @Override
    public EconomyResponse isBankOwner(String name, String playerName) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented!");
    }

    @Override
    public EconomyResponse isBankOwner(String name, OfflinePlayer player) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented!");
    }

    @Override
    public EconomyResponse isBankMember(String name, String playerName) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented!");
    }

    @Override
    public EconomyResponse isBankMember(String name, OfflinePlayer player) {
        return new EconomyResponse(0.0D, 0.0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented!");
    }

    @Override
    public List<String> getBanks() {
        return null;
    }

    @Override
    public boolean createPlayerAccount(String playerName) {
        UserService.getUser(playerName);
        return true;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player) {
        UserService.getUser(player.getName());
        return true;
    }

    @Override
    public boolean createPlayerAccount(String playerName, String worldName) {
        UserService.getUser(playerName);
        return true;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player, String worldName) {
        UserService.getUser(player.getName());
        return true;
    }
}
