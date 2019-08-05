package classes.domain.extras;

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

    // matrice
    private Object[][] flightArea;

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
        flightArea = new Object[SIZE_X][SIZE_Y];
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


    public static void main(String[] args) {
        FlightArea fa = new FlightArea();
        System.out.println(fa);
        System.out.println(fa.getSizeX());
        System.out.println(fa.getSizeY());
    }

    @Override
    public String toString() {
        String out = "";
        for (int i = 0; i < SIZE_X; i++) {
            for (int j = 0; j < SIZE_Y; j++) {
                if (flightArea[i][j] != null) {
//                    out += String.format("%-2s", flightArea[i][j]);
                    out += " A ";
                } else {
                    out += " * ";
                }
            }
            out += '\n';
        }
        return out;
    }
}
