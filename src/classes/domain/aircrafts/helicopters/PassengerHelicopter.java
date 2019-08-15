package classes.domain.aircrafts.helicopters;

import classes.domain.aircrafts.Aircraft;
import classes.domain.persons.Person;

import java.util.HashMap;
import java.util.List;

public class PassengerHelicopter extends Aircraft {

    private int numberOfSeats;

    public PassengerHelicopter(String aircraftId, boolean foreign, Integer height, String model, int speed, int numberOfSeats) {
        super(aircraftId, foreign, height, model, speed);
        this.numberOfSeats = numberOfSeats;
    }

    public PassengerHelicopter(String aircraftId, boolean foreign, Integer height, String model, List<Person> persons, HashMap<Integer, String> characteristics, int speed, int numberOfSeats) {
        super(aircraftId, foreign, height, model, persons, characteristics, speed);
        this.numberOfSeats = numberOfSeats;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", PassengerHelicopter" +
                ", numberOfSeats=" + numberOfSeats;
    }
}
