package classes.controllers;

import classes.Radar;
import classes.domain.extras.FileWatcher;
import classes.domain.extras.FlightArea;
import classes.simulator.Simulator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


public class Controller implements Initializable {

    public static Controller app;

    @FXML
    private GridPane flightAreaGridPane;

    @FXML
    private Button check;

    public void test(ActionEvent actionEvent) {
//        content.setText(Simulator.flightArea.toString());
        System.out.println("Ovo je test kontrolera");
    }

    public synchronized void refreshTextArea(List<String> data) {
        // todo - fix this to show actual data
//        final String processedData = data.stream()
//                .map(row -> String.join(",", row))
//                .collect(Collectors.joining("\n"));
//        content.setText(processedData);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        app = this;

        // todo - initialize with the correct size of map (gridpane)
        System.out.println("started");
        for (int i = 0; i < FlightArea.getSizeX(); i++) {
            for (int j = 0; j < FlightArea.getSizeY(); j++) {
                VBox columnLayout = new VBox();
                Text cellText = new Text(" ** ");      // the aircraftId is 6 letters long
                columnLayout.getChildren().addAll(cellText);
                columnLayout.setAlignment(Pos.CENTER);
                flightAreaGridPane.add(columnLayout, i, j);
                flightAreaGridPane.setAlignment(Pos.CENTER);
                flightAreaGridPane.setVgap(15);
                flightAreaGridPane.setHgap(15);
                flightAreaGridPane.setPadding(new Insets(10,10,10,10));
                ColumnConstraints s = new ColumnConstraints();
                // todo - check min width and height

            }
        }

        System.out.println(flightAreaGridPane.getColumnCount());
        System.out.println(flightAreaGridPane.getRowCount());
        FileWatcher fw = new FileWatcher("map.txt", Radar.PATH_TO_FILES);
//        fw.start();

    }
}
