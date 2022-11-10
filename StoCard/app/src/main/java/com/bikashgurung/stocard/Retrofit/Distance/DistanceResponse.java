package com.bikashgurung.stocard.Retrofit.Distance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DistanceResponse {

    @SerializedName("destination_addresses")
    @Expose
    private List<String> destinationAddresses = null;

    @SerializedName("origin_addresses")
    @Expose
    private List<String> originAddresses = null;

    @SerializedName("rows")
    @Expose
    private List<Row> rows = null;

    @SerializedName("status")
    @Expose
    private String status;

    public DistanceResponse() {
    }

    public DistanceResponse(List<String> destinationAddresses, List<String> originAddresses, List<Row> rows, String status) {
        this.destinationAddresses = destinationAddresses;
        this.originAddresses = originAddresses;
        this.rows = rows;
        this.status = status;
    }

    public List<String> getDestinationAddresses() {
        return destinationAddresses;
    }

    public void setDestinationAddresses(List<String> destinationAddresses) {
        this.destinationAddresses = destinationAddresses;
    }

    public List<String> getOriginAddresses() {
        return originAddresses;
    }

    public void setOriginAddresses(List<String> originAddresses) {
        this.originAddresses = originAddresses;
    }

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
