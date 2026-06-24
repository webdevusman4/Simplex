package com.simplex.models;

import java.io.Serializable; // Import this

// Add 'implements Serializable' here
public class Crypto implements Serializable {
    private String symbol;
    private String name;
    private double priceInPKR;
    private double change24h;
    private String iconColor;

    public Crypto(String symbol, String name, double priceInPKR, double change24h, String iconColor) {
        this.symbol = symbol;
        this.name = name;
        this.priceInPKR = priceInPKR;
        this.change24h = change24h;
        this.iconColor = iconColor;
    }

    // Getters and Setters
    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public double getPriceInPKR() { return priceInPKR; }
    public void setPriceInPKR(double priceInPKR) { this.priceInPKR = priceInPKR; }
    
    public double getChange24h() { return change24h; }
    public void setChange24h(double change24h) { this.change24h = change24h; }
    
    public String getIconColor() { return iconColor; }
    public void setIconColor(String iconColor) { this.iconColor = iconColor; }

    public String getFormattedPrice() {
        return String.format("PKR %,.0f", priceInPKR);
    }

    public String getFormattedChange() {
        return String.format("%s%.2f%%", change24h >= 0 ? "+" : "", change24h);
    }

    public boolean isPositiveChange() {
        return change24h >= 0;
    }
}
