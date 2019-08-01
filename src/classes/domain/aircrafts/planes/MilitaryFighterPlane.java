package classes.domain.aircrafts.planes;

import classes.domain.aircrafts.Aircraft;
import classes.domain.persons.Person;

import java.util.HashMap;
import java.util.List;

public class MilitaryFighterPlane extends Aircraft {

    public MilitaryFighterPlane(String aircraftId, boolean foreign, Integer height, String model, int speed) {
        super(aircraftId, foreign, height, model, speed);
    }

    public MilitaryFighterPlane(String aircraftId, boolean foreign, Integer height, String model, List<Person> persons, HashMap<Integer, String> characteristics, int speed) {
        super(aircraftId, foreign, height, model, persons, characteristics, speed);
    }
}
