package classes.controllers;

import classes.simulator.Simulator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;


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

    public synchronized void refreshTextArea() {
        content.setText(Simulator.flightArea.toString());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        app = this;
    }
}
