package edu.umb.cs681.hw19;

public record StockEvent(String ticket, Double quote) {
    public String getTicker() {
        return ticket;
    }

    public double getQuote() {
        return quote;
    }
}
