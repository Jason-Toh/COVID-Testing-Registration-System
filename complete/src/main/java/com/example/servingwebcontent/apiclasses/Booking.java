package com.example.servingwebcontent.apiclasses;

import java.util.List;

public class Booking {
    private String id;
    private String smsPin;
    private List<CovidTest> covidTests;

    public Booking(String id, String smsPin, List<CovidTest> covidTests) {
        this.id = id;
        this.smsPin = smsPin;
        this.covidTests = covidTests;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSmsPin() {
        return smsPin;
    }

    public void setSmsPin(String smsPin) {
        this.smsPin = smsPin;
    }

    public List<CovidTest> getCovidTests() {
        return covidTests;
    }

    public void setCovidTests(List<CovidTest> covidTests) {
        this.covidTests = covidTests;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id='" + id + '\'' +
                ", smsPin='" + smsPin + '\'' +
                ", covidTests=" + covidTests +
                '}';
    }
}
