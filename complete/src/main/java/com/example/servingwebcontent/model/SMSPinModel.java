package com.example.servingwebcontent.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "smsPin")
public class SMSPinModel {
    @Id
    private String smsPin;

    public SMSPinModel() {}

    public SMSPinModel(String smsPin) {
        this.smsPin = smsPin;
    }

    public String getSmsPin() {
        return smsPin;
    }
}
