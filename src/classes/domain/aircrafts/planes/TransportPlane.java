package classes.domain.aircrafts.planes;

import classes.domain.aircrafts.Aircraft;
import classes.domain.persons.Person;

import java.util.HashMap;
import java.util.List;

public class TransportPlane extends Aircraft {

    private double maxCargoWeight;

    public TransportPlane(String aircraftId, boolean foreign, double height, String model, int speed, double maxCargoWeight) {
        super(aircraftId, foreign, height, model, speed);
        this.maxCargoWeight = maxCargoWeight;
    }

    public TransportPlane(String aircraftId, boolean foreign, double height, String model, List<Person> persons, HashMap<Integer, String> characteristics, int speed, double maxCargoWeight) {
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
        return "TransportPlane{" +
                "maxCargoWeight=" + maxCargoWeight +
                super.toString() +
                '}';
    }
}
