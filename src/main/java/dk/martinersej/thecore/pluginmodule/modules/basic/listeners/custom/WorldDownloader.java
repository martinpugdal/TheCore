package dk.martinersej.thecore.pluginmodule.modules.basic.listeners.custom;

import dk.martinersej.thecore.pluginmodule.modules.basic.events.WorldDownloaderEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class WorldDownloader implements Listener {

    public WorldDownloader() {
    }

    @EventHandler
    public void onWorldDownloader(WorldDownloaderEvent event) {
        Player player = event.getPlayer();
        if (player.isOp() || player.hasPermission("antiwdl.allow")) {
            event.setCancelled(true);
        }
    }
}