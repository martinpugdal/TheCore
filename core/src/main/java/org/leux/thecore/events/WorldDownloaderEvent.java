package org.leux.thecore.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.leux.theapi.event.CallableEvent;

public class WorldDownloaderEvent extends CallableEvent implements Cancellable {
    private boolean isCancelled = false;
    private final Player player;

    public WorldDownloaderEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isCancelled() {
        return this.isCancelled;
    }

    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }
}
