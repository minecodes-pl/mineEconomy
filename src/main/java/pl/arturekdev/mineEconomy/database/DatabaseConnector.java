package pl.arturekdev.mineEconomy.database;

import lombok.SneakyThrows;
import pl.arturekdev.mineDatabase.DatabaseService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseConnector {

    private final DatabaseService databaseService = new DatabaseService();

    public Connection getConnection() {
        return databaseService.getConnection();
    }

    @SneakyThrows
    public ResultSet executeQuery(String queryString) {
        PreparedStatement preparedStatement = getConnection().prepareStatement(queryString);
        return preparedStatement.executeQuery();
    }

    @SneakyThrows
    public void executeUpdate(String queryString) {
        PreparedStatement preparedStatement = getConnection().prepareStatement(queryString);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    @SneakyThrows
    public void prepareCollection() {
        Statement statement = databaseService.getConnection().createStatement();
        StringBuilder sb = new StringBuilder();

        sb.append("create table if not exists `mineEconomyUsers`(");
        sb.append("`nick` varchar(16) not null,");
        sb.append("`money` int not null,");
        sb.append("primary key (nick));");

        statement.executeUpdate(sb.toString());
        statement.close();
    }
}
