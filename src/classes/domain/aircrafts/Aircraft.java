package classes.domain.aircrafts;

import classes.controllers.Controller;
import classes.domain.extras.FlightArea;
import classes.domain.extras.FlightDirection;
import classes.domain.persons.Person;
import classes.simulator.Simulator;

import java.util.*;
import java.util.logging.Level;

public class Aircraft extends Thread {

    // region Members
    // description members
    protected String aircraftId;
    protected boolean foreign;
    protected Integer height;   // integer because of random generator
    protected String model;
    protected List<Person> persons = new ArrayList<>();
    protected HashMap<Integer, String> characteristics = new HashMap<>();

    // movement members
    protected int speed;
    protected boolean crashed = false;
    protected Integer positionX;
    protected Integer positionY;
    protected FlightDirection direction;
    protected boolean doneMoving = false;

    private static Object lock = new Object();

    // endregion


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

    public Integer getPositionX() {
        return positionX;
    }

    public void setPositionX(Integer positionX) {
        this.positionX = positionX;
    }

    public Integer getPositionY() {
        return positionY;
    }

    public void setPositionY(Integer positionY) {
        this.positionY = positionY;
    }

    public FlightDirection getDirection() {
        return direction;
    }

    public void setDirection(FlightDirection direction) {
        this.direction = direction;
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


    /**
     *  sets direction and position randomly
     */
    public void setPositionAndDirection() {
        Random rand = new Random();
//        Integer flightDirection = rand.nextInt(4); // todo - uncomment this
        Integer flightDirection = 0;
        direction = FlightDirection.values()[flightDirection];
        switch (flightDirection) {
            case 0:
                // UP - bottom up
                positionX = FlightArea.getSizeX() - 1;
                positionY = rand.nextInt(FlightArea.getSizeY());
                break;
            case 1:
                // RIGHT - RTL
                positionX = rand.nextInt(FlightArea.getSizeX());
                positionY = 0;
                break;
            case 2:
                // DOWN - top to bottom
                positionX = 0;
                positionY = rand.nextInt(FlightArea.getSizeY());
                break;
            case 3:
                // LEFT - LTR
                positionX = rand.nextInt(FlightArea.getSizeX());
                positionY = FlightArea.getSizeY() - 1;
                break;
            default:
                // right
                positionX = rand.nextInt(FlightArea.getSizeX());
                positionY = 0;
        }
    }


    // todo - implement run

    // todo - implement motion
    //  - fix and finish run method for aircraft


    @Override
    public void run() {
        System.out.println(Simulator.flightArea);
        System.out.println(this.getClass());
        while(!doneMoving) {
            switch (direction) {
                case UP:
                    System.out.println(positionX + " " + positionY);
                    while (positionX > 0) {
                        Simulator.flightArea.setPosition(null, positionX, positionY, height);
                        positionX--;
                        Simulator.flightArea.setPosition(this, positionX, positionY, height);
//                        Controller.app.refreshTextArea();
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // todo upisivati u fajl a ne u text field
                    }

                    doneMoving = true;
                    break;
                case LEFT:

                    break;
                case DOWN:
                    System.out.println("JEeeeeaafsdasf");
                    break;
                case RIGHT:

                    break;
                default:
                    doneMoving = true;
                    break;
            }
            doneMoving = true;
        }
        System.out.println("Gotov");
        Simulator.flightArea.setPosition(null, positionX, positionY, height);
        System.out.println(Simulator.flightArea);
//        Controller.app.refreshTextArea();
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
                ", speed=" + speed +
                ", direction=" + direction +
                ", [" + positionX + ", " + positionY + "]";
    }
}
