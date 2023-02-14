package com.whatsnew.app.gbversion.latest.gbtheme.savestatus.Models;

import java.io.File;

public class GBV_Status {

    private File file;
    private String title;
    private String path;
    private boolean isVideo;
    String uri;
    public GBV_Status(File file, String title, String path) {
        this.file = file;
        this.title = title;
        this.path = path;
        String MP4 = ".mp4";
        this.isVideo = file.getName().endsWith(MP4);
    }
    public String getUri() {
        return this.uri;
    }

    public void setUri(String str) {
        this.uri = str;
    }
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isVideo() {
        return isVideo;
    }

}
