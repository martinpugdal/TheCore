package org.leux.thecore.listeners.plugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.leux.TheCore;
import org.leux.thecore.events.WorldDownloaderEvent;

public class PluginMessageRecieved implements PluginMessageListener {

    private final static String CHANNEL_1_13 = "wdl:init"; // From 1.13
    private final static String OLD_CHANNEL = "wdl|init"; // Up to 1.12
    private final static PluginMessageRecieved pluginMessageRecieved = new PluginMessageRecieved();

    public static void init() {
        Bukkit.getMessenger().registerIncomingPluginChannel(TheCore.getInstance(), CHANNEL_1_13.toUpperCase(), pluginMessageRecieved);
        try {
            Bukkit.getMessenger().registerIncomingPluginChannel(TheCore.getInstance(), OLD_CHANNEL.toUpperCase(), pluginMessageRecieved);
        } catch (Exception ignore) {
        }
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] data) {
        if (!channel.equalsIgnoreCase(OLD_CHANNEL) && !channel.equalsIgnoreCase(CHANNEL_1_13)) return;
        WorldDownloaderEvent eventWD = new WorldDownloaderEvent(player);
        eventWD.call();
        if (!eventWD.isCancelled()) {
            player.kickPlayer("WorldDownloader is not allowed");
        }
    }

    public static void unregisterChannels() {
        Bukkit.getMessenger().unregisterIncomingPluginChannel(TheCore.getInstance(), CHANNEL_1_13);
        try {
            Bukkit.getMessenger().unregisterIncomingPluginChannel(TheCore.getInstance(), OLD_CHANNEL);
        } catch (Exception ignored) {
        }
    }
}