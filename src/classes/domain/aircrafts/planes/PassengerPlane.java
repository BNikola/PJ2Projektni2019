package classes.domain.aircrafts.planes;

import classes.domain.aircrafts.Aircraft;
import classes.domain.persons.Person;

import java.util.HashMap;
import java.util.List;

public class PassengerPlane extends Aircraft {

    private int numberOfSeats;
    private double maxWeight;

    /**
     * @param numberOfSeats in the passenger plane
     * @param maxWeight of the passenger plane
     */
    public PassengerPlane(String aircraftId, boolean foreign, double height, String model, int speed, int numberOfSeats, double maxWeight) {
        super(aircraftId, foreign, height, model, speed);
        this.numberOfSeats = numberOfSeats;
        this.maxWeight = maxWeight;
    }

    public PassengerPlane(String aircraftId, boolean foreign, double height, String model, List<Person> persons, HashMap<Integer, String> characteristics, int speed, int numberOfSeats) {
        super(aircraftId, foreign, height, model, persons, characteristics, speed);
        this.numberOfSeats = numberOfSeats;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(double maxWeight) {
        this.maxWeight = maxWeight;
    }

    @Override
    public String toString() {
        return "PassengerPlane{" +
                "numberOfSeats=" + numberOfSeats +
                ", maxWeight=" + maxWeight + ", " +
                super.toString() +
                '}';
    }
}
