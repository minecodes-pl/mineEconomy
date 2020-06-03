package pl.mineEconomy.comparator;


import pl.mineEconomy.objects.User;

import java.util.Comparator;

public class Sort implements Comparator<User> {

    @Override
    public int compare(User u1, User u2) {
        return u2.getMoney() - u1.getMoney();
    }
}
