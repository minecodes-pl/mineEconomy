package pl.arturekdev.mineEconomy.user;

import lombok.Getter;
import lombok.Setter;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
@Setter
public class User {

    private String name;
    private double money;
    private double moneyFromAwards;
    private long lastLimitAward;
    private boolean update;

    public User() {
    }

    public User(ResultSet rs) throws SQLException {

        this.name = rs.getString("nick");
        this.money = rs.getInt("money");
        UserService.getUsers().add(this);

    }

    public void update(UserService userService) {

        if (!this.update) {
            return;
        }

        String update = "INSERT INTO mineEconomyUsers (nick, money) VALUES (" +
                "'" + this.name + "'," +
                "'" + this.money + "'" +
                ") ON DUPLICATE KEY UPDATE " +
                "money='" + this.money + "';";

        this.update = false;

        userService.getDatabaseConnector().executeUpdate(update);
    }

}
