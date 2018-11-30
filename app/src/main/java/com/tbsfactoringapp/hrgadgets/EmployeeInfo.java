package com.tbsfactoringapp.hrgadgets;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class EmployeeInfo {

    String mEmpId;
    String mEmpName;
    String mEmpSalary;
    String mEmpAge;
    String mEmpImage;

    public EmployeeInfo(){

    }

    public EmployeeInfo(String empId, String empName, String empSalary, String empAge, String empImage) {
        this.mEmpId = empId;
        this.mEmpName = empName;
        this.mEmpSalary = empSalary;
        this.mEmpAge = empAge;
        this.mEmpImage = empImage;
    }

    public EmployeeInfo(String empName, String empSalary, String empAge, String empImage) {
        this.mEmpName = empName;
        this.mEmpSalary = empSalary;
        this.mEmpAge = empAge;
        this.mEmpImage = empImage;
    }

    public String getId() {
        return mEmpId;
    }

    public void setId(String id) {
        this.mEmpId = id;
    }

    public String getEmployeeName() {
        return mEmpName;
    }

    public void setEmployeeName(String employeeName) {
        this.mEmpName = employeeName;
    }

    public String getEmployeeSalary() {
        return mEmpSalary;
    }

    public void setEmployeeSalary(String employeeSalary) {
        this.mEmpSalary = employeeSalary;
    }

    public String getEmployeeAge() {
        return mEmpAge;
    }

    public void setEmployeeAge(String employeeAge) {
        this.mEmpAge = employeeAge;
    }

    public String getProfileImage() {
        return mEmpImage;
    }

    public void setProfileImage(String profileImage) {
        this.mEmpImage = profileImage;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", mEmpId)
                .append("employeeName", mEmpName)
                .append("employeeSalary", mEmpSalary)
                .append("employeeAge", mEmpAge)
                .append("profileImage", mEmpImage).toString();
    }
}
