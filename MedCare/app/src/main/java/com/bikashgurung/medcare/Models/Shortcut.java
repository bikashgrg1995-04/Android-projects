package com.bikashgurung.medcare.Models;

import java.sql.Blob;

public class Shortcut {

    // string course_name for storing course_name
    // and imgid for storing image id.
    private String title;
    private int image;
    private Boolean status;

    public Shortcut() {
    }

    public Shortcut( String title, int image, Boolean status) {
        this.title = title;
        this.image = image;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
