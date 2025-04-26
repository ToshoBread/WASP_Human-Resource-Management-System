package wasp.database;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import wasp.util.Encryptor;

public class DBAction extends DatabaseConnection {
    private Encryptor enc = new Encryptor();

    public boolean verifyUser(String username, String password) {
        try {
            Connection conn = super.getConnection();
            String sql = "SELECT password, salt FROM employeeInfo WHERE username = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                String storedPassword = result.getString("password");
                String storedSalt = result.getString("salt");

                byte[] saltByte = enc.hexToBytes(storedSalt);
                String hashedPassword = enc.encryptPass(password, saltByte);

                if (hashedPassword.equals(storedPassword)) {
                    return true;
                }
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean usernameExists(String username) {
        try {
            Connection conn = super.getConnection();
            String sql = "SELECT username FROM employeeInfo WHERE username = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                return true;
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean emailIsUsed(String email) {
        try {
            Connection conn = super.getConnection();
            String sql = "SELECT email FROM employeeInfo WHERE email = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                return true;
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getName(String username) {
        try {
            Connection conn = super.getConnection();
            String sql = "SELECT firstName, lastName FROM employeeInfo WHERE username = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                String fName = result.getString("FirstName");
                String lName = result.getString("LastName");

                return String.format("%s %s", fName, lName);
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addUser(String username, String password, String email, String lastName, String firstName, int age,
            String sex) {
        try {
            Connection conn = super.getConnection();

            String sql = "INSERT INTO employeeinfo (username, password, email, lastName, firstName, age, sex, salt) values (?, ?, ?, ?, ?, ?, ?, ?)";

            byte[] salt = enc.generateSalt();
            String hexSalt = enc.bytesToHex(salt);
            String hashedPassword = enc.encryptPass(password, salt);

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);
            stmt.setString(3, email);
            stmt.setString(4, lastName);
            stmt.setString(5, firstName);
            stmt.setInt(6, age);
            stmt.setString(7, sex);
            stmt.setString(8, hexSalt);

            stmt.execute();

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
