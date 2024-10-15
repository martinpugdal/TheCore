package dk.martinersej.thecore;


import dk.martinersej.thecore.commands.Commands;
import dk.martinersej.thecore.ecnomy.VaultEconomyProvider;
import dk.martinersej.thecore.managers.SQLiteDatabase;
import dk.martinersej.thecore.pluginmodule.PluginModuleManager;
import dk.martinersej.theapi.TheAPI;
import lombok.Getter;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class TheCore extends JavaPlugin {

    private static TheCore instance;

    private PluginModuleManager pluginModuleManager;
    private SQLiteDatabase sqLiteDatabase;

    public static TheCore get() {
        return instance;
    }

    @Override
    public void onLoad() {
        instance = this;

        // setup folder if it doesn't exist
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
    }

    @Override
    public void onEnable() {
        // Initialize TheAPI
        TheAPI.initializeAPI(this);
        if (!TheAPI.isInitialized()) {
            getLogger().severe("TheAPI failed to initialize. Disabling plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Initialize sqLiteDatabase
        sqLiteDatabase = new SQLiteDatabase();
        if (!sqLiteDatabase.isConnected()) {
            getLogger().severe("Failed to connect to the sqLiteDatabase. Disabling plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Set the Economy for the VaultEconomyProvider
        getServer().getServicesManager().register(net.milkbowl.vault2.economy.Economy.class, new VaultEconomyProvider(), this, ServicePriority.Normal);

        // Initialize modules
        pluginModuleManager = new PluginModuleManager(this);
        // Load modules
        pluginModuleManager.loadModules();

        // Set up basic commands
        Commands.register();
    }

    @Override
    public void onDisable() {
        Commands.unregister();
        pluginModuleManager.disableModules();
    }
}
