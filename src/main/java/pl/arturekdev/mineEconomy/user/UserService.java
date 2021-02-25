package pl.arturekdev.mineEconomy.user;

import lombok.*;
import org.bukkit.*;
import pl.arturekdev.mineEconomy.database.*;

import java.sql.*;
import java.util.*;

public class UserService {

    private static final List<User> users = new ArrayList<>();
    @Getter
    private final DatabaseConnector databaseConnector;

    public UserService(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    public static List<User> getUsers() {
        return users;
    }

    public static User getUser(String name) {
        for (User u : users) {
            if (u.getName().equals(name)) {
                return u;
            }
        }
        User u = new User();
        u.setName(name);
        u.setUpdate(true);
        users.add(u);
        return u;
    }

    public static User getUserIsPossibleNull(String name) {
        for (User user : users) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }

    public void loadUsers() {
        try {
            try (ResultSet rs = databaseConnector.executeQuery("SELECT * FROM mineEconomyUsers")) {
                while (rs.next()) {
                    new User(rs);
                }
                Bukkit.getLogger().fine("Pomyślnie załadowano " + UserService.users.size() + " autoryzowanych kont.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
