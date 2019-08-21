package classes.simulator;

import classes.AirTrafficControl;
import classes.domain.aircrafts.Aircraft;
import classes.domain.aircrafts.helicopters.FirefightingHelicopter;
import classes.domain.aircrafts.helicopters.PassengerHelicopter;
import classes.domain.aircrafts.helicopters.TransportHelicopter;
import classes.domain.aircrafts.planes.FirefightingPlane;
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

    // region Members
    // random generator
    private Random rand = new Random();
    // Flight Area
    public static FlightArea flightArea = new FlightArea();
    // properties file
    public static final Properties PROPERTIES = new Properties();

    // record of aircraft, for unique aircraftId
    public static HashMap<String, Aircraft> aircraftRegistry = new HashMap<>();
    // interval of creating aircraft
    private static int interval = 500;
    // path to config
    private static String pathToConfig = System.getProperty("user.dir")
            + File.separator + "src"
            + File.separator + "configs"
            + File.separator + "config.properties";

    public static boolean isNFZ = false;    // no fly zone

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
//        s.configWatcher.start();
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
        a.setSpeed(2);
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
        Simulator.isNFZ = true;
//        b.changeDirection();
//        b.start();
        System.out.println("---------------------------------");
        System.out.println(flightArea);
        System.out.println("---------------------------------");
//        System.out.println("A: " + a.getClosestExit() + a);
//        System.out.println("B: " + b.getClosestExit() + b);
//        System.out.println("C: " + c.getClosestExit() + c);
//        System.out.println("D: " + d.getClosestExit() + d);
        try {
            a.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("---------------------------------");
        System.out.println(flightArea);
        System.out.println("---------------------------------");

//        s.generateRandomAircraft().start();
//
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            AirTrafficControl.LOGGER.log(Level.SEVERE, e.toString(), e);
//        }
//        a.start();
//        b.start();
//
//        System.out.println(String.join("", Collections.nCopies(100, "=")));
//        System.out.println("aaaaaaaaaaaaaaaaaaaa\n" + flightArea);
//        s.configWatcher.interrupt();
    }

    @Override
    public void run() {
        // region Real
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                sleep(interval);
                generateRandomAircraft().start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // endregion

//        // region Testing
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
//        b.setHeight(2);
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
//        Simulator.isNFZ = true;
//
//        // endregion
//  testing
//        Aircraft a = generateRandomAircraft();
//        Aircraft b = generateRandomAircraft();
//        this.flightArea.setPosition(null, a.getPositionX(), a.getPositionY(), a.getHeight());
//        this.flightArea.setPosition(null, b.getPositionX(), b.getPositionY(), b.getHeight());
//        a.setPositionX(7);
//        a.setPositionY(0);
//        a.setDirection(FlightDirection.UP);
//        a.setHeight(1);
//        a.setSpeed(3);
//        b.setPositionX(9);
//        b.setPositionY(0);
//        b.setDirection(FlightDirection.UP);
//        b.setHeight(1);
//        b.setSpeed(2);
//        System.out.println("A: " + a);
//        System.out.println("B: " + b);
//        this.flightArea.setPosition(a, a.getPositionX(), a.getPositionY(), a.getHeight());
//        this.flightArea.setPosition(b, b.getPositionX(), b.getPositionY(), b.getHeight());
//        a.start();
//        try {
//            sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        b.start();
//        generateRandomAircraft().start();
    }

    // region private methods
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int COUNT = 6;

    // config watcher
    private ConfigWatcher configWatcher = new ConfigWatcher("config.properties");

    // generate random aircraft id
    private String randomAlphaNumeric(int lenght) {
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
