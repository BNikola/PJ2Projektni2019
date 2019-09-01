package classes.domain.extras;

import classes.AirTrafficControl;
import classes.Radar;
import classes.domain.aircrafts.Aircraft;
import classes.domain.aircrafts.planes.MilitaryFighterPlane;

import java.io.*;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;

public class FlightArea {

    // region Members
    // defining size of the map
    private static int SIZE_X;
    private static int SIZE_Y;
    private static final Properties PROPERTIES = new Properties();
    private static String pathToConfig = System.getProperty("user.dir")
            + File.separator + "src"
            + File.separator + "configs"
            + File.separator + "config.properties";

    // matrice
    private Field[][] flightArea;

    // lock object
    public AtomicBoolean lock = new AtomicBoolean();
    // test for crash
    private volatile boolean crash = false;
    private volatile boolean noFlight = false;

    // endregion

    static {
        // loading properties file

        try {
            PROPERTIES.load(new FileInputStream(new File(pathToConfig)));
        } catch (IOException e) {
            AirTrafficControl.LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        // loading properties
        try {
            SIZE_X = Integer.parseInt(PROPERTIES.getProperty("sizeX"));
            SIZE_Y = Integer.parseInt(PROPERTIES.getProperty("sizeY"));
        } catch (NumberFormatException e) {
            AirTrafficControl.LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    public FlightArea() {
        super();
        flightArea = new Field[SIZE_X][SIZE_Y];
        for (int i = 0; i < SIZE_X; i++) {
            for (int j = 0; j < SIZE_Y; j++) {
                flightArea[i][j] = new Field();
            }
        }
    }

    //region Getters and Setters

    public static int getSizeX() {
        return SIZE_X;
    }

    public static void setSizeX(int sizeX) {
        SIZE_X = sizeX;
    }

    public static int getSizeY() {
        return SIZE_Y;
    }

    public static void setSizeY(int sizeY) {
        SIZE_Y = sizeY;
    }

    public Field[][] getFlightArea() {
        return flightArea;
    }

    public void setFlightArea(Field[][] flightArea) {
        this.flightArea = flightArea;
    }

    public AtomicBoolean getLock() {
        return lock;
    }

    public void setLock(AtomicBoolean lock) {
        this.lock = lock;
    }

    public boolean isCrash() {
        return crash;
    }

    public void setCrash(boolean crash) {
        this.crash = crash;
    }

    public boolean isNoFlight() {
        return noFlight;
    }

    public void setNoFlight(boolean noFlight) {
        this.noFlight = noFlight;
    }

    // get-set object to position and height
    public Object getPosition(int x, int y, int height) {
        return flightArea[x][y].getObjectFromHeight(height);
    }

    public synchronized void setPosition(Object object, int x, int y, int height) {
        if (object != null) {
            if (object.equals(flightArea[x][y].getObjectFromHeight(height))) {
                crash = true;
                // TODO: 1.9.2019. Can change this to accomodate Rockets too
                Aircraft objectFromHeight = (Aircraft) flightArea[x][y].getObjectFromHeight(height);
                Radar.processCollision(objectFromHeight, (Aircraft)object);
                objectFromHeight.setCrashed(true);
                ((Aircraft) object).setCrashed(true);
            } else {
                flightArea[x][y].setObjectToHeight(object, height);
            }
        } else {
            flightArea[x][y].setObjectToHeight(object, height);
        }
    }

    public void print() {
        for (int i = 0; i < SIZE_X; i++) {
            for (int j = 0; j < SIZE_Y; j++) {
                System.out.print(flightArea[i][j].print() + " | ");
            }
            System.out.println();
        }
    }
    //endregion

    @Override
    public synchronized String toString() {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < SIZE_X; i++) {
            for (int j = 0; j < SIZE_Y; j++) {
                if (flightArea[i][j] != null) {
                    out.append(flightArea[i][j]);
                }
            }
        }
        return out.toString();
    }
}
