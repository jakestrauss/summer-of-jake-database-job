package com.summerofjake.job;

import com.summerofjake.job.activities.*;
import com.summerofjake.job.db.api.JpaApi;
import com.summerofjake.job.strava.api.ActivityApi;


public class JobRunner {

    public static void main(String[] args) {

        ActivityApi activityApi = new ActivityApi();
        JpaApi jpaApi = new JpaApi();
        MasterActivity masterActivity = new MasterActivity(activityApi, jpaApi);
        masterActivity.executeJob();
    }
}
