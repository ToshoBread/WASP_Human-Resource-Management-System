package wasp.controllers;

import java.io.IOException;

import wasp.database.DBAction;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label labelWrongLogin;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private DBAction db = new DBAction();

    public void login(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Dashboard.fxml"));
            root = loader.load();

            DashboardController dashboardController = loader.getController();
            String username = usernameField.getText().trim().toLowerCase();
            String password = passwordField.getText().trim();

            if (db.verifyUser(username, password)) {
                dashboardController.displayName(db.getName(username));

                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                labelWrongLogin.setText("Incorrect Credentials!");
                labelWrongLogin.setVisible(true);
            }

        } catch (Exception e) {
            System.err.println(e);
        }

    }

    public void gotoRegister(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/register.fxml"));
        root = loader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
