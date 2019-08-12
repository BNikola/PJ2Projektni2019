package classes;

import classes.domain.extras.FlightArea;
import classes.simulator.Simulator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

public class Radar extends Thread {
    // region Members
    // todo - maybe change to non static
    public static FlightArea flightArea = new FlightArea();
    public static int refreshRate;
    public static final Properties PROPERTIES = new Properties();
    private static final String PATH_TO_MAP = System.getProperty("user.dir")
            + File.separator + "src"
            + File.separator + "files"
            + File.separator + "map.txt";
    private static final String PATH_TO_CONFIG = System.getProperty("user.dir")
            + File.separator + "src"
            + File.separator + "configs"
            + File.separator + "radar.properties";

    private File mapFile = new File(PATH_TO_MAP);
    // endregion Members

    static {
        // setting up logger
        try {
            FileHandler fileHandler = new FileHandler("error.log", true);
            Simulator.LOGGER.addHandler(fileHandler);
            SimpleFormatter simpleFormatter = new SimpleFormatter(); // formatting the logger
            fileHandler.setFormatter(simpleFormatter);
//            LOGGER.setUseParentHandlers(false);                    // do not print on console
        } catch (IOException e) {
            e.printStackTrace();
        }

        // loading properties file
        try {
            PROPERTIES.load(new FileInputStream(new File(PATH_TO_CONFIG)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // loading properties
        try {
            refreshRate = Integer.valueOf(PROPERTIES.getProperty("refreshRate"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }


    }

    public Radar() {
    }

    public Radar(FlightArea flightArea) {
        this.flightArea = flightArea;
    }
}
