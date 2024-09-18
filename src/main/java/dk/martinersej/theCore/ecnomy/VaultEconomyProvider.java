package dk.martinersej.theCore.ecnomy;

import dk.martinersej.theCore.TheCore;
import net.milkbowl.vault2.economy.Economy;
import net.milkbowl.vault2.economy.EconomyResponse;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class VaultEconomyProvider implements Economy {

    private final TheCore theCore;

    public VaultEconomyProvider(TheCore theCore) {
        this.theCore = theCore;
    }

    @Override
    public boolean isEnabled() {
        return theCore.isEnabled();
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
        return currencyNameSingular();
    }

    @Override
    public String currencyNameSingular() {
        return "PD";
    }

    @Override
    public boolean createAccount(UUID uuid, String name) {
        return false;
    }

    @Override
    public boolean createAccount(UUID uuid, String name, String worldName) {
        return false;
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
        return false;
    }

    @Override
    public boolean hasAccount(UUID uuid, String worldName) {
        return false;
    }

    @Override
    public boolean renameAccount(UUID uuid, String name) {
        return false;
    }

    @Override
    public BigDecimal getBalance(UUID uuid) {
        return null;
    }

    @Override
    public BigDecimal getBalance(UUID uuid, String world) {
        return null;
    }

    @Override
    public boolean has(UUID uuid, BigDecimal amount) {
        return false;
    }

    @Override
    public boolean has(UUID uuid, String worldName, BigDecimal amount) {
        return false;
    }

    @Override
    public EconomyResponse withdraw(UUID uuid, BigDecimal amount) {
        return null;
    }

    @Override
    public EconomyResponse withdraw(UUID uuid, String worldName, BigDecimal amount) {
        return null;
    }

    @Override
    public EconomyResponse deposit(UUID uuid, BigDecimal amount) {
        return null;
    }

    @Override
    public EconomyResponse deposit(UUID uuid, String worldName, BigDecimal amount) {
        return null;
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
