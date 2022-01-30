package com.vtr.camera.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vtr.camera.Utils;

public class ImageView {

    private String name;
    private int size;
    private ZonedDateTime timeRead;
    private String url;

    public ImageView(Image image, String url) {
        this.name = image.getName();
        this.size = image.getSize();
        this.timeRead = image.getZonedDateTime();
        this.url = url;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @JsonProperty("time_read")
    public ZonedDateTime getTimeRead() {
        return timeRead;
    }

    public void setTimeRead(ZonedDateTime timeRead) {
        this.timeRead = timeRead;
    }

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @JsonProperty("size")
    public double getSizeMB() {
        return Utils.convertBytesToMB(size);
    }


}
