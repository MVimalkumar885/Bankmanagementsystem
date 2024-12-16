import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.HashMap;

public class BankingManagementSystem {

    // Bank account class to hold user information
    static class BankAccount {
        String username;
        String password;
        String bankName;
        double balance;

        BankAccount(String username, String password, String bankName) {
            this.username = username;
            this.password = password;
            this.bankName = bankName;
            this.balance = 0.0;
        }

        @Override
        public String toString() {
            return username + "," + password + "," + bankName + "," + balance;
        }
    }

    // Global data storage for users
    private static final HashMap<String, BankAccount> users = new HashMap<>();
    private static final String FILE_PATH = "users.txt";

    public static void main(String[] args) {
        loadUsersFromFile(); // Load users from file on startup
        SwingUtilities.invokeLater(BankingManagementSystem::createLoginFrame);
    }

    // Load users from file
    private static void loadUsersFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String username = parts[0];
                    String password = parts[1];
                    String bankName = parts[2];
                    double balance = Double.parseDouble(parts[3]);
                    users.put(username, new BankAccount(username, password, bankName));
                    users.get(username).balance = balance;
                }
            }
        } catch (IOException e) {
            System.err.println("File not found or corrupted: " + FILE_PATH);
        }
    }

    // Save users to file
    private static void saveUsersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (BankAccount account : users.values()) {
                writer.write(account.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving data to file: " + FILE_PATH);
        }
    }

    // Login frame creation
    private static void createLoginFrame() {
        JFrame loginFrame = new JFrame("Banking Management System - Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel userLabel = new JLabel("Username:");
        JTextField userText = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordText = new JPasswordField();

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        panel.add(userLabel);
        panel.add(userText);
        panel.add(passwordLabel);
        panel.add(passwordText);
        panel.add(loginButton);
        panel.add(registerButton);

        loginFrame.add(panel);
        loginFrame.setVisible(true);

        loginButton.addActionListener(e -> {
            String username = userText.getText();
            String password = new String(passwordText.getPassword());
            if (users.containsKey(username) && users.get(username).password.equals(password)) {
                JOptionPane.showMessageDialog(loginFrame, "Login successful!");
                loginFrame.dispose();
                createBankingFrame(users.get(username)); // Go to banking frame
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid username or password!");
            }
        });

        registerButton.addActionListener(e -> {
            loginFrame.dispose();
            createRegisterFrame(); // Go to register frame
        });
    }

    // Registration frame creation
    private static void createRegisterFrame() {
        JFrame registerFrame = new JFrame("Banking Management System - Register");
        registerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        registerFrame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        JLabel userLabel = new JLabel("Username:");
        JTextField userText = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordText = new JPasswordField();
        JLabel bankLabel = new JLabel("Bank Name:");
        String[] banks = {"SBI", "Kotak", "HDFC"};
        JComboBox<String> bankComboBox = new JComboBox<>(banks);

        JButton registerButton = new JButton("Register");

        panel.add(userLabel);
        panel.add(userText);
        panel.add(passwordLabel);
        panel.add(passwordText);
        panel.add(bankLabel);
        panel.add(bankComboBox);
        panel.add(new JLabel()); // Empty cell
        panel.add(registerButton);

        registerFrame.add(panel);
        registerFrame.setVisible(true);

        registerButton.addActionListener(e -> {
            String username = userText.getText();
            String password = new String(passwordText.getPassword());
            String bankName = (String) bankComboBox.getSelectedItem();

            if (!username.isEmpty() && !password.isEmpty() && !users.containsKey(username)) {
                BankAccount newAccount = new BankAccount(username, password, bankName);
                users.put(username, newAccount);
                saveUsersToFile(); // Save changes to file
                JOptionPane.showMessageDialog(registerFrame, "Registration successful!");
                registerFrame.dispose();
                createLoginFrame(); // Go back to login frame
            } else {
                JOptionPane.showMessageDialog(registerFrame, "Invalid input or user already exists!");
            }
        });
    }

    // Banking frame creation for logged-in users
    private static void createBankingFrame(BankAccount bankAccount) {
        JFrame bankingFrame = new JFrame("Welcome to " + bankAccount.bankName);
        bankingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        bankingFrame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        JLabel balanceLabel = new JLabel("Balance: " + bankAccount.balance);
        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton transferButton = new JButton("Transfer Money");
        JButton viewAccountsButton = new JButton("View All Accounts");
        JButton logoutButton = new JButton("Logout");

        panel.add(balanceLabel);
        panel.add(new JLabel()); // Empty cell
        panel.add(depositButton);
        panel.add(withdrawButton);
        panel.add(transferButton);
        panel.add(viewAccountsButton);
        panel.add(logoutButton);

        bankingFrame.add(panel);
        bankingFrame.setVisible(true);

        depositButton.addActionListener(e -> {
            String amountStr = JOptionPane.showInputDialog(bankingFrame, "Enter amount to deposit:");
            try {
                double amount = Double.parseDouble(amountStr);
                if (amount > 0) {
                    bankAccount.balance += amount;
                    saveUsersToFile(); // Save updated balance
                    balanceLabel.setText("Balance: " + bankAccount.balance);
                } else {
                    JOptionPane.showMessageDialog(bankingFrame, "Invalid amount!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(bankingFrame, "Invalid input!");
            }
        });

        withdrawButton.addActionListener(e -> {
            String amountStr = JOptionPane.showInputDialog(bankingFrame, "Enter amount to withdraw:");
            try {
                double amount = Double.parseDouble(amountStr);
                if (amount > 0 && amount <= bankAccount.balance) {
                    bankAccount.balance -= amount;
                    saveUsersToFile(); // Save updated balance
                    balanceLabel.setText("Balance: " + bankAccount.balance);
                } else {
                    JOptionPane.showMessageDialog(bankingFrame, "Insufficient balance or invalid amount!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(bankingFrame, "Invalid input!");
            }
        });

        transferButton.addActionListener(e -> {
            String usernameToTransfer = JOptionPane.showInputDialog(bankingFrame, "Enter username to transfer to:");
            if (users.containsKey(usernameToTransfer) && !usernameToTransfer.equals(bankAccount.username)) {
                String amountStr = JOptionPane.showInputDialog(bankingFrame, "Enter amount to transfer:");
                try {
                    double amount = Double.parseDouble(amountStr);
                    if (amount > 0 && amount <= bankAccount.balance) {
                        BankAccount receiverAccount = users.get(usernameToTransfer);
                        bankAccount.balance -= amount;
                        receiverAccount.balance += amount;
                        saveUsersToFile(); // Save updated balances
                        JOptionPane.showMessageDialog(bankingFrame, "Transfer successful!");
                        balanceLabel.setText("Balance: " + bankAccount.balance);
                    } else {
                        JOptionPane.showMessageDialog(bankingFrame, "Insufficient balance or invalid amount!");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(bankingFrame, "Invalid input!");
                }
            } else {
                JOptionPane.showMessageDialog(bankingFrame, "Invalid username or you cannot transfer to yourself!");
            }
        });

        viewAccountsButton.addActionListener(e -> {
            StringBuilder accountDetails = new StringBuilder();
            for (BankAccount account : users.values()) {
                accountDetails.append(account.username)
                        .append(" - Bank: ").append(account.bankName)
                        .append(" - Balance: ").append(account.balance)
                        .append("\n");
            }
            JOptionPane.showMessageDialog(bankingFrame, accountDetails.toString());
        });

        logoutButton.addActionListener(e -> {
            bankingFrame.dispose();
            createLoginFrame(); // Logout and go back to login
        });
    }
}
