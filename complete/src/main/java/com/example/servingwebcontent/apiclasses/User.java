package com.example.servingwebcontent.apiclasses;

import java.util.List;

public class User {
    // later modified to singleton
    private String id;
    private String givenName;
    private String familyName;
    private String userName;
    private String phoneNumber;
    private boolean isCustomer;
    private boolean isReceptionist;
    private boolean isHealthcareWorker;

    private List<UserBooking> bookings;
    private List<CovidTest> testsTaken;
    private List<User> testsAdministered;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isCustomer() {
        return isCustomer;
    }

    public void setCustomer(boolean customer) {
        isCustomer = customer;
    }

    public boolean isReceptionist() {
        return isReceptionist;
    }

    public void setReceptionist(boolean receptionist) {
        isReceptionist = receptionist;
    }

    public boolean isHealthcareWorker() {
        return isHealthcareWorker;
    }

    public void setHealthcareWorker(boolean healthcareWorker) {
        isHealthcareWorker = healthcareWorker;
    }

    public List<UserBooking> getBookings() {
        return bookings;
    }

    public void setBookings(List<UserBooking> bookings) {
        this.bookings = bookings;
    }

    public List<CovidTest> getTestsTaken() {
        return testsTaken;
    }

    public void setTestsTaken(List<CovidTest> testsTaken) {
        this.testsTaken = testsTaken;
    }

    public List<User> getTestsAdministered() {
        return testsAdministered;
    }

    public void setTestsAdministered(List<User> testsAdministered) {
        this.testsAdministered = testsAdministered;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", givenName='" + givenName + '\'' +
                ", familyName='" + familyName + '\'' +
                ", userName='" + userName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", isCustomer=" + isCustomer +
                ", isReceptionist=" + isReceptionist +
                ", isHealthcareWorker=" + isHealthcareWorker +
                ", bookings=" + bookings +
                ", testsTaken=" + testsTaken +
                ", testsAdministered=" + testsAdministered +
                '}';
    }
}
