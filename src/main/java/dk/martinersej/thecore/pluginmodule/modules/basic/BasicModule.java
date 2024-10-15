package dk.martinersej.thecore.pluginmodule.modules.basic;

import dk.martinersej.thecore.pluginmodule.PluginModule;
import dk.martinersej.thecore.pluginmodule.modules.basic.listeners.Listeners;

public class BasicModule extends PluginModule {
    @Override
    protected void onEnable() {
        Listeners.register();
    }

    @Override
    protected void onDisable() {
        Listeners.unregister();
    }
}
