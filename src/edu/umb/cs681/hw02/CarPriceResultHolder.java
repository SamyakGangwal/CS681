package edu.umb.cs681.hw02;

import java.util.ArrayList;

public class CarPriceResultHolder {
	private int numCarExamined;
	private double average;

	public void setNumCarExamined(int numCarExamined) {
		this.numCarExamined = numCarExamined;
	}
		
	public void setAverage(double average) {
		this.average = average;
	}

	public double getAverage() {
		return average;
	}

	public int getNumCarExamined() {
		return numCarExamined;
	}

}