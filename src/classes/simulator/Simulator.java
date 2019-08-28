package classes.simulator;

import classes.AirTrafficControl;
import classes.domain.aircrafts.Aircraft;
import classes.domain.aircrafts.helicopters.FirefightingHelicopter;
import classes.domain.aircrafts.helicopters.PassengerHelicopter;
import classes.domain.aircrafts.helicopters.TransportHelicopter;
import classes.domain.aircrafts.planes.FirefightingPlane;
import classes.domain.aircrafts.planes.MilitaryFighterPlane;
import classes.domain.aircrafts.planes.PassengerPlane;
import classes.domain.aircrafts.planes.TransportPlane;
import classes.domain.extras.ConfigWatcher;
import classes.domain.extras.FlightArea;
import classes.domain.extras.FlightDirection;
import classes.domain.extras.Height;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Simulator extends Thread {
    // TODO: 21.8.2019. Add creation of foreign aircraft
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
            int height = aircraft.getHeight();
            FlightDirection flightDirection = aircraft.getDirection();

            MilitaryFighterPlane fighter1 = null;
            MilitaryFighterPlane fighter2 = null;

            if (x == 0 && (flightDirection.equals(FlightDirection.LEFT) || flightDirection.equals(FlightDirection.RIGHT))) {        // if it is in a top row
                if (flightDirection.equals(FlightDirection.LEFT)) {
                    fighter1 = generateEscortAircraft(x, FlightArea.getSizeY() - 1, height, flightDirection);
                    fighter2 = generateEscortAircraft(x + 1, FlightArea.getSizeY() - 1, height, flightDirection);
                } else {
                    fighter1 = generateEscortAircraft(x, 0, height, flightDirection);
                    fighter2 = generateEscortAircraft(x + 1, 0, height, flightDirection);
                }
            } else if (y == FlightArea.getSizeY() - 1 && (flightDirection.equals(FlightDirection.UP) || flightDirection.equals(FlightDirection.DOWN))) {        // if it is in a far right column
                if (flightDirection.equals(FlightDirection.UP)) {
                    fighter1 = generateEscortAircraft(FlightArea.getSizeX() - 1, y, height, flightDirection);
                    fighter2 = generateEscortAircraft(FlightArea.getSizeX() - 1, y - 1, height, flightDirection);
                } else {
                    fighter1 = generateEscortAircraft(0, y, height, flightDirection);
                    fighter2 = generateEscortAircraft(0, y - 1, height, flightDirection);
                }
            } else if (x == FlightArea.getSizeX() - 1 && (flightDirection.equals(FlightDirection.LEFT) || flightDirection.equals(FlightDirection.RIGHT))) {     // if it is in a bottom row
                if (flightDirection.equals(FlightDirection.LEFT)) {
                    fighter1 = generateEscortAircraft(x, FlightArea.getSizeY() - 1, height, flightDirection);
                    fighter2 = generateEscortAircraft(x - 1, FlightArea.getSizeY() - 1, height, flightDirection);
                } else {
                    fighter1 = generateEscortAircraft(x, 0, height, flightDirection);
                    fighter2 = generateEscortAircraft(x - 1, 0, height, flightDirection);
                }
            } else if (y == 0 && (flightDirection.equals(FlightDirection.UP) || flightDirection.equals(FlightDirection.DOWN))) {        // if it is in a far left column
                if (flightDirection.equals(FlightDirection.UP)) {
                    fighter1 = generateEscortAircraft(FlightArea.getSizeX() - 1, y, height, flightDirection);
                    fighter2 = generateEscortAircraft(FlightArea.getSizeX() - 1, y + 1, height, flightDirection);
                } else {
                    fighter1 = generateEscortAircraft(0, y, height, flightDirection);
                    fighter2 = generateEscortAircraft(0, y + 1, height, flightDirection);
                }
            } else {
                switch (flightDirection) {
                    case UP:
                        fighter1 = generateEscortAircraft(FlightArea.getSizeX() - 1, y - 1, height, flightDirection);
                        fighter2 = generateEscortAircraft(FlightArea.getSizeX() - 1, y + 1, height, flightDirection);
                        break;
                    case LEFT:
                        fighter1 = generateEscortAircraft(x - 1, FlightArea.getSizeY() - 1, height, flightDirection);
                        fighter2 = generateEscortAircraft(x + 1, FlightArea.getSizeY() - 1, height, flightDirection);
                        break;
                    case DOWN:
                        fighter1 = generateEscortAircraft(0, y - 1, height, flightDirection);
                        fighter2 = generateEscortAircraft(0, y + 1, height, flightDirection);
                        break;
                    case RIGHT:
                        fighter1 = generateEscortAircraft(x - 1, 0, height, flightDirection);
                        fighter2 = generateEscortAircraft(x + 1, 0, height, flightDirection);
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
        int choice = rand.nextInt(6);
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
            default:
                aircraft = new PassengerPlane(
                        aircraftId, false, height, model, speed,
                        rand.nextInt(240) + 5, rand.nextInt(3000) + 100);

                // todo - drone
        }

        aircraft.setPositionAndDirection();
        aircraftRegistry.put(aircraftId, aircraft);
        flightArea.setPosition(aircraft, aircraft.getPositionX(), aircraft.getPositionY(), aircraft.getHeight());
        return aircraft;
    }

    public static void main(String[] args) {
//        Simulator s = new Simulator();
        Simulator s = new Simulator(flightArea);
        System.out.println(FlightArea.getSizeX());
        System.out.println(FlightArea.getSizeY());
        s.configWatcher.start();
        int i = 0;
        List<Aircraft> aircrafts = new ArrayList<>();
//        while (i++ < 30) {
//        while(i == 0) {
//            if (s.configWatcher.isChange()) {
//                interval = Integer.parseInt(s.configWatcher.getOptions().get("interval"));
//            }
//            try {
//                Thread.sleep(interval);
//                s.generateRandomAircraft().start();
////                Aircraft a = s.generateRandomAircraft();
////                aircrafts.add(a);
////                a.start();
//            } catch (InterruptedException e) {
//                AirTrafficControl.LOGGER.log(Level.SEVERE, e.toString(), e);
//            }
//        }

        Aircraft a = s.generateRandomAircraft();
        Aircraft b = s.generateRandomAircraft();
        Aircraft c = s.generateRandomAircraft();
        Aircraft d = s.generateRandomAircraft();
        flightArea.setPosition(null, a.getPositionX(), a.getPositionY(), a.getHeight());
        flightArea.setPosition(null, b.getPositionX(), b.getPositionY(), b.getHeight());
        flightArea.setPosition(null, c.getPositionX(), c.getPositionY(), c.getHeight());
        flightArea.setPosition(null, d.getPositionX(), d.getPositionY(), d.getHeight());

        a.setPositionX(6);
        a.setPositionY(5);
        a.setDirection(FlightDirection.UP);
        a.setSpeed(3);
        a.setHeight(2);
        b.setPositionX(8);
        b.setPositionY(4);
        b.setDirection(FlightDirection.DOWN);
        b.setHeight(2);
        c.setPositionX(7);
        c.setPositionY(7);
        c.setDirection(FlightDirection.LEFT);
        d.setPositionX(5);
        d.setPositionY(5);
        d.setDirection(FlightDirection.UP);
        flightArea.setPosition(a, a.getPositionX(), a.getPositionY(), a.getHeight());
        flightArea.setPosition(b, b.getPositionX(), b.getPositionY(), b.getHeight());
        flightArea.setPosition(c, c.getPositionX(), c.getPositionY(), c.getHeight());
        flightArea.setPosition(d, d.getPositionX(), d.getPositionY(), d.getHeight());


        a.start();
//        Simulator.isNFZ = true;
//        b.changeDirection();
//        b.start();
        System.out.println("---------------------------------");
        System.out.println(flightArea);
        System.out.println("---------------------------------");
        if (s.configWatcher.isChange()) {
            System.out.println("change detected");
        }
//        System.out.println("A: " + a.getClosestExit() + a);
//        System.out.println("B: " + b.getClosestExit() + b);
//        System.out.println("C: " + c.getClosestExit() + c);
//        System.out.println("D: " + d.getClosestExit() + d);
        try {
            a.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        s.configWatcher.interrupt();
        System.out.println("---------------------------------");
        System.out.println(flightArea);
        System.out.println("---------------------------------");
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
//            System.out.println(configWatcher.isChange());
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
            if (foreign > 0) {
                System.out.println("------------------------");
                System.out.println(foreign);
                System.out.println("------------------------");
                Aircraft foreignAircraft = generateRandomAircraft();    // TODO: 22.8.2019. Change this to foreign military aircraft
                foreignAircraft.setForeign(true);
                System.out.println("Created: " + foreignAircraft);
                foreignAircraft.start();
                foreign--;
                try {
                    sleep(interval);
                } catch (InterruptedException e) {
                    AirTrafficControl.LOGGER.log(Level.SEVERE, e.toString(), e);
                }
            }
//            System.out.println("NF Simulator: " + flightArea.isNoFlight() + " foreign: " + foreign);

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

//         // region Testing
//
//        if (configWatcher.isChange()) {
//            System.out.println(configWatcher.getOptions().get("interval"));
//            System.out.println(configWatcher.getOptions().get("foreign"));
//            System.out.println(configWatcher.getOptions().get("domestic"));
//            configWatcher.setChange(false);
//        }
//        Aircraft a = this.generateRandomAircraft();
//        Aircraft b = this.generateRandomAircraft();
//        Aircraft c = this.generateRandomAircraft();
//        Aircraft d = this.generateRandomAircraft();
//        flightArea.setPosition(null, a.getPositionX(), a.getPositionY(), a.getHeight());
//        flightArea.setPosition(null, b.getPositionX(), b.getPositionY(), b.getHeight());
//        flightArea.setPosition(null, c.getPositionX(), c.getPositionY(), c.getHeight());
//        flightArea.setPosition(null, d.getPositionX(), d.getPositionY(), d.getHeight());
//
//        a.setPositionX(6);
//        a.setPositionY(5);
//        a.setDirection(FlightDirection.UP);
//        a.setSpeed(2);
//        a.setHeight(2);
//        b.setPositionX(8);
//        b.setPositionY(4);
//        b.setDirection(FlightDirection.DOWN);
//        b.setHeight(1);
//        c.setPositionX(7);
//        c.setPositionY(7);
//        c.setDirection(FlightDirection.LEFT);
//        d.setPositionX(5);
//        d.setPositionY(5);
//        d.setDirection(FlightDirection.UP);
//        flightArea.setPosition(a, a.getPositionX(), a.getPositionY(), a.getHeight());
//        flightArea.setPosition(b, b.getPositionX(), b.getPositionY(), b.getHeight());
//        flightArea.setPosition(c, c.getPositionX(), c.getPositionY(), c.getHeight());
//        flightArea.setPosition(d, d.getPositionX(), d.getPositionY(), d.getHeight());
//
//
//        a.start();
//
//        // endregion

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
    private static MilitaryFighterPlane generateEscortAircraft(int pos_X, int pos_Y, int height, FlightDirection flightDirection) {
        String aircraftId = randomAlphaNumeric(COUNT);
        String model = randomAlphaNumeric(COUNT).toLowerCase();
        MilitaryFighterPlane fighterPlane = new MilitaryFighterPlane(aircraftId, false, height, model, rand.nextInt(3) + 1);

        fighterPlane.setDirection(flightDirection);
        fighterPlane.setPositionX(pos_X);
        fighterPlane.setPositionY(pos_Y);

        return fighterPlane;
    }
    // endregion

}
