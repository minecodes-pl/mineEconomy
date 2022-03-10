package pl.minecodes.mineeconomy.hook.vault;

import eu.okaeri.injector.annotation.Inject;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import pl.minecodes.mineeconomy.EconomyPlugin;
import pl.minecodes.mineeconomy.data.configuration.Configuration;
import pl.minecodes.mineeconomy.profile.Profile;
import pl.minecodes.mineeconomy.profile.ProfileService;
import pl.minecodes.mineeconomy.profile.helper.BalanceOperationCallback;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class VaultManager implements Economy {

    @Inject private Configuration configuration;
    @Inject private ProfileService profileService;

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
    public String format(double value) {
        switch (this.configuration.getCurrencyPositionVault()) {
            case AHEAD:
                return this.configuration.getCurrency(value) + EconomyPlugin.FORMAT.format(value);
            case BEHIND:
                return EconomyPlugin.FORMAT.format(value) + this.configuration.getCurrency(value);
        }
        return this.configuration.getCurrency(value) + EconomyPlugin.FORMAT.format(value);
    }

    @Override
    public String currencyNamePlural() {
        return this.configuration.getCurrency(0);
    }

    @Override
    public String currencyNameSingular() {
        return this.configuration.getCurrency(0);
    }

    @Override
    public boolean hasAccount(String username) {
        return true;
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer) {
        return true;
    }

    @Override
    public boolean hasAccount(String s, String s1) {
        return true;
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer, String s) {
        return true;
    }

    @Override
    public double getBalance(String username) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(username);
        if (offlinePlayer == null) return 0;

        Profile profile = this.profileService.getProfile(offlinePlayer.getUniqueId());
        return profile.getBalance();
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer) {
        Profile profile = this.profileService.getProfile(offlinePlayer.getUniqueId());
        return profile.getBalance();
    }

    @Override
    public double getBalance(String username, String world) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(username);
        if (offlinePlayer == null) return 0;

        Profile profile = this.profileService.getProfile(offlinePlayer.getUniqueId());
        return profile.getBalance();
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer, String world) {
        Profile profile = this.profileService.getProfile(offlinePlayer.getUniqueId());
        return profile.getBalance();
    }

    @Override
    public boolean has(String username, double value) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(username);
        if (offlinePlayer == null) return false;

        Profile profile = this.profileService.getProfile(offlinePlayer.getUniqueId());
        return profile.has(value);
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, double value) {
        Profile profile = this.profileService.getProfile(offlinePlayer.getUniqueId());
        return profile.has(value);
    }

    @Override
    public boolean has(String username, String world, double value) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(username);
        if (offlinePlayer == null) return false;

        Profile profile = this.profileService.getProfile(offlinePlayer.getUniqueId());
        return profile.has(value);
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, String world, double value) {
        Profile profile = this.profileService.getProfile(offlinePlayer.getUniqueId());
        return profile.has(value);
    }

    @Override
    public EconomyResponse withdrawPlayer(String username, double value) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(username);
        if (offlinePlayer == null) {
            return new EconomyResponse(value, 0, EconomyResponse.ResponseType.FAILURE, "OfflinePlayer is null.");
        }

        Profile profile = this.profileService.getProfile(offlinePlayer.getUniqueId());

        AtomicReference<EconomyResponse> economyResponse = new AtomicReference<>();
        profile.withdraw(value, new BalanceOperationCallback() {
            @Override
            public void done() {
                economyResponse.set(new EconomyResponse(value, profile.getBalance(), EconomyResponse.ResponseType.SUCCESS, "Successfully withdraw."));
            }

            @Override
            public void cancel(CancelReason reason) {
                economyResponse.set(new EconomyResponse(value, profile.getBalance(), EconomyResponse.ResponseType.FAILURE, reason.toString()));
            }
        });
        return economyResponse.get();
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, double value) {
        Profile profile = this.profileService.getProfile(offlinePlayer.getUniqueId());

        AtomicReference<EconomyResponse> economyResponse = new AtomicReference<>();
        profile.withdraw(value, new BalanceOperationCallback() {
            @Override
            public void done() {
                economyResponse.set(new EconomyResponse(value, profile.getBalance(), EconomyResponse.ResponseType.SUCCESS, "Successfully withdraw."));
            }

            @Override
            public void cancel(CancelReason reason) {
                economyResponse.set(new EconomyResponse(value, profile.getBalance(), EconomyResponse.ResponseType.FAILURE, reason.toString()));
            }
        });
        return economyResponse.get();
    }

    @Override
    public EconomyResponse withdrawPlayer(String username, String world, double value) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(username);
        if (offlinePlayer == null) {
            return new EconomyResponse(value, 0, EconomyResponse.ResponseType.FAILURE, "OfflinePlayer is null.");
        }

        Profile profile = this.profileService.getProfile(offlinePlayer.getUniqueId());

        AtomicReference<EconomyResponse> economyResponse = new AtomicReference<>();
        profile.withdraw(value, new BalanceOperationCallback() {
            @Override
            public void done() {
                economyResponse.set(new EconomyResponse(value, profile.getBalance(), EconomyResponse.ResponseType.SUCCESS, "Successfully withdraw."));
            }

            @Override
            public void cancel(CancelReason reason) {
                economyResponse.set(new EconomyResponse(value, profile.getBalance(), EconomyResponse.ResponseType.FAILURE, reason.toString()));
            }
        });
        return economyResponse.get();
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, String username, double value) {
        Profile profile = this.profileService.getProfile(offlinePlayer.getUniqueId());

        AtomicReference<EconomyResponse> economyResponse = new AtomicReference<>();
        profile.withdraw(value, new BalanceOperationCallback() {
            @Override
            public void done() {
                economyResponse.set(new EconomyResponse(value, profile.getBalance(), EconomyResponse.ResponseType.SUCCESS, "Successfully withdraw."));
            }

            @Override
            public void cancel(CancelReason reason) {
                economyResponse.set(new EconomyResponse(value, profile.getBalance(), EconomyResponse.ResponseType.FAILURE, reason.toString()));
            }
        });
        return economyResponse.get();
    }

    @Override
    public EconomyResponse depositPlayer(String username, double value) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(username);
        if (offlinePlayer == null) {
            return new EconomyResponse(value, 0, EconomyResponse.ResponseType.FAILURE, "OfflinePlayer is null.");
        }

        Profile profile = this.profileService.getProfile(offlinePlayer.getUniqueId());

        AtomicReference<EconomyResponse> economyResponse = new AtomicReference<>();
        profile.deposit(value, new BalanceOperationCallback() {
            @Override
            public void done() {
                economyResponse.set(new EconomyResponse(value, profile.getBalance(), EconomyResponse.ResponseType.SUCCESS, "Successfully deposit."));
            }

            @Override
            public void cancel(CancelReason reason) {
                economyResponse.set(new EconomyResponse(value, profile.getBalance(), EconomyResponse.ResponseType.FAILURE, reason.toString()));
            }
        });
        return economyResponse.get();
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, double value) {
        Profile profile = this.profileService.getProfile(offlinePlayer.getUniqueId());

        AtomicReference<EconomyResponse> economyResponse = new AtomicReference<>();
        profile.deposit(value, new BalanceOperationCallback() {
            @Override
            public void done() {
                economyResponse.set(new EconomyResponse(value, profile.getBalance(), EconomyResponse.ResponseType.SUCCESS, "Successfully deposit."));
            }

            @Override
            public void cancel(CancelReason reason) {
                economyResponse.set(new EconomyResponse(value, profile.getBalance(), EconomyResponse.ResponseType.FAILURE, reason.toString()));
            }
        });
        return economyResponse.get();
    }

    @Override
    public EconomyResponse depositPlayer(String username, String world, double value) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(username);
        if (offlinePlayer == null) {
            return new EconomyResponse(value, 0, EconomyResponse.ResponseType.FAILURE, "OfflinePlayer is null.");
        }

        Profile profile = this.profileService.getProfile(offlinePlayer.getUniqueId());

        AtomicReference<EconomyResponse> economyResponse = new AtomicReference<>();
        profile.deposit(value, new BalanceOperationCallback() {
            @Override
            public void done() {
                economyResponse.set(new EconomyResponse(value, profile.getBalance(), EconomyResponse.ResponseType.SUCCESS, "Successfully deposit."));
            }

            @Override
            public void cancel(CancelReason reason) {
                economyResponse.set(new EconomyResponse(value, profile.getBalance(), EconomyResponse.ResponseType.FAILURE, reason.toString()));
            }
        });
        return economyResponse.get();
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, String world, double value) {
        Profile profile = this.profileService.getProfile(offlinePlayer.getUniqueId());

        AtomicReference<EconomyResponse> economyResponse = new AtomicReference<>();
        profile.deposit(value, new BalanceOperationCallback() {
            @Override
            public void done() {
                economyResponse.set(new EconomyResponse(value, profile.getBalance(), EconomyResponse.ResponseType.SUCCESS, "Successfully deposit."));
            }

            @Override
            public void cancel(CancelReason reason) {
                economyResponse.set(new EconomyResponse(value, profile.getBalance(), EconomyResponse.ResponseType.FAILURE, reason.toString()));
            }
        });
        return economyResponse.get();
    }

    @Override
    public EconomyResponse createBank(String username, String world) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented.");
    }

    @Override
    public EconomyResponse createBank(String username, OfflinePlayer offlinePlayer) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented.");
    }

    @Override
    public EconomyResponse deleteBank(String username) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented.");
    }

    @Override
    public EconomyResponse bankBalance(String username) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented.");
    }

    @Override
    public EconomyResponse bankHas(String username, double value) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented.");
    }

    @Override
    public EconomyResponse bankWithdraw(String username, double value) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented.");
    }

    @Override
    public EconomyResponse bankDeposit(String username, double value) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented.");
    }

    @Override
    public EconomyResponse isBankOwner(String username, String world) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented.");
    }

    @Override
    public EconomyResponse isBankOwner(String username, OfflinePlayer offlinePlayer) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented.");
    }

    @Override
    public EconomyResponse isBankMember(String username, String world) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented.");
    }

    @Override
    public EconomyResponse isBankMember(String username, OfflinePlayer offlinePlayer) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented.");
    }

    @Override
    public List<String> getBanks() {
        return Collections.emptyList();
    }

    @Override
    public boolean createPlayerAccount(String username) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(username);
        if (offlinePlayer == null) return false;

        Profile profile = this.profileService.getProfile(offlinePlayer.getUniqueId());
        return profile != null;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer) {
        Profile profile = this.profileService.getProfile(offlinePlayer.getUniqueId());
        return profile != null;
    }

    @Override
    public boolean createPlayerAccount(String username, String world) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(username);
        if (offlinePlayer == null) return false;

        Profile profile = this.profileService.getProfile(offlinePlayer.getUniqueId());
        return profile != null;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer, String world) {
        Profile profile = this.profileService.getProfile(offlinePlayer.getUniqueId());
        return profile != null;
    }
}
