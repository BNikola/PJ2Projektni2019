package classes.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ForeignView implements Initializable {

    @FXML
    private ListView foreignList;

    private ObservableList<String> foreignAircrafts;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // list of foreign aircrafts
        foreignAircrafts = FXCollections.observableArrayList();
        loadDetectedForeignAircrafts(foreignAircrafts);
        foreignList.getItems().addAll(foreignAircrafts);
    }

    private void loadDetectedForeignAircrafts(ObservableList<String> list) {
        File[] files = new File("events").listFiles();
        if (files != null) {
            for (File f : files) {
                try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                    br.readLine();
                    String data = br.readLine();
                    data +=" detected at: " + f.getName().trim().replace(".txt", "");
                    list.add(data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
