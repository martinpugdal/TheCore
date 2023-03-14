package org.leux.thecore.listeners.custom;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.leux.TheCore;
import org.leux.thecore.events.WorldDownloaderEvent;

import static org.leux.theapi.actionbar.ActionBarAPI.sendActionBar;

public class WorldDownloader implements Listener {

    public WorldDownloader() {
    }

    public static void init() {
        Bukkit.getPluginManager().registerEvents(new WorldDownloader(), TheCore.getInstance());
    }

    @EventHandler
    public void onWorldDownloader(WorldDownloaderEvent event) {
        Player player = event.getPlayer();
        if (player.isOp() || player.hasPermission("antiwdl.allow")) {
            event.setCancelled(true);
        }
    }
}