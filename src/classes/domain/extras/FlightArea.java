package classes.domain.extras;

import classes.domain.aircrafts.Aircraft;
import classes.domain.aircrafts.helicopters.PassengerHelicopter;
import classes.domain.aircrafts.planes.PassengerPlane;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

public class FlightArea {

    // region Members
    // defining size of the map
    private static int SIZE_X;
    private static int SIZE_Y;
    public static final Properties PROPERTIES = new Properties();
    private static String pathToConfig = System.getProperty("user.dir")
            + File.separator + "src"
            + File.separator + "configs"
            + File.separator + "config.properties";

    //todo - solve size initialization
    // todo - remodel this to take in account the height

    // matrice
    private Field[][] flightArea;

    // lock object
    public AtomicBoolean lock = new AtomicBoolean();
    // test for crash
    private volatile boolean crash = false;
    public volatile boolean noFlight = false;

    // endregion

    static {
        // loading properties file

        try {
            PROPERTIES.load(new FileInputStream(new File(pathToConfig)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // loading properties
        try {
            SIZE_X = Integer.valueOf(PROPERTIES.getProperty("sizeX"));
            SIZE_Y = Integer.valueOf(PROPERTIES.getProperty("sizeY"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
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
    public synchronized Object getPosition(int x, int y, int height) {
        return flightArea[x][y].getObjectFromHeight(height);
    }

    public synchronized void setPosition(Object object, int x, int y, int height) {
        flightArea[x][y].setObjectToHeight(object, height);
    }



    //endregion

    @Override
    public String toString() {
        String out = "";
        for (int i = 0; i < SIZE_X; i++) {
            for (int j = 0; j < SIZE_Y; j++) {
                if (flightArea[i][j] != null) {
//                    out += String.format("%-2s", flightArea[i][j]);
                    out += flightArea[i][j];  // todo - change this
                }
//                else {
//                    out += " * ";
//                }
            }
//            out += '\n';
        }
        return out;
    }
}
