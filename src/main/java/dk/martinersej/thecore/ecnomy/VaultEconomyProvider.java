package dk.martinersej.thecore.ecnomy;

import dk.martinersej.thecore.TheCore;
import dk.martinersej.thecore.ecnomy.wallet.Wallet;
import dk.martinersej.thecore.user.UserMap;
import net.milkbowl.vault2.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * TODO:
 * - refactor WalletOwner to User instead, so the User object can be used in the EconomyProvider
 * - User should have a Wallet object, where the User object contains multiple data, such as balance, last login, etc.
 */

public class VaultEconomyProvider implements net.milkbowl.vault2.economy.Economy {

    public VaultEconomyProvider() {
    }

    @Override
    public boolean isEnabled() {
        return TheCore.get().isEnabled();
    }

    @Override
    public String getName() {
        return "TheCore Economy";
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return -1; // No fractional digits, f.ex. 1.00 or 1.0
    }

    @Override
    public String format(BigDecimal amount) {
        if (amount == null) {
            return "";
            // if negative, add "-" before the amount
        } else if (amount.signum() < 0) {
            return "-" + amount;
        } else {
            return amount.toString();
        }
    }

    @Override
    public String currencyNamePlural() {
        return currencyNameSingular() + "er";
    }

    @Override
    public String currencyNameSingular() {
        return "mønt";
    }

    @Override
    public boolean createAccount(UUID uuid, String name) {
        return true;
    }

    @Override
    public boolean createAccount(UUID uuid, String name, String worldName) {
        return createAccount(uuid, name);
    }

    @Override
    public Map<UUID, String> getUUIDNameMap() {
        return Collections.emptyMap();
    }

    @Override
    public String getAccountName(UUID uuid) {
        return "";
    }

    @Override
    public boolean hasAccount(UUID uuid) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
        return economy.getWalletOwners().stream()
                .anyMatch(walletOwner -> walletOwner.getOwnerUUID().equals(player.getUniqueId()));
    }

    @Override
    public boolean hasAccount(UUID uuid, String worldName) {
        return hasAccount(uuid);
    }

    @Override
    public boolean renameAccount(UUID uuid, String name) {
        return economy.getWalletOwners().stream()
                .anyMatch(walletOwner -> walletOwner.getOwnerUUID().equals(uuid));
    }

    @Override
    public BigDecimal getBalance(UUID uuid) {
        WalletOwner walletOwner = economy.getWalletOwner(uuid);
        if (walletOwner == null) {
            return economy.loadWallet(uuid).getBalance();
        }
        return walletOwner.getWallet().getBalance();
    }

    @Override
    public BigDecimal getBalance(UUID uuid, String world) {
        return getBalance(uuid);
    }

    @Override
    public boolean has(UUID uuid, BigDecimal amount) {
        return getBalance(uuid).compareTo(amount) >= 0;
    }

    @Override
    public boolean has(UUID uuid, String worldName, BigDecimal amount) {
        return has(uuid, amount);
    }

    @Override
    public EconomyResponse withdraw(UUID uuid, BigDecimal amount) {
        WalletOwner walletOwner = economy.getWalletOwner(uuid);

        // if the player is offline, load the wallet from the database
        if (walletOwner == null) {
            Wallet wallet = economy.loadWallet(uuid);
            if (wallet == null) {
                // if the wallet is null, return a failure response because player doesn't exist
                return new EconomyResponse(amount, BigDecimal.ZERO, EconomyResponse.ResponseType.FAILURE, "Failed to load wallet");
            } else {
                if (!wallet.has(amount)) {
                    // if the player doesn't have enough money, return a failure response
                    return new EconomyResponse(amount, wallet.getBalance(), EconomyResponse.ResponseType.FAILURE, "Insufficient funds");
                } else {
                    // withdraw the amount from the database for the player, as the player is offline
                    economy.offlineDeposit(uuid, amount.negate());
                    return new EconomyResponse(amount, wallet.getBalance().subtract(amount), EconomyResponse.ResponseType.SUCCESS, "");
                }
            }
        }

        // if the player doesn't have enough money, return a failure response
        if (!walletOwner.getWallet().has(amount)) {
            return new EconomyResponse(amount, walletOwner.getWallet().getBalance(), EconomyResponse.ResponseType.FAILURE, "Insufficient funds");
        } else {
            walletOwner.getWallet().subtract(amount);
            return new EconomyResponse(amount, walletOwner.getWallet().getBalance(), EconomyResponse.ResponseType.SUCCESS, "");
        }
    }

    @Override
    public EconomyResponse withdraw(UUID uuid, String worldName, BigDecimal amount) {
        return withdraw(uuid, amount);
    }

    @Override
    public EconomyResponse deposit(UUID uuid, BigDecimal amount) {
        WalletOwner walletOwner = economy.getWalletOwner(uuid);
        if (walletOwner == null) {
            // load the wallet from the database
            Wallet wallet = economy.loadWallet(uuid);
            if (wallet == null) {
                // if the wallet is null, return a failure response because player doesn't exist
                return new EconomyResponse(amount, BigDecimal.ZERO, EconomyResponse.ResponseType.FAILURE, "Failed to load wallet");
            } else {
                // deposit the amount to the database for the player, as the player is offline
                economy.offlineDeposit(uuid, amount);
                return new EconomyResponse(amount, amount, EconomyResponse.ResponseType.SUCCESS, "");
            }
        }
        walletOwner.getWallet().add(amount);
        return new EconomyResponse(amount, walletOwner.getWallet().getBalance(), EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse deposit(UUID uuid, String worldName, BigDecimal amount) {
        return deposit(uuid, amount);
    }

    // Bank methods below are not implemented, as we don't have banks
    @Override
    public boolean createBank(String name, UUID uuid) {
        return false;
    }

    @Override
    public boolean deleteBank(UUID uuid) {
        return false;
    }

    @Override
    public Map<UUID, String> getBankUUIDNameMap() {
        return Collections.emptyMap();
    }

    @Override
    public String getBankAccountName(UUID uuid) {
        return "";
    }

    @Override
    public boolean hasBankAccount(UUID uuid) {
        return false;
    }

    @Override
    public boolean renameBankAccount(UUID uuid, String name) {
        return false;
    }

    @Override
    public BigDecimal bankBalance(UUID uuid) {
        return null;
    }

    @Override
    public boolean bankHas(UUID uuid, BigDecimal amount) {
        return false;
    }

    @Override
    public EconomyResponse bankWithdraw(String name, BigDecimal amount) {
        return null;
    }

    @Override
    public EconomyResponse bankDeposit(String name, BigDecimal amount) {
        return null;
    }

    @Override
    public boolean isBankOwner(UUID uuid, UUID bankUUID) {
        return false;
    }

    @Override
    public boolean isBankMember(UUID uuid, UUID bankUUID) {
        return false;
    }

    @Override
    public List<UUID> getBanks() {
        return Collections.emptyList();
    }
}
