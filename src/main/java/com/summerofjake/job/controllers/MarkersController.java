package com.summerofjake.job.controllers;

import com.summerofjake.job.db.client.JpaApi;
import com.summerofjake.job.strava.api.ActivityApi;
import com.summerofjake.server.model.Marker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MarkersController {
    private ActivityApi activityApi;
    private JpaApi jpaApi;

    public MarkersController(ActivityApi activityApi) {
        this.activityApi = activityApi;
        this.jpaApi = new JpaApi();
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

        //Write values to database
        boolean posted = false;
        try {
            posted = jpaApi.postMarkers(allMarkers);
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
