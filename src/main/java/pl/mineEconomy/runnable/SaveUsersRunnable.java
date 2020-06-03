package pl.mineEconomy.runnable;

import pl.mineEconomy.Logger;
import pl.mineEconomy.managers.DatabaseManager;
import pl.mineEconomy.managers.UserManager;
import pl.mineEconomy.objects.User;

public class SaveUsersRunnable implements Runnable {

    @Override
    public void run() {
        new Thread(() -> {
            StringBuilder sb = new StringBuilder();
            int i = 0;
            long time = System.currentTimeMillis();
            if (UserManager.getUsers().size() > 0)
                for (User u : UserManager.getUsers()) {
                    if (u.needUpdate()) {
                        sb.append(UserManager.getUserUpdate(u)).append(" ");
                        u.setUpdate(false);
                        i++;
                    }
                }

            if (i > 0) {
                DatabaseManager.updateQuery(sb.toString());
                time = System.currentTimeMillis() - time;
                Logger.mysql("Wyslano update dla " + i + " userów (" + time + "ms)");
            }
        }).start();

    }
}
