package com.exercise.Lab46.model;

import com.sun.istack.NotNull;

public class EmployeeUpdateStatusDto {
    @NotNull
    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public EmployeeUpdateStatusDto(Status status) {
        this.status = status;
    }

    public EmployeeUpdateStatusDto(){};
}
