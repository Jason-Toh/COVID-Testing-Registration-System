package com.example.servingwebcontent.apiclasses;

import java.util.List;

public class TestingSite {
    private String id;
    private String name;
    private String description;
    private String websiteUrl;
    private String phoneNumber;
    private Address address;
    private List<TestingSiteBooking> bookings;
    private TestingSiteStatus additonalInfo;

    public TestingSite(String id, String name, String description, String websiteUrl, String phoneNumber, Address address, List<TestingSiteBooking> bookings, TestingSiteStatus additonalInfo) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.websiteUrl = websiteUrl;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.bookings = bookings;
        this.additonalInfo = additonalInfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<TestingSiteBooking> getBookings() {
        return bookings;
    }

    public void setBookings(List<TestingSiteBooking> bookings) {
        this.bookings = bookings;
    }

    public TestingSiteStatus getAdditonalInfo() {
        return additonalInfo;
    }

    public void setAdditonalInfo(TestingSiteStatus additonalInfo) {
        this.additonalInfo = additonalInfo;
    }

    @Override
    public String toString() {
        return "TestingSite{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", websiteUrl='" + websiteUrl + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address=" + address +
                ", bookings=" + bookings +
                ", additonalInfo=" + additonalInfo +
                '}';
    }
}
