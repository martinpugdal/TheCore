package org.leux.thecore.managers;

import org.bukkit.Bukkit;
import org.leux.TheCore;
import org.leux.theapi.interfaces.IConnector;
import org.leux.theapi.database.connectors.MySQLConnector;
import org.leux.theapi.database.connectors.SQLiteConnector;
import org.leux.theapi.utils.ColorUtils;
import org.leux.thecore.configuration.Config;

public class DatabaseManager {

    private IConnector databaseConnector;

    public DatabaseManager() {
        init();
    }

    public void init() {
        if (Config.MYSQL_ENABLED.getBoolean()) {
            String hostname = Config.MYSQL_HOSTNAME.getString();
            int port = Config.MYSQL_PORT.getInteger();
            String database = Config.MYSQL_DATABASE.getString();
            String username = Config.MYSQL_USERNAME.getString();
            String password = Config.MYSQL_PASSWORD.getString();
            boolean useSSL = Config.MYSQL_USE_SSL.getBoolean();
            this.databaseConnector = new MySQLConnector(TheCore.getInstance(), hostname, port, database, username, password, useSSL);
            if (this.databaseConnector.isInitialized()) {
                Bukkit.getConsoleSender().sendMessage(ColorUtils.getColored("    &a- Connected to MySQL database"));
            } else {
                Bukkit.getConsoleSender().sendMessage(ColorUtils.getColored("    &c- Failed to connect to MySQL database"));
            }
        } else {
            this.databaseConnector = new SQLiteConnector(TheCore.getInstance());
            if (this.databaseConnector.isInitialized()) {
                Bukkit.getConsoleSender().sendMessage(ColorUtils.getColored("    &a- Connected to SQLite database"));
            } else {
                Bukkit.getConsoleSender().sendMessage(ColorUtils.getColored("    &c- Failed to connect to SQLite database"));
            }
        }
    }

    public IConnector getDatabase() {
        return this.databaseConnector;
    }
}
