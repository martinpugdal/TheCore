package dk.martinersej.theCore.pluginmodule.modules.basic.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class WorldDownloaderEvent extends Event implements Cancellable {

    private final Player player;
    private boolean isCancelled = false;

    public WorldDownloaderEvent(Player player) {
        this.player = player;
    }

    public static HandlerList getHandlerList() {
        return new HandlerList();
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

    @Override
    public HandlerList getHandlers() {
        return getHandlerList();
    }
}