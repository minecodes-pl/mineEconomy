package pl.arturekdev.mineEconomy.managers;

import lombok.Getter;
import pl.arturekdev.mineEconomy.Logger;
import pl.arturekdev.mineEconomy.database.DatabaseConnector;
import pl.arturekdev.mineEconomy.objects.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserManager {

    private static final List<User> users = new ArrayList<>();
    @Getter
    private final DatabaseConnector databaseConnector;

    public UserManager(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

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

    public void loadUsers() {
        try {
            final ResultSet rs = databaseConnector.executeQuery("SELECT * FROM mineEconomyUsers");
            try {
                while (rs.next()) {
                    new User(rs);
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


}
