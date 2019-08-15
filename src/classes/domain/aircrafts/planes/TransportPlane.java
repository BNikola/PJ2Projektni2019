package classes.domain.aircrafts.planes;

import classes.domain.aircrafts.Aircraft;
import classes.domain.persons.Person;

import java.util.HashMap;
import java.util.List;

public class TransportPlane extends Aircraft {

    private Integer maxCargoWeight;

    public TransportPlane(String aircraftId, boolean foreign, Integer height, String model, int speed, Integer maxCargoWeight) {
        super(aircraftId, foreign, height, model, speed);
        this.maxCargoWeight = maxCargoWeight;
    }

    public TransportPlane(String aircraftId, boolean foreign, Integer height, String model, List<Person> persons, HashMap<Integer, String> characteristics, int speed, Integer maxCargoWeight) {
        super(aircraftId, foreign, height, model, persons, characteristics, speed);
        this.maxCargoWeight = maxCargoWeight;
    }

    public Integer getMaxCargoWeight() {
        return maxCargoWeight;
    }

    public void setMaxCargoWeight(Integer maxCargoWeight) {
        this.maxCargoWeight = maxCargoWeight;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", TransportPlane" +
                ", maxCargoWeight=" + maxCargoWeight;
    }
}
