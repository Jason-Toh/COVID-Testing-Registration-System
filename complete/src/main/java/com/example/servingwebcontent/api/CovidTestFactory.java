package com.example.servingwebcontent.api;

public class CovidTestFactory implements APIfactory{
    private final String api;
    private final String testType;
    private final String patientId;
    private final String administererId;
    private final String bookingId;
    private final String result;

    public CovidTestFactory(String api, String testType, String patientId, String administererId, String bookingId) {
        this.api = api;
        this.testType = testType;
        this.patientId = patientId;
        this.administererId = administererId;
        this.bookingId = bookingId;
        this.result = "PENDING";
    }

    @Override
    public Get createGet() {
        return null;
    }

    @Override
    public Post createPost() {
        return new CovidTestPost(api, testType, patientId, administererId, bookingId, result);
    }
}
