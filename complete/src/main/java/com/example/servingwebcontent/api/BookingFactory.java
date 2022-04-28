package com.example.servingwebcontent.api;

import com.example.servingwebcontent.models.BookingStatus;

public class BookingFactory implements APIfactory {
    private String api;
    private String customerId;
    private String testingSiteId;
    private String startTime;
    private String bookingId;
    private String symptom;
    private BookingStatus bookingStatus;

    public BookingFactory(String api) {
        this.api = api;
    }

    public BookingFactory(String api, String bookingId, String symptom, BookingStatus bookingStatus) {
        this.api = api;
        this.bookingId = bookingId;
        this.symptom = symptom;
        this.bookingStatus = bookingStatus;
    }

    public BookingFactory(String api, String customerId, String testingSiteId, String startTime) {
        this.api = api;
        this.customerId = customerId;
        this.testingSiteId = testingSiteId;
        this.startTime = startTime;
    }

    @Override
    public Get createGet() {
        return new BookingGet(api);
    }

    @Override
    public Post createPost() {
        return new BookingPost(api, customerId, testingSiteId, startTime);
    }

    @Override
    public Patch createPatch() {
        return new BookingPatch(api, bookingId, symptom, bookingStatus);
    }
}
