package com.example.servingwebcontent.models;

import java.util.ArrayList;
import java.util.List;

public class Booking {
    private String bookingId;
    private String customerId;
    private String customerName;
    private String testingSiteId;
    private String testingSiteName;
    private String smsPin;
    private String startTime;
    private String status;
    private List<CovidTest> covidTests;

    public static Booking getInstance(String bookingId, String customerId, String customerName, String testingSiteId,
            String testingSiteName,
            String smsPin,
            String startTime, String status) {
        Booking booking = new Booking(bookingId, customerId, customerName, testingSiteId, testingSiteName, smsPin,
                startTime, status);
        return booking;
    };

    public static Booking getInstance(String bookingId, String testingSiteId,
            String testingSiteName,
            String smsPin,
            String startTime, String status) {
        Booking booking = new Booking(bookingId, testingSiteId, testingSiteName, smsPin,
                startTime, status);
        return booking;
    };

    private Booking(String bookingId, String customerId, String customerName, String testingSiteId,
            String testingSiteName, String smsPin,
            String startTime, String status) {
        this.covidTests = new ArrayList<>();
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.testingSiteId = testingSiteId;
        this.testingSiteName = testingSiteName;
        this.smsPin = smsPin;
        this.startTime = startTime;
        this.status = status;
    }

    private Booking(String bookingId, String testingSiteId,
            String testingSiteName, String smsPin,
            String startTime, String status) {
        this.covidTests = new ArrayList<>();
        this.bookingId = bookingId;
        this.testingSiteId = testingSiteId;
        this.testingSiteName = testingSiteName;
        this.smsPin = smsPin;
        this.startTime = startTime;
        this.status = status;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
                ", startTime='" + startTime + '\'' +
                ", covidTests=" + covidTests +
                '}';
    }
}
