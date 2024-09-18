package dk.martinersej.theCore.ecnomy;

import dk.martinersej.theCore.ecnomy.wallet.WalletOwner;

import java.util.ArrayList;
import java.util.List;

public class Economy {

    private final List<WalletOwner> walletOwners;

    public Economy() {


        walletOwners = new ArrayList<>();
    }

    public void addWalletOwner(WalletOwner walletOwner) {
        walletOwners.add(walletOwner);
    }

    public void removeWalletOwner(WalletOwner walletOwner) {
        walletOwners.remove(walletOwner);
    }

    public List<WalletOwner> getWalletOwners() {
        return walletOwners;
    }
}
