package wasp.services;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import wasp.DAO.EmployeeDAO;
import wasp.DTO.Employee;
import wasp.util.Encryptor;

public class EmployeeService {
    private Encryptor enc = new Encryptor();
    EmployeeDAO empDAO = new EmployeeDAO();

    public boolean verifyUser(String username, String password) throws SQLException, NoSuchAlgorithmException {
        Employee employee = empDAO.getByUsername(username);

        if (employee != null) {
            String storedPassword = employee.getPassword();
            String storedSalt = employee.getSalt();

            byte[] saltByte = enc.hexToBytes(storedSalt);
            String hashedPassword = enc.encryptPass(password, saltByte);

            if (hashedPassword.equals(storedPassword)) {
                return true;
            }

        }

        return false;
    }

    public boolean usernameExists(String username) throws SQLException {
        Employee employee = empDAO.getByUsername(username);
        return employee.getUsername() != null;
    }

    public boolean emailIsUsed(String email) throws SQLException {
        Employee employee = empDAO.getByEmail(email);
        return employee.getEmail() != null;
    }

    public boolean verifyEmail(String email) {
        String emailRegex = "^[a-z0-9_+&*-]+(?:\\.[a-z0-9_+&*-]+)*@(?:[a-z0-9_+&*-]+\\.)*[a-z]{2,7}$";
        return email.matches(emailRegex);
    }
}
