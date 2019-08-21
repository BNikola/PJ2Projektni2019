package classes.controllers;

import classes.domain.extras.CrashWarning;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;

import java.net.URL;
import java.util.ResourceBundle;

public class CrashController implements Initializable {

    // TODO: 21.8.2019. remake this for  listing all crashes
    @FXML
    private ListView crashDetailsListView;
    @FXML
    private ScrollPane scrollPane;

    private CrashWarning crashWarning;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        crashDetailsListView.getItems().add(crashWarning);
    }

    public CrashWarning getCrashWarning() {
        return crashWarning;
    }

    public void setCrashWarning(CrashWarning crashWarning) {
        this.crashWarning = crashWarning;
    }
}
