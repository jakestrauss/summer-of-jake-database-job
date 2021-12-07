package com.summerofjake.job.model;

import java.time.LocalDateTime;

public class Route {

    private long id;

    private String url;

    private LocalDateTime activityDate;

    private String activityTitle;

    private String activityDescription;

    private String activityId;

    public Route() {}

    public Route(String url, LocalDateTime activityDate, String activityTitle, String activityDescription, String activityId) {
        this.url = url;
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
}