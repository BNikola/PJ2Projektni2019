package classes.domain.aircrafts;

import classes.domain.persons.Person;
import classes.domain.persons.Pilot;

import java.util.*;

public class Aircraft {

    protected String aircraftId;
    protected boolean foreign;
    protected Integer height;
    protected String model;
    protected List<Person> persons = new ArrayList<>();
    protected HashMap<Integer, String> characteristics = new HashMap<>();
    protected int speed;
    protected boolean crashed = false;

    // region Constructors
    /**
     * @param aircraftId id of a aircraft
     * @param foreign if the aircraft is foreign or not
     * @param height flight height of the aircraft
     * @param model model of the aircraft
     * @param speed speed of the aircraft (pause time)
     */
    public Aircraft(String aircraftId, boolean foreign, Integer height, String model, int speed) {
        this.aircraftId = aircraftId;
        this.foreign = foreign;
        this.height = height;
        this.model = model;
        this.speed = speed;
    }

    /**
     * @param persons persons in the aircraft
     * @param characteristics characteristics of the aircraft <int, string> map
     */
    public Aircraft(String aircraftId, boolean foreign, Integer height, String model, List<Person> persons, HashMap<Integer, String> characteristics, int speed) {
        this.aircraftId = aircraftId;
        this.foreign = foreign;
        this.height = height;
        this.model = model;
        this.persons = persons;
        this.characteristics = characteristics;
        this.speed = speed;
    }
    // endregion

    // region Getters and Setters
    public String getAircraftId() {
        return aircraftId;
    }

    public void setAircraftId(String aircraftId) {
        this.aircraftId = aircraftId;
    }

    public boolean isForeign() {
        return foreign;
    }

    public void setForeign(boolean foreign) {
        this.foreign = foreign;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public HashMap<Integer, String> getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(HashMap<Integer, String> characteristics) {
        this.characteristics = characteristics;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isCrashed() {
        return crashed;
    }

    public void setCrashed(boolean crashed) {
        this.crashed = crashed;
    }

    // endregion

    // region Methods
    /**
     * @param characteristic to add
     */
    public void addCharacteristic(String characteristic) {
        if (characteristics == null) {
            characteristics = new HashMap<>();
            characteristics.put(0, characteristic);
        } else {
            int characteristicNumber = 0;
            Iterator<Map.Entry<Integer, String>> iterator = characteristics.entrySet().iterator();
            while (iterator.hasNext()) {
                characteristicNumber++;
                iterator.next();
            }

            characteristics.put(characteristicNumber, characteristic);
        }
    }


    /**
     * @param person to add to persons in airplane
     */
    public void addPerson(Person person) {
        if (persons != null) {
            persons.add(person);
        } else {
            persons = new ArrayList<>();
            persons.add(person);
        }
    }
    // endregion

    @Override
    public String toString() {
        return " aircraftId='" + aircraftId + '\'' +
                ", foreign=" + foreign +
                ", height=" + height +
                ", model='" + model + '\'' +
                ", persons=" + persons +
                ", characteristics=" + characteristics +
                ", speed=" + speed;
    }
}
