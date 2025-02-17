package com.hello_doctor_api.constants;

public enum Role {
    R001("PATIENT"),
    R002("DOCTOR");

    Role(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;
}
