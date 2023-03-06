package edu.umb.cs681.hw04;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class Statistics {
    private double highest;
    private double lowest;
    private double average;

    /*
     * 
     * Compute the highest, lowest and average price of those houses.
     * 
    */

    public Statistics(double highest, double lowest, double average) {
        this.highest = highest;
        this.lowest = lowest;
        this.average = average;
    }

    public double getHighest() {
        return highest;
    }

    public double getLowest() {
        return lowest;
    }

    public double getAverage() {
        return average;
    }

	/*
	
	Identify the areas/blocks within the top (lowest) 10% of 
	“low” crime rate and the top (lowest) 10% of pupil-teacher 
	ratio.
	
	sort, get 10 percent of array
	*/
	
}
