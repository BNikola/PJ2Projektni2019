package classes;

import classes.controllers.Controller;
import classes.domain.aircrafts.Aircraft;
import classes.domain.extras.CrashWarning;
import classes.domain.extras.FileWatcher;
import classes.domain.extras.FlightArea;
import classes.domain.extras.ForeignWatcher;
import classes.simulator.Simulator;
import javafx.event.ActionEvent;

import javax.swing.text.DateFormatter;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;

public class Radar extends Thread {
    // region Members
    // todo - maybe change to non static
    public static FlightArea flightArea = new FlightArea();
    private static HashMap<String, Boolean> detectedForeign = new HashMap<>();
    ForeignWatcher foreignWatcher = new ForeignWatcher(flightArea.toString());

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

    // region Constructors
    public Radar() {
    }

    public Radar(FlightArea flightArea) {
        Radar.flightArea = flightArea;
    }
    // endregion

    public static void removeFromDetectedForeign(String key) {
        detectedForeign.remove(key);
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
            sleep(2000);
        } catch (InterruptedException e) {
            AirTrafficControl.LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        foreignWatcher.start();
        System.out.println(this.getId());
        // todo - add parameter to stop
        while (true) {
            foreignWatcher.setData(flightArea.toString());
            // if foreign is detected
            if (foreignWatcher.getCollect().keySet().size() > 0) {
                for (String id : foreignWatcher.getCollect().keySet()) {
                    if (!detectedForeign.containsKey(id)) {
                        detectedForeign.put(id, false);
                    }
                }
            }
            if (detectedForeign.keySet().size() > 0) {
                // activate NFZ
                for (String id : detectedForeign.keySet()) {
                    if (!detectedForeign.get(id) && (Simulator.aircraftRegistry.get(id) != null)) {
                        flightArea.setNoFlight(true);
                        // create a file in folder events
                        try (PrintWriter printWriter = new PrintWriter(new FileWriter(
                                "events"
                                        + File.separator
                                        + new SimpleDateFormat("yyyy-MM-dd'_'HH-mm-ss").format(new Date())
                                        + ".txt"))) {
                            printWriter.println("Detected foreign aircraft");
                            printWriter.println(Simulator.aircraftRegistry.get(id));
                        } catch (IOException e) {
                            AirTrafficControl.LOGGER.log(Level.SEVERE, e.toString(), e);
                        }
                        Controller.app.refreshEventsLabel("Detected aircraft: " + id + " at " + new Date());
                        Simulator.sendEscort(Simulator.aircraftRegistry.get(id));
                        detectedForeign.replace(id, true);
                    }
                }
            }
            try {
                if (flightArea.isCrash()) {
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
        if (a != null && b != null) {
            System.out.println("Colision at: [" + a.getPositionX() + "-" + a.getPositionY() + "] " + "[" + b.getPositionX() + "-" + b.getPositionY() + "]");
        }
        System.out.println("From RADAR: " + a);
        System.out.println("From RADAR: " + b);
        flightArea.setCrash(false);
        // create CrashObject
        String position = "[x=" + a.getPositionX() + " y=" + a.getPositionY() + " h=" + a.getHeight() + "]";
        String details = a.printCrash() + "\n" + b.printCrash();
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
