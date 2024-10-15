package dk.martinersej.thecore.pluginmodule.modules.basic.listeners.event.player;

import dk.martinersej.thecore.pluginmodule.modules.basic.events.SkullGriefingEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SkullInteract implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            if (player.getItemInHand() != null && player.getItemInHand().getType() == Material.ANVIL) {
                Block block = event.getClickedBlock().getRelative(event.getBlockFace());
                if (block.getType().toString().endsWith("SKULL")) {
                    SkullGriefingEvent eventSG = new SkullGriefingEvent(player);
                    Bukkit.getPluginManager().callEvent(eventSG);
                    if (!eventSG.isCancelled()) {
                        event.setCancelled(true);
                        block.getState().update(true, false);
                    }
                }
            }
        }
    }
}