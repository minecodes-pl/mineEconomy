package pl.minecodes.mineeconomy.data.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import eu.okaeri.injector.annotation.Inject;
import pl.minecodes.mineeconomy.data.configuration.Configuration;
import pl.minecodes.mineeconomy.data.database.element.model.DataService;
import pl.minecodes.mineeconomy.profile.Profile;

import java.sql.*;
import java.util.UUID;
import java.util.logging.Logger;

public class MySQLService implements DataService {

    @Inject private Logger logger;
    @Inject private Configuration configuration;

    private HikariDataSource dataSource;
    private Connection connection;

    @Override
    public Profile loadData(UUID uniqueId) {
        try (Statement statement = this.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `economyUsers` WHERE `uniqueId` = '" + uniqueId + "';");
            if (resultSet.first()) {
                return new Profile(uniqueId, resultSet.getDouble("balance"));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveData(Profile profile) {
        try (PreparedStatement preparedStatement = this.getConnection()
                .prepareStatement("INSERT INTO `economyUsers` (`uniqueId`, `balance`) VALUES (?, ?) ON DUPLICATE KEY UPDATE `uniqueId` = VALUES(uniqueId), `balance` = VALUES(balance);")) {
            preparedStatement.setString(1, profile.getUniqueId().toString());
            preparedStatement.setDouble(2, profile.getBalance());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            this.logger.severe("There was an unexpected incident, while trying to save profile with id " + profile.getUniqueId());
            exception.printStackTrace();
        }
    }

    @Override
    public void deleteData(Profile profile) {
        try (PreparedStatement preparedStatement = this.getConnection().prepareStatement("DELETE FROM `economyUsers` WHERE `uniqueId` = ?;")) {
            preparedStatement.setString(1, profile.getUniqueId().toString());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            this.logger.severe("There was an unexpected incident, while trying to remove plot with id " + profile.getUniqueId());
        }
    }

    @Override
    public void connect() {
        dataSource = new HikariDataSource(this.getHikariConfig());
        this.logger.info("Successfully connected to MySQL database!");
        try (Statement statement = dataSource.getConnection().createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `economyUsers` (`uniqueId` VARCHAR(64) PRIMARY KEY, `balance` DOUBLE);");
        } catch (SQLException exception) {
            exception.printStackTrace();
            this.logger.severe("There was an unexpected incident, while trying to create plots table");
        }
    }

    @Override
    public Profile order(int order) {
        return null;
    }

    private Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = dataSource.getConnection();
        }
        return connection;
    }

    private HikariConfig getHikariConfig() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(String.format("jdbc:mysql://%s:%d/%s",
                configuration.getDatabaseData().getHost(),
                configuration.getDatabaseData().getPort(),
                configuration.getDatabaseData().getDatabase()));
        hikariConfig.setUsername(configuration.getDatabaseData().getUsername());
        hikariConfig.setPassword(configuration.getDatabaseData().getPassword());

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
