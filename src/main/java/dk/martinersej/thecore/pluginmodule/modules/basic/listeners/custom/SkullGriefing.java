package dk.martinersej.thecore.pluginmodule.modules.basic.listeners.custom;

import dk.martinersej.thecore.pluginmodule.modules.basic.events.SkullGriefingEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SkullGriefing implements Listener {

    public SkullGriefing() {
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