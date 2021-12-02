package com.summerofjake.job.activities;

import com.summerofjake.job.model.Marker;

import java.util.List;

/**
 * Activity to get and populate all strava routes in database
 */
public interface RoutesActivity {
    public void getRoutes(List<Marker> markers);
}
