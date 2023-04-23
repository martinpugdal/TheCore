package org.leux.theapi.interfaces;

import org.bukkit.plugin.java.JavaPlugin;
import org.leux.theapi.enums.Hook;

public interface IHook {

    String getName();

    Hook getEnum();

    boolean isEnabled();

    boolean init(JavaPlugin plugin);
}