package classes.domain.aircrafts.planes;

import classes.domain.aircrafts.Aircraft;
import classes.domain.persons.Person;

import java.util.HashMap;
import java.util.List;

public class MilitaryBomberPlane extends Aircraft {

    public MilitaryBomberPlane(String aircraftId, boolean foreign, Integer height, String model, int speed) {
        super(aircraftId, foreign, height, model, speed);
    }

    public MilitaryBomberPlane(String aircraftId, boolean foreign, Integer height, String model, List<Person> persons, HashMap<Integer, String> characteristics, int speed) {
        super(aircraftId, foreign, height, model, persons, characteristics, speed);
    }

    @Override
    public String toString() {
        return super.toString() +
                ", MilitaryBomberPlane";
    }

    @Override
    public String printCrash() {
        return super.printCrash() + ":MilitaryBomberPlane";
    }
}
