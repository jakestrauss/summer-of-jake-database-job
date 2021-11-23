package com.summerofjake.job.model;

public class LatLong {
    private Double lat;
    private Double lon;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public LatLong(Double lat, Double lon) {
        this.lat = lat;
        this.lon = lon;
    }

}
