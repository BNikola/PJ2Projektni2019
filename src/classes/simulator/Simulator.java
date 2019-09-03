package classes.simulator;

import classes.AirTrafficControl;
import classes.domain.aircrafts.Aircraft;
import classes.domain.aircrafts.Drone;
import classes.domain.aircrafts.helicopters.FirefightingHelicopter;
import classes.domain.aircrafts.helicopters.PassengerHelicopter;
import classes.domain.aircrafts.helicopters.TransportHelicopter;
import classes.domain.aircrafts.planes.*;
import classes.domain.extras.ConfigWatcher;
import classes.domain.extras.FlightArea;
import classes.domain.extras.FlightDirection;
import classes.domain.extras.Height;
import classes.domain.rockets.HailRocket;
import classes.domain.rockets.MilitaryRocket;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

public class Simulator extends Thread {
    // region Members
    // random generator
    private static Random rand = new Random();
    // Flight Area
    public static FlightArea flightArea = new FlightArea();
    // properties file
    public static final Properties PROPERTIES = new Properties();

    // record of aircraft, for unique aircraftId
    public static HashMap<String, Aircraft> aircraftRegistry = new HashMap<>();
    // interval of creating aircraft
    private static int interval = 2000;
    // path to config
    private static String pathToConfig = System.getProperty("user.dir")
            + File.separator + "src"
            + File.separator + "configs"
            + File.separator + "config.properties";

    public static int foreign;
    public static int domestic;

    // endregion


