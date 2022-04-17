package com.example.servingwebcontent.apiclasses;

public class TestingSiteStatus {
     private String typeOfFacility;
     private boolean onSiteBookingAndTesting;
     private int waitingTimeInMins;

    public TestingSiteStatus(String typeOfFacility, boolean onSiteBookingAndTesting, int waitingTimeInMins) {
        this.typeOfFacility = typeOfFacility;
        this.onSiteBookingAndTesting = onSiteBookingAndTesting;
        this.waitingTimeInMins = waitingTimeInMins;
    }

    public String getTypeOfFacility() {
        return typeOfFacility;
    }

    public void setTypeOfFacility(String typeOfFacility) {
        this.typeOfFacility = typeOfFacility;
    }

    public boolean isOnSiteBookingAndTesting() {
        return onSiteBookingAndTesting;
    }

    public void setOnSiteBookingAndTesting(boolean onSiteBookingAndTesting) {
        this.onSiteBookingAndTesting = onSiteBookingAndTesting;
    }

    public int getWaitingTimeInMins() {
        return waitingTimeInMins;
    }

    public void setWaitingTimeInMins(int waitingTimeInMins) {
        this.waitingTimeInMins = waitingTimeInMins;
    }


    public String toJSONStringFormat() {
        return "additionalInfo:{" +
                "\"typeOfFacility\":\"" + typeOfFacility + '\"' +
                ", \"onSiteBookingAndTesting\":" + onSiteBookingAndTesting +
                ", \"waitingTimeInMins\":" + waitingTimeInMins +
                '}';
    }

    @Override
    public String toString() {
        return "TestingSiteStatus{" +
                "typeOfFacility='" + typeOfFacility + '\'' +
                ", onSiteBookingAndTesting=" + onSiteBookingAndTesting +
                ", waitingTimeInMins=" + waitingTimeInMins +
                '}';
    }
}
