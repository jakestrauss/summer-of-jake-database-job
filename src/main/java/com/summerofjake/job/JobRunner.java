package com.summerofjake.job;

import com.summerofjake.job.activities.*;
import com.summerofjake.job.db.api.JpaApi;
import com.summerofjake.job.model.Marker;
import com.summerofjake.job.strava.api.ActivityApi;

import java.util.List;

public class JobRunner {

    public static void main(String[] args) {

        ActivityApi activityApi = new ActivityApi();
        JpaApi jpaApi = new JpaApi();
        MasterActivity masterActivity = new MasterActivity(activityApi, jpaApi);
        masterActivity.executeJob();
    }
}
