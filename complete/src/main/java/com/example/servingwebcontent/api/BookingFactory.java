package com.example.servingwebcontent.api;

public class BookingFactory implements APIfactory{
    private String api;
    private String customerId;
    private String testingSiteId;
    private String startTime;

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

}
