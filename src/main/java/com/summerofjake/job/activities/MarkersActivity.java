package com.summerofjake.job.activities;

import com.summerofjake.job.model.Marker;

import java.util.List;

/**
 * Activity to get and populate all photo markers in database
 */
public interface MarkersActivity {
    public List<Marker> getMarkers(List<Long> activityIds);
}
