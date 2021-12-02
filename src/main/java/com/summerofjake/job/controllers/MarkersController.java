package com.summerofjake.job.controllers;

import com.summerofjake.job.db.client.JpaApi;
import com.summerofjake.job.model.Marker;
import com.summerofjake.job.strava.api.ActivityApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MarkersController {
    private final ActivityApi activityApi;
    private final JpaApi jpaApi;

    public MarkersController(ActivityApi activityApi, JpaApi jpaApi) {
        this.activityApi = activityApi;
        this.jpaApi = jpaApi;
    }

    public List<Marker> getMarkers(List<Long> activityIds) {
        //Get all markers from last day's worth of activities
        List<Marker> allMarkers = new ArrayList<>();
        for (Long activityId : activityIds) {
            List<Marker> activityMarkers = activityApi.getMarkersForActivity(activityId.toString());

            //add all current activityMarkers to list of allMarkers
            if(activityMarkers != null) {
                activityMarkers.stream().filter(Objects::nonNull).forEachOrdered(allMarkers::add);
            }
        }

        System.out.println("All marker info from activities in the last day:");
        for(Marker m : allMarkers) {
            System.out.println(m.getActivityId() + " " + m.getActivityDate() +  " " + m.getUrl() + " " + m.getLat()
                    + " " + m.getLng() + " " + m.getActivityTitle() + " |||| " + m.getActivityDescription());
        }

        //filter out markers without photoUrls
        List<Marker> allPhotoMarkers = allMarkers.stream().filter(marker -> !(marker.getUrl().isEmpty())).collect(Collectors.toList());

        if(allPhotoMarkers.isEmpty()) {
            System.out.println("No new markers to post!");
            return(allMarkers);
        }

        //Write values to database
        boolean posted = false;
        try {
            posted = jpaApi.postMarkers(allPhotoMarkers);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        if(posted) {
            System.out.println("Markers successfully posted");
        } else {
            System.out.println("Markers NOT successfully posted");
        }

        return(allMarkers);
    }
}
