package dk.martinersej.theCore;


import dk.martinersej.theCore.commands.Commands;
import dk.martinersej.theCore.pluginmodule.PluginModuleManager;
import dk.martinersej.theapi.TheAPI;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class TheCore extends JavaPlugin {

    private static TheCore instance;

    private PluginModuleManager pluginModuleManager;

    public static TheCore get() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        TheAPI.initializeAPI(this);
        if (!TheAPI.isInitialized()) {
            getLogger().severe("TheAPI failed to initialize. Disabling plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Initialize modules
        pluginModuleManager = new PluginModuleManager(this);
        // Load modules
        pluginModuleManager.loadModules();

        // Set up commands
        Commands.register();
    }

    @Override
    public void onDisable() {
        Commands.unregister();
        pluginModuleManager.disableModules();
    }
}
