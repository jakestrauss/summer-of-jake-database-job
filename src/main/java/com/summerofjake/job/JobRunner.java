package com.summerofjake.job;

import com.summerofjake.job.activities.*;
import com.summerofjake.job.db.client.JpaApi;
import com.summerofjake.job.model.Marker;
import com.summerofjake.job.strava.api.ActivityApi;

import java.util.List;

public class JobRunner {

    public static void main(String[] args) {
        //if running on EC2, pass path to Google cloud credentials file as command line argument
        if(args.length == 1) {
            System.setProperty("GCLOUD_CREDENTIALS_PATH", args[0]);
        }

        ActivityApi activityApi = new ActivityApi();
        JpaApi jpaApi = new JpaApi();
        GetActivityIdsActivity getActivityIdsActivity = new GetActivityIdsActivity(activityApi);
        List<Long> activityIds = getActivityIdsActivity.getActivityIds();

        MarkersActivity markersActivity = new MarkersActivityImpl(activityApi, jpaApi);
        List<Marker> markers = markersActivity.getMarkers(activityIds);

        RoutesActivity routesActivity = new RoutesActivityImpl(activityApi, jpaApi);
        routesActivity.getRoutes(markers);
    }
}
