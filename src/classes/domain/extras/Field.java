package classes.domain.extras;

import classes.domain.aircrafts.Aircraft;
import classes.domain.aircrafts.Drones;
import classes.domain.aircrafts.helicopters.FirefightingHelicopter;
import classes.domain.aircrafts.helicopters.PassengerHelicopter;
import classes.domain.aircrafts.helicopters.TransportHelicopter;
import classes.domain.aircrafts.planes.*;

import java.util.Arrays;

public class Field {
    private Object[] heights;

    public Field() {
        heights = new Object[Height.values().length];
    }

    public Object[] getHeights() {
        return heights;
    }

    public void setHeights(Object[] heights) {
        this.heights = heights;
    }

    public void setObjectToHeight(Object object, int height) {
        heights[height] = object;
    }

    public Object getObjectFromHeight(int height) {
        return heights[height];
    }

    // returns the highest or foreign aircraft
//    @Override
//    public String toString() {
//        String ret = " * ";
//        for (Object o : heights) {
//            if (o != null) {
//                if (((Aircraft) o).isForeign()) {
//                    if (o instanceof FirefightingPlane || o instanceof MilitaryBomberPlane || o instanceof MilitaryFighterPlane
//                            || o instanceof PassengerPlane || o instanceof TransportPlane) {
//                        ret = " A ";
//                    } else if (o instanceof FirefightingHelicopter || o instanceof PassengerHelicopter || o instanceof TransportHelicopter) {
//                        ret = " H ";
//                    } else if (o instanceof Drones) {
//                        ret = " D ";
//                    } else {
//                        ret = " A ";
//                    }
//                    break;
//                } else if (o instanceof FirefightingPlane || o instanceof MilitaryBomberPlane || o instanceof MilitaryFighterPlane
//                        || o instanceof PassengerPlane || o instanceof TransportPlane) {
//                    ret = " A ";
//                } else if (o instanceof FirefightingHelicopter || o instanceof PassengerHelicopter || o instanceof TransportHelicopter) {
//                    ret = " H ";
//                } else if (o instanceof Drones) {
//                    ret = " D ";
//                } else {
//                    ret = " A ";
//                }
//            }
//        }
//        return ret;
//    }


    // printing info about aircrafts
    @Override
    public String toString() {
        String result = "";
        for (Object o : heights) {
            if (o != null) {
                result += "-";
                result += o.toString();
                result += "\n";
            }
        }
        return result;
    }
}
