package com.example.servingwebcontent.apiclasses;

import java.util.ArrayList;
import java.util.List;

public class Booking {
    private String id;
    private String smsPin;

    public Booking(String id, String smsPin) {
        this.id = id;
        this.smsPin = smsPin;
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


    @Override
    public String toString() {
        return "Booking{" +
                "id='" + id + '\'' +
                ", smsPin='" + smsPin + '\'' +
                '}';
    }
}
