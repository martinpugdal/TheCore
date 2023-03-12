package org.leux.thecore.listener;

import org.leux.thecore.listener.custom.SkullGriefing;
import org.leux.thecore.listener.plugin.PluginMessageRecieved;
import org.leux.thecore.listener.spigot.player.PlayerInteract;

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
