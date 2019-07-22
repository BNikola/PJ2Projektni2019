package classes.domain.aircrafts.helicopters;

import classes.domain.aircrafts.Aircraft;
import classes.domain.persons.Person;

import java.util.HashMap;
import java.util.List;

public class TransportHelicopter extends Aircraft {

    private double maxCargoWeight;

    public TransportHelicopter(String aircraftId, boolean foreign, double height, String model, int speed, double maxCargoWeight) {
        super(aircraftId, foreign, height, model, speed);
        this.maxCargoWeight = maxCargoWeight;
    }

    public TransportHelicopter(String aircraftId, boolean foreign, double height, String model, List<Person> persons, HashMap<Integer, String> characteristics, int speed, double maxCargoWeight) {
        super(aircraftId, foreign, height, model, persons, characteristics, speed);
        this.maxCargoWeight = maxCargoWeight;
    }

    public double getMaxCargoWeight() {
        return maxCargoWeight;
    }

    public void setMaxCargoWeight(double maxCargoWeight) {
        this.maxCargoWeight = maxCargoWeight;
    }

    @Override
    public String toString() {
        return "TransportHelicopter{" +
                "maxCargoWeight=" + maxCargoWeight +
                super.toString() +
                '}';
    }
}