package com.bikashgurung.facedetection;

public class Result {

    private boolean ok;
    private Face face;

    public Result(boolean ok, Face face) {
        this.ok = ok;
        this.face = face;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public Face getFace() {
        return face;
    }

    public void setFace(Face face) {
        this.face = face;
    }
}
