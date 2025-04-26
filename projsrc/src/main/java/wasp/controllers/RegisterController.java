package wasp.controllers;

import java.io.IOException;

import wasp.database.DBAction;
import wasp.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {

    @FXML
    private TextField usernameField, lastNameField, firstNameField, emailField, ageField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private RadioButton rMale, rFemale;

    @FXML
    private Button returnButton, registerButton;

    @FXML
    private Label registerMessage;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private DBAction db = new DBAction();

    public void registerUser(ActionEvent event) throws IOException {
        try {
            int minAge = 18;
            int maxAge = 99;

            String username = usernameField.getText().trim().toLowerCase();
            String lastName = Utils.toTitleCase(lastNameField.getText().trim());
            String firstName = Utils.toTitleCase(firstNameField.getText().trim());
            String email = emailField.getText().trim().toLowerCase();
            String password = passwordField.getText().trim();
            int age = Integer.parseInt(ageField.getText().trim());
            String sex = "";

            String emailRegex = "^[a-z0-9_+&*-]+(?:\\.[a-z0-9_+&*-]+)*@(?:[a-z0-9_+&*-]+\\.)*[a-z]{2,7}$";

            if (rMale.isSelected()) {
                sex = "M";
            } else if (rFemale.isSelected()) {
                sex = "F";
            }

            if (db.usernameExists(username)) {
                registerMessage.setText("Username is taken.");
                registerMessage.setVisible(true);
                return;
            }

            if (Utils.containsNumeric(lastName) || Utils.containsNumeric(firstName)) {
                registerMessage.setText("Name must not contain numerical characters.");
                registerMessage.setVisible(true);
                return;
            }

            if (!email.matches(emailRegex)) {
                registerMessage.setText("Invalid Email.");
                registerMessage.setVisible(true);
                return;
            }

            if (db.emailIsUsed(email)) {
                registerMessage.setText("Email already in use.");
                registerMessage.setVisible(true);
                return;
            }

            if (minAge > age || age > maxAge) {
                registerMessage.setText("Invalid age.");
                registerMessage.setVisible(true);
                return;
            }

            db.addUser(username, password, email, lastName, firstName, age, sex);
            registerMessage.setText("User Registered Successfully.");
            registerMessage.setVisible(true);

        } catch (NumberFormatException e) {
            System.err.println("Enter numbers only - " + e);
            registerMessage.setText("Age must be a number!");
        }

    }

    public void gotoLogin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/app.fxml"));
        root = loader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
