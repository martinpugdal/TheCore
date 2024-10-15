package dk.martinersej.thecore.user;

import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class UserManager {

    private static UserManager instance = new UserManager();

    private final UserMap userMap = new UserMap();

    public User loadUser(final UUID uuid) {
        return userMap.load(uuid);
    }

    public User getUser(final UUID uuid) {
        return userMap.get(uuid);
    }

    public User getUser(final OfflinePlayer player) {
        return userMap.get(player);
    }
}
