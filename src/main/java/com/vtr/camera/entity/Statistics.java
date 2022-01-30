package com.vtr.camera.entity;

public class Statistics {
    private double size;
    private int count;

    public Statistics(double size, int count) {
        this.size = size;
        this.count = count;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
