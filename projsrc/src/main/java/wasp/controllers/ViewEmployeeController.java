package wasp.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import wasp.DAO.EmployeeDAO;
import wasp.DAO.JobDAO;
import wasp.DAO.SexDAO;
import wasp.DTO.Employee;
import wasp.DTO.Job;
import wasp.services.EmployeeService;
import wasp.singleton.LoggedInUser;

public class ViewEmployeeController implements Initializable {

    @FXML
    private Button editBtn, viewBtn;

    @FXML
    private Label empID, empName, empSex, empBirthdate, empEmail, empDept, empPosition, employDate,
            effectiveDate, separateDate, currentSalary, editMessage;

    @FXML
    private TextField lnameField, fnameField, emailField;

    @FXML
    private DatePicker birthdateField;

    @FXML
    private Button saveBtn;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private Employee employee;

    private EmployeeService empService = new EmployeeService();
    private SexDAO sexDAO = new SexDAO();
    private JobDAO jobDAO = new JobDAO();
    private EmployeeDAO employeeDAO = new EmployeeDAO();

    private boolean editMode = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewBtn.setVisible(false);
        viewBtn.setManaged(false);

        lnameField.setVisible(false);
        lnameField.setManaged(false);
        fnameField.setVisible(false);
        fnameField.setManaged(false);
        birthdateField.setVisible(false);
        birthdateField.setManaged(false);
        emailField.setVisible(false);
        emailField.setManaged(false);

        saveBtn.setVisible(false);
    }

    public void setEmployee(Employee employee) throws SQLException {
        this.employee = employee;
        displayEmployeeInfo();
    }

    private void displayEmployeeInfo() throws SQLException {
        Job job = jobDAO.getById(employee.getEmployeeID());

        empID.setText(String.format("ID: %d", employee.getEmployeeID()));
        empName.setText(String.format("%s, %s", employee.getLastName(), employee.getFirstName()));
        empSex.setText(sexDAO.getById(employee.getSexID()).getSexLabel());
        empBirthdate.setText(String.valueOf(employee.getBirthdate()));
        empEmail.setText(employee.getEmail());

        empDept.setText(String.format("Department: %s", job.getDepartment()));
        empPosition.setText(String.format("Position: %s", job.getPosition()));
        employDate.setText(String.format("Employment Date: %s", job.getEmployDate()));
        effectiveDate.setText(String.format("Effectivity Date: %s", job.getEffectivityDate()));
        separateDate.setText(String.format("Separation Date: %s",
                job.getSeparationDate() != null ? job.getSeparationDate() : "None"));

        currentSalary.setText("Current Salary: To implement");
    }

    public void toggleMode(ActionEvent event) {
        editMode = !editMode;

        editBtn.setVisible(!editMode);
        editBtn.setManaged(!editMode);

        viewBtn.setVisible(editMode);
        viewBtn.setManaged(editMode);

        empName.setVisible(!editMode);
        empName.setManaged(!editMode);
        empSex.setVisible(!editMode);
        empSex.setManaged(!editMode);
        empBirthdate.setVisible(!editMode);
        empBirthdate.setManaged(!editMode);
        empEmail.setVisible(!editMode);
        empEmail.setManaged(!editMode);

        lnameField.setVisible(editMode);
        lnameField.setManaged(editMode);
        lnameField.setText(employee.getLastName());
        fnameField.setVisible(editMode);
        fnameField.setManaged(editMode);
        fnameField.setText(employee.getFirstName());
        birthdateField.setVisible(editMode);
        birthdateField.setManaged(editMode);
        birthdateField.setPromptText(String.valueOf(employee.getBirthdate()));
        emailField.setVisible(editMode);
        emailField.setManaged(editMode);
        emailField.setText(employee.getEmail());

        saveBtn.setVisible(editMode);
        editMessage.setVisible(false);
    }

    public void saveChanges(ActionEvent event) throws SQLException {
        String email = emailField.getText().trim().toLowerCase();

        if (!empService.verifyEmail(email)) {
            editMessage.setText("Invalid Email.");
            editMessage.setTextFill(Color.RED);
            editMessage.setVisible(true);
            return;
        }

        employee.setLastName(lnameField.getText().trim());
        employee.setFirstName(fnameField.getText().trim());
        employee.setBirthdate(Date.valueOf((LocalDate) birthdateField.getValue()));
        employee.setEmail(email);

        employeeDAO.update(employee);

        editMessage.setText("Changes Saved.");
        editMessage.setTextFill(Color.GREEN);
        editMessage.setVisible(true);
    }

    public void gotoDashboard(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Dashboard.fxml"));
            root = loader.load();

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logout(ActionEvent event) throws IOException {
        LoggedInUser loggedInUser = LoggedInUser.getInstance();

        try {
            loggedInUser.clearUser();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
            root = loader.load();

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
