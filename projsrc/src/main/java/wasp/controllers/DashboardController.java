package wasp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardController {

    @FXML
    Label nameLabel;

    public void displayName(String user) {
        nameLabel.setText("Welcome " + user);
    }

}
