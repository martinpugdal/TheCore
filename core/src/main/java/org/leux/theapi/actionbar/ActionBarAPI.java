package org.leux.theapi.actionbar;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.leux.TheCore;
import org.leux.theapi.utils.NMSUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * ActionBar API
 *
 * <p>
 *     This class is part of the PinkCore project.
 *     <a href="https://github.com/PinkPrison/PinkCore">PinkCore</a> is licensed under the MIT license.
 * </p>
 * @author WildTooth, PandaPeter
 * @since 1.0.0
 */
public class ActionBarAPI {

    static private final Class<?> craftPlayerClass;
    static private final Class<?> packetPlayOutChatClass;
    static private final Class<?> chatComponentTextClass;
    static private final Class<?> iChatBaseComponentClass;
    static private final Class<?> chatMessageTypeClass;
    static private final Method craftPlayerHandleMethod;

    static {
        try {
            craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + NMSUtils.getNMSVersion() + ".entity.CraftPlayer");
            packetPlayOutChatClass = NMSUtils.getNMSClass("PacketPlayOutChat");
            chatComponentTextClass = NMSUtils.getNMSClass("ChatComponentText");
            iChatBaseComponentClass = NMSUtils.getNMSClass("IChatBaseComponent");
            chatMessageTypeClass = NMSUtils.getNMSClassOrNull("ChatMessageType");
            craftPlayerHandleMethod = craftPlayerClass.getDeclaredMethod("getHandle");
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sends an actionbar message to a player
     *
     * @param player Player to send the message to
     *               (if online)
     * @param message Message to send
     */
    public static void sendActionBar(Player player, String message) {
        if (!player.isOnline()) {
            return; // Player may have logged out
        }
        try {
            Object craftPlayer = craftPlayerClass.cast(player);
            Object packet;
            if (chatMessageTypeClass != null) {
                Object[] chatMessageTypes = chatMessageTypeClass.getEnumConstants();
                Object chatMessageType = null;
                for (Object obj : chatMessageTypes) {
                    if (obj.toString().equals("GAME_INFO")) {
                        chatMessageType = obj;
                    }
                }
                Object chatComponentText = Objects.requireNonNull(chatComponentTextClass).getConstructor(new Class<?>[]{String.class}).newInstance(message);
                packet = Objects.requireNonNull(packetPlayOutChatClass).getConstructor(new Class<?>[]{iChatBaseComponentClass, chatMessageTypeClass}).newInstance(chatComponentText, chatMessageType);
            } else {
                Object chatComponentText = Objects.requireNonNull(chatComponentTextClass).getConstructor(new Class<?>[]{String.class}).newInstance(message);
                packet = Objects.requireNonNull(packetPlayOutChatClass).getConstructor(new Class<?>[]{iChatBaseComponentClass, byte.class}).newInstance(chatComponentText, (byte) 2);
            }
            Object craftPlayerHandle = craftPlayerHandleMethod.invoke(craftPlayer);
            Field playerConnectionField = craftPlayerHandle.getClass().getDeclaredField("playerConnection");
            Object playerConnection = playerConnectionField.get(craftPlayerHandle);
            Method sendPacketMethod = playerConnection.getClass().getDeclaredMethod("sendPacket", NMSUtils.getNMSClass("Packet"));
            sendPacketMethod.invoke(playerConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends an actionbar message to a player for a set amount of time
     *
     * @param player Player to send the message to
     *               (if online)
     * @param message Message to send
     * @param duration Duration to send the message for
     *                 (in ticks)
     */
    public static void sendActionBar(final Player player, final String message, int duration) {
        sendActionBar(player, message);

        if (duration >= 0) {
            // Sends empty message at the end of the duration. Allow messages shorter than 3 seconds, ensures precision.
            new BukkitRunnable() {
                @Override
                public void run() {
                    sendActionBar(player, "");
                }
            }.runTaskLater(TheCore.getInstance(), duration + 1);
        }

        // Re-sends the messages every 3 seconds, so it doesn't go away from the player's screen.
        while (duration > 40) {
            duration -= 40;
            new BukkitRunnable() {
                @Override
                public void run() {
                    sendActionBar(player, message);
                }
            }.runTaskLater(TheCore.getInstance(), duration);
        }
    }

    /**
     * Sends an actionbar message to all players
     *
     * @param message Message to send
     */
    public static void sendActionBarToAllPlayers(String message) {
        sendActionBarToAllPlayers(message, -1);
    }

    /**
     * Sends an actionbar message to all players for a set amount of time
     *
     * @param message Message to send
     * @param duration Duration to send the message for
     *                 (in ticks)
     */
    public static void sendActionBarToAllPlayers(String message, int duration) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            sendActionBar(p, message, duration);
        }
    }
}
