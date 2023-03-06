package edu.umb.cs681.hw05;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class BostonHousingTest {
	private static List<HousingDataset> hDList;

	@BeforeAll
	public static void setup() {
		hDList = Utils.parseCSV();
	}

	public void processOne() {
		Statistics dPO = new Statistics(
				hDList.stream().mapToDouble(HousingDataset::getMedv).max().orElse(Double.NaN),
				hDList.stream().mapToDouble(HousingDataset::getMedv).min().orElse(Double.NaN),
				hDList.stream().mapToDouble(HousingDataset::getMedv).average().orElse(Double.NaN));

		assertEquals(50.0, dPO.getHighest());
		assertEquals(5.0, dPO.getLowest());
		assertEquals(22.532806324110673, dPO.getAverage());
	}

	public void processTwo() {
		// Processing two
		List<HousingDataset> TopTen = Utils.getTopTenPercentCrimPtratio(hDList); // MAX,
		// MIN, AVG PRICE
		Statistics dPO1 = new Statistics(
				TopTen.stream().mapToDouble(HousingDataset::getMedv).max().orElse(Double.NaN),
				TopTen.stream().mapToDouble(HousingDataset::getMedv).min().orElse(Double.NaN),
				TopTen.stream().mapToDouble(HousingDataset::getMedv).average().orElse(Double.NaN));

		assertEquals(50.0, dPO1.getHighest());
		assertEquals(16.5, dPO1.getLowest());
		assertEquals(29.28, dPO1.getAverage());

		// MAX, MIN, AVG NOX
		Statistics dPO2 = new Statistics(
				TopTen.stream().mapToDouble(HousingDataset::getNox).max().orElse(Double.NaN),
				TopTen.stream().mapToDouble(HousingDataset::getNox).min().orElse(Double.NaN),
				TopTen.stream().mapToDouble(HousingDataset::getNox).average().orElse(Double.NaN));

		assertEquals(0.538, dPO2.getHighest());
		assertEquals(0.385, dPO2.getLowest());
		assertEquals(0.43554000000000004, dPO2.getAverage());


		// MAX, MIN, AVG RM
		Statistics dPO3 = new Statistics(
				TopTen.stream().mapToDouble(HousingDataset::getRm).max().orElse(Double.NaN),
				TopTen.stream().mapToDouble(HousingDataset::getRm).min().orElse(Double.NaN),
				TopTen.stream().mapToDouble(HousingDataset::getRm).average().orElse(Double.NaN));

		assertEquals(8.034, dPO3.getHighest());
		assertEquals(5.869, dPO3.getLowest());
		assertEquals(6.738519999999999, dPO3.getAverage());


	}

	public void processThree() {
		// Processing 3
		// To get the top 10 percent of the least areas with lowest population
		List<HousingDataset> hDLSP = Utils.getTopTenPercentLeastPop(hDList);

		// MAX MIN PRICE// MAX, MIN, AVG PRICE
		Statistics dPO4 = new Statistics(
				hDLSP.stream().mapToDouble(HousingDataset::getMedv).max().orElse(Double.NaN),
				hDLSP.stream().mapToDouble(HousingDataset::getMedv).min().orElse(Double.NaN),
				hDLSP.stream().mapToDouble(HousingDataset::getMedv).average().orElse(Double.NaN));

		assertEquals(27.5, dPO4.getHighest());
		assertEquals(7.0, dPO4.getLowest());
		assertEquals(13.792, dPO4.getAverage());
	}

	public void processFour() {
		// Processing 4
		// Highest Tax rates
		List<HousingDataset> hDLTax = Utils.getTopTenPercentHighestTax(hDList);

		// MAX MIN PRICE// MAX, MIN, AVG PRICE
		Statistics dPO5 = new Statistics(
				hDLTax.stream().mapToDouble(HousingDataset::getMedv).max().orElse(Double.NaN),
				hDLTax.stream().mapToDouble(HousingDataset::getMedv).min().orElse(Double.NaN),
				hDLTax.stream().mapToDouble(HousingDataset::getMedv).average().orElse(Double.NaN));

		assertEquals(38.7, dPO5.getHighest());
		assertEquals(11.8, dPO5.getLowest());
		assertEquals(23.081999999999997, dPO5.getAverage());
	}

	@Test
	public void allProcesses() throws InterruptedException {
		Thread thread1 = new Thread(() -> {
			processOne();
		});

		Thread thread2 = new Thread(() -> {
			processTwo();
		});

		Thread thread3 = new Thread(() -> {
			processThree();
		});

		Thread thread4 = new Thread(() -> {
			processFour();
		});

		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
		thread1.join();
		thread2.join();
		thread3.join();
		thread4.join();
	}
}
