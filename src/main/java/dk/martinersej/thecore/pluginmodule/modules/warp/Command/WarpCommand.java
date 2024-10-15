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

public class WarpCommand extends Command implements TabCompleter {

    private final WarpUtil warpUtil;

    public WarpCommand() {
        super("warp", new String[]{}, "Warp to a location", "/warp <warp>", "thecore.warp", "You do not have permission to warp");

        this.warpUtil = TheCore.get().getPluginModuleManager().getModule(WarpModule.class).getWarpUtil();
        setPlayerOnly(true);

        inject(TheCore.get());
    }

    @Override
    public boolean run(CommandSender commandSender, String label, String[] args) {
        executeWarps((Player) commandSender, args);
        return true;
    }

    private void executeWarps(Player player, String... args) {
        if (args.length < 1) {
            String warps = String.join(", ", warpUtil.getSortedAllowableWarps(player));
            player.sendMessage("§aWarps: §2" + warps);
            return;
        }

        String warpID = args[0].toLowerCase();

        if (!warpUtil.doesWarpExist(warpID)) {
            String warps = String.join(", ", warpUtil.getSortedAllowableWarps(player));
            player.sendMessage("§cWarp §4" + warpID + " §ceksisterer ikke!");
            player.sendMessage("§aWarps: §2" + warps);
            return;
        }

        Location location = warpUtil.getWarpLocation(warpID);
        player.teleport(location);
        player.sendMessage("§aTeleporteret til warp §2" + warpID + "§a!");
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, String label, String[] args) {
        Player player = (Player) commandSender;
        List<String> tabComplete = warpUtil.getSortedAllowableWarps(player);

        if (args.length == 1) {
            tabComplete.removeIf(warp -> !warp.startsWith(args[0]));
        }

        return tabComplete;
    }
}
