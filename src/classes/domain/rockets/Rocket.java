package classes.domain.rockets;

public class Rocket {

    protected double height;
    protected int range;
    protected int speed;

    public Rocket(double height, int range, int speed) {
        this.height = height;
        this.range = range;
        this.speed = speed;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
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

    @Override
    public String toString() {
        return " height=" + height +
                ", range=" + range +
                ", speed=" + speed;
    }
}
