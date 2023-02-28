package edu.umb.cs681.hw02;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
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
	public void averagePrice() {

		double averagePrice = usedCars.stream().map(Car::getPrice)
				.reduce(new CarPriceResultHolder(), (result, price) -> {
					result.setAverage((result.getNumCarExamined() * result.getAverage() + price)
							/ (result.getNumCarExamined() + 1));
					result.setNumCarExamined(result.getNumCarExamined() + 1);
					return result;
				}, (finalResult, intermediateResult) -> finalResult).getAverage();

		double avg = 46750.0;

		assertEquals(avg, averagePrice);
	}
}
