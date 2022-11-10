package com.bikashgurung.radar.Retrofit.Model.List_Geofences;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Geofence {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("live")
    @Expose
    private Boolean live;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("tag")
    @Expose
    private String tag;
    @SerializedName("externalId")
    @Expose
    private String externalId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("mode")
    @Expose
    private String mode;
    @SerializedName("geometryCenter")
    @Expose
    private GeometryCenter geometryCenter;
    @SerializedName("geometryRadius")
    @Expose
    private Integer geometryRadius;
    @SerializedName("geometry")
    @Expose
    private Geometry geometry;
    @SerializedName("enabled")
    @Expose
    private Boolean enabled;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getLive() {
        return live;
    }

    public void setLive(Boolean live) {
        this.live = live;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public GeometryCenter getGeometryCenter() {
        return geometryCenter;
    }

    public void setGeometryCenter(GeometryCenter geometryCenter) {
        this.geometryCenter = geometryCenter;
    }

    public Integer getGeometryRadius() {
        return geometryRadius;
    }

    public void setGeometryRadius(Integer geometryRadius) {
        this.geometryRadius = geometryRadius;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

}