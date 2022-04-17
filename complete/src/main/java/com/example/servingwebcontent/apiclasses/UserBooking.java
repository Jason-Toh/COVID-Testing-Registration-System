package com.example.servingwebcontent.apiclasses;

import java.util.List;

public class UserBooking extends Booking{

    private TestingSite testingSite;

    public UserBooking(String id, String smsPin, TestingSite testingSite) {
        super(id, smsPin);
        this.testingSite = testingSite;
    }

    public TestingSite getTestingSite() {
        return testingSite;
    }

    public void setTestingSite(TestingSite testingSite) {
        this.testingSite = testingSite;
    }

    @Override
    public String toString() {
        return "UserBooking{" +
                "testingSite=" + testingSite +
                '}';
    }
}
