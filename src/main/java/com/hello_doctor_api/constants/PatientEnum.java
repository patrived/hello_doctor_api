package com.hello_doctor_api.constants;

public enum PatientEnum {

    E500("An unexpected error occurred:"),
    PE002("Failed to create patient"),
    PE003("Error processing the patient data."),
    S000("Response successful"),
    PE004("Image is Empty");

    public String getMessgae() {
        return messgae;
    }

    public void setMessgae(String messgae) {
        this.messgae = messgae;
    }

    PatientEnum(String messgae) {
        this.messgae = messgae;
    }

    private String messgae;
}
