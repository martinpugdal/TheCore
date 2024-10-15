package dk.martinersej.thecore.pluginmodule.modules.warp.Command;

import dk.martinersej.thecore.TheCore;
import dk.martinersej.thecore.pluginmodule.modules.warp.WarpModule;
import dk.martinersej.thecore.pluginmodule.modules.warp.WarpUtil;
import dk.martinersej.theapi.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class DelWarpCommand extends Command implements TabCompleter {

    private final WarpUtil warpUtil;

    public DelWarpCommand() {
        super("delwarp", new String[]{}, "Delete a warp location", "/delwarp <warp>", "thecore.delwarp", "You do not have permission to delete a warp");

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

        if (!warpUtil.doesWarpExist(warpID)) {
            commandSender.sendMessage("§cWarp §4" + warpID + " §ceksisterer ikke!");
        } else {
            warpUtil.deleteWarp(warpID);
            commandSender.sendMessage("§aWarp §2" + warpID + " §aer blevet slettet!");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, String label, String[] args) {
        return null;
    }
}
