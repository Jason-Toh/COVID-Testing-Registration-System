package com.example.servingwebcontent.api;

import com.example.servingwebcontent.enumeration.BookingStatus;
import com.example.servingwebcontent.models.Booking;

public class BookingFactory implements APIfactory<Booking> {

    private String api;
    private String customerId;
    private String testingSiteId;
    private String startTime;
    private String bookingId;
    private String symptom;
    private BookingStatus bookingStatus;
    private String url;
    private String qrCode;

    public BookingFactory(String api) {
        this.api = api;
    }

    public BookingFactory(String api, String bookingId, BookingStatus bookingStatus) {
        this.api = api;
        this.bookingId = bookingId;
        this.bookingStatus = bookingStatus;
    }

    public BookingFactory(String api, String bookingId, String qrCode, String url, String idk) {
        this.api = api;
        this.qrCode = qrCode;
        this.url = url;
        this.bookingId = bookingId;
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
    public Get<Booking> createGet() {
        return new BookingGet(api);
    }

    @Override
    public Post createPost() {
        return new BookingPost(api, customerId, testingSiteId, startTime);
    }

    @Override
    public Delete createDelete() { return new BookingDelete(api); }

    @Override
    public Patch createPatch() {
        return new BookingPatch(api, bookingId, symptom, bookingStatus, qrCode, url);
    }
}
