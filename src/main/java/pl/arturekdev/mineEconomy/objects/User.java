package pl.arturekdev.mineEconomy.objects;

import lombok.Getter;
import lombok.Setter;
import pl.arturekdev.mineEconomy.managers.UserManager;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
@Setter
public class User {

    private String name;
    private int money;
    private boolean update;

    public User() {
    }

    public User(ResultSet rs) throws SQLException {

        this.name = rs.getString("nick");
        this.money = rs.getInt("money");
        UserManager.getUsers().add(this);

    }

    public void update(UserManager userManager) {

        if (!this.update) {
            return;
        }

        String update = "INSERT INTO mineEconomyUsers (nick, money) VALUES (" +
                "'" + this.name + "'," +
                "'" + this.money + "'" +
                ") ON DUPLICATE KEY UPDATE " +
                "money='" + this.money + "';";

        userManager.getDatabaseConnector().executeUpdate(update);
    }

}
