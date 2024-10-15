package dk.martinersej.thecore.managers;

import dk.martinersej.thecore.TheCore;
import dk.martinersej.theapi.database.Constraint;
import dk.martinersej.theapi.database.Database;
import dk.martinersej.theapi.database.QueryBuilder;
import lombok.SneakyThrows;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SQLiteDatabase implements Database {

    private final Lock lock = new ReentrantLock(true);
    private Connection connection;

    public SQLiteDatabase() {
        establishConnection();
        createTables();
    }

    @Override
    public Lock getLock() {
        return lock;
    }

    private void establishConnection() {
        try {
            Class.forName(getDriver());
            if (getProperties() == null) {
                connection = DriverManager.getConnection(getURL());
            } else {
                connection = DriverManager.getConnection(getURL(), getProperties());
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @SneakyThrows
    @Override
    public boolean isConnected() {
        return connection != null && !connection.isClosed();
    }

    @Override
    public String getHost() {
        return "localhost";
    }

    @Override
    public String getPort() {
        return "3306";
    }

    @Override
    public String getDatabase() {
        return TheCore.get().getName();
    }

    @Override
    public String getUsername() {
        return "root";
    }

    @Override
    public String getPassword() {
        return "password";
    }

    @Override
    public String getPrefix() {
        return "core_";
    }

    @Override
    public String getURL() {
        return String.format("jdbc:sqlite:%s%s%s.db", TheCore.get().getDataFolder(), File.separator, getDatabase());
    }

    @Override
    public String getDriver() {
        return "org.sqlite.JDBC";
    }

    @Override
    public Properties getProperties() {
        return null;
    }

    @Override
    public void createTables() {
        List<String> tables = new ArrayList<>();

         String walletTable = QueryBuilder.QueryTableBuilder.createTable("wallets").
            values("uuid", "VARCHAR(36)", Constraint.PRIMARY_KEY, Constraint.NOT_NULL).
            values("balance", "REAL", Constraint.NOT_NULL).
            build();
        tables.add(walletTable);

        System.out.println(walletTable);
        sync((connection) -> {
            try {
                for (String table : tables) {
                    connection.createStatement().executeUpdate(table);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
