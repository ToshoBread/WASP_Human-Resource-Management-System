package wasp.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import wasp.DTO.Employee;
import wasp.database.DatabaseConnection;

public class EmployeeDAO extends DAO<Employee> {

    @Override
    public Employee getById(int id) throws SQLException {
        Employee employee = new Employee();
        String sql = "SELECT * FROM employeeInfo WHERE employeeID = ?";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setInt(1, id);

            try (ResultSet result = stmt.executeQuery();) {

                if (result.next()) {
                    int storedEmployeeID = result.getInt("employeeID");
                    String storedLastName = result.getString("lastName");
                    String storedFirstName = result.getString("firstName");
                    String storedUsername = result.getString("username");
                    String storedEmail = result.getString("email");
                    int storedSexID = result.getInt("sexID");
                    int storedRoleID = result.getInt("roleID");
                    String storedPasswordHash = result.getString("passwordHash");
                    String storedSalt = result.getString("salt");

                    employee.setEmployeeID(storedEmployeeID);
                    employee.setLastName(storedLastName);
                    employee.setFirstName(storedFirstName);
                    employee.setUsername(storedUsername);
                    employee.setEmail(storedEmail);
                    employee.setSexID(storedSexID);
                    employee.setRoleID(storedRoleID);
                    employee.setPassword(storedPasswordHash);
                    employee.setSalt(storedSalt);
                }
            }
        }

        return employee;
    }

    public Employee getByUsername(String username) throws SQLException {
        Employee employee = new Employee();
        String sql = "SELECT * FROM employeeInfo WHERE username = ?";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setString(1, username);

            try (ResultSet result = stmt.executeQuery();) {

                if (result.next()) {
                    int storedEmployeeID = result.getInt("employeeID");
                    String storedLastName = result.getString("lastName");
                    String storedFirstName = result.getString("firstName");
                    String storedUsername = result.getString("username");
                    String storedEmail = result.getString("email");
                    int storedSexID = result.getInt("sexID");
                    int storedRoleID = result.getInt("roleID");
                    String storedPasswordHash = result.getString("passwordHash");
                    String storedSalt = result.getString("salt");

                    employee.setEmployeeID(storedEmployeeID);
                    employee.setLastName(storedLastName);
                    employee.setFirstName(storedFirstName);
                    employee.setUsername(storedUsername);
                    employee.setEmail(storedEmail);
                    employee.setSexID(storedSexID);
                    employee.setRoleID(storedRoleID);
                    employee.setPassword(storedPasswordHash);
                    employee.setSalt(storedSalt);
                }
            }
        }

        return employee;
    }

    public Employee getByEmail(String email) throws SQLException {
        Employee employee = new Employee();
        String sql = "SELECT * FROM employeeInfo WHERE email = ?";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setString(1, email);

            try (ResultSet result = stmt.executeQuery();) {

                if (result.next()) {
                    int employeeID = result.getInt("employeeID");
                    String lastName = result.getString("lastName");
                    String firstName = result.getString("firstName");
                    String username = result.getString("username");
                    String storedEmail = result.getString("email");
                    int sexID = result.getInt("sexID");

                    employee.setEmployeeID(employeeID);
                    employee.setLastName(lastName);
                    employee.setFirstName(firstName);
                    employee.setUsername(username);
                    employee.setEmail(storedEmail);
                    employee.setSexID(sexID);
                }
            }
        }

        return employee;
    }

    @Override
    public List<Employee> getAll() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employeeInfo";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet result = stmt.executeQuery();) {

            while (result.next()) {
                Employee employee = new Employee();

                int employeeID = result.getInt("employeeID");
                String lastName = result.getString("lastName");
                String firstName = result.getString("firstName");
                String username = result.getString("username");
                String email = result.getString("email");
                int sexID = result.getInt("sexID");

                employee.setEmployeeID(employeeID);
                employee.setLastName(lastName);
                employee.setFirstName(firstName);
                employee.setUsername(username);
                employee.setEmail(email);
                employee.setSexID(sexID);

                employees.add(employee);
            }
        }

        return employees;
    }

    @Override
    public void insert(Employee employee) throws SQLException {
        String sql = "INSERT INTO employeeInfo (lastName, firstName, email, roleID, username, birthDate, sexID, passwordHash, salt) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setString(1, employee.getLastName());
            stmt.setString(2, employee.getFirstName());
            stmt.setString(3, employee.getEmail());
            stmt.setInt(4, employee.getRoleID());
            stmt.setString(5, employee.getUsername());
            stmt.setDate(6, employee.getBirthdate());
            stmt.setInt(7, employee.getSexID());
            stmt.setString(8, employee.getPassword());
            stmt.setString(9, employee.getSalt());

            stmt.executeUpdate();
        }
    }

    @Override
    public void update(Employee employee) throws SQLException {
        String sql = "UPDATE employeeInfo " +
                "SET lastName = ?, firstName = ?, email = ?, roleID = ?, username = ?, birthDate = ?, sexID = ?, passwordHash = ? "
                +
                "WHERE employeeID = ?";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setString(1, employee.getLastName());
            stmt.setString(2, employee.getFirstName());
            stmt.setString(3, employee.getEmail());
            stmt.setInt(4, employee.getRoleID());
            stmt.setString(5, employee.getUsername());
            stmt.setDate(6, employee.getBirthdate());
            stmt.setInt(7, employee.getSexID());
            stmt.setString(8, employee.getPassword());
            stmt.setInt(9, employee.getEmployeeID());
            stmt.executeUpdate();
        }
    }

    @Override
    public void remove(Employee employee) throws SQLException {
        String sql = "DELETE FROM employeeInfo WHERE employeeID = ?";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setInt(1, employee.getEmployeeID());

            stmt.executeUpdate();
        }
    }
}
