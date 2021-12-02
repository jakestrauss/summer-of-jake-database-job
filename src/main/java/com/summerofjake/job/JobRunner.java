package com.summerofjake.job;

import com.google.common.collect.ImmutableList;
import com.summerofjake.job.activities.*;
import com.summerofjake.job.db.client.JpaApi;
import com.summerofjake.job.model.Marker;
import com.summerofjake.job.strava.api.ActivityApi;

import java.util.List;

public class JobRunner {
    public static void main(String[] args) {
        ActivityApi activityApi = new ActivityApi();
        JpaApi jpaApi = new JpaApi();
        GetActivityIdsActivity getActivityIdsActivity = new GetActivityIdsActivity(activityApi);
        //List<Long> activityIds = getActivityIdsActivity.getActivityIds();
        List<Long> activityIds = ImmutableList.of(Long.valueOf("6323846187"));


        MarkersActivity markersActivity = new MarkersActivityImpl(activityApi, jpaApi);
        List<Marker> markers = markersActivity.getMarkers(activityIds);

        RoutesActivity routesActivity = new RoutesActivityImpl(activityApi, jpaApi);
        routesActivity.getRoutes(markers);
    }
}
