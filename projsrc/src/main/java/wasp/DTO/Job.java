package wasp.DTO;

import java.sql.Date;

public class Job {

    private int jobID;
    private int employeeID;
    private String position;
    private String department;
    private Date employDate;
    private Date effectivityDate;
    private Date separationDate;

    public int getJobID() {
        return jobID;
    }

    public void setJobID(int jobID) {
        this.jobID = jobID;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Date getEmployDate() {
        return employDate;
    }

    public void setEmployDate(Date employDate) {
        this.employDate = employDate;
    }

    public Date getEffectivityDate() {
        return effectivityDate;
    }

    public void setEffectivityDate(Date effectivityDate) {
        this.effectivityDate = effectivityDate;
    }

    public Date getSeparationDate() {
        return separationDate;
    }

    public void setSeparationDate(Date separationDate) {
        this.separationDate = separationDate;
    }

}
