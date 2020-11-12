package pl.arturekdev.mineEconomy.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;

import java.sql.*;

public class DatabaseConnector {

    private final HikariDataSource datasource;
    private Connection connection;

    public DatabaseConnector() {
        this.datasource = new HikariDataSource(getHikariConfiguration());
    }

    @SneakyThrows
    public ResultSet executeQuery(String queryString) {
        if (checkConnection()) {
            connection = getConnection();
        }

        PreparedStatement preparedStatement = connection.prepareStatement(queryString);

        return preparedStatement.executeQuery();
    }

    @SneakyThrows
    public int executeUpdate(String queryString) {
        if (checkConnection()) {
            connection = getConnection();
        }

        PreparedStatement preparedStatement = connection.prepareStatement(queryString);
        int result = preparedStatement.executeUpdate();

        preparedStatement.close();

        return result;
    }

    @SneakyThrows
    public void closeConnection() {
        connection.close();
    }

    @SneakyThrows
    public Connection getConnection() {
        if (connection == null || connection.isClosed()) {
            connection = datasource.getConnection();
        }

        return connection;
    }

    @SneakyThrows
    public void prepareCollection() {
        Statement statement = getConnection().createStatement();
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

    private HikariConfig getHikariConfiguration() {

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(String.format("jdbc:mysql://%s:%s/%s?allowMultiQueries=true&useUnicode=yes&characterEncoding=UTF-8",
                "51.83.145.95",
                3306,
                "mineServer"));
        hikariConfig.setUsername("mineServer");
        hikariConfig.setPassword("77xoJ4r6sYnc5qc9");

        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        hikariConfig.addDataSourceProperty("useServerPrepStmts", "true");
        hikariConfig.addDataSourceProperty("useLocalSessionState", "true");
        hikariConfig.addDataSourceProperty("rewriteBatchedStatements", "true");
        hikariConfig.addDataSourceProperty("cacheResultSetMetadata", "true");
        hikariConfig.addDataSourceProperty("cacheServerConfiguration", "true");
        hikariConfig.addDataSourceProperty("elideSetAutoCommits", "true");
        hikariConfig.addDataSourceProperty("maintainTimeStats", "false");

        return hikariConfig;
    }
}
