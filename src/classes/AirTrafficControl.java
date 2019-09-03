package classes;

import classes.domain.extras.ConfirmBox;
import classes.domain.extras.FlightArea;
import classes.simulator.Simulator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class AirTrafficControl extends Application {

    // region Members
    private Stage window;

    // for logger
    public static final Properties PROPERTIES = new Properties();
    public static final Logger LOGGER = Logger.getLogger("Logger");

    // endregion

    // region Static block for logger
    static {
        try {
            FileHandler fileHandler = new FileHandler("error.log",  true);
            LOGGER.addHandler(fileHandler);
            SimpleFormatter simpleFormatter = new SimpleFormatter();    // formatting of the logger
            fileHandler.setFormatter(simpleFormatter);
//            LOGGER.setUseParentHandlers(false);   // do not print out to console
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // endregion

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../views/sample.fxml"));
        window = primaryStage;
        window.setTitle("Air Traffic Control");
        Scene scene = new Scene(root, 1000, 800);
        scene.getStylesheets().add("views/custom.css");
        window.setScene(scene);
        window.show();

        // on close
        window.setOnCloseRequest( event -> {
            event.consume();
            closeProgram();
        });
    }

    private void closeProgram() {
        boolean answer = ConfirmBox.display("Alert!", "Are you sure you want to exit?");
        if (answer) {
            window.close();
        }
    }

    public static void main(String[] args) {
        FlightArea flightArea = new FlightArea();
        Simulator s = new Simulator(flightArea);
        Radar r = new Radar(flightArea);
        s.start();
        r.start();
        launch(args);
    }

    // TODO: 18.8.2019. Remove unecessary code from everything (Aircraft, FlightArea, Field...)
}
