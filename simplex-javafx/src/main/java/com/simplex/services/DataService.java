package com.simplex.services;

import com.simplex.models.*;
import java.time.LocalDateTime;
import java.util.*;

public class DataService {
    private static DataService instance;

    private Map<String, User> users;
    private Map<String, String> userPasswords; // ADDED: Secure map for passwords
    private Map<String, Crypto> cryptos;
    private List<WithdrawRequest> withdrawRequests;
    private User currentUser;

    private DataService() {
        initializeData();
    }

    public static DataService getInstance() {
        if (instance == null) {
            instance = new DataService();
        }
        return instance;
    }

    private void initializeData() {
        // Initialize users & passwords
        users = new HashMap<>();
        userPasswords = new HashMap<>(); // Initialize the password map

        User user1 = new User("1", "Ahmed Khan", "ahmed@example.com", "1234", 150000, false);
        user1.setCryptoBalance("BTC", 0.05);
        user1.setCryptoBalance("ETH", 1.5);
        user1.setCryptoBalance("USDT", 500);
        user1.addTransaction(new Transaction("t3", Transaction.Type.DEPOSIT, null, 0, 100000,
                LocalDateTime.now().minusDays(3), Transaction.Status.COMPLETED));
        user1.addTransaction(new Transaction("t2", Transaction.Type.SELL, "ETH", 0.5, 490000,
                LocalDateTime.now().minusDays(2), Transaction.Status.COMPLETED));
        user1.addTransaction(new Transaction("t1", Transaction.Type.BUY, "BTC", 0.01, 265000,
                LocalDateTime.now().minusDays(1), Transaction.Status.COMPLETED));
        users.put("ahmed@example.com", user1);
        userPasswords.put("ahmed@example.com", "password123"); // Save demo password

        User admin = new User("admin", "Admin", "admin@rupay.com", "0000", 0, true);
        users.put("admin@rupay.com", admin);
        userPasswords.put("admin@rupay.com", "admin123"); // Save admin password

        // Initialize cryptos
        cryptos = new LinkedHashMap<>();
        cryptos.put("BTC", new Crypto("BTC", "Bitcoin", 26500000, 2.45, "#F7931A"));
        cryptos.put("ETH", new Crypto("ETH", "Ethereum", 980000, -1.23, "#627EEA"));
        cryptos.put("USDT", new Crypto("USDT", "Tether", 279, 0.01, "#26A17B"));
        cryptos.put("BNB", new Crypto("BNB", "BNB", 168000, 3.67, "#F0B90B"));
        cryptos.put("XRP", new Crypto("XRP", "XRP", 145, -0.89, "#23292F"));

        // Initialize withdraw requests
        withdrawRequests = new ArrayList<>();
        withdrawRequests.add(new WithdrawRequest("w1", "1", "Ahmed Khan", "ahmed@example.com",
                50000, "HBL-1234567890"));
    }

    // --- UPDATED User methods ---

    public User login(String email, String password) {
        String cleanEmail = email.toLowerCase();
        User user = users.get(cleanEmail);
        String savedPassword = userPasswords.get(cleanEmail);

        // UPDATED: Now verifies the password matches exactly
        if (user != null && savedPassword != null && savedPassword.equals(password)) {
            currentUser = user;
            return user;
        }
        return null;
    }

