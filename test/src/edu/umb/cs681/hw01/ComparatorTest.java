package edu.umb.cs681.hw01;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ComparatorTest {
	private static ArrayList<Car> usedCars = new ArrayList<>();

	@BeforeAll
	public static void setUpList() {
		usedCars.add(new Car("accord", "honda", 36, 2022, 15000));
		usedCars.add(new Car("gtr r34", "nissan", 35, 1981, 17000));
		usedCars.add(new Car("charger", "dodge", 5, 1969, 45000));
		usedCars.add(new Car("gt", "ford", 12, 1987, 110000));
	}

	@Test
	public void checkMileageComparator() {
		ArrayList<String> expectedHigh = new ArrayList<>();

		ArrayList<String> expectedLow = new ArrayList<>();

		expectedLow.add("charger");
		expectedLow.add("gt");
		expectedHigh.add("gtr r34");
		expectedHigh.add("accord");

		Map<Boolean, List<String>> highLow = usedCars.stream()
				.sorted((Car car1, Car car2) -> car1.getMileage() - car2.getMileage())
				.collect(Collectors.partitioningBy((Car car) -> car.getMileage() > 20, Collectors.mapping(Car::getModel, Collectors.toList())));

		assertArrayEquals(expectedHigh.toArray(), highLow.get(true).toArray());
		assertArrayEquals(expectedLow.toArray(), highLow.get(false).toArray());
	}

	@Test
	public void checkParetoComparator() {
		ArrayList<String> expectedHigh = new ArrayList<>();

		ArrayList<String> expectedLow = new ArrayList<>();

		expectedLow.add("accord");
		expectedLow.add("gtr r34");
		expectedLow.add("gt");
		expectedHigh.add("charger");

		for (Car car : usedCars) {
			car.setDominationCount(usedCars);
		}

		Map<Boolean, List<String>> highLow = usedCars.stream()
				.sorted((Car car1, Car car2) -> car1.getDominationCount() - car2.getDominationCount())
				.collect(Collectors.partitioningBy((Car car) -> car.getDominationCount() > 1, Collectors.mapping(Car::getModel, Collectors.toList())));

		assertArrayEquals(expectedHigh.toArray(), highLow.get(true).toArray());
		assertArrayEquals(expectedLow.toArray(), highLow.get(false).toArray());
	}

	@Test
	public void checkPriceComparator() {
		ArrayList<String> expectedHigh = new ArrayList<>();

		ArrayList<String> expectedLow = new ArrayList<>();

		expectedHigh.add("accord");
		expectedHigh.add("gtr r34");
		expectedLow.add("charger");
		expectedLow.add("gt");

		Map<Boolean, List<String>> highLow = usedCars.stream()
				.sorted((Car car1, Car car2) -> (int)car1.getPrice() - (int)car2.getPrice())
				.collect(Collectors.partitioningBy((Car car) -> car.getPrice() < 20000, Collectors.mapping(Car::getModel, Collectors.toList())));

		assertArrayEquals(expectedHigh.toArray(), highLow.get(true).toArray());
		assertArrayEquals(expectedLow.toArray(), highLow.get(false).toArray());
	}

	@Test
	public void checkYearComparator() {
		ArrayList<String> expectedHigh = new ArrayList<>();

		ArrayList<String> expectedLow = new ArrayList<>();

		expectedHigh.add("accord");
		expectedHigh.add("gt");
		expectedLow.add("gtr r34");
		expectedLow.add("charger");

		Map<Boolean, List<String>> highLow = usedCars.stream()
				.sorted((Car car1, Car car2) -> car2.getYear() - car1.getYear())
				.collect(Collectors.partitioningBy((Car car) -> car.getYear() > 1985, Collectors.mapping(Car::getModel, Collectors.toList())));

		assertArrayEquals(expectedHigh.toArray(), highLow.get(true).toArray());
		assertArrayEquals(expectedLow.toArray(), highLow.get(false).toArray());
	}
}
