import java.util.HashMap;
import java.util.Map;

public class ATMSystem {
    private Map<String, Account> accounts;
    private Account currentAccount;
    
    public ATMSystem() {
        accounts = new HashMap<>();
        // Initialize demo accounts
        accounts.put("1234", new Account("1234", 1000.00));
        accounts.put("5678", new Account("5678", 2500.00));
        accounts.put("9999", new Account("9999", 500.00));
        currentAccount = null;
    }
    
    public boolean login(String pin) {
        if (pin == null || pin.trim().isEmpty()) {
            return false;
        }
        
        Account account = accounts.get(pin);
        if (account != null && account.verifyPin(pin)) {
            currentAccount = account;
            return true;
        }
        return false;
    }
    
    public boolean isLoggedIn() {
        return currentAccount != null;
    }
    
    public double getCurrentBalance() {
        if (!isLoggedIn()) return 0;
        return currentAccount.getBalance();
    }
    
    public boolean deposit(double amount) {
        if (!isLoggedIn()) return false;
        return currentAccount.deposit(amount);
    }
    
    public boolean withdraw(double amount) {
        if (!isLoggedIn()) return false;
        return currentAccount.withdraw(amount);
    }
    
    public String getTransactionHistory() {
        if (!isLoggedIn()) return "No transactions available.";
        
        StringBuilder sb = new StringBuilder();
        sb.append("===============================================\n");
        sb.append("           TRANSACTION HISTORY\n");
        sb.append("===============================================\n\n");
        
        for (Transaction t : currentAccount.getTransactionHistory()) {
            sb.append(t.toFormattedString()).append("\n");
        }
        
        sb.append("\n===============================================\n");
        sb.append("Total Transactions: ").append(currentAccount.getTransactionHistory().size());
        
        return sb.toString();
    }
    
    public void logout() {
        currentAccount = null;
    }
}