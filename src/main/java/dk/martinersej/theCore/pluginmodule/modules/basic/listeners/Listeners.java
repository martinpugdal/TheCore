package dk.martinersej.theCore.pluginmodule.modules.basic.listeners;

import dk.martinersej.theCore.TheCore;
import dk.martinersej.theCore.pluginmodule.modules.basic.listeners.custom.SkullGriefing;
import dk.martinersej.theCore.pluginmodule.modules.basic.listeners.custom.WorldDownloader;
import dk.martinersej.theCore.pluginmodule.modules.basic.listeners.messaging.WDLPluginMessage;
import dk.martinersej.theCore.pluginmodule.modules.basic.listeners.event.player.SkullInteract;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public class Listeners {

    private static final List<Class<?>> listeners = Arrays.asList(
        SkullGriefing.class,
        WorldDownloader.class,
        SkullInteract.class
    );

    public static void register() {
        TheCore.get().getLogger().info("Registering listeners...");

        listeners.forEach(Listeners::register);

        WDLPluginMessage.register();
    }

    public static void unregister() {
        TheCore.get().getLogger().info("Unregistering listeners...");

        HandlerList.unregisterAll(JavaPlugin.getProvidingPlugin(Listeners.class));
        WDLPluginMessage.unregisterChannels();
    }

    private static void register(Class<?> listener) {
        if (!Listener.class.isAssignableFrom(listener)) {
            TheCore.get().getLogger().warning("Listener " + listener.getName() + " does not implement Listener interface.");
            return;
        } else {
            TheCore.get().getLogger().info("Registered listener " + listener.getName());
        }
        try {
            Bukkit.getPluginManager().registerEvents((Listener) listener.newInstance(), JavaPlugin.getProvidingPlugin(Listeners.class));
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}
