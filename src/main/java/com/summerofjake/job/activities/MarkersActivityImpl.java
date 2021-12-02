package com.summerofjake.job.activities;

import com.summerofjake.job.controllers.MarkersController;
import com.summerofjake.job.db.client.JpaApi;
import com.summerofjake.job.model.Marker;
import com.summerofjake.job.strava.api.ActivityApi;

import java.util.List;

public class MarkersActivityImpl implements  MarkersActivity{
    private final ActivityApi activityApi;
    private final JpaApi jpaApi;

    public MarkersActivityImpl(ActivityApi activityApi, JpaApi jpaApi) {
        this.activityApi = activityApi;
        this.jpaApi = jpaApi;
    }

    @Override
    public List<Marker> getMarkers(List<Long> activityIds) {
        MarkersController markersController = new MarkersController(activityApi, jpaApi);
        return markersController.getMarkers(activityIds);
    }
}
