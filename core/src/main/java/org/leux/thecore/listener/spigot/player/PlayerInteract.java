package org.leux.thecore.listener.spigot.player;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.leux.TheCore;
import org.leux.thecore.events.SkullGriefingEvent;

public class PlayerInteract implements Listener {
    public PlayerInteract() {
    }

    public static void init() {
        Bukkit.getPluginManager().registerEvents(new PlayerInteract(), TheCore.getInstance());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSkullGrief(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            if (player.getItemInHand() != null && player.getItemInHand().getType() == Material.ANVIL) {
                Block block = event.getClickedBlock().getRelative(event.getBlockFace());
                if (block.getType().toString().endsWith("SKULL")) {
                    SkullGriefingEvent eventSG = new SkullGriefingEvent(player);
                    eventSG.call();
                    if (!eventSG.isCancelled()) {
                        event.setCancelled(true);
                        block.getState().update(true, false);
                    }
                }
            }
        }
    }
}