package classes.domain.rockets;

import classes.AirTrafficControl;
import classes.domain.extras.FlightArea;
import classes.domain.extras.FlightDirection;
import classes.simulator.Simulator;

import java.util.logging.Level;

public class Rocket extends Thread {

    protected String rocketId;
    protected Integer height;
    protected int range;
    protected int speed;

    private int positionX;
    private int positionY;
    private FlightDirection direction;
    private boolean doneMoving = false;
    private boolean crashed = false;
    private int traveledDistance = 0;


    public Rocket(String rocketId, Integer height, int range, int speed) {
        this.rocketId = rocketId;
        this.height = height;
        this.range = range;
        this.speed = speed;
    }

    // region Getters and Setters

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public FlightDirection getDirection() {
        return direction;
    }

    public void setDirection(FlightDirection direction) {
        this.direction = direction;
    }

    public boolean isCrashed() {
        return crashed;
    }

    public void setCrashed(boolean crashed) {
        this.crashed = crashed;
    }

    // endregion

    @Override
    public void run() {
        while(!doneMoving) {
            move();
            if (crashed) {
                doneMoving = true;
            } else {
                switch (direction) {
                    case UP:
                        if (positionX == 0) {
                            doneMoving = true;
                        }
                        break;
                    case LEFT:
                        if (positionY == 0) {
                            doneMoving = true;
                        }
                        break;
                    case DOWN:
                        if (positionX == FlightArea.getSizeX() - 1) {
                            doneMoving = true;
                        }
                        break;
                    case RIGHT:
                        if (positionY == FlightArea.getSizeX() - 1) {
                            doneMoving = true;
                        }
                }
            }
        }
        Simulator.flightArea.setPosition(null, positionX, positionY, height);
    }

    private void move() {
        switch (direction) {
            case UP:
                while(positionX > 0 && !crashed && traveledDistance < range) {
                    Simulator.flightArea.setPosition(this, positionX - 1, positionY, height);
                    Simulator.flightArea.setPosition(null, positionX, positionY, height);
                    positionX--;
                    traveledDistance++;
                    try {
                        sleep(speed * 1000);
                    } catch (InterruptedException e) {
                        AirTrafficControl.LOGGER.log(Level.SEVERE, e.toString(), e);
                    }
                }
                doneMoving = true;
                break;
            case LEFT:
                while (positionY > 0 && !crashed && traveledDistance < range) {
                    Simulator.flightArea.setPosition(this, positionX, positionY - 1, height);
                    Simulator.flightArea.setPosition(null, positionX, positionY, height);
                    positionY--;
                    traveledDistance++;
                    try {
                        sleep(speed * 1000);
                    } catch (InterruptedException e) {
                        AirTrafficControl.LOGGER.log(Level.SEVERE, e.toString(), e);
                    }
                }
                doneMoving = true;
                break;
            case DOWN:
                while ((positionX < FlightArea.getSizeX() - 1) && !crashed && traveledDistance < range) {
                    Simulator.flightArea.setPosition(this, positionX + 1, positionY, height);
                    Simulator.flightArea.setPosition(null, positionX, positionY, height);
                    positionX++;
                    traveledDistance++;
                    try {
                        sleep(speed * 1000);
                    } catch (InterruptedException e) {
                        AirTrafficControl.LOGGER.log(Level.SEVERE, e.toString(), e);
                    }
                }
                doneMoving = true;
                break;
            case RIGHT:
                while ((positionY < FlightArea.getSizeY()) && !crashed && traveledDistance < range) {
                    Simulator.flightArea.setPosition(this, positionX, positionY + 1, height);
                    Simulator.flightArea.setPosition(null, positionX, positionY, height);
                    positionY++;
                    traveledDistance++;
                    try {
                        sleep(speed * 1000);
                    } catch (InterruptedException e) {
                        AirTrafficControl.LOGGER.log(Level.SEVERE, e.toString(), e);
                    }
                }
                doneMoving = true;
                break;
            default:
                doneMoving = true;
                break;
        }
    }

    @Override
    public String toString() {
        return "[" + positionX + "-" + positionY + "]" +
                ", rocketId=" + rocketId +
                ", height=" + height +
                ", speed=" + speed +
                ", range=" + range +
                ", traveled=" + traveledDistance +
                ", direction=" + direction;
    }

    public String printCrash() {
        return rocketId + ":" + range + ":" + speed;
    }
}
