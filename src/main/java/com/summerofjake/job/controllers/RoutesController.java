package com.summerofjake.job.controllers;

import com.summerofjake.job.db.client.JpaApi;
import com.summerofjake.job.factories.KmlFactory;
import com.summerofjake.job.factories.KmlFactoryImpl;
import com.summerofjake.job.model.Marker;
import com.summerofjake.job.model.Route;
import com.summerofjake.job.model.RouteBuilder;
import com.summerofjake.job.strava.api.ActivityApi;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class RoutesController {

    private final JpaApi jpaApi;
    private final KmlFactory kmlFactory;

    public RoutesController(ActivityApi activityApi, JpaApi jpaApi) {
        this.jpaApi = jpaApi;
        kmlFactory = new KmlFactoryImpl(activityApi);
    }

    public List<Route> getRoutes(List<Marker> markers) {
        List<Route> routes = new ArrayList<>();
        Set<Marker> uniqueActivityMarkers =  markers.stream()
                .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Marker::getActivityId))));

        for(Marker m : uniqueActivityMarkers) {
            String activityId = m.getActivityId();
            String url = kmlFactory.constructKmlUrl(activityId);

            //construct finished route
            Route r = RouteBuilder.aRoute()
                    .withActivityId(activityId)
                    .withActivityDescription(m.getActivityDescription())
                    .withActivityTitle(m.getActivityTitle())
                    .withActivityDate(m.getActivityDate())
                    .withUrl(url)
                    .build();

            routes.add(r);
        }

        if(routes.isEmpty()) {
            System.out.println("No new routes to post!");
            return(routes);
        }

        boolean posted = false;
        try {
            posted = jpaApi.postRoutes(routes);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        if(posted) {
            System.out.println("Routes successfully posted");
        } else {
            System.out.println("Routes NOT successfully posted");
        }

        return routes;
    }
}
