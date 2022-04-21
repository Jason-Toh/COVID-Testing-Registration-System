package com.example.servingwebcontent.apiclasses;

import java.util.ArrayList;
import java.util.List;

public class Booking {
    private String bookingId;
    private String testingSiteId;
    private String testingSiteName;
    private String smsPin;
    private List<CovidTest> covidTests;

    public Booking(String bookingId, String testingSiteId, String testingSiteName, String smsPin) {
        this.bookingId = bookingId;
        this.testingSiteId = testingSiteId;
        this.testingSiteName = testingSiteName;
        this.smsPin = smsPin;
        this.covidTests = new ArrayList<>();
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getTestingSiteId() {
        return testingSiteId;
    }

    public void setTestingSiteId(String testingSiteId) {
        this.testingSiteId = testingSiteId;
    }

    public String getTestingSiteName() {
        return testingSiteName;
    }

    public void setTestingSiteName(String testingSiteName) {
        this.testingSiteName = testingSiteName;
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
                "bookingId='" + bookingId + '\'' +
                ", testingSiteId='" + testingSiteId + '\'' +
                ", testingSiteName='" + testingSiteName + '\'' +
                ", smsPin='" + smsPin + '\'' +
                ", covidTests=" + covidTests +
                '}';
    }
}
