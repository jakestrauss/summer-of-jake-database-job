package com.summerofjake.job.factories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.summerofjake.job.gcloud.api.GcloudApi;
import com.summerofjake.job.model.Marker;
import com.summerofjake.job.strava.api.ActivityApi;
import org.apache.logging.log4j.util.Strings;
import org.geojson.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeoJsonFactoryImpl implements  GeoJsonFactory {
    private final ActivityApi activityApi;
    private static final String BASE_JSON_URL = "https://storage.googleapis.com/uploaded-geojsons/";

    public GeoJsonFactoryImpl(ActivityApi activityApi) {
        this.activityApi = activityApi;
    }

    @Override
    public String constructGeoJson(Marker marker) {
        final String activityId = marker.getActivityId();;
        List<LngLatAlt> coordinateList = activityApi.getActivityRouteStream(activityId);

        String json = Strings.EMPTY;
        FeatureCollection featureCollection = new FeatureCollection();
        Feature lineStreamFeature = new Feature();
        LineString lineString = new LineString();

        lineString.setCoordinates(coordinateList);
        lineStreamFeature.setGeometry(lineString);

        Map<String, Object> properties = new HashMap<>();

        properties.put("date", marker.getActivityDate().toString());
        properties.put("activity_title", marker.getActivityTitle());
        properties.put("activity_description", marker.getActivityDescription());

        lineStreamFeature.setProperties(properties);
        featureCollection.add(lineStreamFeature);

        try {
            json = new ObjectMapper().writeValueAsString(featureCollection);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        String fileName = writeJsonToBucket(activityId, json);

        try {
            GcloudApi.uploadJson(fileName);
        } catch (IOException e) {
            System.out.println("GeoJson file upload failed! " + e.getLocalizedMessage());
            return Strings.EMPTY;
        }

        return BASE_JSON_URL + activityId + "_route.json";
    }

    private String writeJsonToBucket(String activityId, String json) {
        FileWriter jsonFile;
        String fileName = GcloudApi.BASE_FILE_PATH + activityId + "_route.json";
        try {
            jsonFile = new FileWriter(fileName);
            jsonFile.write(json);
            jsonFile.flush();
            jsonFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return activityId + "_route.json";
    }
}
