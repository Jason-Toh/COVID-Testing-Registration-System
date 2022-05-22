package com.example.servingwebcontent.observer;

public class Main3 {
    public static void main(String[] args) {
        // create objects for testing
        CurrentBookingDisplay currentBookingDisplay = new CurrentBookingDisplay();

        // pass the displays to Cricket data
        BookingData bookingData = new BookingData();

        // register display elements
        bookingData.registerObserver(currentBookingDisplay);

    }
}
