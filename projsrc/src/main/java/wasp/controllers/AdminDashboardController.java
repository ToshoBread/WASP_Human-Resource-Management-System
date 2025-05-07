package wasp.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javax.management.relation.Role;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import wasp.DAO.EmployeeDAO;
import wasp.DTO.Employee;
import wasp.DTO.Sex;
import wasp.singleton.LoggedInUser;

public class AdminDashboardController implements Initializable {

    @FXML
    private Button logoutBtn;

    @FXML
    private Label userDashboardLabel;

    @FXML
    private TextField tableSearchField;

    @FXML
    private TableView<Employee> employeeTable;

    @FXML
    private TableColumn<Employee, String> lastnameColumn, firstnameColumn, emailColumn;

    @FXML
    private TableColumn<Role, String> positionColumn;

    @FXML
    private TableColumn<Sex, String> sexColumn;

    @FXML
    private TableColumn<Employee, Integer> idColumn;

    @FXML
    private TableColumn<Employee, Date> birthdateColumn;

    private Stage stage;
    private Scene scene;
    private Parent root;
    private EmployeeDAO empDAO = new EmployeeDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LoggedInUser loggedInUser = LoggedInUser.getInstance();
        Employee user = loggedInUser.getUser();
        userDashboardLabel.setText(String.format("Dashboard - %s %s", user.getFirstName(), user.getLastName()));

        // TODO: Display other table's values with the main employee table

        idColumn.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("employeeID"));
        lastnameColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastName"));
        firstnameColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("firstName"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<Role, String>("roleLabel"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("email"));
        sexColumn.setCellValueFactory(new PropertyValueFactory<Sex, String>("sexLabel"));
        birthdateColumn.setCellValueFactory(new PropertyValueFactory<Employee, Date>("birthdate"));

        try {
            displayTableData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logout(ActionEvent event) throws IOException {
        LoggedInUser loggedInUser = LoggedInUser.getInstance();

        try {
            loggedInUser.clearUser();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ChooseRole.fxml"));
            root = loader.load();

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayTableData() throws SQLException {
        List<Employee> employeesList = empDAO.getAll();
        ObservableList<Employee> employees = FXCollections.observableArrayList(employeesList);
        FilteredList<Employee> filteredData = new FilteredList<>(employees, p -> true);

        tableSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(employee -> {
                if (newValue.trim() == null || newValue.trim().isEmpty()) {
                    return true;
                }
                String lowercaseFilter = newValue.toLowerCase();

                return employee.getLastName().toLowerCase().contains(lowercaseFilter)
                        || employee.getFirstName().toLowerCase().contains(lowercaseFilter)
                        || employee.getEmail().toLowerCase().contains(lowercaseFilter)
                        || String.valueOf(employee.getEmployeeID()).contains(lowercaseFilter);
            });
        });

        SortedList<Employee> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(employeeTable.comparatorProperty());

        employeeTable.setItems(sortedData);
    }
}
