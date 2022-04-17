package com.example.servingwebcontent.apiclasses;

public class CovidTestBooking extends CovidTest{
    private Booking booking;
    public CovidTestBooking(String id, TestType testType, User patient, User administer, Result result, Booking booking) {
        super(id, testType, patient, administer, result);
        this.booking = booking;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    @Override
    public String toString() {
        return "CovidTestBooking{" +
                "booking=" + booking +
                '}';
    }
}
