package com.bikashgurung.facedetection;

public class Face {

    private String id;
    private String version;
    private String url;

    public Face(String id, String version, String url) {
        this.id = id;
        this.version = version;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
