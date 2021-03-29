package pl.arturekdev.mineEconomy.vault;

import net.milkbowl.vault.economy.*;
import org.bukkit.*;
import pl.arturekdev.mineEconomy.*;
import pl.arturekdev.mineEconomy.config.*;
import pl.arturekdev.mineEconomy.user.*;

import java.util.*;

public class VaultManager implements Economy {

    private final EconomyService economyService = new EconomyService();
    private final EcoConfiguration ecoConfiguration;

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
        return "Błędny format kwoty.";
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
        return EconomyService.hasStatic(playerName, amount);
    }

    @Override
    public boolean has(OfflinePlayer player, double amount) {
        return EconomyService.hasStatic(player.getName(), amount);
    }

    @Override
    public boolean has(String playerName, String worldName, double amount) {
        return EconomyService.hasStatic(playerName, amount);
    }

    @Override
    public boolean has(OfflinePlayer player, String worldName, double amount) {
        return economyService.has(player.getName(), amount);
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, double amount) {
        if (has(playerName, amount)) {
            economyService.takeMoney(playerName, amount);
            return new EconomyResponse(amount, UserService.getUser(playerName).getMoney(), EconomyResponse.ResponseType.SUCCESS, "Brak możliwości pobrania pieniędzy.");
        } else {
            return new EconomyResponse(amount, UserService.getUser(playerName).getMoney(), EconomyResponse.ResponseType.FAILURE, "Brak możliwości pobrania pieniędzy.");
        }
    }


    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
        if (has(player, amount)) {
            economyService.takeMoney(player.getName(), amount);
            return new EconomyResponse(amount, UserService.getUser(player.getName()).getMoney(), EconomyResponse.ResponseType.SUCCESS, "Brak możliwości pobrania pieniędzy.");
        } else {
            return new EconomyResponse(amount, UserService.getUser(player.getName()).getMoney(), EconomyResponse.ResponseType.FAILURE, "Brak możliwości pobrania pieniędzy.");
        }
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
        if (has(playerName, amount)) {
            economyService.takeMoney(playerName, amount);
            return new EconomyResponse(amount, UserService.getUser(playerName).getMoney(), EconomyResponse.ResponseType.SUCCESS, "Brak możliwości pobrania pieniędzy.");
        } else {
            return new EconomyResponse(amount, UserService.getUser(playerName).getMoney(), EconomyResponse.ResponseType.FAILURE, "Brak możliwości pobrania pieniędzy.");
        }
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount) {
        if (has(player.getName(), amount)) {
            economyService.takeMoney(player.getName(), amount);
            return new EconomyResponse(amount, UserService.getUser(player.getName()).getMoney(), EconomyResponse.ResponseType.SUCCESS, "Brak możliwości pobrania pieniędzy.");
        } else {
            return new EconomyResponse(amount, UserService.getUser(player.getName()).getMoney(), EconomyResponse.ResponseType.FAILURE, "Brak możliwości pobrania pieniędzy.");
        }
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, double amount) {
        economyService.giveMoney(playerName, (int) amount);
        return new EconomyResponse(amount, UserService.getUser(playerName).getMoney(), EconomyResponse.ResponseType.SUCCESS, "Brak możliwości dodania pieniędzy.");
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
        economyService.giveMoney(player.getName(), (int) amount);
        return new EconomyResponse(amount, UserService.getUser(player.getName()).getMoney(), EconomyResponse.ResponseType.SUCCESS, "Brak możliwości dodania pieniędzy.");
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
        economyService.giveMoney(playerName, (int) amount);
        return new EconomyResponse(amount, UserService.getUser(playerName).getMoney(), EconomyResponse.ResponseType.SUCCESS, "Brak możliwości dodania pieniędzy.");
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount) {
        economyService.giveMoney(player.getName(), (int) amount);
        return new EconomyResponse(amount, UserService.getUser(player.getName()).getMoney(), EconomyResponse.ResponseType.SUCCESS, "Brak możliwości dodania pieniędzy.");
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
