package edu.umb.cs681.hw19;

public class TableObserver implements Observer<StockEvent> {
    private StockEvent s;

    public StockEvent getS() {
        return s;
    }

    @Override
    public void update(Observable<StockEvent> sender, StockEvent event) {
        StockEvent t =
                new StockEvent(event.getTicker(), event.getQuote());


        this.s = t;
        System.out.println("Processing Stock event TableObserver");
        System.out.println("TableObserver Stock Updated: " + s.getTicker() + " Price: " + s.getQuote());
    }

}
