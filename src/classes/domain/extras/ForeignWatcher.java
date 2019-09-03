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
}