    // UPDATED: Added password parameter to register method
    public User register(String name, String email, String password, String pin) {
        String cleanEmail = email.toLowerCase();
        if (users.containsKey(cleanEmail)) {
            return null; // Email already exists
        }

        User newUser = new User(UUID.randomUUID().toString(), name, email, pin, 0, false);
        users.put(cleanEmail, newUser);
        userPasswords.put(cleanEmail, password); // Securely store the new user's password

        currentUser = newUser;
        return newUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public void logout() {
        currentUser = null;
    }

    public boolean verifyPin(String pin) {
        return currentUser != null && currentUser.getPin().equals(pin);
    }

    public Collection<User> getAllUsers() {
        return users.values();
    }

    // --- Crypto methods ---
    public Collection<Crypto> getAllCryptos() {
        return cryptos.values();
    }

    public Crypto getCrypto(String symbol) {
        return cryptos.get(symbol);
    }

    // --- Transaction methods ---
    public List<Transaction> getUserTransactions() {
        if (currentUser == null) {
            return new ArrayList<>();
        }
        return currentUser.getTransactionHistory();
    }

    public List<Transaction> getFilteredTransactions(Transaction.Type type) {
        if (type == null)
            return getUserTransactions();
        List<Transaction> filtered = new ArrayList<>();
        for (Transaction t : getUserTransactions()) {
            if (t.getType() == type) {
                filtered.add(t);
            }
        }
        return filtered;
    }

    public void addTransaction(Transaction transaction) {
        if (currentUser != null) {
            currentUser.addTransaction(transaction);
        }
    }

    // --- Trading methods ---
    public boolean buyCrypto(String symbol, double amount) {
        Crypto crypto = cryptos.get(symbol);
        if (crypto == null || currentUser == null)
            return false;

        double cost = amount * crypto.getPriceInPKR();
        if (currentUser.getPkrBalance() < cost)
            return false;

        currentUser.setPkrBalance(currentUser.getPkrBalance() - cost);
        currentUser.setCryptoBalance(symbol, currentUser.getCryptoBalance(symbol) + amount);

        addTransaction(new Transaction(
                UUID.randomUUID().toString(),
                Transaction.Type.BUY,
                symbol,
                amount,
                cost,
                Transaction.Status.COMPLETED));

        return true;
    }

    public boolean sellCrypto(String symbol, double amount) {
        Crypto crypto = cryptos.get(symbol);
        if (crypto == null || currentUser == null)
            return false;

        if (currentUser.getCryptoBalance(symbol) < amount)
            return false;

        double value = amount * crypto.getPriceInPKR();
        currentUser.setCryptoBalance(symbol, currentUser.getCryptoBalance(symbol) - amount);
        currentUser.setPkrBalance(currentUser.getPkrBalance() + value);

        addTransaction(new Transaction(
                UUID.randomUUID().toString(),
                Transaction.Type.SELL,
                symbol,
                amount,
                value,
                Transaction.Status.COMPLETED));

        return true;
    }

    public boolean transferCrypto(String symbol, double amount, String recipientEmail) {
        User recipient = users.get(recipientEmail.toLowerCase());
        if (recipient == null || currentUser == null)
            return false;
        if (recipient.getEmail().equalsIgnoreCase(currentUser.getEmail()))
            return false;
        if (currentUser.getCryptoBalance(symbol) < amount)
            return false;

        Crypto crypto = cryptos.get(symbol);
        double value = amount * crypto.getPriceInPKR();

        currentUser.setCryptoBalance(symbol, currentUser.getCryptoBalance(symbol) - amount);
        recipient.setCryptoBalance(symbol, recipient.getCryptoBalance(symbol) + amount);

        Transaction senderTransaction = new Transaction(
                UUID.randomUUID().toString(),
                Transaction.Type.TRANSFER_OUT,
                symbol,
                amount,
                value,
                Transaction.Status.COMPLETED);
        addTransaction(senderTransaction);

        recipient.addTransaction(new Transaction(
                UUID.randomUUID().toString(),
                Transaction.Type.TRANSFER_IN,
                symbol,
                amount,
                value,
                Transaction.Status.COMPLETED));

        return true;
    }

    // --- Wallet methods ---
    public boolean depositPKR(double amount) {
        if (currentUser == null || amount <= 0)
            return false;

        currentUser.setPkrBalance(currentUser.getPkrBalance() + amount);
        addTransaction(new Transaction(
                UUID.randomUUID().toString(),
                Transaction.Type.DEPOSIT,
                null,
                0,
                amount,
                Transaction.Status.COMPLETED));
        return true;
    }

    public boolean requestWithdraw(double amount, String bankAccount) {
        if (currentUser == null || amount <= 0 || currentUser.getPkrBalance() < amount)
            return false;

        withdrawRequests.add(new WithdrawRequest(
                UUID.randomUUID().toString(),
                currentUser.getId(),
                currentUser.getName(),
                currentUser.getEmail(),
                amount,
                bankAccount));

        addTransaction(new Transaction(
                UUID.randomUUID().toString(),
                Transaction.Type.WITHDRAW,
                null,
                0,
                amount,
                Transaction.Status.PENDING));

        return true;
    }

    // --- Admin methods ---
    public List<WithdrawRequest> getPendingWithdrawals() {
        List<WithdrawRequest> pending = new ArrayList<>();
        for (WithdrawRequest req : withdrawRequests) {
            if (req.getStatus() == WithdrawRequest.Status.PENDING) {
                pending.add(req);
            }
        }
        return pending;
    }

    public void approveWithdrawal(String requestId) {
        for (WithdrawRequest req : withdrawRequests) {
            if (req.getId().equals(requestId)) {
                req.setStatus(WithdrawRequest.Status.APPROVED);
                User user = users.get(req.getUserEmail().toLowerCase());
                if (user != null) {
                    user.setPkrBalance(user.getPkrBalance() - req.getAmount());
                }
                break;
            }
        }
    }

    public void rejectWithdrawal(String requestId) {
        for (WithdrawRequest req : withdrawRequests) {
            if (req.getId().equals(requestId)) {
                req.setStatus(WithdrawRequest.Status.REJECTED);
                break;
            }
        }
    }
}
