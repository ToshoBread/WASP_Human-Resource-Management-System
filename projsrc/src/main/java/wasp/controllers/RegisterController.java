package wasp.controllers;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import wasp.DAO.EmployeeDAO;
import wasp.DTO.Employee;
import wasp.services.EmployeeService;
import wasp.util.Encryptor;
import wasp.util.Utils;

public class RegisterController {

    @FXML
    private TextField usernameField, lastNameField, firstNameField, emailField;

    @FXML
    private PasswordField passwordField, confirmPasswordField;

    @FXML
    DatePicker birthdateField;

    @FXML
    private RadioButton radioMale, radioFemale, radioEmployee, radioSupervisor, radioHR, radioIT;

    @FXML
    private Button returnButton, registerButton;

    @FXML
    private Label registerMessage;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private Encryptor enc = new Encryptor();
    private Utils util = new Utils();
    private EmployeeService empService = new EmployeeService();
    private EmployeeDAO empDAO = new EmployeeDAO();

    public void registerUser(ActionEvent event) throws IOException {
        Employee employee = new Employee();

        try {
            String username = usernameField.getText().trim().toLowerCase();
            String lastName = util.toTitleCase(lastNameField.getText().trim());
            String firstName = util.toTitleCase(firstNameField.getText().trim());
            String email = emailField.getText().trim().toLowerCase();
            String password = passwordField.getText().trim();
            String confirmPassword = confirmPasswordField.getText().trim();
            LocalDate birthdate = birthdateField.getValue();
            int sexID = 0;
            int roleID = 0;

            String emailRegex = "^[a-z0-9_+&*-]+(?:\\.[a-z0-9_+&*-]+)*@(?:[a-z0-9_+&*-]+\\.)*[a-z]{2,7}$";

            if (radioMale.isSelected()) {
                sexID = 1;
            } else if (radioFemale.isSelected()) {
                sexID = 2;
            }

            if (radioEmployee.isSelected()) {
                roleID = 1;
            } else if (radioSupervisor.isSelected()) {
                roleID = 2;
            } else if (radioHR.isSelected()) {
                roleID = 3;
            } else if (radioIT.isSelected()) {
                roleID = 4;
            }

            if (username.isBlank() || lastName.isBlank() || firstName.isBlank() || email.isBlank()
                    || password.isBlank()) {
                showRegisterMessage("Please fill up all fields.");
                return;
            }

            if (util.containsNumeric(lastName) || util.containsNumeric(firstName)) {
                showRegisterMessage("Name must not contain numerical characters.");
                return;
            }

            if (sexID == 0) {
                showRegisterMessage("Please select corresponding sex.");
                return;
            }

            if (roleID == 0) {
                showRegisterMessage("Please select corresponding role.");
                return;
            }

            if (!email.matches(emailRegex)) {
                showRegisterMessage("Invalid Email.");
                return;
            }

            if (!password.equals(confirmPassword)) {
                showRegisterMessage("Passwords do not match.");
                return;
            }

            if (empService.usernameExists(username)) {
                showRegisterMessage("Username is taken.");
                return;
            }

            if (empService.emailIsUsed(email)) {
                showRegisterMessage("Email already in use.");
                return;
            }

            byte[] saltBytes = enc.generateSalt();
            String hexSalt = enc.bytesToHex(saltBytes);
            String hashedPassword = enc.encryptPass(password, saltBytes);

            employee.setLastName(lastName);
            employee.setFirstName(firstName);
            employee.setEmail(email);
            employee.setUsername(username);
            employee.setPassword(hashedPassword);
            employee.setBirthdate(Date.valueOf(birthdate));
            employee.setSalt(hexSalt);
            employee.setSexID(sexID);
            employee.setRoleID(roleID);

            empDAO.insert(employee);

            registerMessage.setTextFill(Color.GREEN);
            showRegisterMessage("User Registered Successfully.");

        } catch (Exception e) {
            e.printStackTrace();
            registerMessage.setText("Something went wrong.");
        }

    }

    private void showRegisterMessage(String message) {
        registerMessage.setText(message);
        registerMessage.setVisible(true);
    }

}
