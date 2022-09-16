package com.exercise.Lab46.model;

import com.sun.istack.NotNull;

public class EmployeeUpdateDepartmentDto {
    @NotNull
    private String department;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
