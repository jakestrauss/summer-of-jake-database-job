package com.summerofjake.job;

import com.google.common.collect.ImmutableList;
import com.summerofjake.job.activities.GetActivityIdsActivity;
import com.summerofjake.job.activities.MarkersActivity;
import com.summerofjake.job.activities.MarkersActivityImpl;
import com.summerofjake.job.strava.api.ActivityApi;
import com.summerofjake.server.model.Marker;

import java.util.List;

public class JobRunner {
    public static void main(String[] args) {
        ActivityApi activityApi = new ActivityApi();
        GetActivityIdsActivity getActivityIdsActivity = new GetActivityIdsActivity(activityApi);
        List<Long> activityIds = getActivityIdsActivity.getActivityIds();

        Long test = Long.valueOf("6143121580");
        MarkersActivity markersActivity = new MarkersActivityImpl(activityApi);
        List<Marker> markers = markersActivity.getMarkers(ImmutableList.of(test));
    }
}
