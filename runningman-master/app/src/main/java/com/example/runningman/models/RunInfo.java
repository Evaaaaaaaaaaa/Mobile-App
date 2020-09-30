package com.example.runningman.models;

import java.util.Date;

public class RunInfo {
    private long timelast;
    private double distance;
    private double mean_speed;
    private String date;

    public RunInfo(long timelast, double distance, double mean_speed, String date) {
        this.timelast = timelast;
        this.distance = distance;
        this.mean_speed = mean_speed;
        this.date = date;
    }

    public double getTimelast() {
        return timelast;
    }

    public double getDistance() {
        return distance;
    }

    public double getMean_speed() {
        return mean_speed;
    }

    public String getDate() {
        return date;
    }
}
