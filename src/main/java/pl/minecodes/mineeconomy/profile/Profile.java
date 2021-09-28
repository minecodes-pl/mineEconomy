package pl.minecodes.mineeconomy.profile;

import pl.minecodes.mineeconomy.profile.helper.BalanceOperationCallback;

import java.util.UUID;

public class Profile {

    private final UUID uniqueId;
    private double balance;

    public Profile(UUID uniqueId) {
        this.uniqueId = uniqueId;
        this.balance = 0;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public double getBalance() {
        return balance;
    }

    /**
     * Deposit money for player.
     *
     * @param money how much deposit for player.
     */
    public void deposit(double money, BalanceOperationCallback callback) {
        if (money < 0) {
            callback.cancel(BalanceOperationCallback.CancelReason.NEGATIVE_PARAMETER);
            return;
        }

        callback.done();
        this.balance += money;
    }

    /**
     * Withdraw moneys form player balance.
     *
     * @param money how much withdraw money.
     */
    public void withdraw(double money, BalanceOperationCallback callback) {
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
        this.balance -= money;
    }

    /**
     * Setup player balance.
     *
     * @param balance balance weight.
     */
    public void setupBalance(double balance, BalanceOperationCallback callback) {
        if (balance < 0) {
            callback.cancel(BalanceOperationCallback.CancelReason.NEGATIVE_PARAMETER);
            return;
        }

        callback.done();
        this.balance = balance;
    }
}
