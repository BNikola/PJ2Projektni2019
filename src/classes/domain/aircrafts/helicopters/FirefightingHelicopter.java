package classes.domain.aircrafts.helicopters;

import classes.domain.aircrafts.Aircraft;
import classes.domain.persons.Person;

import java.util.HashMap;
import java.util.List;

public class FirefightingHelicopter extends Aircraft {

    private Integer waterCapacity;

    public FirefightingHelicopter(String aircraftId, boolean foreign, Integer height, String model, int speed, Integer waterCapacity) {
        super(aircraftId, foreign, height, model, speed);
        this.waterCapacity = waterCapacity;
    }

    public FirefightingHelicopter(String aircraftId, boolean foreign, Integer height, String model, List<Person> persons, HashMap<Integer, String> characteristics, int speed, Integer waterCapacity) {
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
        return "FirefightingHelicopter{" +
                "waterCapacity=" + waterCapacity +
                super.toString() +
                '}';
    }
}