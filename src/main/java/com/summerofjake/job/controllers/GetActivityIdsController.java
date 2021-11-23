package com.summerofjake.job.controllers;

import com.summerofjake.job.strava.api.ActivityApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetActivityIdsController {
    private ActivityApi activityApi;

    public GetActivityIdsController(ActivityApi activityApi) {
        this.activityApi = activityApi;
    }

    public List<Long> getActivityIds() {
        List<Long> activityIds = new ArrayList<>();
        try {
            activityIds = activityApi.getLoggedInAthleteActivities();
            System.out.println("Activity Ids of last day:");
            System.out.println(Arrays.toString(activityIds.toArray()));
        } catch(Exception e) {
            System.out.println("Get Activities call failed " + e.toString());
        }

        return activityIds;
    }
}
