package pl.mineEconomy.managers;

import pl.mineEconomy.Logger;
import pl.mineEconomy.objects.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserManager {

    private static final List<User> users = new ArrayList<>();

    public static List<User> getUsers() {
        return users;
    }

    public static User getUser(String name) {
        for (User u : users) {
            if (u.getName().equalsIgnoreCase(name)) {
                return u;
            }
        }
        User u = new User();
        u.setName(name);
        u.setUpdate(true);
        users.add(u);
        return u;
    }


    public static void load() {
        try {
            final ResultSet rs = DatabaseManager.executeQuery("SELECT * FROM economyUser");
            try {
                while (rs.next()) {
                    User u = new User();

                    u.setName(rs.getString("nick"));
                    u.setMoney(rs.getInt("balance"));
                    u.setUpdate(false);
                    users.add(u);

                }
                Logger.mysql("Pomyślnie załadowano " + UserManager.users.size() + " autoryzowanych kont.");
            } finally {
                if (rs != null) {
                    rs.close();
                }
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getUserUpdate(User u) {
        final StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO economyUser (nick, balance) VALUES (");
        sb.append("'" + u.getName() + "',");
        sb.append("'" + u.getMoney() + "'");
        sb.append(") ON DUPLICATE KEY UPDATE ");
        sb.append("balance='" + u.getMoney() + "';");
        return sb.toString();
    }


}
