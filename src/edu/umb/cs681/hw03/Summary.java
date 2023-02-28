package edu.umb.cs681.hw03;

public class Summary {
	private double open;
	private double close;	
	private double high;
	private double low;
	
	public double getLow() {
		return low;
	}
	public double getHigh() {
		return high;
	}
	public double getClose() {
		return close;
	}
	public double getOpen() {
		return open;
	}

	public Summary(double open, double close, double high, double low) {
		this.open = open;
		this.close = close;
		this.high = high;
		this.low = low;
	}

	public static void main(String[] args) {
		CandleStickObserver canfCandleStickObserver = new CandleStickObserver();

		DJIAWkSummaryObservable djO = new DJIAWkSummaryObservable();

		djO.addObserver(canfCandleStickObserver);

		djO.parseCSV();
	}
}
