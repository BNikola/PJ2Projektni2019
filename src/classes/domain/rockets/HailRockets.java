package classes.domain.rockets;

public class HailRockets extends Rocket {

    public HailRockets(double height, int range, int speed) {
        super(height, range, speed);
    }

    @Override
    public String toString() {
        return "HailRockets{" +
                super.toString() +
                '}';
    }
}
