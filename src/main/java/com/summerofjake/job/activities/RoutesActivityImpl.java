package com.summerofjake.job.activities;

import com.summerofjake.job.controllers.RoutesController;
import com.summerofjake.job.model.Route;
import com.summerofjake.job.strava.api.ActivityApi;

import java.util.List;

/**
 * Ac
 */
public class RoutesActivityImpl implements RoutesActivity {

    public RoutesActivityImpl() {
    }

    @Override
    public void getRoutes(List<Long> activityIds) {
        ActivityApi activityApi = new ActivityApi();
        RoutesController routesController = new RoutesController();

        List<Route> routes = routesController.getRoutes();
    }
}
