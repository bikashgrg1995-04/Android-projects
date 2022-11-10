package com.bikashgurung.stocard.Retrofit.Nearby;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Geometry implements Serializable {

    @SerializedName("location")
    @Expose
    private Location location;

    @SerializedName("viewport")
    @Expose
    private Viewport viewport;

    public Geometry() {
    }

    public Geometry(Location location, Viewport viewport) {
        this.location = location;
        this.viewport = viewport;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }
}
