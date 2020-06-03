package pl.mineEconomy.runnable;

import pl.mineEconomy.managers.DatabaseManager;
import pl.mineEconomy.managers.UserManager;
import pl.mineEconomy.objects.User;

public class UpdateRunnable implements Runnable {


    @Override
    public void run() {
        new Thread(() -> {
            for (User u : UserManager.users) {
                if (u.needUpdate()) {
                    DatabaseManager.updateQuery(UserManager.getAccountUpdate(u));
                    u.setUpdate(false);
                }
            }
        }).start();
    }
}
