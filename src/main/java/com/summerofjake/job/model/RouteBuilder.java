package com.summerofjake.job.model;

import java.time.LocalDateTime;

public final class RouteBuilder {
    private String url;
    private LocalDateTime activityDate;
    private String activityTitle;
    private String activityDescription;
    private String activityId;

    private RouteBuilder() {
    }

    public static RouteBuilder aRoute() {
        return new RouteBuilder();
    }

    public RouteBuilder withUrl(String url) {
        this.url = url;
        return this;
    }

    public RouteBuilder withActivityDate(LocalDateTime activityDate) {
        this.activityDate = activityDate;
        return this;
    }

    public RouteBuilder withActivityTitle(String activityTitle) {
        this.activityTitle = activityTitle;
        return this;
    }

    public RouteBuilder withActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
        return this;
    }

    public RouteBuilder withActivityId(String activityId) {
        this.activityId = activityId;
        return this;
    }

    public Route build() {
        Route route = new Route();
        route.setUrl(url);
        route.setActivityDate(activityDate);
        route.setActivityTitle(activityTitle);
        route.setActivityDescription(activityDescription);
        route.setActivityId(activityId);
        return route;
    }
}
