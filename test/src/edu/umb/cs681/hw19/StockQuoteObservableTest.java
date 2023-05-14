package edu.umb.cs681.hw19;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class StockQuoteObservableTest {
    @Test
    public void updateStockEvents() {
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

        for (int i = 0;i < 15;i ++) {
            assertTrue(sqo[i].getStockPriceQuote().get("AAPL") > 458);
            assertTrue(sqo[i].getStockPriceQuote().get("AMZN") > 1000);
            assertTrue(sqo[i].getStockPriceQuote().get("MSFT") > 3400);
            assertTrue(sqo[i].getStockPriceQuote().get("GOOGL") > 2345);
        }
    }
}
