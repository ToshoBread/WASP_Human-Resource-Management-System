DROP TABLE EmployeeInfo;
DROP TABLE PayrollInfo;
DROP TABLE AttendanceTracker;
DROP TABLE JobHistory;
DROP TABLE SalaryList;

CREATE TABLE PayrollInfo (
    payrollID INT AUTO_INCREMENT PRIMARY KEY,
    salaryID INT,
    type VARCHAR(20),
    amount DECIMAL(10, 2)
);

CREATE TABLE AttendanceTracker (
    attendanceID INT AUTO_INCREMENT PRIMARY KEY,
    timeIn TIME,
    timeOut TIME,
    date DATE
);

CREATE TABLE JobHistory (
    jobID INT AUTO_INCREMENT PRIMARY KEY,
    position VARCHAR(50),
    employDate DATE,
    resignDate DATE,
    effectivityDate DATE
);

CREATE TABLE EmployeeInfo (
    employeeID INT AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(50) NOT NULL,
    lastName VARCHAR(50) NOT NULL,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    sex VARCHAR(10) NOT NULL,
    department VARCHAR(50),
    jobID INT,
    payrollID INT,
    attendanceID INT,
    salt VARCHAR(255) NOT NULL,
    FOREIGN KEY (jobID) REFERENCES JobHistory(jobID),
    FOREIGN KEY (payrollID) REFERENCES PayrollInfo(payrollID),
    FOREIGN KEY (attendanceID) REFERENCES AttendanceTracker(attendanceID)
);

CREATE TABLE SalaryList (
    salaryID INT PRIMARY KEY,
    type VARCHAR(20),
    amount DECIMAL(10, 2)
);

INSERT INTO SalaryList (salaryID, type, amount)
VALUES 
(1, 'FullTime', 30000.00),
(2, 'PartTime', 20000.00);

INSERT INTO AttendanceTracker (attendanceID, timeIn, timeOut, date)
VALUES 
(1, '08:00:00', '17:00:00', '2025-04-20'),
(2, '08:30:00', '17:30:00', '2025-04-20'),
(3, '09:00:00', '18:00:00', '2025-04-20');

INSERT INTO PayrollInfo (payrollID, salaryID, type, amount)
VALUES
(1, 1, 'FullTime', 30000.00),
(2, 2, 'PartTime', 20000.00),
(3, 2, 'PartTime', 20000.00);

INSERT INTO JobHistory (jobID, position, employDate, resignDate, effectivityDate)
VALUES
(1, 'HR Personnel', '2023-03-20', NULL, '2024-09-27'),
(2, 'Security Officer', '2025-04-15', NULL, NULL),
(3, 'Branch Manager', '2025-03-28', NULL, NULL);


INSERT INTO EmployeeInfo (username, password, email, jobID, firstName, lastName, age, sex, payrollID, department, attendanceID, salt)
VALUES 
('', '', '', 1, '', '', NULL, '', 1, 'Research Department', 1, ''),
('', '', '', 2, '', '', NULL, '', 1, 'Arts Department', 2, ''),
('', '', '', 1, '', '', NULL, '', 3, 'Record Department', 3, '');

