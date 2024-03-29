package classes.domain.rockets;

public class MilitaryRocket extends Rocket {

    public MilitaryRocket(String rocketId, Integer height, int range, int speed) {
        super(rocketId, height, range, speed);
    }

    @Override
    public String toString() {
        return super.toString() +
                ", MilitaryRocket";
    }

    @Override
    public String printCrash() {
        return super.printCrash() + ":MilitaryRocket";
    }
}
