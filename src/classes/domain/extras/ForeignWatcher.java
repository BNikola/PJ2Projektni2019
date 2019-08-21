package classes.domain.extras;

import classes.domain.aircrafts.Aircraft;
import classes.simulator.Simulator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ForeignWatcher extends Thread {

    private String data;

    public ForeignWatcher(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public void run() {
        List<String> list = Arrays.asList(data.split("\n"));
        Map<String, String> collect = list.stream()
                .filter(line -> line.contains("foreign=true"))
                .map(line -> line.split(", "))
                .collect(Collectors.toMap(array -> array[1].split("=")[1], array -> array[7]));
        System.out.println(collect.keySet().size());
        for (String s : collect.keySet()) {
            if (Simulator.aircraftRegistry.containsKey(s)) {
                System.out.println(Simulator.aircraftRegistry.get(s));
            }
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

        System.out.println("-----------");
        System.out.println(Simulator.flightArea);
        System.out.println("-----------");
    }
}
