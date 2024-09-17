package dk.martinersej.theCore.pluginmodule.modules.basic;

import dk.martinersej.theCore.pluginmodule.PluginModule;
import dk.martinersej.theCore.pluginmodule.modules.basic.listeners.Listeners;

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
