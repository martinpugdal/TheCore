package org.leux.thecore.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.leux.theapi.event.CallableEvent;

public class SkullGriefingEvent extends CallableEvent implements Cancellable {

    private boolean isCancelled = false;
    private final Player player;
    private final Block block;

    public SkullGriefingEvent(Player player, Block block) {
        this.player = player;
        this.block = block;
    }

    public Player getPlayer() {
        return player;
    }

    public Block getBlock() {
        return block;
    }

    public boolean isCancelled() {
        return this.isCancelled;
    }

    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }
}