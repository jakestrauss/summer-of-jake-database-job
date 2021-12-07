package com.summerofjake.job.activities;

import com.google.common.collect.ImmutableList;
import com.summerofjake.job.db.api.JpaApi;
import com.summerofjake.job.model.Marker;
import com.summerofjake.job.strava.api.ActivityApi;

import java.util.List;

//Master activity to run all required activities for database backfill job
public class MasterActivity {
    private ActivityApi activityApi;
    private JpaApi jpaApi;

    public MasterActivity(ActivityApi activityApi, JpaApi jpaApi) {
        this.activityApi = activityApi;
        this.jpaApi = jpaApi;
    }

    public void executeJob() {
        GetActivityIdsActivity getActivityIdsActivity = new GetActivityIdsActivity(activityApi);
        List<Long> activityIds = getActivityIdsActivity.getActivityIds();

        MarkersActivity markersActivity = new MarkersActivityImpl(activityApi, jpaApi);
//        List<Long> test = ImmutableList.of(Long.valueOf("6028197641"));
        List<Marker> markers = markersActivity.getMarkers(activityIds);

        RoutesActivity routesActivity = new RoutesActivityImpl(activityApi, jpaApi);
        routesActivity.getRoutes(markers);
    }
}
