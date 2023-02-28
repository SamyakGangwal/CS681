package edu.umb.cs681.hw03;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DJIAWeeklyTest {
	@Test
	public void getWeeklySummary() {
		CandleStickObserver canfCandleStickObserver = new CandleStickObserver();

		DJIAWkSummaryObservable djO = new DJIAWkSummaryObservable();

		djO.addObserver(canfCandleStickObserver);

		WkSummary wkSummary = new WkSummary(33175.39, 34041.16, 32795.67, 33696.85);

		djO.parseCSV();

		assertEquals(wkSummary.getOpen(), canfCandleStickObserver.getEveSummary().getOpen());
		assertEquals(wkSummary.getHigh(), canfCandleStickObserver.getEveSummary().getHigh());
		assertEquals(wkSummary.getLow(), canfCandleStickObserver.getEveSummary().getLow());		
		assertEquals(wkSummary.getClose(), canfCandleStickObserver.getEveSummary().getClose());
	}
}