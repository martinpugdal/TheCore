package dk.martinersej.thecore.pluginmodule.modules.basic.listeners.messaging;

import dk.martinersej.thecore.TheCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import dk.martinersej.thecore.pluginmodule.modules.basic.events.WorldDownloaderEvent;

public class WDLPluginMessage implements PluginMessageListener {

    private final static String CHANNEL_1_13 = "wdl:register"; // From 1.13
    private final static String OLD_CHANNEL = "wdl|register"; // Up to 1.12

    public static void register() {
        WDLPluginMessage pluginMessageReceived = new WDLPluginMessage();
        Bukkit.getMessenger().registerIncomingPluginChannel(TheCore.get(), CHANNEL_1_13.toUpperCase(), pluginMessageReceived);
        try {
            Bukkit.getMessenger().registerIncomingPluginChannel(TheCore.get(), OLD_CHANNEL.toUpperCase(), pluginMessageReceived);
        } catch (Exception ignore) {
        }
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] data) {
        if (!channel.equalsIgnoreCase(OLD_CHANNEL) && !channel.equalsIgnoreCase(CHANNEL_1_13)) return;
        WorldDownloaderEvent eventWD = new WorldDownloaderEvent(player);
        Bukkit.getPluginManager().callEvent(eventWD);
        if (!eventWD.isCancelled()) {
            player.kickPlayer("WorldDownloader is not allowed");
        }
    }

    public static void unregisterChannels() {
        Bukkit.getMessenger().unregisterIncomingPluginChannel(TheCore.get(), CHANNEL_1_13);
        try {
            Bukkit.getMessenger().unregisterIncomingPluginChannel(TheCore.get(), OLD_CHANNEL);
        } catch (Exception ignored) {
        }
    }
}