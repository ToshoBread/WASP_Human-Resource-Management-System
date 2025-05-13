package wasp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import wasp.DAO.JobDAO;
import wasp.DAO.SexDAO;
import wasp.DTO.Employee;
import wasp.singleton.LoggedInUser;

public class ProfileController implements Initializable {

    @FXML
    private Label empName, empSex, empPosition, empDept, empEmail, hireDate;

    @FXML
    private Button dashboardBtn;

    @FXML
    private Rectangle separator;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private SexDAO sexDAO = new SexDAO();
    private JobDAO jobDAO = new JobDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LoggedInUser loggedInUser = LoggedInUser.getInstance();
        Employee user = loggedInUser.getUser();

        if (user.getRoleID() == 3) {
            dashboardBtn.setVisible(true);
            separator.setVisible(true);
        }

        try {
            empName.setText(String.format("Name: %s, %s", user.getLastName(), user.getFirstName()));
            empSex.setText(String.format("Sex: %s", sexDAO.getById(user.getSexID()).getSexLabel()));
            empPosition.setText(String.format("Position: %s", jobDAO.getById(user.getEmployeeID()).getPosition()));
            empDept.setText(String.format("Department: %s", jobDAO.getById(user.getEmployeeID()).getDepartment()));
            empEmail.setText(String.format("Email: %s", user.getEmail()));
            hireDate.setText(String.format("Hired on: %s", jobDAO.getById(user.getEmployeeID()).getEmployDate()));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
