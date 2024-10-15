package dk.martinersej.thecore.user;

import org.bukkit.OfflinePlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserMap {

    private final Map<UUID, User> userMap = new HashMap<>();

    public User load(final UUID uuid) {
        User user = userMap.get(uuid);
        if (user != null) {
            return user;
        }

        user = new User(uuid);
        userMap.put(uuid, user);
        return user;
    }

    public User get(final UUID uuid) {
        return userMap.get(uuid);
    }

    public User get(final OfflinePlayer player) {
        return get(player.getUniqueId());
    }
}
