package org.leux.thecore.listener.custom;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
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
            event.getPlayer().sendMessage("Du er op..");
            event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("I am way more fancy than nms and reflection :D"));
            event.setCancelled(true);
        } else {
            event.getPlayer().sendMessage("Du er ikke op...");
        }
        //add permission checker here too..
    }
}