    // region Static block
    static {

        // loading properties file
        try {
            PROPERTIES.load(new FileInputStream(new File(pathToConfig)));
        } catch (IOException e) {
            AirTrafficControl.LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        // loading properties
        try {
            interval = Integer.parseInt(PROPERTIES.getProperty("interval"));
            foreign = Integer.parseInt(PROPERTIES.getProperty("foreign"));
            domestic = Integer.parseInt(PROPERTIES.getProperty("domestic"));
        } catch (NumberFormatException e) {
            AirTrafficControl.LOGGER.log(Level.SEVERE, e.toString(), e);
        }


    }

    // endregion

    public Simulator() {
        super();
    }

    public Simulator(FlightArea flightArea) {
        Simulator.flightArea = flightArea;
    }

    public static void sendEscort(Aircraft aircraft) {
        if (aircraft != null) {
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                AirTrafficControl.LOGGER.log(Level.SEVERE, e.toString(), e);
            }
            int x = aircraft.getPositionX();
            int y = aircraft.getPositionY();
            int height = (aircraft.getHeight() + 1) % Height.values().length;
            FlightDirection flightDirection = aircraft.getDirection();

            MilitaryFighterPlane fighter1 = null;
            MilitaryFighterPlane fighter2 = null;

            if (x == 0 && (flightDirection.equals(FlightDirection.LEFT) || flightDirection.equals(FlightDirection.RIGHT))) {        // if it is in a top row
                if (flightDirection.equals(FlightDirection.LEFT)) {
                    fighter1 = generateEscortAircraft(x, FlightArea.getSizeY() - 1, height, flightDirection, aircraft, 0);
                    fighter2 = generateEscortAircraft(x + 1, FlightArea.getSizeY() - 1, height, flightDirection, aircraft, -1);
                } else {
                    fighter1 = generateEscortAircraft(x, 0, height, flightDirection, aircraft, 0);
                    fighter2 = generateEscortAircraft(x + 1, 0, height, flightDirection, aircraft, 1);
                }
            } else if (y == FlightArea.getSizeY() - 1 && (flightDirection.equals(FlightDirection.UP) || flightDirection.equals(FlightDirection.DOWN))) {        // if it is in a far right column
                if (flightDirection.equals(FlightDirection.UP)) {
                    fighter1 = generateEscortAircraft(FlightArea.getSizeX() - 1, y, height, flightDirection, aircraft, 0);
                    fighter2 = generateEscortAircraft(FlightArea.getSizeX() - 1, y - 1, height, flightDirection, aircraft, -1);
                } else {
                    fighter1 = generateEscortAircraft(0, y, height, flightDirection, aircraft, 0);
                    fighter2 = generateEscortAircraft(0, y - 1, height, flightDirection, aircraft, 1);
                }
            } else if (x == FlightArea.getSizeX() - 1 && (flightDirection.equals(FlightDirection.LEFT) || flightDirection.equals(FlightDirection.RIGHT))) {     // if it is in a bottom row
                if (flightDirection.equals(FlightDirection.LEFT)) {
                    fighter1 = generateEscortAircraft(x, FlightArea.getSizeY() - 1, height, flightDirection, aircraft, 0);
                    fighter2 = generateEscortAircraft(x - 1, FlightArea.getSizeY() - 1, height, flightDirection, aircraft, 1);
                } else {
                    fighter1 = generateEscortAircraft(x, 0, height, flightDirection, aircraft, 0);
                    fighter2 = generateEscortAircraft(x - 1, 0, height, flightDirection, aircraft, -1);
                }
            } else if (y == 0 && (flightDirection.equals(FlightDirection.UP) || flightDirection.equals(FlightDirection.DOWN))) {        // if it is in a far left column
                if (flightDirection.equals(FlightDirection.UP)) {
                    fighter1 = generateEscortAircraft(FlightArea.getSizeX() - 1, y, height, flightDirection, aircraft, 0);
                    fighter2 = generateEscortAircraft(FlightArea.getSizeX() - 1, y + 1, height, flightDirection, aircraft, 1);
                } else {
                    fighter1 = generateEscortAircraft(0, y, height, flightDirection, aircraft, 0);
                    fighter2 = generateEscortAircraft(0, y + 1, height, flightDirection, aircraft, -1);
                }
            } else {
                switch (flightDirection) {
                    case UP:
                        fighter1 = generateEscortAircraft(FlightArea.getSizeX() - 1, y - 1, height, flightDirection, aircraft, -1);
                        fighter2 = generateEscortAircraft(FlightArea.getSizeX() - 1, y + 1, height, flightDirection, aircraft, 1);
                        break;
                    case LEFT:
                        fighter1 = generateEscortAircraft(x - 1, FlightArea.getSizeY() - 1, height, flightDirection, aircraft, 1);
                        fighter2 = generateEscortAircraft(x + 1, FlightArea.getSizeY() - 1, height, flightDirection, aircraft, -1);
                        break;
                    case DOWN:
                        fighter1 = generateEscortAircraft(0, y - 1, height, flightDirection, aircraft, 1);
                        fighter2 = generateEscortAircraft(0, y + 1, height, flightDirection, aircraft, -1);
                        break;
                    case RIGHT:
                        fighter1 = generateEscortAircraft(x - 1, 0, height, flightDirection, aircraft, -1);
                        fighter2 = generateEscortAircraft(x + 1, 0, height, flightDirection, aircraft, 1);
                        break;
                }
            }

            flightArea.setPosition(fighter1, fighter1.getPositionX(), fighter1.getPositionY(), fighter1.getHeight());
            aircraftRegistry.put(fighter1.getAircraftId(), fighter1);
            fighter1.start();

            flightArea.setPosition(fighter2, fighter2.getPositionX(), fighter2.getPositionY(), fighter2.getHeight());
            aircraftRegistry.put(fighter2.getAircraftId(), fighter2);
            fighter2.start();

        }
    }


    // todo - change to private
    public Aircraft generateRandomAircraft() {
        Aircraft aircraft = null;
        int choice = rand.nextInt(7);
        String aircraftId = randomAlphaNumeric(COUNT);
        Integer height = Height.values()[rand.nextInt(Height.values().length)].getHeight();      // getting random height
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
            case 6:
                aircraft = new Drone(aircraftId, false, height, model, speed);
                break;
            default:
                aircraft = new PassengerPlane(
                        aircraftId, false, height, model, speed,
                        rand.nextInt(240) + 5, rand.nextInt(3000) + 100);
        }

