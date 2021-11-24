package com.summerofjake.job.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "routes")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column (name = "url", unique = true)
    private String url;

    @Column(name="activityDate", columnDefinition = "TIMESTAMP")
    private LocalDateTime activityDate;

    @Column(name="activityTitle")
    private String activityTitle;

    @Column(name="activityDescription")
    private String activityDescription;

    @Column(name="activityId", unique = true)
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