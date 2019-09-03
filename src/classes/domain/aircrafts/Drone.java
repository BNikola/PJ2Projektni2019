package classes.domain.aircrafts;

public class Drone extends Aircraft {

    public Drone(String aircraftId, boolean foreign, Integer height, String model, int speed) {
        super(aircraftId, foreign, height, model, speed);
    }

    @Override
    public String toString() {
        return super.toString() +
                ", Drone";
    }

    @Override
    public String printCrash() {
        return super.printCrash() + ":Drone";
    }
}
