import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.HierarchyEvent;

import java.time.format.DateTimeFormatter;

public class Main extends JFrame {
    private ATMSystem atmSystem;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    
    // Modern "Midnight" Palette
    private final Color NAVY_DARK = new Color(15, 23, 42);   
    private final Color ACCENT_BLUE = new Color(59, 130, 246); 
    private final Color CARD_WHITE = new Color(255, 255, 255);
    
    private final Color TEXT_SUB = new Color(100, 116, 139);
    private final Color SUCCESS_GREEN = new Color(34, 197, 94);
    private final Color ERROR_RED = new Color(239, 68, 68);

    private JPasswordField pinField;
    private JTextField depositAmountField, withdrawAmountField;
    private JPanel historyListContainer; // Replaced JTextArea
    private JLabel balanceLabel;

    public Main() {
        atmSystem = new ATMSystem();
        setupFrame();
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(NAVY_DARK);

        mainPanel.add(createLoginPanel(), "LOGIN");
        mainPanel.add(createMenuPanel(), "MENU");
        mainPanel.add(createBalancePanel(), "BALANCE");
        mainPanel.add(createDepositPanel(), "DEPOSIT");
        mainPanel.add(createWithdrawPanel(), "WITHDRAW");
        mainPanel.add(createHistoryPanel(), "HISTORY");

        add(mainPanel);
        cardLayout.show(mainPanel, "LOGIN");
    }

    private void setupFrame() {
        setTitle("Bank India");
        setSize(400, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        System.setProperty("awt.useSystemAAFontSettings","on");
        System.setProperty("swing.aatext", "true");
    }

    // --- SCREEN CREATION ---

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(NAVY_DARK);
        panel.setBorder(new EmptyBorder(60, 40, 60, 40));

        JLabel logo = new JLabel("Indian Bank", JLabel.CENTER);
        logo.setFont(new Font("Inter", Font.BOLD, 28));
        logo.setForeground(Color.WHITE);
        panel.add(logo, BorderLayout.NORTH);

        RoundedPanel card = new RoundedPanel(25, CARD_WHITE);
        card.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0); gbc.gridx = 0;

        JLabel loginTitle = new JLabel("Enter your PIN");
        loginTitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        loginTitle.setForeground(TEXT_SUB);
        
