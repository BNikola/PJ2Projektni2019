package classes.controllers;

import classes.AirTrafficControl;
import classes.Radar;
import classes.domain.extras.AlertBox;
import classes.domain.extras.FileWatcher;
import classes.domain.extras.FlightArea;
import classes.simulator.Simulator;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;


public class Controller implements Initializable {

    public static Controller app;
    @FXML
    private Button ShowCrashesButton;
    @FXML
    private Button ShowForeignButton;
    @FXML
    private Button NFZButton;
    @FXML
    private Label NFZLabel;
    @FXML
    private GridPane flightAreaGridPane;
    @FXML
    private Label newEventLabel;

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


    public void showCrashes(ActionEvent actionEvent) {
//        Platform.runLater(() -> {
            try {
                System.out.println();
                Parent root = FXMLLoader.load(getClass().getResource("../../views/crashWarning.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();

                stage.setScene(scene);
                stage.setTitle("Crash details");

                scene.getWindow().setOnCloseRequest(e -> {
                    e.consume();
                    stage.close();
                });

                stage.show();
            } catch (IOException ex) {
                AirTrafficControl.LOGGER.log(Level.SEVERE, ex.toString(), ex);
            }
//        });
    }


    public void showForeign(ActionEvent actionEvent) {
        try {
            System.out.println();
            Parent root = FXMLLoader.load(getClass().getResource("../../views/foreignView.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setTitle("Foreign details");

            scene.getWindow().setOnCloseRequest(e -> {
                e.consume();
                stage.close();
            });

            stage.show();
        } catch (IOException ex) {
            AirTrafficControl.LOGGER.log(Level.SEVERE, ex.toString(), ex);
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
            Integer height = (Integer.parseInt(split[2].split("=")[1]) + 1) * 100;

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
                    case "MilitaryFighterPlane":
                        if (split[3].contains("false")) {
                            t.setFill(Color.web("#2E86AB"));
                        } else {
                            t.setFill(Color.web("#075070"));
                        }
                        break;
                    case "MilitaryBomberPlane":
                        if (split[3].contains("false")) {
                            t.setFill(Color.web("#EC9A29"));
                        } else {
                            t.setFill(Color.web("#C67400"));
                        }
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

    public void activateNFZ(ActionEvent actionEvent) {
        System.out.println("NFZ is activated " + Simulator.flightArea.isNoFlight());
        if (Simulator.flightArea.isNoFlight()) {
            NFZLabel.setText("NFZ is OFF");
            NFZLabel.setTextFill(Color.GREEN);
            Simulator.flightArea.setNoFlight(false);
        } else {
            NFZLabel.setText("NFZ is ON");
            NFZLabel.setTextFill(Color.RED);
            Simulator.flightArea.setNoFlight(true);
        }
    }

    public void refreshEventsLabel(String eventData) {
        Platform.runLater(() -> newEventLabel.setText(eventData));
    }

    public static void displayCrash(String crashDetails) {
        Platform.runLater(() -> {
            AlertBox.display("Crash detected", crashDetails);
        });
    }

}
