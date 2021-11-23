package com.summerofjake.job.activities;

import com.summerofjake.job.controllers.MarkersController;
import com.summerofjake.job.strava.api.ActivityApi;
import com.summerofjake.server.model.Marker;

import java.util.List;

public class MarkersActivityImpl implements  MarkersActivity{
    private ActivityApi activityApi;

    public MarkersActivityImpl(ActivityApi activityApi) {
        this.activityApi = activityApi;
    }

    @Override
    public List<Marker> getMarkers(List<Long> activityIds) {
        MarkersController markersController = new MarkersController(activityApi);
        return markersController.getMarkers(activityIds);
    }
}
