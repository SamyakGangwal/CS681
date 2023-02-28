package edu.umb.cs681.hw03;

public class CandleStickObserver implements Observer<WkSummary> {
	private WkSummary eve;

	@Override
	public void update(Observable<WkSummary> sender, WkSummary event) {
		System.out.println("Weekly Summary");
		System.out.println("Open: " + event.getOpen());
		System.out.println("High: " + event.getHigh());
		System.out.println("Low: " + event.getLow());
		System.out.println("Close: " + event.getClose());

		this.eve = event;
	}

	public WkSummary getEveSummary() {
		return this.eve;
	}


}

