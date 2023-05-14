package edu.umb.cs681.hw19;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class StockQuoteObservable extends Observable<StockEvent> {
    private HashMap<String, Double> stockPriceQuote;

    public HashMap<String, Double> getStockPriceQuote() {
        return stockPriceQuote;
    }

    private ReentrantLock lockTQ = new ReentrantLock();


    public StockQuoteObservable() {
        lockTQ.lock();
        try {
            this.stockPriceQuote = new HashMap<>();
        } finally {
            lockTQ.unlock();
        }
    }

    public void changeQuote(String t, double q) {
        lockTQ.lock();
        try {
            this.stockPriceQuote.put(t, q);
            notifyObservers(new StockEvent(t, q));
        } finally {
            lockTQ.unlock();
        }
    }

    public static void main(String[] args) {
        Thread[] thread = new Thread[15];
        StockQuoteObservable[] sqo = new StockQuoteObservable[15];
        StockMarket[] sm = new StockMarket[15];

        T3DObserver t3 = new T3DObserver();
        TableObserver to = new TableObserver();
        LineChartObserver lo = new LineChartObserver();

        List<StockEvent> stocks = new ArrayList<>();

        stocks.add(new StockEvent("AAPL", 458.0));
        stocks.add(new StockEvent("AMZN", 1000.0));
        stocks.add(new StockEvent("MSFT", 3400.0));
        stocks.add(new StockEvent("GOOGL", 2345.0));

        for (int i = 0; i < 15; i++) {
            sqo[i] = new StockQuoteObservable();
            sqo[i].addObserver(t3);
            sqo[i].addObserver(to);
            sqo[i].addObserver(lo);
            sm[i] = new StockMarket(stocks, sqo[i]);
            thread[i] = new Thread(sm[i]);
        }

        for (int i = 0; i < 15; i++) {
            thread[i].start();
        }

        for (int i = 0; i < 15; i++) {
            sm[i].setDone();
            thread[i].interrupt();
            try {
                thread[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < 15; i++) {
            System.out.println("STOCK: " + sqo[i].getStockPriceQuote());
        }
    }
}
