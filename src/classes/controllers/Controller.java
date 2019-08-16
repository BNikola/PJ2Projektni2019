package classes.controllers;

import classes.Radar;
import classes.domain.extras.FileWatcher;
import classes.domain.extras.FlightArea;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    public static Controller app;

    @FXML
    private GridPane flightAreaGridPane;

    @FXML
    private Button check;

    public void test(ActionEvent actionEvent) {
        for (int i = 0; i < FlightArea.getSizeX(); i++) {
            for (int j = 0; j < FlightArea.getSizeY(); j++) {
                Text t = (Text) ((VBox)flightAreaGridPane.getChildren().get(i*FlightArea.getSizeX() + j)).getChildren().get(0);
                t.setFill(Color.BLACK);
                t.setTextAlignment(TextAlignment.CENTER);
                t.setText(" ** ");
            }
        }
    }



    public synchronized void refreshTextArea(List<String> data) {
        for (int i = 0; i < FlightArea.getSizeX(); i++) {
            for (int j = 0; j < FlightArea.getSizeY(); j++) {
                Text t = (Text) ((VBox) flightAreaGridPane.getChildren().get(i * FlightArea.getSizeX() + j)).getChildren().get(0);
                t.setFill(Color.BLACK);
                t.setTextAlignment(TextAlignment.CENTER);
                t.setText("  **  ");
            }
        }
        List<String> list = new ArrayList<>();
        for (String s : data) {
            String[] split = s.split(", ");
            String pos = split[0];
            String id = split[1].split("=")[1];
            String height = split[2].split("=")[1];
            String category = split[7];
            String[] position = pos.substring(1, pos.length() - 1).split("-");
            int x = Integer.parseInt(position[0]);
            int y = Integer.parseInt(position[1]);
            String element = x + " " + y + " " + height + " " + category + " ";
            list.add(element);
            Text display = new Text(id + "\n" + height);
            StackPane sp = new StackPane();
            sp.getChildren().add(display);
            Platform.runLater(() -> {
                Text t = (Text) ((VBox) flightAreaGridPane.getChildren().get(x + y * FlightArea.getSizeY())).getChildren().get(0);
                // TODO: 16.8.2019. - add military planes
                switch (category) {
                    case "FirefightingHelicopter":
                        t.setFill(Color.DARKRED);
                        break;
                    case "PassengerHelicopter":
                        t.setFill(Color.DARKBLUE);
                        break;
                    case "TransportHelicopter":
                        t.setFill(Color.DARKGREEN);
                        break;
                    case "FirefightingPlane":
                        t.setFill(Color.ORANGERED);
                        break;
                    case "PassengerPlane":
                        t.setFill(Color.DEEPSKYBLUE);
                        break;
                    case "TransportPlane":
                        t.setFill(Color.LAWNGREEN);
                        break;
                    default:
                        t.setFill(Color.BLACK);
                }

                t.setText(id + "\n" + height);
                t.setTextAlignment(TextAlignment.CENTER);
            });
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        app = this;
        Platform.runLater(() -> {
            System.out.println("started");
            for (int i = 0; i < FlightArea.getSizeX(); i++) {
                for (int j = 0; j < FlightArea.getSizeY(); j++) {
                    VBox columnLayout = new VBox();
                    Text cellText = new Text("  **  ");      // the aircraftId is 6 letters long
                    columnLayout.getChildren().addAll(cellText);
                    columnLayout.setAlignment(Pos.CENTER);
                    flightAreaGridPane.add(columnLayout, i, j);
                    flightAreaGridPane.setAlignment(Pos.CENTER);
                    flightAreaGridPane.setVgap(15);
                    flightAreaGridPane.setHgap(15);
                    flightAreaGridPane.setMinHeight(30);
                    flightAreaGridPane.setPadding(new Insets(10, 10, 10, 10));
                    ColumnConstraints s = new ColumnConstraints();
                }
            }
        });

        System.out.println("From controller");
        System.out.println(flightAreaGridPane.getColumnCount());
        System.out.println(flightAreaGridPane.getRowCount());
        FileWatcher fw = new FileWatcher("map.txt", Radar.PATH_TO_FILES);
        fw.start();

    }
}
