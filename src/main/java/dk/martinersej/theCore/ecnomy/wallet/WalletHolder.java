package dk.martinersej.theCore.ecnomy.wallet;

public interface WalletHolder {

    Wallet getWallet();

    void setWallet(Wallet wallet);

    default void deposit(double amount) {
        getWallet().add(amount);
    }

    default void withdraw(double amount) {
        getWallet().subtract(amount);
    }

    default boolean has(double amount) {
        return getWallet().has(amount);
    }

    default double getBalance() {
        return getWallet().getBalanceDouble();
    }

    default void setBalance(double balance) {
        getWallet().setBalance(balance);
    }

    default void resetBalance() {
        getWallet().setBalance(0);
    }
}
