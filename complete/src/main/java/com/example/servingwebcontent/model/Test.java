package com.example.servingwebcontent.model;



import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "test")
public class Test {
    @Id
    private String testCode;
    private String testName;
    private int price;

    public Test() {}

    public Test(String testCode, String testName, int price) {
        this.testCode = testCode;
        this.testName = testName;
        this.price = price;
    }

    public String getTestCode() {
        return testCode;
    }

    public String getTestName() {
        return testName;
    }

    public int getPrice() {
        return price;
    }
}
