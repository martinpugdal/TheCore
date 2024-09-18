package dk.martinersej.theCore.ecnomy.wallet;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

public interface WalletOwner extends WalletHolder {

    String getOwnerName();

    UUID getOwnerUUID();

    default OfflinePlayer getOwner() {
        return Bukkit.getOfflinePlayer(getOwnerUUID());
    }
}
