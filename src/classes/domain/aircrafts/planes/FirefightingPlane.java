package classes.domain.aircrafts.planes;

import classes.domain.aircrafts.Aircraft;
import classes.domain.persons.Person;

import java.util.HashMap;
import java.util.List;

public class FirefightingPlane extends Aircraft {

    private double waterCapacity;

    public FirefightingPlane(String aircraftId, boolean foreign, double height, String model, int speed, double waterCapacity) {
        super(aircraftId, foreign, height, model, speed);
        this.waterCapacity = waterCapacity;
    }

    public FirefightingPlane(String aircraftId, boolean foreign, double height, String model, List<Person> persons, HashMap<Integer, String> characteristics, int speed, double waterCapacity) {
        super(aircraftId, foreign, height, model, persons, characteristics, speed);
        this.waterCapacity = waterCapacity;
    }

    public double getWaterCapacity() {
        return waterCapacity;
    }

    public void setWaterCapacity(double waterCapacity) {
        this.waterCapacity = waterCapacity;
    }

    @Override
    public String toString() {
        return "FirefightingPlane{" +
                "waterCapacity=" +
                super.toString() +
                '}';
    }
}
