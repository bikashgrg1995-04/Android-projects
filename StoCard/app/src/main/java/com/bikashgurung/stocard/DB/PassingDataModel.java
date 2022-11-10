package com.bikashgurung.stocard.DB;

public class PassingDataModel {

    String distance, destination_address;

    public PassingDataModel() {
    }

    public PassingDataModel(String distance, String destination_address) {
        this.distance = distance;
        this.destination_address = destination_address;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDestination_address() {
        return destination_address;
    }

    public void setDestination_address(String destination_address) {
        this.destination_address = destination_address;
    }
}
