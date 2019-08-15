package classes.domain.aircrafts.planes;

import classes.domain.aircrafts.Aircraft;
import classes.domain.persons.Person;

import java.util.HashMap;
import java.util.List;

public class PassengerPlane extends Aircraft {

    private int numberOfSeats;
    private Integer maxWeight;

    /**
     * @param numberOfSeats in the passenger plane
     * @param maxWeight of the passenger plane
     */
    public PassengerPlane(String aircraftId, boolean foreign, Integer height, String model, int speed, int numberOfSeats, Integer maxWeight) {
        super(aircraftId, foreign, height, model, speed);
        this.numberOfSeats = numberOfSeats;
        this.maxWeight = maxWeight;
    }

    public PassengerPlane(String aircraftId, boolean foreign, Integer height, String model, List<Person> persons, HashMap<Integer, String> characteristics, int speed, int numberOfSeats) {
        super(aircraftId, foreign, height, model, persons, characteristics, speed);
        this.numberOfSeats = numberOfSeats;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public Integer getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(Integer maxWeight) {
        this.maxWeight = maxWeight;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", PassengerPlane" +
                ", numberOfSeats=" + numberOfSeats +
                ", maxWeight=" + maxWeight;
    }
}
