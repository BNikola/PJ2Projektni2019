package classes;

import classes.domain.extras.ConfirmBox;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AirTrafficControl extends Application {

    private Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("views/sample.fxml"));
        window = primaryStage;
        window.setTitle("Air Traffic Control");
        Scene scene = new Scene(root, 800, 600);
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
        // TODO: 17.8.2019. add termination of Simulator and Radar
        boolean answer = ConfirmBox.display("Alert!", "Are you sure you want to exit?");
        if (answer) {
            window.close();
        }
    }

    public static void main(String[] args) {
        // TODO: 17.8.2019. add Simulator and Radar
        launch(args);
    }
}
