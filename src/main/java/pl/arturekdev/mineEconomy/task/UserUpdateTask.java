package pl.arturekdev.mineEconomy.task;

import pl.arturekdev.mineEconomy.user.UserService;

public class UserUpdateTask implements Runnable {

    private final UserService userService;

    public UserUpdateTask(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run() {
        UserService.getUsers().forEach(user -> user.update(userService));
    }

}
