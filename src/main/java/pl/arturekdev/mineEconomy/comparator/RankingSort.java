package pl.arturekdev.mineEconomy.comparator;


import pl.arturekdev.mineEconomy.user.User;

import java.util.Comparator;

public class RankingSort implements Comparator<User> {

    @Override
    public int compare(User u1, User u2) {
        return (int) (u2.getMoney() - u1.getMoney());
    }
}
