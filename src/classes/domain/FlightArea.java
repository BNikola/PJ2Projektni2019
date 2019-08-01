package classes.domain;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

public class FlightArea {

    // region Members
    // defining size of the map
    private static int SIZE_X = 100;
    private static int SIZE_Y = 100;

    // matrice
    private Object[][] flightArea = new Object[SIZE_X][SIZE_Y];


    // lock object
    public AtomicBoolean lock = new AtomicBoolean();
    // test for crash
    private volatile boolean crash = false;
    public volatile boolean noFlight = false;

    // endregion

    public FlightArea() {
        super();
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

    public Object[][] getFlightArea() {
        return flightArea;
    }

    public void setFlightArea(Object[][] flightArea) {
        this.flightArea = flightArea;
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

    // get/set object to position
    public Object getPosition(int x, int y) {
        return flightArea[x][y];
    }

    public void setPosition(Object obj, int x, int y) {
        flightArea[x][y] = obj;
    }
    //endregion


    @Override
    public String toString() {
        String out = "";
        for (int i = 0; i < SIZE_X; i++) {
            for (int j = 0; j < SIZE_Y; j++) {
                if (flightArea[i][j] != null) {
                    out += String.format("%-2s", flightArea[i][j]);
                } else {
                    out += " * ";
                }
            }
            out += '\n';
        }
        return out;
    }
}
