import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    private ATMSystem atmSystem;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    
    // Panels for different views
    private JPanel loginPanel;
    private JPanel menuPanel;
    private JPanel balancePanel;
    private JPanel depositPanel;
    private JPanel withdrawPanel;
    private JPanel historyPanel;
    
    // Components
    private JPasswordField pinField;
    private JTextField depositAmountField;
    private JTextField withdrawAmountField;
    private JTextArea historyArea;
    private JLabel balanceLabel;
    private JLabel messageLabel;
    
    public Main() {
        atmSystem = new ATMSystem();
        
        setTitle("ATM Simulator");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        createLoginPanel();
        createMenuPanel();
        createBalancePanel();
        createDepositPanel();
        createWithdrawPanel();
        createHistoryPanel();
        
        mainPanel.add(loginPanel, "LOGIN");
        mainPanel.add(menuPanel, "MENU");
        mainPanel.add(balancePanel, "BALANCE");
        mainPanel.add(depositPanel, "DEPOSIT");
        mainPanel.add(withdrawPanel, "WITHDRAW");
        mainPanel.add(historyPanel, "HISTORY");
        
        add(mainPanel);
        cardLayout.show(mainPanel, "LOGIN");
    }
    
    private void createLoginPanel() {
        loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        loginPanel.setBackground(new Color(30, 58, 138));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel titleLabel = new JLabel("ATM Simulator");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        loginPanel.add(titleLabel, gbc);
        
        JLabel subtitleLabel = new JLabel("Enter your PIN");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(200, 200, 200));
        gbc.gridy = 1;
        loginPanel.add(subtitleLabel, gbc);
        
        JPanel whitePanel = new JPanel();
        whitePanel.setBackground(Color.WHITE);
        whitePanel.setLayout(new GridBagLayout());
        whitePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints whitePanelGbc = new GridBagConstraints();
        whitePanelGbc.insets = new Insets(5, 5, 5, 5);
        whitePanelGbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel pinLabel = new JLabel("PIN:");
        pinLabel.setFont(new Font("Arial", Font.BOLD, 14));
        whitePanelGbc.gridx = 0;
        whitePanelGbc.gridy = 0;
        whitePanel.add(pinLabel, whitePanelGbc);
        
        pinField = new JPasswordField(15);
        pinField.setFont(new Font("Arial", Font.PLAIN, 18));
        whitePanelGbc.gridy = 1;
        whitePanel.add(pinField, whitePanelGbc);
        
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(37, 99, 235));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.addActionListener(e -> handleLogin());
        whitePanelGbc.gridy = 2;
        whitePanel.add(loginButton, whitePanelGbc);
        
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        loginPanel.add(whitePanel, gbc);
        
        JPanel demoPanel = new JPanel();
        demoPanel.setLayout(new BoxLayout(demoPanel, BoxLayout.Y_AXIS));
        demoPanel.setBackground(new Color(243, 244, 246));
        demoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel demoTitle = new JLabel("Demo Accounts:");
        demoTitle.setFont(new Font("Arial", Font.BOLD, 12));
        demoPanel.add(demoTitle);
        
        JLabel demo1 = new JLabel("PIN: 1234 ($1,000.00)");
        demo1.setFont(new Font("Arial", Font.PLAIN, 11));
        demoPanel.add(demo1);
        
        JLabel demo2 = new JLabel("PIN: 5678 ($2,500.00)");
        demo2.setFont(new Font("Arial", Font.PLAIN, 11));
        demoPanel.add(demo2);
        
        JLabel demo3 = new JLabel("PIN: 9999 ($500.00)");
        demo3.setFont(new Font("Arial", Font.PLAIN, 11));
        demoPanel.add(demo3);
        
        gbc.gridy = 3;
        loginPanel.add(demoPanel, gbc);
    }
    
    private void createMenuPanel() {
        menuPanel = new JPanel();
        menuPanel.setLayout(new GridBagLayout());
        menuPanel.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        
        JLabel menuTitle = new JLabel("Main Menu");
        menuTitle.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridy = 0;
        menuPanel.add(menuTitle, gbc);
        
        messageLabel = new JLabel("");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridy = 1;
        menuPanel.add(messageLabel, gbc);
        
        JButton balanceButton = createMenuButton("Check Balance");
        balanceButton.addActionListener(e -> showBalance());
        gbc.gridy = 2;
        menuPanel.add(balanceButton, gbc);
        
        JButton depositButton = createMenuButton("Deposit Money");
        depositButton.addActionListener(e -> showDeposit());
        gbc.gridy = 3;
        menuPanel.add(depositButton, gbc);
        
        JButton withdrawButton = createMenuButton("Withdraw Money");
        withdrawButton.addActionListener(e -> showWithdraw());
        gbc.gridy = 4;
        menuPanel.add(withdrawButton, gbc);
        
        JButton historyButton = createMenuButton("Transaction History");
        historyButton.addActionListener(e -> showHistory());
        gbc.gridy = 5;
        menuPanel.add(historyButton, gbc);
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        logoutButton.setBackground(new Color(220, 38, 38));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.addActionListener(e -> handleLogout());
        gbc.gridy = 6;
        menuPanel.add(logoutButton, gbc);
    }
    
    private void createBalancePanel() {
        balancePanel = new JPanel();
        balancePanel.setLayout(new GridBagLayout());
        balancePanel.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        
        JLabel title = new JLabel("Current Balance");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridy = 0;
        balancePanel.add(title, gbc);
        
        JPanel balanceCard = new JPanel();
        balanceCard.setBackground(new Color(37, 99, 235));
        balanceCard.setLayout(new GridBagLayout());
        balanceCard.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        balanceCard.setPreferredSize(new Dimension(400, 150));
        
        GridBagConstraints cardGbc = new GridBagConstraints();
        cardGbc.gridx = 0;
        
        JLabel balanceText = new JLabel("Available Balance");
        balanceText.setFont(new Font("Arial", Font.PLAIN, 14));
        balanceText.setForeground(new Color(200, 200, 255));
        cardGbc.gridy = 0;
        balanceCard.add(balanceText, cardGbc);
        
        balanceLabel = new JLabel("$0.00");
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 36));
        balanceLabel.setForeground(Color.WHITE);
        cardGbc.gridy = 1;
        balanceCard.add(balanceLabel, cardGbc);
        
        gbc.gridy = 1;
        balancePanel.add(balanceCard, gbc);
        
        JButton backButton = new JButton("Back to Menu");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(new Color(156, 163, 175));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MENU"));
        gbc.gridy = 2;
        balancePanel.add(backButton, gbc);
    }
    
    private void createDepositPanel() {
        depositPanel = new JPanel();
        depositPanel.setLayout(new GridBagLayout());
        depositPanel.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        
        JLabel title = new JLabel("Deposit Money");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        depositPanel.add(title, gbc);
        
        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        depositPanel.add(amountLabel, gbc);
        
        depositAmountField = new JTextField(20);
        depositAmountField.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        depositPanel.add(depositAmountField, gbc);
        
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setBackground(new Color(156, 163, 175));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.addActionListener(e -> {
            depositAmountField.setText("");
            cardLayout.show(mainPanel, "MENU");
        });
        buttonPanel.add(cancelButton);
        
        JButton depositButton = new JButton("Deposit");
        depositButton.setFont(new Font("Arial", Font.BOLD, 14));
        depositButton.setBackground(new Color(34, 197, 94));
        depositButton.setForeground(Color.WHITE);
        depositButton.setFocusPainted(false);
        depositButton.addActionListener(e -> handleDeposit());
        buttonPanel.add(depositButton);
        
        gbc.gridy = 3;
        depositPanel.add(buttonPanel, gbc);
    }
    
    private void createWithdrawPanel() {
        withdrawPanel = new JPanel();
        withdrawPanel.setLayout(new GridBagLayout());
        withdrawPanel.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        
        JLabel title = new JLabel("Withdraw Money");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        withdrawPanel.add(title, gbc);
        
        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        withdrawPanel.add(amountLabel, gbc);
        
        withdrawAmountField = new JTextField(20);
        withdrawAmountField.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        withdrawPanel.add(withdrawAmountField, gbc);
        
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setBackground(new Color(156, 163, 175));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.addActionListener(e -> {
            withdrawAmountField.setText("");
            cardLayout.show(mainPanel, "MENU");
        });
        buttonPanel.add(cancelButton);
        
        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setFont(new Font("Arial", Font.BOLD, 14));
        withdrawButton.setBackground(new Color(249, 115, 22));
        withdrawButton.setForeground(Color.WHITE);
        withdrawButton.setFocusPainted(false);
        withdrawButton.addActionListener(e -> handleWithdraw());
        buttonPanel.add(withdrawButton);
        
        gbc.gridy = 3;
        withdrawPanel.add(buttonPanel, gbc);
    }
    
    private void createHistoryPanel() {
        historyPanel = new JPanel();
        historyPanel.setLayout(new GridBagLayout());
        historyPanel.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        
        JLabel title = new JLabel("Transaction History");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        historyPanel.add(title, gbc);
        
        historyArea = new JTextArea(20, 40);
        historyArea.setEditable(false);
        historyArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(historyArea);
        scrollPane.setPreferredSize(new Dimension(450, 400));
        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        historyPanel.add(scrollPane, gbc);
        
        JButton backButton = new JButton("Back to Menu");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(new Color(156, 163, 175));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MENU"));
        gbc.gridy = 2;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        historyPanel.add(backButton, gbc);
    }
    
    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(300, 50));
        button.setBackground(new Color(219, 234, 254));
        button.setFocusPainted(false);
        return button;
    }
    
    private void handleLogin() {
        String pin = new String(pinField.getPassword());
        if (atmSystem.login(pin)) {
            pinField.setText("");
            showMessage("Login successful!", new Color(34, 197, 94));
            cardLayout.show(mainPanel, "MENU");
        } else {
            JOptionPane.showMessageDialog(this, "Invalid PIN. Please try again.", 
                "Login Failed", JOptionPane.ERROR_MESSAGE);
            pinField.setText("");
        }
    }
    
    private void showBalance() {
        double balance = atmSystem.getCurrentBalance();
        balanceLabel.setText(String.format("$%.2f", balance));
        cardLayout.show(mainPanel, "BALANCE");
    }
    
    private void showDeposit() {
        depositAmountField.setText("");
        cardLayout.show(mainPanel, "DEPOSIT");
    }
    
    private void showWithdraw() {
        withdrawAmountField.setText("");
        cardLayout.show(mainPanel, "WITHDRAW");
    }
    
    private void handleDeposit() {
        try {
            double amount = Double.parseDouble(depositAmountField.getText());
            if (atmSystem.deposit(amount)) {
                depositAmountField.setText("");
                showMessage("Deposit successful!", new Color(34, 197, 94));
                cardLayout.show(mainPanel, "MENU");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid amount. Please enter a positive number.", 
                    "Deposit Failed", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number.", 
                "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleWithdraw() {
        try {
            double amount = Double.parseDouble(withdrawAmountField.getText());
            if (atmSystem.withdraw(amount)) {
                withdrawAmountField.setText("");
                showMessage("Withdrawal successful!", new Color(34, 197, 94));
                cardLayout.show(mainPanel, "MENU");
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Insufficient funds or invalid amount.", 
                    "Withdrawal Failed", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number.", 
                "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void showHistory() {
        String history = atmSystem.getTransactionHistory();
        historyArea.setText(history);
        cardLayout.show(mainPanel, "HISTORY");
    }
    
    private void handleLogout() {
        atmSystem.logout();
        messageLabel.setText("");
        cardLayout.show(mainPanel, "LOGIN");
    }
    
    private void showMessage(String message, Color color) {
        messageLabel.setText(message);
        messageLabel.setForeground(color);
        Timer timer = new Timer(3000, e -> messageLabel.setText(""));
        timer.setRepeats(false);
        timer.start();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main frame = new Main();
            frame.setVisible(true);
        });
    }
}