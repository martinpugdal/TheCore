package dk.martinersej.theCore.pluginmodule.modules.warp;

import dk.martinersej.theCore.pluginmodule.PluginModule;
import dk.martinersej.theCore.pluginmodule.modules.warp.Command.DelWarpCommand;
import dk.martinersej.theCore.pluginmodule.modules.warp.Command.SetWarpCommand;
import dk.martinersej.theCore.pluginmodule.modules.warp.Command.WarpCommand;
import dk.martinersej.theapi.command.Command;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class WarpModule extends PluginModule {

    @Getter
    private final String warpSection = "warps";
    private final List<Command> commands = new ArrayList<>();
    @Getter
    private WarpUtil warpUtil;

    @Override
    protected boolean requiresConfig() {
        return true; // config for data storage of warps
    }

    @Override
    protected void onEnable() {
        if (getConfig().getConfigurationSection(warpSection) == null) {
            getConfig().createSection(warpSection);
            getModuleFile().save();
        }

        warpUtil = new WarpUtil(this);
        commands.add(new WarpCommand());
        commands.add(new SetWarpCommand());
        commands.add(new DelWarpCommand());
    }

    @Override
    protected void onDisable() {
        commands.forEach(Command::uninject);
        commands.clear();
        warpUtil = null;
    }
}
