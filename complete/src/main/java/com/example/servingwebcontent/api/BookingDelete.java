package com.example.servingwebcontent.api;

public class BookingDelete extends Delete{
    private final String myApiKey;
    private final String bookingId;

    public BookingDelete(String myApiKey, String bookingId) {
        this.myApiKey = myApiKey;
        this.bookingId = bookingId;
    }
    //TO DO delete the CovidTest by Id first before deleting the Booking by Id

    @Override
    public void deleteApi(String id) {

    }
}
