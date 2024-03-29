package edu.umb.cs681.hw15;

public class T3DObserver implements Observer<StockEvent> {
    private StockEvent s;

    public StockEvent getS() {
        return s;
    }

    @Override
    public void update(Observable<StockEvent> sender, StockEvent event) {
        StockEvent t =
        new StockEvent(event.getTicker(), event.getQuote());

        this.s = t;

        System.out.println("Processing Stock event T3DObserver");
        System.out.println("T3DObserver Stock Updated: " + s.getTicker() + " Price: " + s.getQuote());

    }

}
