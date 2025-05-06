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
import wasp.DAO.EmployeeDAO;
import wasp.DTO.Employee;
import wasp.services.EmployeeService;
import wasp.singleton.LoggedInUser;

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
    private EmployeeDAO empDAO = new EmployeeDAO();

    @Override
    public void login(ActionEvent event) throws IOException {
        LoggedInUser loggedInUser = LoggedInUser.getInstance();

        try {
            String username = userField.getText().trim().toLowerCase();
            String password = passwordField.getText().trim();

            Employee employee = empDAO.getByUsername(username);

            if (username == null || password == null) {
                showLoginMessage("Please fill up all fields.");
                return;
            }

            if (employee.getRoleID() != 3) {
                showLoginMessage("Incorrect Privilege.");
                return;
            }

            if (!empService.verifyUser(username, password)) {
                showLoginMessage("Incorrect Credentials.");
                return;
            }

            loggedInUser.setUser(employee);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Admin.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showLoginMessage("Something went wrong.");
        }

    }

    @Override
    public void showLoginMessage(String message) {
        loginMessage.setText(message);
        loginMessage.setVisible(true);
    }
}
