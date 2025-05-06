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

        if (employee.getUsername() != null) {
            return true;
        }

        return false;
    }

    public boolean emailIsUsed(String email) throws SQLException {
        Employee employee = empDAO.getByEmail(email);

        if (employee.getEmail() != null) {
            return true;
        }

        return false;
    }
}
