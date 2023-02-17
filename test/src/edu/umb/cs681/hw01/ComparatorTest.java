package edu.umb.cs681.hw01;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
				.collect(Collectors.partitioningBy((Car car) -> car.getMileage() > 20,
						Collectors.mapping(Car::getModel, Collectors.toList())));

		assertArrayEquals(expectedHigh.toArray(), highLow.get(true).toArray());
	}

	@Test
	public void checkMileageComparatorHighAverage() {
		double avgexp = 35.5;

		Map<Boolean, List<Car>> highLow = usedCars.stream()
				.sorted((Car car1, Car car2) -> car1.getMileage() - car2.getMileage())
				.collect(Collectors.partitioningBy((Car car) -> car.getMileage() > 20));

		double avg =
				highLow.get(true).stream().mapToInt(Car::getMileage).average().orElse(Double.NaN);

		assertEquals(avgexp, avg);
	}

	@Test
	public void checkMileageComparatorHighHighest() {
		int highestexp = 36;

		Map<Boolean, List<Car>> highLow = usedCars.stream()
				.sorted((Car car1, Car car2) -> car1.getMileage() - car2.getMileage())
				.collect(Collectors.partitioningBy((Car car) -> car.getMileage() > 20));

		int highest = highLow.get(true).stream().mapToInt(Car::getMileage).max()
				.orElse(Integer.MAX_VALUE);

		assertEquals(highestexp, highest);
	}

	@Test
	public void checkMileageComparatorHighLowest() {
		int lowestexp = 35;

		Map<Boolean, List<Car>> highLow = usedCars.stream()
				.sorted((Car car1, Car car2) -> car1.getMileage() - car2.getMileage())
				.collect(Collectors.partitioningBy((Car car) -> car.getMileage() > 20));

		int lowest = highLow.get(true).stream().mapToInt(Car::getMileage).min()
				.orElse(Integer.MIN_VALUE);

		assertEquals(lowestexp, lowest);
	}

	@Test
	public void checkMileageComparatorHighCount() {
		long countexp = 2;

		Map<Boolean, List<Car>> highLow = usedCars.stream()
				.sorted((Car car1, Car car2) -> car1.getMileage() - car2.getMileage())
				.collect(Collectors.partitioningBy((Car car) -> car.getMileage() > 20));

		long count = highLow.get(true).stream().mapToInt(Car::getMileage).count();

		assertEquals(countexp, count);
	}

	@Test
	public void checkMileageComparatorLow() {
		ArrayList<String> expectedLow = new ArrayList<>();

		expectedLow.add("charger");
		expectedLow.add("gt");

		Map<Boolean, List<String>> highLow = usedCars.stream()
				.sorted((Car car1, Car car2) -> car1.getMileage() - car2.getMileage())
				.collect(Collectors.partitioningBy((Car car) -> car.getMileage() > 20,
						Collectors.mapping(Car::getModel, Collectors.toList())));

		assertArrayEquals(expectedLow.toArray(), highLow.get(false).toArray());
	}

	@Test
	public void checkMileageComparatorLowAverage() {
		double avgexp = 8.5;

		Map<Boolean, List<Car>> highLow = usedCars.stream()
				.sorted((Car car1, Car car2) -> car1.getMileage() - car2.getMileage())
				.collect(Collectors.partitioningBy((Car car) -> car.getMileage() > 20));

		double avg =
				highLow.get(false).stream().mapToInt(Car::getMileage).average().orElse(Double.NaN);

		assertEquals(avgexp, avg);
	}

	@Test
	public void checkMileageComparatorLowHighest() {
		int highexp = 12;

		Map<Boolean, List<Car>> highLow = usedCars.stream()
				.sorted((Car car1, Car car2) -> car1.getMileage() - car2.getMileage())
				.collect(Collectors.partitioningBy((Car car) -> car.getMileage() > 20));

		int high = highLow.get(false).stream().mapToInt(Car::getMileage).max()
				.orElse(Integer.MAX_VALUE);

		assertEquals(highexp, high);
	}

	@Test
	public void checkMileageComparatorLowLowest() {
		int lowexp = 5;

		Map<Boolean, List<Car>> highLow = usedCars.stream()
				.sorted((Car car1, Car car2) -> car1.getMileage() - car2.getMileage())
				.collect(Collectors.partitioningBy((Car car) -> car.getMileage() > 20));

		int low = highLow.get(false).stream().mapToInt(Car::getMileage).min()
				.orElse(Integer.MIN_VALUE);

		assertEquals(lowexp, low);
	}

	@Test
	public void checkMileageComparatorLowCount() {
		long countexp = 2;

		Map<Boolean, List<Car>> highLow = usedCars.stream()
				.sorted((Car car1, Car car2) -> car1.getMileage() - car2.getMileage())
				.collect(Collectors.partitioningBy((Car car) -> car.getMileage() > 20));

		long count = highLow.get(false).stream().mapToInt(Car::getMileage).count();

		assertEquals(countexp, count);
	}

	@Test
	public void checkParetoComparatorHigh() {
		ArrayList<String> expectedHigh = new ArrayList<>();

		expectedHigh.add("charger");

		for (Car car : usedCars) {
			car.setDominationCount(usedCars);
		}

		Map<Boolean, List<String>> highLow = usedCars.stream().sorted(
				(Car car1, Car car2) -> car1.getDominationCount() - car2.getDominationCount())
				.collect(Collectors.partitioningBy((Car car) -> car.getDominationCount() > 1,
						Collectors.mapping(Car::getModel, Collectors.toList())));

		assertArrayEquals(expectedHigh.toArray(), highLow.get(true).toArray());
	}

	@Test
	public void checkParetoComparatorHighAverage() {
		double avgexp = 2.0;

		for (Car car : usedCars) {
			car.setDominationCount(usedCars);
		}

		Map<Boolean, List<Car>> highLow = usedCars.stream().sorted(
				(Car car1, Car car2) -> car1.getDominationCount() - car2.getDominationCount())
				.collect(Collectors.partitioningBy((Car car) -> car.getDominationCount() > 1));

		double avg = highLow.get(true).stream().mapToInt(Car::getDominationCount).average()
				.orElse(Double.NaN);

		assertEquals(avgexp, avg);
	}

	@Test
	public void checkParetoComparatorHighHighest() {
		int highestexp = 2;

		for (Car car : usedCars) {
			car.setDominationCount(usedCars);
		}

		Map<Boolean, List<Car>> highLow = usedCars.stream().sorted(
				(Car car1, Car car2) -> car1.getDominationCount() - car2.getDominationCount())
				.collect(Collectors.partitioningBy((Car car) -> car.getDominationCount() > 1));

		int highest = highLow.get(true).stream().mapToInt(Car::getDominationCount).max()
				.orElse(Integer.MAX_VALUE);

		assertEquals(highestexp, highest);
	}

	@Test
	public void checkParetoComparatorHighLowest() {
		int lowestexp = 2;

		for (Car car : usedCars) {
			car.setDominationCount(usedCars);
		}

		Map<Boolean, List<Car>> highLow = usedCars.stream().sorted(
				(Car car1, Car car2) -> car1.getDominationCount() - car2.getDominationCount())
				.collect(Collectors.partitioningBy((Car car) -> car.getDominationCount() > 1));

		int lowest = highLow.get(true).stream().mapToInt(Car::getDominationCount).min()
				.orElse(Integer.MIN_VALUE);

		assertEquals(lowestexp, lowest);
	}

	@Test
	public void checkParetoComparatorHighCount() {
		long countexp = 1;

		for (Car car : usedCars) {
			car.setDominationCount(usedCars);
		}

		Map<Boolean, List<Car>> highLow = usedCars.stream().sorted(
				(Car car1, Car car2) -> car1.getDominationCount() - car2.getDominationCount())
				.collect(Collectors.partitioningBy((Car car) -> car.getDominationCount() > 1));

		long count = highLow.get(true).stream().mapToInt(Car::getDominationCount).count();

		assertEquals(countexp, count);
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

		Map<Boolean, List<String>> highLow = usedCars.stream().sorted(
				(Car car1, Car car2) -> car1.getDominationCount() - car2.getDominationCount())
				.collect(Collectors.partitioningBy((Car car) -> car.getDominationCount() > 1,
						Collectors.mapping(Car::getModel, Collectors.toList())));

		assertArrayEquals(expectedLow.toArray(), highLow.get(false).toArray());
	}

	@Test
	public void checkParetoComparatorLowAvergage() {
		double averagexp = 0.6666666666666666;

		for (Car car : usedCars) {
			car.setDominationCount(usedCars);
		}

		Map<Boolean, List<Car>> highLow = usedCars.stream().sorted(
				(Car car1, Car car2) -> car1.getDominationCount() - car2.getDominationCount())
				.collect(Collectors.partitioningBy((Car car) -> car.getDominationCount() > 1));

		double average = highLow.get(false).stream().mapToInt(Car::getDominationCount).average()
				.orElse(Double.NaN);

		assertEquals(averagexp, average);
	}

	@Test
	public void checkParetoComparatorLowHighest() {
		int highestexp = 1;

		for (Car car : usedCars) {
			car.setDominationCount(usedCars);
		}

		Map<Boolean, List<Car>> highLow = usedCars.stream().sorted(
				(Car car1, Car car2) -> car1.getDominationCount() - car2.getDominationCount())
				.collect(Collectors.partitioningBy((Car car) -> car.getDominationCount() > 1));

		int highest = highLow.get(false).stream().mapToInt(Car::getDominationCount).max()
				.orElse(Integer.MAX_VALUE);

		assertEquals(highestexp, highest);
	}

	@Test
	public void checkParetoComparatorLowLowest() {
		int lowestexp = 0;

		for (Car car : usedCars) {
			car.setDominationCount(usedCars);
		}

		Map<Boolean, List<Car>> highLow = usedCars.stream().sorted(
				(Car car1, Car car2) -> car1.getDominationCount() - car2.getDominationCount())
				.collect(Collectors.partitioningBy((Car car) -> car.getDominationCount() > 1));

		int lowest = highLow.get(false).stream().mapToInt(Car::getDominationCount).min()
				.orElse(Integer.MAX_VALUE);

		assertEquals(lowestexp, lowest);
	}

	@Test
	public void checkParetoComparatorLowCount() {
		long countexp = 3;

		for (Car car : usedCars) {
			car.setDominationCount(usedCars);
		}

		Map<Boolean, List<Car>> highLow = usedCars.stream().sorted(
				(Car car1, Car car2) -> car1.getDominationCount() - car2.getDominationCount())
				.collect(Collectors.partitioningBy((Car car) -> car.getDominationCount() > 1));

		long count = highLow.get(false).stream().mapToInt(Car::getDominationCount).count();

		assertEquals(countexp, count);
	}

	@Test
	public void checkPriceComparatorHigh() {
		ArrayList<String> expectedHigh = new ArrayList<>();

		expectedHigh.add("accord");
		expectedHigh.add("gtr r34");

		Map<Boolean, List<String>> highLow = usedCars.stream()
				.sorted((Car car1, Car car2) -> (int) car1.getPrice() - (int) car2.getPrice())
				.collect(Collectors.partitioningBy((Car car) -> car.getPrice() < 20000,
						Collectors.mapping(Car::getModel, Collectors.toList())));

		assertArrayEquals(expectedHigh.toArray(), highLow.get(true).toArray());
	}

	@Test
	public void checkPriceComparatorHighAverage() {
		double averageexp = 16000.0;

		Map<Boolean, List<Car>> highLow = usedCars.stream()
				.sorted((Car car1, Car car2) -> (int) car1.getPrice() - (int) car2.getPrice())
				.collect(Collectors.partitioningBy((Car car) -> car.getPrice() < 20000));

		double average =
				highLow.get(true).stream().mapToDouble(Car::getPrice).average().orElse(Double.NaN);

		assertEquals(averageexp, average);
	}

	@Test
	public void checkPriceComparatorHighHighest() {
		double highestexp = 17000.0;

		Map<Boolean, List<Car>> highLow = usedCars.stream()
				.sorted((Car car1, Car car2) -> (int) car1.getPrice() - (int) car2.getPrice())
				.collect(Collectors.partitioningBy((Car car) -> car.getPrice() < 20000));

		double highest =
				highLow.get(true).stream().mapToDouble(Car::getPrice).max().orElse(Double.NaN);

		assertEquals(highestexp, highest);
	}

	@Test
	public void checkPriceComparatorHighLowest() {
		double lowestexp = 15000.0;

		Map<Boolean, List<Car>> highLow = usedCars.stream()
				.sorted((Car car1, Car car2) -> (int) car1.getPrice() - (int) car2.getPrice())
				.collect(Collectors.partitioningBy((Car car) -> car.getPrice() < 20000));

		double lowest =
				highLow.get(true).stream().mapToDouble(Car::getPrice).min().orElse(Double.NaN);

		assertEquals(lowestexp, lowest);
	}

	@Test
	public void checkPriceComparatorLow() {
		ArrayList<String> expectedLow = new ArrayList<>();

		expectedLow.add("charger");
		expectedLow.add("gt");

		Map<Boolean, List<String>> highLow = usedCars.stream()
				.sorted((Car car1, Car car2) -> (int) car1.getPrice() - (int) car2.getPrice())
				.collect(Collectors.partitioningBy((Car car) -> car.getPrice() < 20000,
						Collectors.mapping(Car::getModel, Collectors.toList())));

		assertArrayEquals(expectedLow.toArray(), highLow.get(false).toArray());
	}

	@Test
	public void checkPriceComparatorLowAverage() {
		double averageexp = 77500.0;

		Map<Boolean, List<Car>> highLow = usedCars.stream()
				.sorted((Car car1, Car car2) -> (int) car1.getPrice() - (int) car2.getPrice())
				.collect(Collectors.partitioningBy((Car car) -> car.getPrice() < 20000));

		double average =
				highLow.get(false).stream().mapToDouble(Car::getPrice).average().orElse(Double.NaN);

		assertEquals(averageexp, average);
	}

	@Test
	public void checkPriceComparatorLowHighest() {
		double highestexp = 110000.0;

		Map<Boolean, List<Car>> highLow = usedCars.stream()
				.sorted((Car car1, Car car2) -> (int) car1.getPrice() - (int) car2.getPrice())
				.collect(Collectors.partitioningBy((Car car) -> car.getPrice() < 20000));

		double highest =
				highLow.get(false).stream().mapToDouble(Car::getPrice).max().orElse(Double.NaN);

		assertEquals(highestexp, highest);
	}

	@Test
	public void checkPriceComparatorLowLowest() {
		double lowestexp = 45000.0;

		Map<Boolean, List<Car>> highLow = usedCars.stream()
				.sorted((Car car1, Car car2) -> (int) car1.getPrice() - (int) car2.getPrice())
				.collect(Collectors.partitioningBy((Car car) -> car.getPrice() < 20000));

		double lowest =
				highLow.get(false).stream().mapToDouble(Car::getPrice).min().orElse(Double.NaN);

		assertEquals(lowestexp, lowest);
	}

	@Test
	public void checkYearComparatorHigh() {
		ArrayList<String> expectedHigh = new ArrayList<>();

		expectedHigh.add("accord");
		expectedHigh.add("gt");

		Map<Boolean, List<String>> highLow =
				usedCars.stream().sorted((Car car1, Car car2) -> car2.getYear() - car1.getYear())
						.collect(Collectors.partitioningBy((Car car) -> car.getYear() > 1985,
								Collectors.mapping(Car::getModel, Collectors.toList())));

		assertArrayEquals(expectedHigh.toArray(), highLow.get(true).toArray());
	}

	@Test
	public void checkYearComparatorHighAverage() {
		double averageexp = 2004.5;

		Map<Boolean, List<Car>> highLow =
				usedCars.stream().sorted((Car car1, Car car2) -> car2.getYear() - car1.getYear())
						.collect(Collectors.partitioningBy((Car car) -> car.getYear() > 1985));

		double average =
				highLow.get(true).stream().mapToDouble(Car::getYear).average().orElse(Double.NaN);

		assertEquals(averageexp, average);
	}

	@Test
	public void checkYearComparatorHighHighest() {
		double highestexp = 2004.5;

		Map<Boolean, List<Car>> highLow =
				usedCars.stream().sorted((Car car1, Car car2) -> car2.getYear() - car1.getYear())
						.collect(Collectors.partitioningBy((Car car) -> car.getYear() > 1985));

		double highest =
				highLow.get(true).stream().mapToInt(Car::getYear).average().orElse(Double.NaN);

		assertEquals(highestexp, highest);
	}

	@Test
	public void checkYearComparatorHighLowest() {
		double lowestexp = 2022.0;

		Map<Boolean, List<Car>> highLow =
				usedCars.stream().sorted((Car car1, Car car2) -> car2.getYear() - car1.getYear())
						.collect(Collectors.partitioningBy((Car car) -> car.getYear() > 1985));

		double lowest =
				highLow.get(true).stream().mapToInt(Car::getYear).max().orElse(Integer.MAX_VALUE);

		assertEquals(lowestexp, lowest);
	}

	@Test
	public void checkYearComparatorLow() {
		ArrayList<String> expectedLow = new ArrayList<>();

		expectedLow.add("gtr r34");
		expectedLow.add("charger");

		Map<Boolean, List<String>> highLow =
				usedCars.stream().sorted((Car car1, Car car2) -> car2.getYear() - car1.getYear())
						.collect(Collectors.partitioningBy((Car car) -> car.getYear() > 1985,
								Collectors.mapping(Car::getModel, Collectors.toList())));

		assertArrayEquals(expectedLow.toArray(), highLow.get(false).toArray());
	}

	@Test
	public void checkYearComparatorLowAverage() {
		double averageexp = 1975.0;

		Map<Boolean, List<Car>> highLow =
				usedCars.stream().sorted((Car car1, Car car2) -> car2.getYear() - car1.getYear())
						.collect(Collectors.partitioningBy((Car car) -> car.getYear() > 1985));

		double average =
				highLow.get(false).stream().mapToInt(Car::getYear).average().orElse(Double.NaN);

		assertEquals(averageexp, average);
	}

	@Test
	public void checkYearComparatorLowHighest() {
		int highestexp = 1981;

		Map<Boolean, List<Car>> highLow =
				usedCars.stream().sorted((Car car1, Car car2) -> car2.getYear() - car1.getYear())
						.collect(Collectors.partitioningBy((Car car) -> car.getYear() > 1985));

		int highest =
				highLow.get(false).stream().mapToInt(Car::getYear).max().orElse(Integer.MAX_VALUE);

		assertEquals(highestexp, highest);
	}

	@Test
	public void checkYearComparatorLowLowest() {
		int lowestexp = 1969;

		Map<Boolean, List<Car>> highLow =
				usedCars.stream().sorted((Car car1, Car car2) -> car2.getYear() - car1.getYear())
						.collect(Collectors.partitioningBy((Car car) -> car.getYear() > 1985));

		int lowest =
				highLow.get(false).stream().mapToInt(Car::getYear).min().orElse(Integer.MIN_VALUE);

		assertEquals(lowestexp, lowest);
	}

	@Test
	public void checkYearComparatorLowCount() {
		long countexp = 2;

		Map<Boolean, List<Car>> highLow =
				usedCars.stream().sorted((Car car1, Car car2) -> car2.getYear() - car1.getYear())
						.collect(Collectors.partitioningBy((Car car) -> car.getYear() > 1985));

		long count =
				highLow.get(false).stream().mapToInt(Car::getYear).count();

		assertEquals(countexp, count);
	}
}
