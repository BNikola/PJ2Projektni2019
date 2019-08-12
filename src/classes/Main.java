package classes;

import classes.simulator.Simulator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../views/sample.fxml"));
        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(root, 300, 275);
        scene.getStylesheets().add("views/custom.css");
        primaryStage.setScene(scene);
        primaryStage.show();

        // start simulator

    }


    public static void main(String[] args) {
        new Thread(() -> Simulator.main(args)).start();
        launch(args);
    }
}
