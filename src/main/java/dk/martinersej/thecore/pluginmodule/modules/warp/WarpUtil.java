package dk.martinersej.thecore.pluginmodule.modules.warp;

import dk.martinersej.theapi.utils.LocationUtils;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WarpUtil {

    private final WarpModule warpModule;
    private final Map<String, Location> cachedWarps;

    public WarpUtil(WarpModule warpHandler) {
        this.warpModule = warpHandler;
        this.cachedWarps = loadWarps();
    }

    private Map<String, Location> loadWarps() {
        ConfigurationSection section = warpModule.getConfig().getConfigurationSection(warpModule.getWarpSection());
        if (section == null) {
            return new HashMap<>();
        }

        Map<String, Location> warps = new HashMap<>();
        for (String key : section.getKeys(false)) {
            warps.put(key, LocationUtils.stringToLocation(section.getString(key)));
        }
        return warps;
    }

    public void setWarp(String warpID, Location location) {
        String locationString = LocationUtils.locationToString(location);
        warpModule.getConfig().set(warpModule.getWarpSection() + "." + warpID, locationString);
        warpModule.getModuleFile().save();

        // Update cache with new warp location or replace existing
        if (cachedWarps.containsKey(warpID)) {
            cachedWarps.replace(warpID, location);
        } else {
            cachedWarps.put(warpID, location);
        }
    }

    public void deleteWarp(String warpID) {
        warpModule.getConfig().set(warpModule.getWarpSection() + "." + warpID, null);
        warpModule.getModuleFile().save();
        cachedWarps.remove(warpID);
    }

    public boolean doesWarpExist(String warpID) {
        return cachedWarps.containsKey(warpID);
    }

    public Location getWarpLocation(String warpID) {
        return cachedWarps.get(warpID);
    }

    public List<String> getAllowableWarps(Player player) {
        List<String> allowableWarps = new ArrayList<>();
        if (player.hasPermission("thecore.warps.*")) {
            allowableWarps.addAll(cachedWarps.keySet());
        } else {
            for (String warp : cachedWarps.keySet()) {
                if (player.hasPermission("thecore.warps." + warp)) {
                    allowableWarps.add(warp);
                }
            }
        }
        return allowableWarps;
    }

    public List<String> getSortedAllowableWarps(Player player) {
        List<String> allowableWarps = getAllowableWarps(player);
        allowableWarps.sort(String::compareToIgnoreCase);
        return allowableWarps;
    }
}