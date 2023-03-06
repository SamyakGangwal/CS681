package edu.umb.cs681.hw04;

import java.util.Comparator;
import java.util.List;
import java.util.stream.DoubleStream;

/**
 * @param crim per capita crime rate by town
 * @param zn proportion of residential land zoned for lots over 25,000 sq.ft.
 * @param indus proportion of non-retail business acres per town
 * @param chas Charles River dummy variable (= 1 if tract bounds river; 0 otherwise)
 * @param nox nitric oxides concentration (parts per 10 million)
 * @param rm average number of rooms per dwelling
 * @param age weighted distances to five Boston employment centres
 * @param dis index of accessibility to radial highways
 * @param rad full-value property-tax rate per $10,000
 * @param tax pupil-teacher ratio by town
 * @param ptratio 1000(Bk - 0.63)^2 where Bk is the proportion of blacks by town
 * @param b % lower status of the population
 * @param lstat Median value of owner-occupied homes in $1000's
 * @param medv proportion of owner-occupied units built prior to 1940
 */
public class HousingDataset {
    double crim;
    double zn;
    double indus;
    boolean chas;
    double nox;
    double rm;
    double age;
    double dis;
    int rad;
    int tax;
    double ptratio;
    double b;
    double lstat;
    double medv;


    public HousingDataset(double crim, double zn, double indus, boolean chas, double nox, double rm,
            double age, double dis, int rad, int tax, double ptratio, double b, double lstat,
            double medv) {
        this.crim = crim;
        this.zn = zn;
        this.indus = indus;
        this.chas = chas;
        this.nox = nox;
        this.rm = rm;
        this.age = age;
        this.dis = dis;
        this.rad = rad;
        this.tax = tax;
        this.ptratio = ptratio;
        this.b = b;
        this.lstat = lstat;
        this.medv = medv;
    }

    public double getCrim() {
        return crim;
    }

    public double getZn() {
        return zn;
    }

    public double getIndus() {
        return indus;
    }

    public boolean isChas() {
        return chas;
    }

    public double getNox() {
        return nox;
    }

    public double getRm() {
        return rm;
    }

    public double getAge() {
        return age;
    }

    public double getDis() {
        return dis;
    }

    public int getRad() {
        return rad;
    }

    public int getTax() {
        return tax;
    }

    public double getPtratio() {
        return ptratio;
    }

    public double getB() {
        return b;
    }

    public double getLstat() {
        return lstat;
    }

    public double getMedv() {
        return medv;
    }

    public static void main(String[] args) {
        List<HousingDataset> hDList = Utils.parseCSV();

        // Processing one;
        Statistics dPO = new Statistics(
                hDList.stream().filter(hd -> hd.isChas()).mapToDouble(HousingDataset::getMedv).max()
                        .orElse(Double.NaN),
                hDList.stream().filter(hd -> hd.isChas()).mapToDouble(HousingDataset::getMedv).min()
                        .orElse(Double.NaN),
                hDList.stream().filter(hd -> hd.isChas()).mapToDouble(HousingDataset::getMedv)
                        .average().orElse(Double.NaN));

        System.out.println("Highest: " + dPO.getHighest());
        System.out.println("Lowest: " + dPO.getLowest());
        System.out.println("Average: " + dPO.getAverage());
        // Processing Two
        /*
         * 
         * – Identify the areas/blocks within the top (lowest) 10% of “low” crime rate and the top
         * (lowest) 10% of pupil-teacher ratio. – Compute the max, min and average of: • Price • NOX
         * concentration • # of rooms
         */

        // Processing two
        List<HousingDataset> TopTen = Utils.getTopTenPercentCrimPtratio(hDList);
        // MAX, MIN, AVG PRICE
        Statistics dPO1 = new Statistics(
                TopTen.stream().mapToDouble(HousingDataset::getMedv).max().orElse(Double.NaN),
                TopTen.stream().mapToDouble(HousingDataset::getMedv).min().orElse(Double.NaN),
                TopTen.stream().mapToDouble(HousingDataset::getMedv).average().orElse(Double.NaN));

        System.out.println("Highest price: " + dPO1.getHighest());
        System.out.println("Lowest price: " + dPO1.getLowest());
        System.out.println("Average price: " + dPO1.getAverage());

        // MAX, MIN, AVG NOX
        Statistics dPO2 = new Statistics(
                TopTen.stream().mapToDouble(HousingDataset::getNox).max().orElse(Double.NaN),
                TopTen.stream().mapToDouble(HousingDataset::getNox).min().orElse(Double.NaN),
                TopTen.stream().mapToDouble(HousingDataset::getNox).average().orElse(Double.NaN));

        System.out.println("Highest NOX: " + dPO2.getHighest());
        System.out.println("Lowest NOX: " + dPO2.getLowest());
        System.out.println("Average NOX: " + dPO2.getAverage());


        // MAX, MIN, AVG RM
        Statistics dPO3 = new Statistics(
                TopTen.stream().mapToDouble(HousingDataset::getRm).max().orElse(Double.NaN),
                TopTen.stream().mapToDouble(HousingDataset::getRm).min().orElse(Double.NaN),
                TopTen.stream().mapToDouble(HousingDataset::getRm).average().orElse(Double.NaN));

        System.out.println("Highest RM: " + dPO3.getHighest());
        System.out.println("Lowest RM: " + dPO3.getLowest());
        System.out.println("Average RM: " + dPO3.getAverage());


        // Processing 3
        // To get the top 10 percent of the least areas with lowest population
        List<HousingDataset> hDLSP = Utils.getTopTenPercentLeastPop(hDList);

        // MAX MIN PRICE// MAX, MIN, AVG PRICE
        Statistics dPO4 = new Statistics(
                hDLSP.stream().mapToDouble(HousingDataset::getMedv).max().orElse(Double.NaN),
                hDLSP.stream().mapToDouble(HousingDataset::getMedv).min().orElse(Double.NaN),
                hDLSP.stream().mapToDouble(HousingDataset::getMedv).average().orElse(Double.NaN));

        System.out.println("Highest price: " + dPO4.getHighest());
        System.out.println("Lowest price: " + dPO4.getLowest());
        System.out.println("Average price: " + dPO4.getAverage());


        // Processing 4
        // Highest Tax rates
        List<HousingDataset> hDLTax = Utils.getTopTenPercentHighestTax(hDList);

        // MAX MIN PRICE// MAX, MIN, AVG PRICE
        Statistics dPO5 = new Statistics(
                hDLTax.stream().mapToDouble(HousingDataset::getMedv).max().orElse(Double.NaN),
                hDLTax.stream().mapToDouble(HousingDataset::getMedv).min().orElse(Double.NaN),
                hDLTax.stream().mapToDouble(HousingDataset::getMedv).average().orElse(Double.NaN));

        System.out.println("Highest price: " + dPO5.getHighest());
        System.out.println("Lowest price: " + dPO5.getLowest());
        System.out.println("Average price: " + dPO5.getAverage());
    }

}
