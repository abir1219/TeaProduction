package com.tea.teaproduction.ui.Home.Model;

public class ConsignmentModel {
    String empId,empCode,weight,category,shift,sector,date;

    public ConsignmentModel(String empId, String empCode, String weight, String category, String shift, String sector, String date) {
        this.empId = empId;
        this.empCode = empCode;
        this.weight = weight;
        this.category = category;
        this.shift = shift;
        this.sector = sector;
        this.date = date;
    }

    public ConsignmentModel(String empCode, String weight, String date) {
        this.empCode = empCode;
        this.weight = weight;
        this.date = date;
    }

    public String getEmpId() {
        return empId;
    }

    public String getEmpCode() {
        return empCode;
    }

    public String getWeight() {
        return weight;
    }

    public String getCategory() {
        return category;
    }

    public String getShift() {
        return shift;
    }

    public String getSector() {
        return sector;
    }

    public String getDate() {
        return date;
    }
}
