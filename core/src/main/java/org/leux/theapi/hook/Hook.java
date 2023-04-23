package org.leux.theapi.hook;

import org.bukkit.Bukkit;
import org.leux.theapi.interfaces.IHook;

public abstract class Hook implements IHook {

    private final String name;
    private final org.leux.theapi.enums.Hook hook;
    private final boolean isEnabled;

    public Hook(String paramString, org.leux.theapi.enums.Hook paramHook){
        this.name = paramString;
        this.hook = paramHook;
        this.isEnabled = paramHook.isBuiltIn() || Bukkit.getPluginManager().getPlugin(getName()) != null && Bukkit.getPluginManager().getPlugin(getName()).isEnabled();
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public org.leux.theapi.enums.Hook getEnum() {
        return hook;
    }
}