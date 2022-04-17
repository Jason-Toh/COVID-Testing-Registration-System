package com.example.servingwebcontent.apiclasses;

import java.util.List;

public class TestingSiteBooking extends Booking{

    private User customer;

    public TestingSiteBooking(String id, String smsPin, User customer) {
        super(id, smsPin);
        this.customer = customer;
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
                '}';
    }
}
