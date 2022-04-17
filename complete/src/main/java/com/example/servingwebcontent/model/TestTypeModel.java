package com.example.servingwebcontent.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "testType")
public class TestTypeModel{
    @Id
    private String nameTestType;

    public TestTypeModel() {}

    public TestTypeModel(String nameTestType) {
        this.nameTestType = nameTestType;
    }

    public String getNameTestType() {
        return nameTestType;
    }
}
