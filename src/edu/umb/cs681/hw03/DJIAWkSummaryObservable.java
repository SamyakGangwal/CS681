package edu.umb.cs681.hw03;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DJIAWkSummaryObservable extends Observable<WkSummary> {
	private static List<DSummary> dailySummaryList = new ArrayList<>();

	public List<DSummary> getDailySummaryList() {
		return dailySummaryList;
	}

	public void addSummary(DSummary dSummary) {
		DJIAWkSummaryObservable.dailySummaryList.add(dSummary);

		if (dailySummaryList.size() == 5) {

			double open = dailySummaryList.stream().mapToDouble(DSummary::getOpen).findFirst()
					.orElse(Double.NaN);

			double high = dailySummaryList.stream().mapToDouble(DSummary::getHigh).max()
					.orElse(Double.NaN);

			double low = dailySummaryList.stream().mapToDouble(DSummary::getLow).min()
					.orElse(Double.NaN);

			double close = dailySummaryList.stream().mapToDouble(DSummary::getClose).reduce((first, second) -> second).orElse(Double.NaN);


			WkSummary test = new WkSummary(open, high, low, close);

			notifyObservers(test);

			DJIAWkSummaryObservable.dailySummaryList.clear();
		}
	}

	public void parseCSV() {
		Path path = Paths.get("assets/HistoricalPrices.csv");
		try (Stream<String> lines = Files.lines(path)) {
			List<List<Double>> csv = lines.skip(1).map(line -> Stream.of(line.split(",")).skip(1)
					.map(Double::parseDouble).collect(Collectors.toList()))
					.collect(Collectors.toList());
			csv.stream().forEach(p -> {
				DSummary dSum = new DSummary(p.get(0), p.get(1), p.get(2), p.get(3));
				this.addSummary(dSum);
			});
		} catch (IOException ex) {
			// handle exception
		}
	}
}
