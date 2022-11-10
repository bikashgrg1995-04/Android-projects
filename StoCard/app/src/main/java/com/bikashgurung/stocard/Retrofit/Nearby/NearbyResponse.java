package com.bikashgurung.stocard.Retrofit.Nearby;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class NearbyResponse implements Serializable {

    @SerializedName("html_attributions")
    @Expose
    private List<Object> htmlAttributions = null;

    @SerializedName("results")
    @Expose
    private List<Result> results = null;

    @SerializedName("status")
    @Expose
    private String status;

    public NearbyResponse() {
    }

    public NearbyResponse(List<Object> htmlAttributions, List<Result> results, String status) {
        this.htmlAttributions = htmlAttributions;
        this.results = results;
        this.status = status;
    }

    public List<Object> getHtmlAttributions() {
        return htmlAttributions;
    }

    public void setHtmlAttributions(List<Object> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
