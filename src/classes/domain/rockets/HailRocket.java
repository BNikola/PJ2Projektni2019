package classes.domain.rockets;

public class HailRocket extends Rocket {

    public HailRocket(String rocketId, Integer height, int range, int speed) {
        super(rocketId, height, range, speed);
    }

    @Override
    public String toString() {
        return super.toString() +
                ", HailRocket";
    }

    @Override
    public String printCrash() {
        return super.printCrash() + ":HailRocket";
    }
}
