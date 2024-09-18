package dk.martinersej.theCore.ecnomy.wallet;

import java.math.BigDecimal;

public class Wallet {

    private BigDecimal balance;

    public Wallet() {
        this.balance = BigDecimal.ZERO;
    }

    public Wallet(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public double getBalanceDouble() {
        return balance.doubleValue();
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setBalance(double balance) {
        setBalance(BigDecimal.valueOf(balance));
    }

    public void add(BigDecimal amount) {
        balance = balance.add(amount);
    }

    public void add(double amount) {
        add(BigDecimal.valueOf(amount));
    }

    public void subtract(BigDecimal amount) {
        balance = balance.subtract(amount);
    }

    public void subtract(double amount) {
        subtract(BigDecimal.valueOf(amount));
    }

    public boolean has(BigDecimal amount) {
        return balance.compareTo(amount) >= 0;
    }

    public boolean has(double amount) {
        return has(BigDecimal.valueOf(amount));
    }
}
