package wasp.controllers;

import java.io.IOException;

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
import wasp.services.EmployeeService;

public class AdminLoginController extends LoginController {

    @FXML
    private TextField userField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label loginMessage;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private EmployeeService empService = new EmployeeService();

    @Override
    public void login(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Admin.fxml"));
            root = loader.load();

            // DashboardController dashboardController = loader.getController();
            String username = userField.getText().trim().toLowerCase();
            String password = passwordField.getText().trim();

            if (empService.verifyUser(username, password)) {
                // dashboardController.displayName(db.getName(username));

                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                loginMessage.setText("Incorrect Credentials!");
                loginMessage.setVisible(true);
            }

        } catch (Exception e) {
            System.err.println(e);
        }

    }
}
