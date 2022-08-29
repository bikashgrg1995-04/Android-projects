package com.bikashgurung.task2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Support {


    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("text")
    @Expose
    private String text;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
