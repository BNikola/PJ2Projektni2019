package classes.simulator;

import classes.domain.FlightArea;
import classes.domain.Height;
import classes.domain.aircrafts.Aircraft;
import classes.domain.aircrafts.helicopters.FirefightingHelicopter;
import classes.domain.aircrafts.helicopters.PassengerHelicopter;
import classes.domain.aircrafts.helicopters.TransportHelicopter;
import classes.domain.aircrafts.planes.FirefightingPlane;
import classes.domain.aircrafts.planes.PassengerPlane;
import classes.domain.aircrafts.planes.TransportPlane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Simulator {

    // region Members
    // random generator
    private Random rand = new Random();
    // Flight Area
    private static FlightArea flightArea = new FlightArea();
    // properties file
    public static final Properties PROPERTIES = new Properties();
    // logger
    public static final Logger LOGGER = Logger.getLogger("Logger");
    private HashMap<String, Aircraft> aircraftRegistry = new HashMap<>();
    // endregion

    // region Static block
    static {
        // setting up logger
        try {
            FileHandler fileHandler = new FileHandler("error.log", true);
            LOGGER.addHandler(fileHandler);
            SimpleFormatter simpleFormatter = new SimpleFormatter(); // formatting the logger
            fileHandler.setFormatter(simpleFormatter);
//            LOGGER.setUseParentHandlers(false);                    // do not print on console
        } catch (IOException e) {
            e.printStackTrace();
        }

        // loading properties file
        try {
            PROPERTIES.load(new FileInputStream("config.properties"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // loading properties
        try {
            flightArea.setSizeX(Integer.valueOf(PROPERTIES.getProperty("sizeX")));
            flightArea.setSizeY(Integer.valueOf(PROPERTIES.getProperty("sizeY")));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
    // endregion

    public Simulator() {
        super();
    }

    // region Getters and Setters
    public static FlightArea getFlightArea() {
        return flightArea;
    }

    public static void setFlightArea(FlightArea flightArea) {
        Simulator.flightArea = flightArea;
    }
    //endregion

    private Aircraft generateRandomAircraft() {
        Aircraft aircraft = null;
        int choice = rand.nextInt(6);
        String aircraftId = randomAlphaNumeric(COUNT);
        Integer height = Height.values()[rand.nextInt(5)].getHeight();      // getting random height
        String model = "Model";
        int speed = rand.nextInt(3) + 1;
        switch (choice) {
            case 0:
                aircraft = new FirefightingHelicopter(
                        aircraftId, false, height, model, speed,
                        rand.nextInt(300) + 50);
                break;
            case 1:
                aircraft = new FirefightingPlane(
                        aircraftId, false, height, model, speed,
                        rand.nextInt(600) + 100);
                break;
            case 2:
                aircraft = new PassengerHelicopter(
                        aircraftId, false, height, model, speed,
                        rand.nextInt(6) + 2);
                break;
            case 3:
                aircraft = new PassengerPlane(
                        aircraftId, false, height, model, speed,
                        rand.nextInt(240) + 5, rand.nextInt(3000) + 100);
                break;
            case 4:
                aircraft = new TransportHelicopter(
                        aircraftId, false, height, model, speed,
                        rand.nextInt(400) + 50);
                break;
            case 5:
                aircraft = new TransportPlane(
                        aircraftId, false, height, model, speed,
                        rand.nextInt(1000) + 100);
                break;
                // todo - default and drone
        }
        aircraftRegistry.put(aircraftId, aircraft);
        return aircraft;
    }

    public static void main(String[] args) {
        Simulator s = new Simulator();
        int i = 0;
        while (i++ < 10) {
            System.out.println(s.generateRandomAircraft());
        }
    }




    // region private methods
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int COUNT = 6;

    // generate random aircraft id
    public String randomAlphaNumeric(int lenght) {
        StringBuilder builder = new StringBuilder();

        do {
            int count = lenght;
            while (count-- != 0) {
                int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
                builder.append(ALPHA_NUMERIC_STRING.charAt(character));
            }
        } while(aircraftRegistry.containsKey(builder));
        return builder.toString();
    }
    // endregion
}
