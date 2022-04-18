package com.example.servingwebcontent.apiclasses;

import java.util.ArrayList;
import java.util.List;

public class TestingSiteBooking extends Booking{

    private User customer;
    private List<CovidTest> covidTests;

    public TestingSiteBooking(String id, String smsPin, User customer) {
        super(id, smsPin);
        this.customer = customer;
        this.covidTests = new ArrayList<>();
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "TestingSiteBooking{" +
                "customer=" + customer +
                ", covidTests=" + covidTests +
                '}';
    }
}
