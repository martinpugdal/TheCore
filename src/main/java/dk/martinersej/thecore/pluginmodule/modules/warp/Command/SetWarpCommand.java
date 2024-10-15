package dk.martinersej.thecore.pluginmodule.modules.warp.Command;

import dk.martinersej.thecore.TheCore;
import dk.martinersej.thecore.pluginmodule.modules.warp.WarpModule;
import dk.martinersej.thecore.pluginmodule.modules.warp.WarpUtil;
import dk.martinersej.theapi.command.Command;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

public class SetWarpCommand extends Command implements TabCompleter {

    private final WarpUtil warpUtil;

    public SetWarpCommand() {
        super("setwarp", new String[]{}, "Set a warp location", "/setwarp <warp>", "thecore.setwarp", "You do not have permission to set a warp");

        this.warpUtil = TheCore.get().getPluginModuleManager().getModule(WarpModule.class).getWarpUtil();
        setPlayerOnly(true);

        inject(TheCore.get());
    }

    @Override
    public boolean run(CommandSender commandSender, String label, String[] args) {
        if (args.length < 1) {
            return false;
        }

        String warpID = args[0].toLowerCase();
        Location location = ((Player) commandSender).getLocation();

        if (warpUtil.doesWarpExist(warpID)) {
            commandSender.sendMessage("§cWarp §4" + warpID + " §cfindes allerede!");
        } else {
            warpUtil.setWarp(warpID, location);
            commandSender.sendMessage("§aWarp §2" + warpID + " §aer blevet sat!");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, String label, String[] args) {
        return null;
    }
}
