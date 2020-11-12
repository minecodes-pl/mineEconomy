package pl.arturekdev.mineEconomy.runnable;

import pl.arturekdev.mineEconomy.managers.UserManager;

public class UserUpdateRunnable implements Runnable {

    private final UserManager userManager;

    public UserUpdateRunnable(UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public void run() {
        UserManager.getUsers().forEach(user -> user.update(userManager));
    }

}
