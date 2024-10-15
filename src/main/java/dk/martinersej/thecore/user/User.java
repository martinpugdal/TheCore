package dk.martinersej.thecore.user;

import dk.martinersej.thecore.ecnomy.wallet.Wallet;

import java.util.UUID;

public class User {

    private final UUID uuid;
    private final Wallet wallet = new Wallet();

    public User(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUUID() {
        return uuid;
    }
}
