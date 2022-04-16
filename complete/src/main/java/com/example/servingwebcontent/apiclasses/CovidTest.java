package com.example.servingwebcontent.apiclasses;


public class CovidTest {
    private String id;
    private TestType testType;
    private User patient;
    private User administer;
    private Booking booking;
    private Result result;

    public CovidTest(String id, TestType testType, User patient, User administer, Booking booking, Result result) {
        this.id = id;
        this.testType = testType;
        this.patient = patient;
        this.administer = administer;
        this.booking = booking;
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TestType getTestType() {
        return testType;
    }

    public void setTestType(TestType testType) {
        this.testType = testType;
    }

    public User getPatient() {
        return patient;
    }

    public void setPatient(User patient) {
        this.patient = patient;
    }

    public User getAdminister() {
        return administer;
    }

    public void setAdminister(User administer) {
        this.administer = administer;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "CovidTest{" +
                "id='" + id + '\'' +
                ", testType=" + testType +
                ", patient=" + patient +
                ", administer=" + administer +
                ", booking=" + booking +
                ", result=" + result +
                '}';
    }
}