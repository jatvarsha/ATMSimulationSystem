import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private String type;
    private double amount;
    private double balanceAfter;
    private LocalDateTime timestamp;
    
    public Transaction(String type, double amount, double balanceAfter) {
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.timestamp = LocalDateTime.now();
    }
    
    public String getType() {
        return type;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public double getBalanceAfter() {
        return balanceAfter;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public String toFormattedString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = timestamp.format(formatter);
        
        return String.format("%-12s | $%-10.2f | Balance: $%-10.2f\n%s", 
            type, amount, balanceAfter, formattedDateTime);
    }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("%s - %s: $%.2f (Balance: $%.2f)", 
            timestamp.format(formatter), type, amount, balanceAfter);
    }
}