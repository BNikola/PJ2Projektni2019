package classes.domain.aircrafts.planes;

import classes.domain.aircrafts.Aircraft;
import classes.domain.persons.Person;

import java.util.HashMap;
import java.util.List;

public class FirefightingPlane extends Aircraft {

    private Integer waterCapacity;

    public FirefightingPlane(String aircraftId, boolean foreign, Integer height, String model, int speed, Integer waterCapacity) {
        super(aircraftId, foreign, height, model, speed);
        this.waterCapacity = waterCapacity;
    }

    public FirefightingPlane(String aircraftId, boolean foreign, Integer height, String model, List<Person> persons, HashMap<Integer, String> characteristics, int speed, Integer waterCapacity) {
        super(aircraftId, foreign, height, model, persons, characteristics, speed);
        this.waterCapacity = waterCapacity;
    }

    public Integer getWaterCapacity() {
        return waterCapacity;
    }

    public void setWaterCapacity(Integer waterCapacity) {
        this.waterCapacity = waterCapacity;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", FirefightingPlane" +
                ", waterCapacity=" + waterCapacity;
    }

    @Override
    public String printCrash() {
        return super.printCrash() + ":FirefightingPlane";
    }

    public void putOutFire() {
        System.out.println("Putting out fire:\n" + this);
    }
}
