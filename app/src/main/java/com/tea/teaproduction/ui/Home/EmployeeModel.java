package com.tea.teaproduction.ui.Home;

public class EmployeeModel {
    String empId,empCode,empName;

    public EmployeeModel(String empId,String empCode,String empName) {
        this.empId = empId;
        this.empCode = empCode;
        this.empName = empName;
    }

    public String getEmpId() {
        return empId;
    }

    public String getEmpCode() {
        return empCode;
    }

    public String getEmpName() {
        return empName;
    }
}
