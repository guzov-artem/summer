package com.vtr.camera.entity;

import java.time.ZonedDateTime;

public class CameraData {

    private byte[] data;
    private ZonedDateTime date;

    public CameraData() {
    }

    public CameraData(byte[] data, ZonedDateTime date) {
        this.data = data;
        this.date = date;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }
}
