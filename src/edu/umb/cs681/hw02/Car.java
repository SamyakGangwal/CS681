package edu.umb.cs681.hw02;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Car {
	private String model, make;
	private int mileage, year;
	private float price;
	private int dominationCount;

	public Car(String model, String make, int mileage, int year, float price) {
		this.model = model;
		this.make = make;
		this.mileage = mileage;
		this.year = year;
		this.price = price;
	}

	public String getMake() {
		return make;
	}

	public String getModel() {
		return model;
	}

	public int getYear() {
		return year;
	}

	public int getMileage() {
		return mileage;
	}

	public float getPrice() {
		return price;
	}

	public int getDominationCount() {
		return dominationCount;
	}

	public void setDominationCount(ArrayList<Car> usedCars) {
		for (int i = 0; i < usedCars.size(); i++) {

			if ((this.mileage <= usedCars.get(i).mileage) && (this.price >= usedCars.get(i).price)
					&& (this.year <= usedCars.get(i).year)) {

				if ((this.mileage != usedCars.get(i).mileage)
						|| (this.price != usedCars.get(i).price)
						|| (this.year != usedCars.get(i).year)) {
					this.dominationCount++;
				}
			}
		}
	}

	public static void main(String[] args) {
		ArrayList<Car> usedCars = new ArrayList<>();

		usedCars.add(new Car("accord", "honda", 36, 2022, 15000));
		usedCars.add(new Car("gtr r34", "nissan", 35, 1981, 17000));
		usedCars.add(new Car("charger", "dodge", 5, 1969, 45000));
		usedCars.add(new Car("gt", "ford", 12, 1987, 110000));

		ArrayList<Car> sortedCars = (ArrayList<Car>) usedCars.stream()
				.sorted((Car car1, Car car2) -> car2.getYear() - car1.getYear())
				.collect(Collectors.toList());

		ArrayList<String> expected = new ArrayList<>();

		expected.add("accord");
		expected.add("gt");
		expected.add("gtr r34");
		expected.add("charger");

		int flag = 0;

		for (int i = 0; i < expected.size(); i++) {
			if (!expected.get(i).equals(sortedCars.get(i).getModel())) {
				System.out.println("Error!");
				flag = 1;
				break;
			}
		}

		if (flag == 0) {
			System.out.println("Success!");
		}

		// Rest of the tests are done in the Test folder

	}
}
