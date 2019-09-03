package classes.domain.extras;

import classes.domain.aircrafts.Aircraft;

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


    // printing info about aircrafts
    @Override
    public synchronized String toString() {
        String result = "";
        for (Object o : heights) {
            if (o != null) {
                result += o.toString();
                result += "\n";
            }
        }
        return result;
    }
}
