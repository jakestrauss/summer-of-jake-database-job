package com.summerofjake.job.activities;

import com.summerofjake.job.controllers.RoutesController;
import com.summerofjake.job.db.api.JpaApi;
import com.summerofjake.job.model.Marker;
import com.summerofjake.job.model.Route;
import com.summerofjake.job.strava.api.ActivityApi;

import java.util.List;

/**
 * Ac
 */
public class RoutesActivityImpl implements RoutesActivity {

    private final ActivityApi activityApi;
    private final JpaApi jpaApi;

    public RoutesActivityImpl(ActivityApi activityApi, JpaApi jpaApi) {
        this.activityApi = activityApi;
        this.jpaApi = jpaApi;
    }

    @Override
    public void getRoutes(List<Marker> markers) {
        ActivityApi activityApi = new ActivityApi();
        JpaApi jpaApi = new JpaApi();
        RoutesController routesController = new RoutesController(activityApi, jpaApi);

        List<Route> routes = routesController.getRoutes(markers);
    }
}
