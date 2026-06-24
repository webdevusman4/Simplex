package com.simplex.models;

import java.io.Serializable; // Import this
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Add 'implements Serializable' here
public class User implements Serializable {
    private static final long serialVersionUID = 1L; // Recommended for version control
    
    private String id;
    private String name;
    private String email;
    private String pin;
    private double pkrBalance;
    private Map<String, Double> cryptoHoldings;
    private List<Transaction> transactionHistory;
    private boolean isAdmin;


    public User(String id, String name, String email, String pin, double pkrBalance, boolean isAdmin) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.pin = pin;
        this.pkrBalance = pkrBalance;
        this.isAdmin = isAdmin;
        this.transactionHistory = new ArrayList<>();
        this.cryptoHoldings = new HashMap<>();
        this.cryptoHoldings.put("BTC", 0.0);
        this.cryptoHoldings.put("ETH", 0.0);
        this.cryptoHoldings.put("USDT", 0.0);
        this.cryptoHoldings.put("BNB", 0.0);
        this.cryptoHoldings.put("XRP", 0.0);
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPin() { return pin; }
    public void setPin(String pin) { this.pin = pin; }
    
    public double getPkrBalance() { return pkrBalance; }
    public void setPkrBalance(double pkrBalance) { this.pkrBalance = pkrBalance; }
    
    public Map<String, Double> getCryptoHoldings() { return cryptoHoldings; }
    public void setCryptoHoldings(Map<String, Double> cryptoHoldings) { this.cryptoHoldings = cryptoHoldings; }

    public List<Transaction> getTransactionHistory() { return transactionHistory; }
    public void setTransactionHistory(List<Transaction> transactionHistory) { this.transactionHistory = transactionHistory; }

    public void addTransaction(Transaction transaction) {
        transactionHistory.add(0, transaction);
    }
    
    public boolean isAdmin() { return isAdmin; }
    public void setAdmin(boolean admin) { isAdmin = admin; }

    public double getCryptoBalance(String symbol) {
        return cryptoHoldings.getOrDefault(symbol, 0.0);
    }

    public void setCryptoBalance(String symbol, double amount) {
        cryptoHoldings.put(symbol, amount);
    }
}
