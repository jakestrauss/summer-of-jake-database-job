package com.summerofjake.job.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "markers")
public class Marker {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "url", unique = true)
    private String url;

    @Column(name = "lat")
    private double lat;

    @Column(name = "lng")
    private double lng;

    @Column(name="activityDate", columnDefinition = "TIMESTAMP")
    private LocalDateTime activityDate;

    @Column(name="activityTitle")
    private String activityTitle;

    @Column(name="activityDescription")
    private String activityDescription;

    @Column(name="activityId")
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
