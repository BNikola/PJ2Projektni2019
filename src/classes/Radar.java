package classes;

import classes.domain.extras.FileWatcher;
import classes.domain.extras.FlightArea;

import java.io.*;
import java.util.Properties;
import java.util.logging.Level;

public class Radar extends Thread {
    // region Members
    // todo - maybe change to non static
    public static FlightArea flightArea = new FlightArea();
    private static int refreshRate;
    private static final Properties PROPERTIES = new Properties();
    // todo - check private or public
    public static final String PATH_TO_FILES = System.getProperty("user.dir")
            + File.separator + "src"
            + File.separator + "files";
    private static final String PATH_TO_MAP = System.getProperty("user.dir")
            + File.separator + "src"
            + File.separator + "files"
            + File.separator + "map.txt";
    private static final String PATH_TO_CONFIG = System.getProperty("user.dir")
            + File.separator + "src"
            + File.separator + "configs"
            + File.separator + "radar.properties";

    private static File mapFile = new File(PATH_TO_MAP);

    // endregion Members

    static {

        // loading properties file
        try {
            PROPERTIES.load(new FileInputStream(new File(PATH_TO_CONFIG)));
        } catch (IOException e) {
            AirTrafficControl.LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        // loading properties
        try {
            refreshRate = Integer.parseInt(PROPERTIES.getProperty("refreshRate"));
        } catch (NumberFormatException e) {
            AirTrafficControl.LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    public Radar() {
    }

    public Radar(FlightArea flightArea) {
        Radar.flightArea = flightArea;
    }

    public static synchronized void writeToFile(String data) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(mapFile))) {
            pw.print(data);
        } catch (IOException e) {
            AirTrafficControl.LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    @Override
    public void run() {
        // todo - add parameter to stop
        //  - maybe put this into main and make new Thread for this main
        while (true) {
            try {
                sleep(refreshRate);
                writeToFile(flightArea.toString());
            } catch (InterruptedException e) {
                AirTrafficControl.LOGGER.log(Level.SEVERE, e.toString(), e);
            }
        }
    }
}