        aircraft.setPositionAndDirection();
        aircraftRegistry.put(aircraftId, aircraft);
        flightArea.setPosition(aircraft, aircraft.getPositionX(), aircraft.getPositionY(), aircraft.getHeight());
        return aircraft;
    }

    // TODO: 29.8.2019. change to private
    public Aircraft generateMilitaryAircraft(boolean foreign) {
        Aircraft aircraft = null;
        int choice = rand.nextInt(2);
        String aircraftId = randomAlphaNumeric(COUNT);
        Integer height = Height.values()[rand.nextInt(Height.values().length)].getHeight();      // getting random height
        String model = "Model";
        int speed = rand.nextInt(3) + 1;
        switch (choice) {
            case 0:
                aircraft = new MilitaryFighterPlane(
                        aircraftId, foreign, height, model, speed);
                break;
            case 1:
                aircraft = new MilitaryBomberPlane(
                        aircraftId, foreign, height, model, speed);
                break;
            default:
                aircraft = new MilitaryFighterPlane(
                        aircraftId, foreign, height, model, speed);
        }

        aircraft.setPositionAndDirection();
        aircraftRegistry.put(aircraftId, aircraft);
        flightArea.setPosition(aircraft, aircraft.getPositionX(), aircraft.getPositionY(), aircraft.getHeight());
        return aircraft;
    }

    @Override
    public void run() {
        configWatcher.start();
        // region Real
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            AirTrafficControl.LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        while (true) {
            if (configWatcher.isChange()) {
                try {
                    interval = Integer.parseInt(configWatcher.getOptions().get("interval"));
                    foreign = Integer.parseInt(configWatcher.getOptions().get("foreign"));
                    domestic = Integer.parseInt(configWatcher.getOptions().get("domestic"));
                    configWatcher.setChange(false);
                } catch (NumberFormatException e) {
                    AirTrafficControl.LOGGER.log(Level.SEVERE, e.toString(), e);
                }
            }
            // generates foreign aircraft if config file has changed
            while (foreign > 0) {
                Aircraft foreignAricraft = generateMilitaryAircraft(true);
                foreignAricraft.start();
                if (Simulator.aircraftRegistry.containsKey(foreignAricraft.getAircraftId())) {
                    foreign--;
                }
                try {
                    sleep(interval);
                } catch (InterruptedException e) {
                    AirTrafficControl.LOGGER.log(Level.SEVERE, e.toString(), e);
                }
            }

            while (domestic > 0) {
                Aircraft domesticAircraft = generateMilitaryAircraft(false);
                domesticAircraft.start();
                if (Simulator.aircraftRegistry.containsKey(domesticAircraft.getAircraftId())) {
                    domestic--;
                }
                try {
                    sleep(interval);
                } catch (InterruptedException e) {
                    AirTrafficControl.LOGGER.log(Level.SEVERE, e.toString(), e);
                }
            }
            if (!flightArea.isNoFlight()) {
                try {
                    sleep(interval);
                    generateRandomAircraft().start();
                } catch (InterruptedException e) {
                    AirTrafficControl.LOGGER.log(Level.SEVERE, e.toString(), e);
                }
            }
        }
        // endregion
    }

    // region private methods
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int COUNT = 6;

    // config watcher
    private ConfigWatcher configWatcher = new ConfigWatcher("config.properties");

    // generate random aircraft id
    private static String randomAlphaNumeric(int lenght) {
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

    // generate escort fighter
    private static MilitaryFighterPlane generateEscortAircraft(int pos_X, int pos_Y, int height, FlightDirection flightDirection, Aircraft intruder, int position) {
        String aircraftId = randomAlphaNumeric(COUNT);
        String model = randomAlphaNumeric(COUNT).toLowerCase();
        MilitaryFighterPlane fighterPlane = new MilitaryFighterPlane(aircraftId, false, height, model, rand.nextInt(3) + 1, true, intruder, position);

        fighterPlane.setDirection(flightDirection);
        fighterPlane.setPositionX(pos_X);
        fighterPlane.setPositionY(pos_Y);

        return fighterPlane;
    }
    // endregion

}
