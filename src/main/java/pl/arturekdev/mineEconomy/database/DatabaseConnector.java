package pl.arturekdev.mineEconomy.database;

import lombok.SneakyThrows;
import pl.arturekdev.mineDatabase.DatabaseService;

import java.sql.*;

public class DatabaseConnector {

    private final DatabaseService databaseService = new DatabaseService();
    private Connection connection = databaseService.getConnection();

    @SneakyThrows
    public ResultSet executeQuery(String queryString) {
        if (checkConnection()) {
            connection = databaseService.getConnection();
        }

        PreparedStatement preparedStatement = connection.prepareStatement(queryString);

        return preparedStatement.executeQuery();
    }

    @SneakyThrows
    public int executeUpdate(String queryString) {
        if (checkConnection()) {
            connection = databaseService.getConnection();
        }

        PreparedStatement preparedStatement = connection.prepareStatement(queryString);
        int result = preparedStatement.executeUpdate();

        preparedStatement.close();

        return result;
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

    private boolean checkConnection() throws SQLException {
        return connection == null || connection.isClosed();
    }

}
