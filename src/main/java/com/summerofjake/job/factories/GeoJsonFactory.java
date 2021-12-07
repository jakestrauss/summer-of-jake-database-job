package com.summerofjake.job.factories;

import com.summerofjake.job.model.Marker;

public interface GeoJsonFactory {
    String constructGeoJson(Marker marker);
}
