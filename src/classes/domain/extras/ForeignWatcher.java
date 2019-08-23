package classes.domain.extras;

import classes.domain.aircrafts.Aircraft;
import classes.simulator.Simulator;

import java.util.*;
import java.util.stream.Collectors;

public class ForeignWatcher extends Thread {

    private String data;
    private Map<String, String> collect = new HashMap<>();

    public ForeignWatcher(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Map<String, String> getCollect() {
        return collect;
    }

    @Override
    public void run() {
        while(true) {
            List<String> list = Arrays.asList(data.split("\n"));
            collect = list.stream()
                    .filter(line -> line.contains("foreign=true"))
                    .map(line -> line.split(", "))
                    .collect(Collectors.toMap(array -> array[1].split("=")[1], array -> array[7]));
        }
    }

    public static void main(String[] args) {
        Simulator simulator = new Simulator();
        Aircraft a = simulator.generateRandomAircraft();
        Aircraft b = simulator.generateRandomAircraft();
        Aircraft c = simulator.generateRandomAircraft();
        c.setForeign(true);
        b.setForeign(true);
        ForeignWatcher fw = new ForeignWatcher(Simulator.flightArea.toString());
        fw.run();
        System.out.println(fw.getCollect().keySet().size());

        if (fw.getCollect().containsKey(b.getAircraftId())) {
            System.out.println("Detected: " + b);
        }

        System.out.println("-----------");
        System.out.println(Simulator.flightArea);
        System.out.println("-----------");
    }
}
