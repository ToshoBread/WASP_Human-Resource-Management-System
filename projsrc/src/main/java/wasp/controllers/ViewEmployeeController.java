package wasp.controllers;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import wasp.DAO.JobDAO;
import wasp.DAO.SexDAO;
import wasp.DTO.Employee;
import wasp.DTO.Job;
import wasp.singleton.LoggedInUser;

public class ViewEmployeeController {

    @FXML
    private Label empID, empName, empSex, empBirthdate, empEmail, empDept, empPosition, employDate,
            effectiveDate, separateDate, currentSalary;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private SexDAO sexDAO = new SexDAO();
    private JobDAO jobDAO = new JobDAO();

    public void setEmployee(Employee employee) throws SQLException {
        Job job = jobDAO.getById(employee.getEmployeeID());

        empID.setText(String.format("ID: %d", employee.getEmployeeID()));
        empName.setText(String.format("Name: %s, %s", employee.getLastName(), employee.getFirstName()));
        empSex.setText(String.format("Sex: %s", sexDAO.getById(employee.getSexID()).getSexLabel()));
        empBirthdate.setText(String.format("Birthdate: %s", employee.getBirthdate()));
        empEmail.setText(String.format("Email: %s", employee.getEmail()));

        empDept.setText(String.format("Department: %s", job.getDepartment()));
        empPosition.setText(String.format("Position: %s", job.getPosition()));
        employDate.setText(String.format("Employment Date: %s", job.getEmployDate()));
        effectiveDate.setText(String.format("Effectivity Date: %s", job.getEffectivityDate()));
        separateDate.setText(String.format("Separation Date: %s",
                job.getSeparationDate() != null ? job.getSeparationDate() : "None"));

        currentSalary.setText("Current Salary: To implement");
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
