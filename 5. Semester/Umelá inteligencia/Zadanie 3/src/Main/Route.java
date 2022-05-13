package Main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Route implements Comparable<Route> {
    private final ArrayList<City> cities = new ArrayList<>();

    public Route(Route route) {
        this.cities.addAll(route.cities);
    }
    public Route(ArrayList<City> cities, boolean shuffle) {
        this.cities.addAll(cities);
        if (shuffle) {
            Collections.shuffle(this.cities);
        }
    }

    // Fitness function, return total route distance
    public double getTotalDistance() {
        double totalDistance = 0;
        for (int i = 0; i < this.cities.size()-1; i++) {
            totalDistance += this.cities.get(i).measureDistance(this.cities.get(i+1));
        }
        totalDistance += this.cities.get(this.cities.size()-1).measureDistance(this.cities.get(0));
        return totalDistance;
    }

    // Return cities ArrayList
    public ArrayList<City> getCities() {
        return this.cities;
    }

    @Override
    public String toString() {
        return Arrays.toString(cities.toArray());
    }

    @Override
    public int compareTo(Route route) {
        if (this.getTotalDistance() < route.getTotalDistance()) {
            return -1;
        }
        else if (route.getTotalDistance() < this.getTotalDistance()) {
            return 1;
        }
        return 0;
    }
}