package org.leux.thecore.managers;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.leux.theapi.database.connectors.IConnector;
import org.leux.theapi.database.connectors.MySQLConnector;
import org.leux.theapi.database.connectors.SQLiteConnector;
import org.leux.theapi.utils.ColorUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private IConnector databaseConnector;
    private static final String DB_URL = "jdbc:mysql://89.117.58.104:3306/test";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "GGhrd2iv:_2e&v";

    public DatabaseManager(JavaPlugin plugin) {
//        this.database = new MySQLConnector(plugin, "89.117.58.104", 3306, "test", "root", "GGhrd2iv:_2e&v", false);
//        System.out.println("Connected to database");
//        init();
    }

    public void init() {
        if (Config.MYSQL_ENABLED.getBoolean()) {
            String hostname = Config.MYSQL_HOSTNAME.getString();
            int port = Config.MYSQL_PORT.getInteger();
            String database = Config.MYSQL_DATABASE.getString();
            String username = Config.MYSQL_USERNAME.getString();
            String password = Config.MYSQL_PASSWORD.getString();
            boolean useSSL = Config.MYSQL_USE_SSL.getBoolean();
            this.databaseConnector = new MySQLConnector(this, hostname, port, database, username, password, useSSL);
            if (this.databaseConnector.isInitialized()) {
                Bukkit.getConsoleSender().sendMessage(ColorUtils.getColored("    &a- Connected to MySQL database"));
            } else {
                Bukkit.getConsoleSender().sendMessage(ColorUtils.getColored("    &c- Failed to connect to MySQL database"));
            }
        } else {
            this.databaseConnector = new SQLiteConnector(this);
            if (this.databaseConnector.isInitialized()) {
                Bukkit.getConsoleSender().sendMessage(ColorUtils.getColored("    &a- Connected to SQLite database"));
            } else {
                Bukkit.getConsoleSender().sendMessage(ColorUtils.getColored("    &c- Failed to connect to SQLite database"));
            }
        }
    }

    public MySQLConnector getDatabase() {
        return database;
    }
}
