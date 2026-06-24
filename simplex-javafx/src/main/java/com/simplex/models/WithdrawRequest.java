package com.simplex.models;

import java.io.Serializable; // Added for Database Persistence
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WithdrawRequest implements Serializable {
    // Standard version ID for serializable classes
    private static final long serialVersionUID = 1L;

    public enum Status {
        PENDING, APPROVED, REJECTED
    }

    private String id;
    private String userId;
    private String userName;
    private String userEmail;
    private double amount;
    private String bankAccount;
    private LocalDateTime timestamp;
    private Status status;

    public WithdrawRequest(String id, String userId, String userName, String userEmail, 
                          double amount, String bankAccount) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.amount = amount;
        this.bankAccount = bankAccount;
        this.timestamp = LocalDateTime.now();
        this.status = Status.PENDING;
    }

    // Getters and Setters
    public String getId() { return id; }
    public String getUserId() { return userId; }
    public String getUserName() { return userName; }
    public String getUserEmail() { return userEmail; }
    public double getAmount() { return amount; }
    public String getBankAccount() { return bankAccount; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public Status getStatus() { return status; }

    public void setStatus(Status status) { this.status = status; }

    public String getFormattedDate() {
        return timestamp.format(DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm"));
    }

    public String getFormattedAmount() {
        return String.format("PKR %,.0f", amount);
    }
}