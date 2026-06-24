package com.simplex.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.Serializable; // Import this

// Add 'implements Serializable' here
public class Transaction implements Serializable {
    public enum Type {
        BUY, SELL, DEPOSIT, WITHDRAW, TRANSFER_IN, TRANSFER_OUT
    }

    public enum Status {
        COMPLETED, PENDING, FAILED
    }

    private String id;
    private Type type;
    private String crypto;
    private double amount;
    private double pkrValue;
    private LocalDateTime timestamp;
    private Status status;
    private String description;

    public Transaction(String id, Type type, String crypto, double amount, double pkrValue, Status status) {
        this.id = id;
        this.type = type;
        this.crypto = crypto;
        this.amount = amount;
        this.pkrValue = pkrValue;
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.description = generateDescription();
    }

    public Transaction(String id, Type type, String crypto, double amount, double pkrValue, 
                      LocalDateTime timestamp, Status status) {
        this.id = id;
        this.type = type;
        this.crypto = crypto;
        this.amount = amount;
        this.pkrValue = pkrValue;
        this.timestamp = timestamp;
        this.status = status;
        this.description = generateDescription();
    }

    private String generateDescription() {
        switch (type) {
            case BUY: return "Bought " + crypto;
            case SELL: return "Sold " + crypto;
            case DEPOSIT: return "PKR Deposit";
            case WITHDRAW: return "PKR Withdrawal";
            case TRANSFER_IN: return "Received " + crypto;
            case TRANSFER_OUT: return "Sent " + crypto;
            default: return "Transaction";
        }
    }

    // Getters and Setters
    public String getId() { return id; }
    public Type getType() { return type; }
    public String getCrypto() { return crypto; }
    public double getAmount() { return amount; }
    public double getPkrValue() { return pkrValue; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public Status getStatus() { return status; }
    public String getDescription() { return description; }

    public void setStatus(Status status) { this.status = status; }

    public String getFormattedDate() {
        return timestamp.format(DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm"));
    }

    public String getFormattedAmount() {
        if (crypto != null && !crypto.isEmpty()) {
            return String.format("%.6f %s", amount, crypto);
        }
        return String.format("PKR %,.0f", pkrValue);
    }

    public String getFormattedPKR() {
        String prefix = (type == Type.SELL || type == Type.DEPOSIT || type == Type.TRANSFER_IN) ? "+" : "-";
        return prefix + String.format("PKR %,.0f", pkrValue);
    }

    public boolean isCredit() {
        return type == Type.SELL || type == Type.DEPOSIT || type == Type.TRANSFER_IN;
    }
}