        pinField = new JPasswordField(4);
        pinField.setFont(new Font("Monospaced", Font.BOLD, 32));
        pinField.setHorizontalAlignment(JTextField.CENTER);
        pinField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, ACCENT_BLUE));
        pinField.setPreferredSize(new Dimension(150, 50));

        RoundedButton loginBtn = new RoundedButton("Secure Login", ACCENT_BLUE);
        loginBtn.setPreferredSize(new Dimension(200, 45));
        loginBtn.addActionListener(e -> handleLogin());

        gbc.gridy = 0; card.add(loginTitle, gbc);
        gbc.gridy = 1; card.add(pinField, gbc);
        gbc.gridy = 2; gbc.insets = new Insets(30, 0, 10, 0); card.add(loginBtn, gbc);

        panel.add(card, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setBackground(NAVY_DARK);
        panel.setBorder(new EmptyBorder(40, 25, 40, 25));

        JLabel title = new JLabel("Banking Services", JLabel.LEFT);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        panel.add(title, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(4, 1, 0, 15));
        grid.setOpaque(false);
        grid.add(createListButton("Current Balance", "View funds in ₹", e -> showBalance()));
        grid.add(createListButton("Deposit", "Add money to account", e -> showDeposit()));
        grid.add(createListButton("Withdraw", "Withdraw cash instantly", e -> showWithdraw()));
        grid.add(createListButton("History", "View passbook activity", e -> showHistory()));

        panel.add(grid, BorderLayout.CENTER);

        JButton logout = new JButton("Secure Logout");
        logout.setForeground(new Color(248, 113, 113));
        logout.setContentAreaFilled(false);
        logout.setBorderPainted(false);
        logout.addActionListener(e -> handleLogout());
        panel.add(logout, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createBalancePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(NAVY_DARK);
        panel.setBorder(new EmptyBorder(40, 25, 40, 25));

        balanceLabel = new JLabel("₹ 0.00", JLabel.CENTER);
        balanceLabel.setFont(new Font("SansSerif", Font.BOLD, 42));
        balanceLabel.setForeground(Color.WHITE);

        JLabel sub = new JLabel("Available Balance", JLabel.CENTER);
        sub.setForeground(TEXT_SUB);

        JPanel center = new JPanel(new GridLayout(2,1));
        center.setOpaque(false);
        center.add(sub); center.add(balanceLabel);

        panel.add(center, BorderLayout.CENTER);

        RoundedButton back = new RoundedButton("Back to Menu", Color.GRAY);
        back.addActionListener(e -> cardLayout.show(mainPanel, "MENU"));
        panel.add(back, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(NAVY_DARK);
        panel.setBorder(new EmptyBorder(30, 25, 30, 25));

        JLabel title = new JLabel("Recent Activity", JLabel.LEFT);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        panel.add(title, BorderLayout.NORTH);

        // Container for the transaction rows
        historyListContainer = new JPanel();
        historyListContainer.setLayout(new BoxLayout(historyListContainer, BoxLayout.Y_AXIS));
        historyListContainer.setBackground(NAVY_DARK);

        JScrollPane scroll = new JScrollPane(historyListContainer);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(NAVY_DARK);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0)); // Hide scrollbar for "app" look
        panel.add(scroll, BorderLayout.CENTER);

        RoundedButton back = new RoundedButton("Back to Menu", Color.GRAY);
        back.addActionListener(e -> cardLayout.show(mainPanel, "MENU"));
        panel.add(back, BorderLayout.SOUTH);

        // This triggers a refresh whenever the screen becomes visible
        panel.addHierarchyListener(e -> {
            if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0 && panel.isShowing()) {
                refreshHistoryUI();
            }
        });

        return panel;
    }

    // --- HELPERS & LOGIC ---

    private void refreshHistoryUI() {
        historyListContainer.removeAll();
        java.util.List<Transaction> transactions = atmSystem.getHistoryList();
        
        // Loop backwards to show newest transactions first
        for (int i = transactions.size() - 1; i >= 0; i--) {
            historyListContainer.add(createTransactionRow(transactions.get(i)));
            historyListContainer.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        historyListContainer.revalidate();
        historyListContainer.repaint();
    }

    private JPanel createTransactionRow(Transaction t) {
        RoundedPanel row = new RoundedPanel(15, new Color(30, 41, 59));
        row.setLayout(new BorderLayout());
        row.setMaximumSize(new Dimension(400, 75));
        row.setBorder(new EmptyBorder(10, 15, 10, 15));

        // Type and Date
        JPanel left = new JPanel(new GridLayout(2, 1));
        left.setOpaque(false);
        JLabel type = new JLabel(t.getType());
        type.setFont(new Font("SansSerif", Font.BOLD, 14));
        type.setForeground(Color.WHITE);
        
        JLabel date = new JLabel(t.getTimestamp().format(DateTimeFormatter.ofPattern("dd MMM, hh:mm a")));
        date.setFont(new Font("SansSerif", Font.PLAIN, 11));
        date.setForeground(TEXT_SUB);
        left.add(type); left.add(date);

        // Amount (Indian Rupees)
        boolean isCredit = t.getType().equals("DEPOSIT") || t.getType().equals("INITIAL");
        JLabel amt = new JLabel((isCredit ? "+ ₹" : "- ₹") + String.format("%.2f", t.getAmount()));
        amt.setFont(new Font("SansSerif", Font.BOLD, 15));
        amt.setForeground(isCredit ? SUCCESS_GREEN : ERROR_RED);

        row.add(left, BorderLayout.WEST);
        row.add(amt, BorderLayout.EAST);
        return row;
    }

    private JPanel createListButton(String title, String sub, java.awt.event.ActionListener action) {
        RoundedPanel p = new RoundedPanel(15, new Color(30, 41, 59));
        p.setLayout(new BorderLayout());
        JButton b = new JButton("<html><div style='padding-left:15px;'><b style='color:white;'>" + title + "</b><br><span style='color:#94a3b8; font-size:10px;'>" + sub + "</span></div></html>");
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.setContentAreaFilled(false); b.setBorderPainted(false); b.setFocusPainted(false);
        b.addActionListener(action);
        p.add(b, BorderLayout.CENTER);
        return p;
    }

    private JPanel createTransactionPanel(String titleStr, JTextField field, java.awt.event.ActionListener action) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(NAVY_DARK);
        panel.setBorder(new EmptyBorder(80, 40, 80, 40));
        RoundedPanel card = new RoundedPanel(25, CARD_WHITE);
        card.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10); gbc.gridx = 0;

        JLabel l = new JLabel(titleStr); l.setForeground(TEXT_SUB);
        field.setFont(new Font("SansSerif", Font.BOLD, 28));
        field.setHorizontalAlignment(JTextField.CENTER);
        field.setPreferredSize(new Dimension(200, 50));
        field.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.LIGHT_GRAY));

        RoundedButton btn = new RoundedButton("Confirm Transaction", ACCENT_BLUE);
        btn.setPreferredSize(new Dimension(220, 45));
        btn.addActionListener(action);

        JButton cancel = new JButton("Cancel");
        cancel.setForeground(TEXT_SUB);
        cancel.addActionListener(e -> cardLayout.show(mainPanel, "MENU"));

        gbc.gridy = 0; card.add(l, gbc);
        gbc.gridy = 1; card.add(field, gbc);
        gbc.gridy = 2; gbc.insets = new Insets(20,0,5,0); card.add(btn, gbc);
        gbc.gridy = 3; card.add(cancel, gbc);

        panel.add(card, BorderLayout.CENTER);
        return panel;
    }

    // --- UPDATED LOGIC HANDLERS ---
    private void handleLogin() {
        if (atmSystem.login(new String(pinField.getPassword()))) {
            pinField.setText(""); cardLayout.show(mainPanel, "MENU");
        } else { JOptionPane.showMessageDialog(this, "Invalid Security PIN"); }
    }
    private void showBalance() { 
        balanceLabel.setText(String.format("₹ %.2f", atmSystem.getCurrentBalance())); 
        cardLayout.show(mainPanel, "BALANCE"); 
    }
    private void showDeposit() { depositAmountField.setText(""); cardLayout.show(mainPanel, "DEPOSIT"); }
    private void showWithdraw() { withdrawAmountField.setText(""); cardLayout.show(mainPanel, "WITHDRAW"); }
    private void showHistory() { cardLayout.show(mainPanel, "HISTORY"); }
    private void handleLogout() { atmSystem.logout(); cardLayout.show(mainPanel, "LOGIN"); }
    
    private void handleDeposit() { 
        try { 
            double amt = Double.parseDouble(depositAmountField.getText());
            if(atmSystem.deposit(amt)) showBalance(); 
            else JOptionPane.showMessageDialog(this, "Enter valid amount");
        } catch(Exception e){ JOptionPane.showMessageDialog(this, "Numbers only"); }
    }
    private void handleWithdraw() { 
        try { 
            double amt = Double.parseDouble(withdrawAmountField.getText());
            if(atmSystem.withdraw(amt)) showBalance(); 
            else JOptionPane.showMessageDialog(this, "Insufficient Balance");
        } catch(Exception e){ JOptionPane.showMessageDialog(this, "Numbers only"); }
    }

    // --- STYLING HELPERS ---
    class RoundedPanel extends JPanel {
        private int r; Color c;
        RoundedPanel(int r, Color c) { this.r = r; this.c = c; setOpaque(false); }
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(c); g2.fillRoundRect(0, 0, getWidth(), getHeight(), r, r);
        }
    }

    class RoundedButton extends JButton {
        Color c;
        RoundedButton(String text, Color c) {
            super(text); this.c = c; setOpaque(false); setContentAreaFilled(false);
            setBorderPainted(false); setFocusPainted(false); setForeground(Color.WHITE);
            setFont(new Font("SansSerif", Font.BOLD, 14)); setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getModel().isPressed() ? c.darker() : c);
            g2.fillRoundRect(0,0,getWidth(),getHeight(), 15, 15);
            super.paintComponent(g);
        }
    }

    private JPanel createDepositPanel() { return createTransactionPanel("Amount to Deposit", depositAmountField = new JTextField(), e -> handleDeposit()); }
    private JPanel createWithdrawPanel() { return createTransactionPanel("Amount to Withdraw", withdrawAmountField = new JTextField(), e -> handleWithdraw()); }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}