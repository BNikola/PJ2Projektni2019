package classes.domain.extras;

import classes.AirTrafficControl;
import classes.domain.aircrafts.Aircraft;

import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;

public class CrashWarning implements Serializable {

    private static final long serialVersionUID = 1L;

    private String crashed1;
    private String crashed2;
    private Date timeOfCrash;
    private String positionOfCrash;

    public CrashWarning(String crashed1, String crashed2, Date timeOfCrash, String positionOfCrash) {
        this.crashed1 = crashed1;
        this.crashed2 = crashed2;
        this.timeOfCrash = timeOfCrash;
        this.positionOfCrash = positionOfCrash;
    }

    public String getCrashed1() {
        return crashed1;
    }

    public void setCrashed1(String crashed1) {
        this.crashed1 = crashed1;
    }

    public String getCrashed2() {
        return crashed2;
    }

    public void setCrashed2(String crashed2) {
        this.crashed2 = crashed2;
    }

    public Date getTimeOfCrash() {
        return timeOfCrash;
    }

    public void setTimeOfCrash(Date timeOfCrash) {
        this.timeOfCrash = timeOfCrash;
    }

    public String getPositionOfCrash() {
        return positionOfCrash;
    }

    public void setPositionOfCrash(String positionOfCrash) {
        this.positionOfCrash = positionOfCrash;
    }

    @Override
    public String toString() {
        return "Position: " + positionOfCrash + "\nTime: " + timeOfCrash + "\nCrashed:\n" + crashed1 + "\nCrashed:\n" + crashed2;
    }
}
