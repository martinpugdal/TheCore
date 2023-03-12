package org.leux.thecore.listener.custom;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.leux.TheCore;
import org.leux.thecore.events.SkullGriefingEvent;

public class SkullGriefing implements Listener {

    public SkullGriefing() {
    }

    public static void init() {
        Bukkit.getPluginManager().registerEvents(new SkullGriefing(), TheCore.getInstance());
    }

    @EventHandler
    public void onSkullGriefing(SkullGriefingEvent event) {
        if (event.getPlayer().isOp()) {
            event.setCancelled(true);
        } else {
        }
        //add permission checker here too..
    }
}