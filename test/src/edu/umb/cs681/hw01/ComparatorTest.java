package edu.umb.cs681.hw01;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ComparatorTest {
	private ArrayList<Car> usedCars = new ArrayList<>();

	@BeforeEach
	public void setUpList() {
		usedCars.add(new Car("accord", "honda", 36, 2022, 15000));
		usedCars.add(new Car("gtr r34", "nissan", 35, 1981, 17000));
		usedCars.add(new Car("charger", "dodge", 5, 1969, 45000));
		usedCars.add(new Car("gt", "ford", 12, 1987, 110000));
	}

	@Test
	public void checkMileageComparatorHigh() {
		ArrayList<String> expectedHigh = new ArrayList<>();

		
		expectedHigh.add("gtr r34");
		expectedHigh.add("accord");

		Map<Boolean, List<String>> highLow = usedCars.stream()
				.sorted((Car car1, Car car2) -> car1.getMileage() - car2.getMileage())
				.collect(Collectors.partitioningBy((Car car) -> car.getMileage() > 20, Collectors.mapping(Car::getModel, Collectors.toList())));

		assertArrayEquals(expectedHigh.toArray(), highLow.get(true).toArray());
	}

	@Test
	public void checkMileageComparatorLow() {
		ArrayList<String> expectedLow = new ArrayList<>();

		expectedLow.add("charger");
		expectedLow.add("gt");

		Map<Boolean, List<String>> highLow = usedCars.stream()
				.sorted((Car car1, Car car2) -> car1.getMileage() - car2.getMileage())
				.collect(Collectors.partitioningBy((Car car) -> car.getMileage() > 20, Collectors.mapping(Car::getModel, Collectors.toList())));

		assertArrayEquals(expectedLow.toArray(), highLow.get(false).toArray());
	}

	@Test
	public void checkParetoComparatorHigh() {
		ArrayList<String> expectedHigh = new ArrayList<>();

		expectedHigh.add("charger");

		for (Car car : usedCars) {
			car.setDominationCount(usedCars);
		}

		Map<Boolean, List<String>> highLow = usedCars.stream()
				.sorted((Car car1, Car car2) -> car1.getDominationCount() - car2.getDominationCount())
				.collect(Collectors.partitioningBy((Car car) -> car.getDominationCount() > 1, Collectors.mapping(Car::getModel, Collectors.toList())));

		assertArrayEquals(expectedHigh.toArray(), highLow.get(true).toArray());
	}

	@Test
	public void checkParetoComparatorLow() {
		ArrayList<String> expectedLow = new ArrayList<>();

		expectedLow.add("accord");
		expectedLow.add("gtr r34");
		expectedLow.add("gt");

		for (Car car : usedCars) {
			car.setDominationCount(usedCars);
		}

		Map<Boolean, List<String>> highLow = usedCars.stream()
				.sorted((Car car1, Car car2) -> car1.getDominationCount() - car2.getDominationCount())
				.collect(Collectors.partitioningBy((Car car) -> car.getDominationCount() > 1, Collectors.mapping(Car::getModel, Collectors.toList())));

		assertArrayEquals(expectedLow.toArray(), highLow.get(false).toArray());
	}

	@Test
	public void checkPriceComparatorHigh() {
		ArrayList<String> expectedHigh = new ArrayList<>();

		expectedHigh.add("accord");
		expectedHigh.add("gtr r34");

		Map<Boolean, List<String>> highLow = usedCars.stream()
				.sorted((Car car1, Car car2) -> (int)car1.getPrice() - (int)car2.getPrice())
				.collect(Collectors.partitioningBy((Car car) -> car.getPrice() < 20000, Collectors.mapping(Car::getModel, Collectors.toList())));

		assertArrayEquals(expectedHigh.toArray(), highLow.get(true).toArray());
	}

	@Test
	public void checkPriceComparatorLow() {
		ArrayList<String> expectedLow = new ArrayList<>();

		expectedLow.add("charger");
		expectedLow.add("gt");

		Map<Boolean, List<String>> highLow = usedCars.stream()
				.sorted((Car car1, Car car2) -> (int)car1.getPrice() - (int)car2.getPrice())
				.collect(Collectors.partitioningBy((Car car) -> car.getPrice() < 20000, Collectors.mapping(Car::getModel, Collectors.toList())));

		assertArrayEquals(expectedLow.toArray(), highLow.get(false).toArray());
	}

	@Test
	public void checkYearComparatorHigh() {
		ArrayList<String> expectedHigh = new ArrayList<>();

		expectedHigh.add("accord");
		expectedHigh.add("gt");

		Map<Boolean, List<String>> highLow = usedCars.stream()
				.sorted((Car car1, Car car2) -> car2.getYear() - car1.getYear())
				.collect(Collectors.partitioningBy((Car car) -> car.getYear() > 1985, Collectors.mapping(Car::getModel, Collectors.toList())));

		assertArrayEquals(expectedHigh.toArray(), highLow.get(true).toArray());
	}

	@Test
	public void checkYearComparatorLow() {
		ArrayList<String> expectedLow = new ArrayList<>();

		expectedLow.add("gtr r34");
		expectedLow.add("charger");

		Map<Boolean, List<String>> highLow = usedCars.stream()
				.sorted((Car car1, Car car2) -> car2.getYear() - car1.getYear())
				.collect(Collectors.partitioningBy((Car car) -> car.getYear() > 1985, Collectors.mapping(Car::getModel, Collectors.toList())));

		assertArrayEquals(expectedLow.toArray(), highLow.get(false).toArray());
	}
}
