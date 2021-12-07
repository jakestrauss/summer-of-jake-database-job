package com.summerofjake.job.model;

import java.time.LocalDateTime;

public class Marker {

    private Long id;

    private String url;

    private double lat;

    private double lng;

    private LocalDateTime activityDate;

    private String activityTitle;

    private String activityDescription;

    private String activityId;

    public Marker() {}

    public Marker(String url, double lat, double lng, LocalDateTime activityDate, String activityTitle, String activityDescription, String activityId) {
        this.url = url;
        this.lat = lat;
        this.lng = lng;
        this.activityDate = activityDate;
        this.activityTitle = activityTitle;
        this.activityDescription = activityDescription;
        this.activityId = activityId;
    }

    public LocalDateTime getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(LocalDateTime activityDate) {
        this.activityDate = activityDate;
    }

    public String getActivityTitle() {
        return activityTitle;
    }

    public void setActivityTitle(String activityTitle) {
        this.activityTitle = activityTitle;
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
