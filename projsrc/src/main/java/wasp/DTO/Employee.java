package wasp.DTO;

import java.sql.Date;
import java.sql.Timestamp;

public class Employee {

    private int employeeID;
    private String lastName;
    private String firstName;
    private String email;
    private int roleID;
    private String username;
    private Date birthdate;
    private int sexID;
    private String password;
    private String salt;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Employee() {
    }

    public Employee(String lastName, String firstName, String email, String username, String password, String salt,
            int roleID, int sexID, Date birthdate, Timestamp createdAt, Timestamp updatedAt) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.roleID = roleID;
        this.username = username;
        this.birthdate = birthdate;
        this.sexID = sexID;
        this.password = password;
        this.salt = salt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int getSexID() {
        return sexID;
    }

    public void setSexID(int sexID) {
        this.sexID = sexID;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

}
