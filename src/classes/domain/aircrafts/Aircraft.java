package classes.domain.aircrafts;

import classes.AirTrafficControl;
import classes.Radar;
import classes.domain.aircrafts.planes.MilitaryFighterPlane;
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
    protected boolean directionChanged = false;

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
        this.crashed = false;
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
        this.crashed = false;
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
        Integer flightDirection = rand.nextInt(4);
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



    @Override
    public synchronized void run() {
        while(!doneMoving) {
            move();
            if (crashed) {
                System.out.println("Srusio se" + this);
                doneMoving = true;
            } else {
                switch (direction) {
                    case UP:
                        if (positionX == 0) {
                            doneMoving = true;
                        }
                        break;
                    case LEFT:
                        if (positionY == 0) {
                            doneMoving = true;
                        }
                        break;
                    case DOWN:
                        if (positionX == FlightArea.getSizeX() - 1) {
                            doneMoving = true;
                        }
                        break;
                    case RIGHT:
                        if (positionY == FlightArea.getSizeX() - 1) {
                            doneMoving = true;
                        }
                }
            }
        }
        // todo - remove this after check for all directions
        System.out.println("Zadnje pozicije: " + positionX + " - " + positionY);
        Simulator.aircraftRegistry.remove(aircraftId);      // removes this id from registry
        Simulator.flightArea.setPosition(null, positionX, positionY, height);

        if (foreign) {
            Radar.removeFromDetectedForeign(aircraftId);
        }
//        for testing simple movement
//        System.out.println("Napisao: " + this.getAircraftId() + "\n" + Simulator.flightArea);
//        System.out.println("-----Radar");
//        System.out.println(Radar.flightArea);
    }



    // endregion

    // todo - remove characteristics maybe - check parsing then
    @Override
    public String toString() {
        return "[" + positionX + "-" + positionY + "]" +
                ", aircraftId=" + aircraftId +
                ", height=" + height +
                ", foreign=" + foreign +
                ", characteristics=" + characteristics +
                ", speed=" + speed +
                ", direction=" + direction;
    }


    // region Private methods
    private void move() {
        switch (direction) {
            case UP:
                while (positionX > 0 && !crashed) {
                    System.out.println(this);
                    if (Simulator.flightArea.isNoFlight() && !directionChanged && !foreign && !(this instanceof MilitaryFighterPlane)) {
                        System.out.println("|- " + this);
                        this.changeDirection();
                        break;
                    }
                    Simulator.flightArea.setPosition(this, positionX - 1, positionY, height);
                    Simulator.flightArea.setPosition(null, positionX, positionY, height);
                    positionX--;
                    try {
                        sleep(speed * 1000);
                    } catch (InterruptedException e) {
                        AirTrafficControl.LOGGER.log(Level.SEVERE, e.toString(), e);
                    }
                }
                if (!directionChanged) {
                    doneMoving = true;
                }
                break;
            case LEFT:
                while (positionY > 0 && !crashed) {
                    System.out.println(this);
                    if (Simulator.flightArea.isNoFlight() && !directionChanged && !foreign && !(this instanceof MilitaryFighterPlane)) {
                        System.out.println("|- " + this);
                        this.changeDirection();
                        break;
                    }
                    Simulator.flightArea.setPosition(this, positionX, positionY-1, height);
                    Simulator.flightArea.setPosition(null, positionX, positionY, height);
                    positionY--;
                    try {
                        sleep(speed * 1000);
                    } catch (InterruptedException e) {
                        AirTrafficControl.LOGGER.log(Level.SEVERE, e.toString(), e);
                    }
                }
                if (!directionChanged) {
                    doneMoving = true;
                }
                break;

            case DOWN:
                while ((positionX < FlightArea.getSizeX() - 1) && !crashed && !foreign && !(this instanceof MilitaryFighterPlane)) {
                    System.out.println(this);
                    if (Simulator.flightArea.isNoFlight() && !directionChanged) {
                        System.out.println("|- " + this);
                        this.changeDirection();
                        break;
                    }
                    Simulator.flightArea.setPosition(this, positionX + 1, positionY, height);
                    Simulator.flightArea.setPosition(null, positionX, positionY, height);
                    positionX++;
                    try {
                        sleep(speed * 1000);
                    } catch (InterruptedException e) {
                        AirTrafficControl.LOGGER.log(Level.SEVERE, e.toString(), e);
                    }
                }
                if (!directionChanged) {
                    doneMoving = true;
                }
                break;
            case RIGHT:
                while ((positionY < FlightArea.getSizeY() - 1) && !crashed && !foreign && !(this instanceof MilitaryFighterPlane)) {
                    System.out.println(this);
                    if (Simulator.flightArea.isNoFlight() && !directionChanged) {
                        System.out.println("|- " + this);
                        this.changeDirection();
                        break;
                    }
                    Simulator.flightArea.setPosition(this, positionX, positionY + 1, height);
                    Simulator.flightArea.setPosition(null, positionX, positionY, height);
                    positionY++;
                    try {
                        sleep(speed * 1000);
                    } catch (InterruptedException e) {
                        AirTrafficControl.LOGGER.log(Level.SEVERE, e.toString(), e);
                    }
                }
                if (!directionChanged) {
                    doneMoving = true;
                }
                break;
            default:
                doneMoving = true;
                break;
        }
    }



    // return the correct flight direction
    private FlightDirection getClosestExit() {
        int index = 0;
        if (positionX == 0 && positionY == 0 && direction.equals(FlightDirection.DOWN)) {
            return FlightDirection.LEFT;
        } else if (positionX == 0 && (positionY == FlightArea.getSizeY() - 1) && direction.equals(FlightDirection.DOWN)) {
            return FlightDirection.RIGHT;
        } else if ((positionX == FlightArea.getSizeX() - 1) && positionY == 0 && direction.equals(FlightDirection.UP)) {
            return FlightDirection.LEFT;
        } else if ((positionX == FlightArea.getSizeX() - 1) && (positionY == FlightArea.getSizeY() - 1) && direction.equals(FlightDirection.LEFT)) {
            return FlightDirection.DOWN;
        } else {

            int top = positionX;
            int min = top;
            int bottom = FlightArea.getSizeX() - 1 - positionX;
            int left = positionY;
            int right = FlightArea.getSizeY() - 1 - positionY;
            int[] directionArray = {top, bottom, left, right};
            for (int i = 0; i < directionArray.length; i++) {
                if (directionArray[i] == 0) {
                    index = i;
                    break;
                } else if (directionArray[i] <= min) {
                    min = directionArray[i];
                    index = i;
                }
            }

            switch (index) {
                case 0:
                    return FlightDirection.UP;
                case 1:
                    return FlightDirection.DOWN;
                case 2:
                    return FlightDirection.LEFT;
                case 3:
                    return FlightDirection.RIGHT;
                default:
                    return FlightDirection.UP;
            }
        }
    }

    private void changeDirection() {
        FlightDirection fd = getClosestExit();
        if (!fd.isOpposite(direction)) {
            // just change direction and move
            direction = fd;
        } else {
            // turn around
            switch (direction) {
                case UP:
                    Simulator.flightArea.setPosition(this, positionX, positionY - 1, height);
                    Simulator.flightArea.setPosition(null, positionX, positionY, height);
                    positionY--;
                    break;
                case LEFT:
                    Simulator.flightArea.setPosition(this, positionX + 1, positionY, height);
                    Simulator.flightArea.setPosition(null, positionX, positionY, height);
                    positionX++;
                    break;
                case DOWN:
                    Simulator.flightArea.setPosition(this, positionX, positionY + 1, height);
                    Simulator.flightArea.setPosition(null, positionX, positionY, height);
                    positionY++;
                    break;
                case RIGHT:
                    Simulator.flightArea.setPosition(this, positionX - 1, positionY, height);
                    Simulator.flightArea.setPosition(null, positionX, positionY, height);
                    positionX--;
                    break;
            }
            direction = fd;
        }
        directionChanged = true;
    }
    // endregion Private methods

    public static void main(String[] args) {
        Simulator s = new Simulator();
        Aircraft a = s.generateRandomAircraft();
        Aircraft b = s.generateRandomAircraft();
        System.out.println(a);
        System.out.println(b);
        a.positionX = 0;
        a.positionY = FlightArea.getSizeY() - 1;
        a.setDirection(FlightDirection.RIGHT);
        System.out.println(a);
        System.out.println(a.getClosestExit());
        System.out.println(b.getClosestExit());
        System.out.println(a.getDirection().equals(a.getClosestExit()));
        System.out.println(b.getDirection().equals(b.getClosestExit()));
        a.changeDirection();
        b.changeDirection();


        System.out.println(a);
        System.out.println(b);
        int height = Integer.parseInt(a.toString().split(", ")[2].split("=")[1]);
        System.out.println(height);

    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else {
            Aircraft a = (Aircraft) obj; // TODO: 18.8.2019. maybe change this to some superclass of Aircraft and Rocket
            return height.equals(a.height);
        }
    }

    public String printCrash() {
        return aircraftId + ":" + foreign;
    }
}
