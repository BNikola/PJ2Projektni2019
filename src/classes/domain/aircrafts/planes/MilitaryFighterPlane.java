package classes.domain.aircrafts.planes;

import classes.AirTrafficControl;
import classes.domain.aircrafts.Aircraft;
import classes.domain.extras.FlightArea;
import classes.domain.interfaces.MilitaryAircraftInterface;
import classes.domain.persons.Person;
import classes.simulator.Simulator;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

public class MilitaryFighterPlane extends Aircraft implements MilitaryAircraftInterface {

    private boolean escort = false;
    private Aircraft intruder = null;
    /**
     * Position:
     * -1 left
     * 0 behind
     * 1 right
     *
     */
    private int position;

    // region Constructors
    public MilitaryFighterPlane(String aircraftId, boolean foreign, Integer height, String model, int speed) {
        super(aircraftId, foreign, height, model, speed);
    }

    public MilitaryFighterPlane(String aircraftId, boolean foreign, Integer height, String model, int speed, boolean escort, Aircraft intruder, int position) {
        super(aircraftId, foreign, height, model, speed);
        this.escort = escort;
        this.intruder = intruder;
        this.position = position;
    }

    public MilitaryFighterPlane(String aircraftId, boolean foreign, Integer height, String model, List<Person> persons, HashMap<Integer, String> characteristics, int speed) {
        super(aircraftId, foreign, height, model, persons, characteristics, speed);
    }
    // endregion

    // region Getters and Setters
    public boolean isEscort() {
        return escort;
    }

    public void setEscort(boolean escort) {
        this.escort = escort;
    }

    public Aircraft getIntruder() {
        return intruder;
    }

    public void setIntruder(Aircraft intruder) {
        this.intruder = intruder;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    // endregion

    @Override
    public String toString() {
        return super.toString() +
                ", MilitaryFighterPlane";
    }

    @Override
    public String printCrash() {
        return super.printCrash() + ":MilitaryFighterPlane";
    }

    @Override
    public synchronized void run() {
        if (!escort) {
            super.run();
        } else {
            while(!doneMoving) {
                pursuit();
                if (crashed) {
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
            Simulator.aircraftRegistry.remove(aircraftId);      // removes this id from registry
            Simulator.flightArea.setPosition(null, positionX, positionY, height);
        }
    }

    private void pursuit() {
        switch (direction) {
            case UP:
                while (positionX > 0 && !crashed) {
                    if (position == 1) {
                        Object scanAhead = Simulator.flightArea.getPosition(positionX, positionY - 1, intruder.getHeight());
                        if (scanAhead != null) {
                            if (((Aircraft) scanAhead).getAircraftId().equals(intruder.getAircraftId())) {
                                intruder.setCrashed(true);
                            }
                        }
                    } else if (position == -1) {
                        Object scanAhead = Simulator.flightArea.getPosition(positionX, positionY + 1, intruder.getHeight());
                        if (scanAhead != null) {
                            if (((Aircraft) scanAhead).getAircraftId().equals(intruder.getAircraftId())) {
                                intruder.setCrashed(true);
                            }
                        }
                    } else if (position == 0) {
                        Object scanAhead = Simulator.flightArea.getPosition(positionX - 1, positionY, intruder.getHeight());
                        if (scanAhead != null) {
                            if (((Aircraft) scanAhead).getAircraftId().equals(intruder.getAircraftId())) {
                                intruder.setCrashed(true);
                            }
                        }
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
                doneMoving = true;
                break;
            case LEFT:
                while (positionY > 0 && !crashed) {
                    if (position == 1) {
                        Object scanAhead = Simulator.flightArea.getPosition(positionX + 1, positionY, intruder.getHeight());
                        if (scanAhead != null) {
                            if (((Aircraft) scanAhead).getAircraftId().equals(intruder.getAircraftId())) {
                                intruder.setCrashed(true);
                            }
                        }
                    } else if (position == -1) {
                        Object scanAhead = Simulator.flightArea.getPosition(positionX - 1, positionY, intruder.getHeight());
                        if (scanAhead != null) {
                            if (((Aircraft) scanAhead).getAircraftId().equals(intruder.getAircraftId())) {
                                intruder.setCrashed(true);
                            }
                        }
                    } else if (position == 0) {
                        Object scanAhead = Simulator.flightArea.getPosition(positionX, positionY - 1, intruder.getHeight());
                        if (scanAhead != null) {
                            if (((Aircraft) scanAhead).getAircraftId().equals(intruder.getAircraftId())) {
                                intruder.setCrashed(true);
                            }
                        }
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
                doneMoving = true;
                break;

            case DOWN:
                while ((positionX < FlightArea.getSizeX() - 1) && !crashed) {
                    if (position == 1) {
                        Object scanAhead = Simulator.flightArea.getPosition(positionX, positionY + 1, intruder.getHeight());
                        if (scanAhead != null) {
                            if (((Aircraft) scanAhead).getAircraftId().equals(intruder.getAircraftId())) {
                                intruder.setCrashed(true);
                            }
                        }
                    } else if (position == -1) {
                        Object scanAhead = Simulator.flightArea.getPosition(positionX, positionY - 1, intruder.getHeight());
                        if (scanAhead != null) {
                            if (((Aircraft) scanAhead).getAircraftId().equals(intruder.getAircraftId())) {
                                intruder.setCrashed(true);
                            }
                        }
                    } else if (position == 0) {
                        Object scanAhead = Simulator.flightArea.getPosition(positionX + 1, positionY, intruder.getHeight());
                        if (scanAhead != null) {
                            if (((Aircraft) scanAhead).getAircraftId().equals(intruder.getAircraftId())) {
                                intruder.setCrashed(true);
                            }
                        }
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
                doneMoving = true;
                break;
            case RIGHT:
                while ((positionY < FlightArea.getSizeY() - 1) && !crashed) {
                    if (position == 1) {
                        Object scanAhead = Simulator.flightArea.getPosition(positionX - 1, positionY, intruder.getHeight());
                        if (scanAhead != null) {
                            if (((Aircraft) scanAhead).getAircraftId().equals(intruder.getAircraftId())) {
                                intruder.setCrashed(true);
                            }
                        }
                    } else if (position == -1) {
                        Object scanAhead = Simulator.flightArea.getPosition(positionX + 1, positionY, intruder.getHeight());
                        if (scanAhead != null) {
                            if (((Aircraft) scanAhead).getAircraftId().equals(intruder.getAircraftId())) {
                                intruder.setCrashed(true);
                            }
                        }
                    } else if (position == 0) {
                        Object scanAhead = Simulator.flightArea.getPosition(positionX, positionY + 1, intruder.getHeight());
                        if (scanAhead != null) {
                            if (((Aircraft) scanAhead).getAircraftId().equals(intruder.getAircraftId())) {
                                intruder.setCrashed(true);
                            }
                        }
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
                doneMoving = true;
                break;
            default:
                doneMoving = true;
                break;
        }
    }

    @Override
    public void attackTarget(int targetLocation) {
        String location = (targetLocation == 0) ? " on the ground." : " in the air.";
        System.out.println("Attacking target" + location);
    }

    @Override
    public void carryArmament() {
        System.out.println("Carry armament:\n" + this);
    }
}