package com.summerofjake.job.activities;

import java.util.List;

/**
 * Activity to get and populate all strava routes in database
 */
public interface RoutesActivity {
    public void getRoutes(List<Long> activityIds);
}
