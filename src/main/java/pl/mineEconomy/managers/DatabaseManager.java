package pl.mineEconomy.managers;

import pl.mineEconomy.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager {

    private static Connection connection = null;
    private static String URL = "jdbc:mysql://" + "51.83.145.95" + ":" + "3306" + "/" + "mineSurvival" + "?useUnicode=true&characterEncoding=utf-8";

    public static void connect() {
        connection = null;
        Logger.mysql("Start connecting to database");
        try {
            connection = DriverManager.getConnection(URL, "mineSurvival", "zgfD6MTLyt8g0GFq");
            Logger.mysql("Connected to database!");

        } catch (Exception e) {
            e.printStackTrace();
            Logger.mysql("Can't connect to database!");
        }
    }

    public static Connection getConnection() {
        if (connection == null)
            connect();
        return connection;
    }

    public static void updateQuery(String query) {
        try {
            getConnection().createStatement().executeUpdate(query);
        } catch (SQLException e) {
            connection = null;
            try {
                getConnection().createStatement().executeUpdate(query);
            } catch (SQLException ex) {
                Logger.mysql(ex.getMessage());
            }
        }
    }

    public static ResultSet executeQuery(String query) {
        ResultSet rs = null;
        try {
            rs = getConnection().createStatement().executeQuery(query);
        } catch (SQLException e) {
            connection = null;
            try {
                rs = getConnection().createStatement().executeQuery(query);
            } catch (SQLException ex) {
                Logger.mysql(ex.getMessage());
            }
        }
        return rs;
    }
}