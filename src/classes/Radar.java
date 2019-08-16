package classes;

import classes.domain.extras.FileWatcher;
import classes.domain.extras.FlightArea;

import java.io.*;
import java.util.Properties;

public class Radar extends Thread {
    // region Members
    // todo - maybe change to non static
    public static FlightArea flightArea = new FlightArea();
    public static int refreshRate;
    public static final Properties PROPERTIES = new Properties();
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
    public FileWatcher mapWatcher = new FileWatcher("map.txt", PATH_TO_FILES);

    // endregion Members

    static {
//        // setting up logger
//        try {
//            FileHandler fileHandler = new FileHandler("error.log", true);
//            Simulator.LOGGER.addHandler(fileHandler);
//            SimpleFormatter simpleFormatter = new SimpleFormatter(); // formatting the logger
//            fileHandler.setFormatter(simpleFormatter);
////            LOGGER.setUseParentHandlers(false);                    // do not print on console
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

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

    public static synchronized void writeToFile(String data) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(mapFile))) {
            pw.print(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        // todo - add parameter to stop
        //  - maybe put this into main and make new Thread for this main
        while (true) {
            try {
                // TODO: 16.8.2019. - use radar.properties for sleep
                sleep(1500);
                writeToFile(flightArea.toString());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
