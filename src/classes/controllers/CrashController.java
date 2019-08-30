package classes.controllers;

import classes.AirTrafficControl;
import classes.domain.extras.CrashWarning;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class CrashController implements Initializable {

    // TODO: 21.8.2019. remake this for  listing all crashes
    @FXML
    private TableView<CrashWarning> crashTable;
    @FXML
    private TableColumn<Object, Object> positionColumn;
    @FXML
    private TableColumn crashedOneColumn;
    @FXML
    private TableColumn crashedTwoColumn;
    @FXML
    private TableColumn<Object, Object> crashedDateColumn;

    private ObservableList<CrashWarning> crashWarning;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // table of crashes
        crashWarning = FXCollections.observableArrayList();
        crashTable.setItems(crashWarning);
        crashTable.setPlaceholder(new Label("There are no foreign aircrafts detected"));

        crashedOneColumn.setCellValueFactory(new PropertyValueFactory<>("crashed1"));
        crashedTwoColumn.setCellValueFactory(new PropertyValueFactory<>("crashed2"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("positionOfCrash"));
        crashedDateColumn.setCellValueFactory(new PropertyValueFactory<>("timeOfCrash"));

        loadCrashesFromFiles(crashWarning);
        crashTable.setItems(crashWarning);
        crashTable.refresh();

    }

    private void loadCrashesFromFiles(ObservableList<CrashWarning> list) {
        File [] files = new File("alert").listFiles();
        if (files != null) {
            for (File f : files) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
                    CrashWarning crashWarning = (CrashWarning) ois.readObject();
                    if (crashWarning != null) {
                        list.add(crashWarning);
                    }
                } catch (IOException | ClassNotFoundException e) {
                    AirTrafficControl.LOGGER.log(Level.SEVERE, e.toString(), e);
                }
            }
        }
    }
}
