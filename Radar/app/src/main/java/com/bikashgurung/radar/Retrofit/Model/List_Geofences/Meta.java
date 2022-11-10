package com.bikashgurung.radar.Retrofit.Model.List_Geofences;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meta {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("hasMore")
    @Expose
    private Boolean hasMore;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Boolean getHasMore() {
        return hasMore;
    }

    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }

}