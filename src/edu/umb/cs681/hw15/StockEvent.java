package edu.umb.cs681.hw15;

public record StockEvent(String ticket, Double quote) {
    public String getTicker() {
        return ticket;
    }

    public double getQuote() {
        return quote;
    }
}
