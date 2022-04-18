package com.example.servingwebcontent.domain;

public class BookingForm {
    private String customerUsername;
    private String testingSite;
    private String administrator;
    private String patient;
    private String testType;
    private String time;



    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public String getTestingSite() {
        return testingSite;
    }

    public void setTestingSite(String testingSite) {
        this.testingSite = testingSite;
    }

    public String getAdministrator() {
        return administrator;
    }

    public void setAdministrator(String administrator) {
        this.administrator = administrator;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "BookingForm{" +
                "customerUsername='" + customerUsername + '\'' +
                ", testingSite='" + testingSite + '\'' +
                ", administrator='" + administrator + '\'' +
                ", patient='" + patient + '\'' +
                ", testType='" + testType + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
