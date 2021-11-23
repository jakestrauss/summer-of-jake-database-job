package com.summerofjake.job.activities;

import com.google.common.collect.ImmutableList;
import com.summerofjake.job.controllers.GetActivityIdsController;
import com.summerofjake.job.strava.api.ActivityApi;

import java.util.List;

public class GetActivityIdsActivity {
    private ActivityApi activityApi;

    public GetActivityIdsActivity(ActivityApi activityApi)  {
        this.activityApi = activityApi;
    }

    public List<Long> getActivityIds() {
        GetActivityIdsController getActivityIdsController = new GetActivityIdsController(activityApi);
        return getActivityIdsController.getActivityIds();
    }
}
