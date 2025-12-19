import java.util.ArrayList;
import java.util.List;

public class Account {
    private String pin;
    private double balance;
    private List<Transaction> transactionHistory;
    
    public Account(String pin, double initialBalance) {
        this.pin = pin;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
        addTransaction("INITIAL", initialBalance, balance);
    }
    
    public boolean verifyPin(String inputPin) {
        return this.pin.equals(inputPin);
    }
    
    public double getBalance() {
        return balance;
    }
    
    public boolean deposit(double amount) {
        if (amount <= 0) {
            return false;
        }
        balance += amount;
        addTransaction("DEPOSIT", amount, balance);
        return true;
    }
    
    public boolean withdraw(double amount) {
        if (amount <= 0 || amount > balance) {
            return false;
        }
        balance -= amount;
        addTransaction("WITHDRAWAL", amount, balance);
        return true;
    }
    
    private void addTransaction(String type, double amount, double balanceAfter) {
        Transaction transaction = new Transaction(type, amount, balanceAfter);
        transactionHistory.add(transaction);
    }
    
    public List<Transaction> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);
    }
}