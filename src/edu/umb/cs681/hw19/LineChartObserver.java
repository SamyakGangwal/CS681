package edu.umb.cs681.hw19;

public class LineChartObserver implements Observer<StockEvent> {
    private StockEvent s;

    public StockEvent getS() {
        return s;
    }

    @Override
    public void update(Observable<StockEvent> sender, StockEvent event) {
        StockEvent t =
                new StockEvent((((StockEvent) event).getTicker()), ((StockEvent) event).getQuote());

        this.s = t;

        System.out.println("Processing Stock event LineChartObserver");
        System.out.println(
                "LineChartObserver Stock Updated: " + s.getTicker() + " Price: " + s.getQuote());


    }


}
