package org.leux.theapi.hook.hooks;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.leux.theapi.hook.Hook;

public class VaultHook extends Hook {

    private static Economy ECONOMY;
    private static Permission PERMISSION;
    private static Chat CHAT;

    public VaultHook(String paramString, org.leux.theapi.enums.Hook paramHook) {
        super(paramString, paramHook);
    }

    @Override
    public boolean init(JavaPlugin plugin) {
        if(!super.isEnabled()) return false;
        if (plugin.getServer().getPluginManager().getPlugin("Vault") != null) {
            setupEconomy(plugin);
            setupChat(plugin);
            setupPermissions(plugin);
        } else {
            plugin.getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", plugin.getDescription().getName()));
        }
        return ECONOMY != null && CHAT != null && PERMISSION != null;
    }

    private void setupEconomy(JavaPlugin plugin) {
        RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
        ECONOMY = rsp.getProvider();
    }

    private void setupChat(JavaPlugin plugin) {
        RegisteredServiceProvider<Chat> rsp = plugin.getServer().getServicesManager().getRegistration(Chat.class);
        CHAT = rsp.getProvider();
    }

    private void setupPermissions(JavaPlugin plugin) {
        RegisteredServiceProvider<Permission> rsp = plugin.getServer().getServicesManager().getRegistration(Permission.class);
        PERMISSION = rsp.getProvider();
    }

    public static Economy getEconomy() {
        return ECONOMY;
    }

    public static Permission getPermission() {
        return PERMISSION;
    }

    public static Chat getChat() {
        return CHAT;
    }

    public static String getPrimaryGroup(Player player){
        if(PERMISSION == null) {
            throw new RuntimeException("PERMISSION_EXCEPTION");
        }
        return PERMISSION.getPrimaryGroup(player);
    }

    public static String[] getPlayerGroups(Player player){
        if(PERMISSION == null) {
            throw new RuntimeException("PERMISSION_EXCEPTION");
        }
        return PERMISSION.getPlayerGroups(player);
    }
}
