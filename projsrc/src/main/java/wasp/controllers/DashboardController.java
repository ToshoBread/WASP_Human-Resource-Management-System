package wasp.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleStringProperty;
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
import wasp.DAO.RoleDAO;
import wasp.DAO.SexDAO;
import wasp.DTO.Employee;
import wasp.DTO.Sex;
import wasp.DTO.Role;
import wasp.singleton.LoggedInUser;

public class DashboardController implements Initializable {

    @FXML
    private Button logoutBtn;

    @FXML
    private Label userDashboardLabel;

    @FXML
    private TextField tableSearchField;

    @FXML
    private TableView<Employee> employeeTable;

    @FXML
    private TableColumn<Employee, Integer> idColumn;

    @FXML
    private TableColumn<Employee, String> lastnameColumn;

    @FXML
    private TableColumn<Employee, String> firstnameColumn;

    @FXML
    private TableColumn<Employee, String> emailColumn;

    @FXML
    private TableColumn<Employee, String> positionColumn;

    @FXML
    private TableColumn<Employee, String> sexColumn;

    @FXML
    private TableColumn<Employee, Date> birthdateColumn;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private EmployeeDAO empDAO = new EmployeeDAO();
    private SexDAO sexDAO = new SexDAO();
    private RoleDAO roleDAO = new RoleDAO();

    private Map<Integer, Sex> sexMap;
    private Map<Integer, Role> roleMap;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LoggedInUser loggedInUser = LoggedInUser.getInstance();
        Employee user = loggedInUser.getUser();
        userDashboardLabel.setText(String.format("Dashboard - %s %s", user.getFirstName(), user.getLastName()));

        try {
            List<Sex> allSexes = sexDAO.getAll();
            List<Role> allRoles = roleDAO.getAll();
            sexMap = allSexes.stream().collect(Collectors.toMap(Sex::getSexID, Function.identity()));
            roleMap = allRoles.stream().collect(Collectors.toMap(Role::getRoleID, Function.identity()));

            idColumn.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("employeeID"));
            lastnameColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastName"));
            firstnameColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("firstName"));
            emailColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("email"));
            birthdateColumn.setCellValueFactory(new PropertyValueFactory<Employee, Date>("birthdate"));

            // TODO: display formatted version of birthdate along with dynamic search

            sexColumn.setCellValueFactory(cellData -> {
                Employee employee = cellData.getValue();
                Sex sex = sexMap.get(employee.getSexID());
                String label = sex != null ? sex.getSexLabel() : "Unknown";
                return new SimpleStringProperty(label);
            });

            positionColumn.setCellValueFactory(cellData -> {
                Employee employee = cellData.getValue();
                Role role = roleMap.get(employee.getRoleID());
                String label = role != null ? role.getRoleCode() : "Null";
                return new SimpleStringProperty(label);
            });

            displayTableData();
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

                String lowercaseFilter = newValue.trim().toLowerCase();

                boolean matchesBasic = employee.getLastName().toLowerCase().contains(lowercaseFilter)
                        || employee.getFirstName().toLowerCase().contains(lowercaseFilter)
                        || employee.getEmail().toLowerCase().contains(lowercaseFilter)
                        || String.valueOf(employee.getEmployeeID()).contains(lowercaseFilter);

                if (matchesBasic) {
                    return true;
                }

                Sex sex = sexMap.get(employee.getSexID());
                if (sex != null && sex.getSexLabel().toLowerCase().contains(lowercaseFilter)) {
                    return true;
                }

                Role role = roleMap.get(employee.getRoleID());
                if (role != null && role.getRoleLabel().toLowerCase().contains(lowercaseFilter)) {
                    return true;
                }

                return false;
            });
        });

        SortedList<Employee> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(employeeTable.comparatorProperty());

        employeeTable.setItems(sortedData);
    }

    public void gotoProfile(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Profile.fxml"));
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
