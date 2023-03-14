package org.leux.thecore.listeners;

import org.leux.thecore.listeners.custom.SkullGriefing;
import org.leux.thecore.listeners.plugin.PluginMessageRecieved;
import org.leux.thecore.listeners.spigot.player.PlayerInteract;

public class Listener {

    public Listener() {
    }

    public static void init() {
        /**
         * Spigot listeners
         */
        PlayerInteract.init();
        /**
         * Custom listeners
         */
        SkullGriefing.init();
        /**
         * Plugin listeners
         */
        PluginMessageRecieved.init();
    }
}
