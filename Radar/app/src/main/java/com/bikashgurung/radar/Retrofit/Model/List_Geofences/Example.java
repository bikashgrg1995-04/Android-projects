package com.bikashgurung.radar.Retrofit.Model.List_Geofences;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Example {

    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("geofences")
    @Expose
    private List<Geofence> geofences = null;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<Geofence> getGeofences() {
        return geofences;
    }

    public void setGeofences(List<Geofence> geofences) {
        this.geofences = geofences;
    }

}