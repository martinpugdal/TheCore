package org.leux;

import org.bukkit.plugin.java.JavaPlugin;
import org.leux.thecore.listener.Listener;

public final class TheCore extends JavaPlugin {



    private static TheCore instance;

    @Override
    public void onEnable() {
        instance = this;

        Listener.init();
    }

    @Override
    public void onDisable() {
    }

    public static TheCore getInstance() {
        return instance;
    }
}
