package edu.umb.cs681.hw15;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class StockMarket implements Runnable {
    private List<StockEvent> stocks = new ArrayList<>();
    private StockQuoteObservable stockQuoteObservable;
    private AtomicBoolean done = new AtomicBoolean();

    public void setDone() {
        this.done = new AtomicBoolean(true);
    }

    public StockMarket(List<StockEvent> stocks, StockQuoteObservable stockQuoteObservable) {
        this.stocks = stocks;
        this.stockQuoteObservable = stockQuoteObservable;
    }

    @Override
    public void run() {
        Random rand = new Random();
        while (true) {
            if (done.get()) {
                System.out.println("Trading Ended!");
                break;
            }

            stocks.forEach(s -> {
                stockQuoteObservable.changeQuote(s.getTicker(),
                        s.getQuote() + 10 * rand.nextDouble());
            });

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
