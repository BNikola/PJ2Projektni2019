package classes.controllers;

import classes.Radar;
import classes.domain.extras.FileWatcher;
import classes.simulator.Simulator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


public class Controller implements Initializable {

    public static Controller app;

    @FXML
    private TextArea content;

    @FXML
    private Button check;

    public void test(ActionEvent actionEvent) {
        content.setText(Simulator.flightArea.toString());
        System.out.println("Ovo je test kontrolera");
    }

    public synchronized void refreshTextArea(List<String> data) {
        final String processedData = data.stream()
                .map(row -> String.join(",", row))
                .collect(Collectors.joining("\n"));
        content.setText(processedData);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        app = this;

        FileWatcher fw = new FileWatcher("map.txt", Radar.PATH_TO_FILES);
        fw.start();
    }
}
