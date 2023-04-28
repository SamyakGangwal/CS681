package edu.umb.cs681.hw09;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public record Position(double latitude, double longitude, double altitude) {

    Position change(double newLat, double newLon, double newAlt) {
        return new Position(newLat, newLon, newAlt);
    }

    public List<Double> coordinate() {
        return new ArrayList<>(Arrays.asList(this.latitude, this.longitude, this.altitude));
    }

    public boolean higherAltThan(Position anotherPosition) {
        return anotherPosition.altitude > this.altitude;
    }

    public boolean lowerAltThan(Position anotherPosition) {
        return anotherPosition.altitude < this.altitude;
    }

    public boolean northOf(Position anotherPosition) {
        return anotherPosition.latitude < this.latitude;
    }

    public boolean southOf(Position anotherPosition) {
        return anotherPosition.latitude > this.latitude;
    }
}


