package org.leux.theapi.event;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CallableEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private boolean isCalled = false;
    private boolean cancelled = false;

    public CallableEvent() {
    }

    public CallableEvent call() {
        if (!this.isCalled) {
            Bukkit.getPluginManager().callEvent(this);
            this.setCalled(true);
        }

        return this;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public boolean isCalled() {
        return this.isCalled;
    }

    public void setCalled(boolean paramBoolean) {
        this.isCalled = paramBoolean;
    }
}
