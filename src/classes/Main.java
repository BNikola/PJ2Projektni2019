package classes;

import classes.domain.extras.FlightArea;
import classes.radar.Radar;
import classes.simulator.Simulator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.Level;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../views/sample.fxml"));
            primaryStage.setTitle("Hello World");
            Scene scene = new Scene(root, 1000, 800);
            scene.getStylesheets().add("views/custom.css");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            AirTrafficControl.LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        // start simulator

    }


    public static void main(String[] args) {
        FlightArea flightArea = new FlightArea();
        Simulator s = new Simulator(flightArea);
//        new Thread(() -> s.main(args)).start();
        Radar r = new Radar(flightArea);
        s.start();
        r.start();
        launch(args);
    }
}
