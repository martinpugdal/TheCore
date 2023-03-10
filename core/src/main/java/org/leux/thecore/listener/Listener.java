package org.leux.thecore.listener;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.leux.thecore.listener.custom.SkullGriefing;
import org.leux.thecore.listener.spigot.player.PlayerInteract;

public class Listener {

    public Listener() {
    }

    public static void init() {
        PlayerInteract.init();
        SkullGriefing.init();
    }
}
