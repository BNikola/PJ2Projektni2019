package classes.domain.extras;

import classes.AirTrafficControl;
import classes.domain.aircrafts.Aircraft;

import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;

public class CrashWarning implements Serializable {

    private static final long serialVersionUID = 1L;

    private String details;
    private Date timeOfCrash;
    private String positionOfCrash;

    public CrashWarning(String details, Date timeOfCrash, String positionOfCrash) {
        this.details = details;
        this.timeOfCrash = timeOfCrash;
        this.positionOfCrash = positionOfCrash;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
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
        return positionOfCrash + "::" + timeOfCrash + "::" + details;
    }
}
