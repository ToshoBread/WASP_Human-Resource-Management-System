DROP TABLE EmployeeInfo;
DROP TABLE JobHistory;
DROP TABLE PayrollInfo;
DROP TABLE AttendanceTracker;
DROP TABLE SalaryList;
DROP TABLE RoleList;
DROP TABLE SexList;

CREATE TABLE SalaryList (
    salaryID INT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(20),
    amount DECIMAL(10, 2)
);

CREATE TABLE SexList (
    sexID INT AUTO_INCREMENT PRIMARY KEY,
    sexCode CHAR(1) NOT NULL UNIQUE,
    sexLabel VARCHAR(20)
);

CREATE TABLE RoleList (
    roleID INT AUTO_INCREMENT PRIMARY KEY,
    roleCode VARCHAR(20) NOT NULL UNIQUE,
    roleLabel VARCHAR(50)
);

CREATE TABLE EmployeeInfo (
    employeeID INT AUTO_INCREMENT PRIMARY KEY,
    lastName VARCHAR(50) NOT NULL,
    firstName VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    roleID INT NOT NULL,
    username VARCHAR(255) NOT NULL UNIQUE,
    birthDate DATE NOT NULL,
    sexID INT NOT NULL,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    passwordHash VARCHAR(255) NOT NULL,
    salt VARCHAR(255) NOT NULL,
    FOREIGN KEY (roleID) REFERENCES RoleList(roleID),
    FOREIGN KEY (sexID) REFERENCES SexList(sexID)
);

CREATE TABLE JobHistory (
    jobID INT AUTO_INCREMENT PRIMARY KEY,
    employeeID INT NOT NULL,
    position VARCHAR(50),
    department VARCHAR(50),
    employDate DATE,
    resignDate DATE,
    effectivityDate DATE,
    FOREIGN KEY (employeeID) REFERENCES EmployeeInfo(employeeID) ON DELETE CASCADE
);

CREATE TABLE PayrollInfo (
    payrollID INT AUTO_INCREMENT PRIMARY KEY,
    employeeID INT NOT NULL,
    salaryID INT,
    FOREIGN KEY (employeeID) REFERENCES EmployeeInfo(employeeID) ON DELETE CASCADE,
    FOREIGN KEY (salaryID) REFERENCES SalaryList(salaryID) ON DELETE CASCADE 
);

CREATE TABLE AttendanceTracker (
    attendanceID INT AUTO_INCREMENT PRIMARY KEY,
    employeeID INT NOT NULL,
    timeIn TIME,
    timeOut TIME,
    date DATE,
    FOREIGN KEY (employeeID) REFERENCES EmployeeInfo(employeeID) ON DELETE CASCADE
);

INSERT INTO SalaryList (salaryID, type, amount)
VALUES 
(1, 'FullTime', 30000.00),
(2, 'PartTime', 20000.00);

INSERT INTO SexList (sexID, sexCode, sexLabel)
VALUES
(1, 'M', 'Male'),
(2, 'F', 'Female');

INSERT INTO RoleList (roleID, roleCode, roleLabel)
VALUES
(1, 'EMP', 'Employee'),
(2, 'SPV', 'Supervisor'),
(3, 'HRA', 'HR Admin'),
(4, 'ITA', 'IT Admin');

