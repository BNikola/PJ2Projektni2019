package classes;

import classes.controllers.Controller;
import classes.domain.aircrafts.Aircraft;
import classes.domain.extras.CrashWarning;
import classes.domain.extras.FileWatcher;
import classes.domain.extras.FlightArea;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            AirTrafficControl.LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        System.out.println(this.getId());
        // todo - add parameter to stop
        //  - maybe put this into main and make new Thread for this main
        while (true) {
            try {
                if (flightArea.isCrash()) {
                    System.out.println("desio se sudar");
                    flightArea.setCrash(false);
                }
                sleep(refreshRate);
                writeToFile(flightArea.toString());
            } catch (InterruptedException e) {
                AirTrafficControl.LOGGER.log(Level.SEVERE, e.toString(), e);
            }
        }
    }

    public static void processCollision(Aircraft a, Aircraft b) {
        if (a != null || b != null) {
            System.out.println("Colision at: [" + a.getPositionX() + "-" + a.getPositionY() + "] " + "[" + b.getPositionX() + "-" + b.getPositionY() + "]");
        }
        System.out.println("From RADAR: " + a);
        System.out.println("From RADAR: " + b);
        flightArea.setCrash(false);
        // create CrashObject
        String position = "[" + a.getPositionX() + "-" + a.getPositionY() + "-" + a.getHeight() + "]";
        // TODO: 20.8.2019. change this to add more details
        String details = "Crashed: " + a.getClass().getCanonicalName() + " and " + b.getClass().getCanonicalName();
        Date date = Calendar.getInstance().getTime();
        CrashWarning crashWarning = new CrashWarning(details, date, position);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh.mm.ss");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("alert/Crash_" + dateFormat.format(date) + ".txt"))) {
            oos.writeObject(crashWarning);
        } catch (IOException e) {
            AirTrafficControl.LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        Controller.displayCrash(crashWarning.toString());

    }
}
