package classes.domain.rockets;

public class MilitaryRocket extends Rocket {

    public MilitaryRocket(double height, int range, int speed) {
        super(height, range, speed);
    }

    @Override
    public String toString() {
        return "MilitaryRocket{" +
                super.toString() +
                '}';
    }
}
