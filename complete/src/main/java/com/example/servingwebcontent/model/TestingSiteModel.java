package com.example.servingwebcontent.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "testingSite")
public class TestingSiteModel {
    @Id
    private String nameTestingSite;

    public TestingSiteModel() {}

    public TestingSiteModel(String name) {
        this.nameTestingSite = name;
    }

    public String getNameTestingSite() {
        return nameTestingSite;
    }
}