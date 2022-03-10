package pl.minecodes.mineeconomy.profile;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import pl.minecodes.mineeconomy.data.configuration.Configuration;
import pl.minecodes.mineeconomy.profile.helper.BalanceOperationCallback;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

public class Profile {

    private final UUID uniqueId;
    private double balance;

    private boolean needUpdate;

    public Profile(UUID uniqueId, Configuration configuration) {
        this.uniqueId = uniqueId;
        this.balance = configuration.getStartBalance();
        this.needUpdate = true;
    }

    public Profile(UUID uniqueId, double balance) {
        this.uniqueId = uniqueId;
        this.balance = balance;
    }

    public OfflinePlayer getPlayer(){
        return Bukkit.getOfflinePlayer(uniqueId);
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double money, BalanceOperationCallback callback, int roundedScale) {
        if (money < 0) {
            callback.cancel(BalanceOperationCallback.CancelReason.NEGATIVE_PARAMETER);
            return;
        }

        callback.done();
        BigDecimal decimal = new BigDecimal(money).setScale(roundedScale, RoundingMode.HALF_UP);
        this.balance += decimal.doubleValue();
        this.needUpdate = true;
    }

    public void withdraw(double money, BalanceOperationCallback callback, int roundedScale) {
        if (this.balance < 0) {
            callback.cancel(BalanceOperationCallback.CancelReason.NEGATIVE_BALANCE);
            return;
        }
        if (money < 0) {
            callback.cancel(BalanceOperationCallback.CancelReason.NEGATIVE_PARAMETER);
            return;
        }
        if ((this.balance - money) < 0) {
            callback.cancel(BalanceOperationCallback.CancelReason.NO_FOUNDS);
            return;
        }

        callback.done();
        BigDecimal decimal = new BigDecimal(money).setScale(roundedScale, RoundingMode.HALF_UP);
        this.balance -= decimal.doubleValue();
        this.needUpdate = true;
    }

    public void setupBalance(double balance, BalanceOperationCallback callback, int roundedScale) {
        if (balance < 0) {
            callback.cancel(BalanceOperationCallback.CancelReason.NEGATIVE_PARAMETER);
            return;
        }

        callback.done();
        BigDecimal decimal = new BigDecimal(balance).setScale(roundedScale, RoundingMode.HALF_UP);
        this.balance = decimal.doubleValue();
        this.needUpdate = true;
    }

    public boolean has(double value) {
        return this.balance >= value;
    }

    public boolean needUpdate() {
        return needUpdate;
    }

    public void needUpdate(boolean b) {
        this.needUpdate = b;
    }
}
