package com.summerofjake.job.strava.api;

import com.google.common.collect.ImmutableList;
import com.summerofjake.job.model.LatLong;
import com.summerofjake.job.model.Marker;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.apache.logging.log4j.util.Strings;
import org.apache.tomcat.util.json.JSONParser;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ActivityApi extends StravaApi {

    public ActivityApi() {
        super();
    }

    public List<Long> getLoggedInAthleteActivities() {

        HttpUrl.Builder urlBuilder
                = HttpUrl.parse(STRAVA_BASE_URL + "athlete/activities").newBuilder();
        urlBuilder.addQueryParameter("access_token", accessToken);
        //Get only activities from the last day
        long yesterdaysEpoch = (Instant.now().toEpochMilli()/1000) - Duration.ofDays(1).getSeconds();
        //urlBuilder.addQueryParameter("after", String.valueOf(yesterdaysEpoch));
        urlBuilder.addQueryParameter("per_page", "45");
        urlBuilder.addQueryParameter("page", "3");
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder().url(url).build();

        Call call = client.newCall(request);
        ResponseBody responseBody = execute(call);

        List<Long> activityIds = new ArrayList<>();

        try {
            JSONParser parser = new JSONParser(responseBody.string());
            ArrayList jsonArray = (ArrayList) parser.parse();

            for(Object jsonObject : jsonArray) {
                activityIds.add(((BigInteger)((LinkedHashMap)jsonObject).get("id")).longValue());
            }

        } catch (Exception e) {
            System.out.println("To string exception: " + e.getMessage());
        }
        return activityIds;
    }

    public List<Marker> getMarkersForActivity(String activityId) {
        HttpUrl.Builder urlBuilder
                = HttpUrl.parse(STRAVA_BASE_URL + "activities/" + activityId).newBuilder();
        urlBuilder.addQueryParameter("access_token", accessToken);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder().url(url).build();

        Call call = client.newCall(request);
        ResponseBody responseBody = execute(call);

        //Marker properties to extract from API response
        String photoUrl = Strings.EMPTY;
        ArrayList<BigDecimal> latLng = new ArrayList<>();
        LocalDateTime activityDate = LocalDateTime.now();
        String activityTitle = Strings.EMPTY;
        String activityDescription = Strings.EMPTY;


        try {
            JSONParser parser = new JSONParser(responseBody.string());
            LinkedHashMap requestMap = (LinkedHashMap) parser.parse();
            LinkedHashMap photosMap = (LinkedHashMap) requestMap.get("photos");

            if(photosMap != null && !photosMap.isEmpty() && photosMap.get("primary") != null) {
                photoUrl = (String)((LinkedHashMap)((LinkedHashMap)photosMap.get("primary")).get("urls")).get("600");
            }

            activityDate = LocalDateTime.parse((String)requestMap.get("start_date_local"), DateTimeFormatter.ISO_DATE_TIME);
            activityTitle = (String)requestMap.get("name");
            activityDescription = requestMap.get("description") != null
                    ? (String)requestMap.get("description") : Strings.EMPTY;

            //Currently no geo-location data of photo accessible from Strava API, so must use start lat/lng
            latLng = (ArrayList)requestMap.get("start_latlng");
        } catch (Exception e) {
            System.out.println("Parsing exception: " + e.getMessage());
        }

        //Currently only one photo marker per route, as this is all Strava's API currently provides
        return ImmutableList.of(
                new Marker(photoUrl, latLng.get(0).doubleValue(), latLng.get(1).doubleValue(),
                        activityDate, activityTitle, activityDescription, activityId));
    }

    public List<LatLong> getActivityRouteStream(String activityId) {

        HttpUrl.Builder urlBuilder
                = HttpUrl.parse(STRAVA_BASE_URL + "activities/" + activityId + "/streams").newBuilder();
        urlBuilder.addQueryParameter("keys", "latlng");
        urlBuilder.addQueryParameter("key_by_type", Boolean.TRUE.toString());
        urlBuilder.addQueryParameter("access_token", accessToken);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder().url(url).build();

        Call call = client.newCall(request);
        ResponseBody responseBody = execute(call);

        List<LatLong> coordinateList = new ArrayList<>();

        try {
            JSONParser parser = new JSONParser(responseBody.string());
            LinkedHashMap jsonObject = (LinkedHashMap) parser.parse();
            Map latLngObject = (LinkedHashMap)jsonObject.get("latlng");
            ArrayList<ArrayList> latLngDataList = (ArrayList)latLngObject.get("data");

            //populate coorindate list with latLng data
            latLngDataList.forEach(latLng -> {
                coordinateList.add(new LatLong(((BigDecimal)(latLng.get(0))).doubleValue(),
                       (((BigDecimal)(latLng.get(1))).doubleValue())));
            });


        } catch (Exception e) {
            System.out.println("To string exception: " + e.getMessage());
        }

        return coordinateList;
    }
}
