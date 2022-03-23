# Summer of Jake adventure map back-end database fill job
# Project currently offline due to high infra costs, will come back later to refactor infrastructure when I have more time :)

This repo hosts the back-end job used to populate the database for summerofjake.com. It runs nightly and call's Strava's APIs to retrieve data from newly uploaded activities, 
and does some data transformations. Then it uploads the data to a google cloud bucket and adds the references to this data on the MySQL database by calling the back-end server.

As of now, this is a personal project so I will not include detailed instructions on how to contribute to future development.

# Personal Notes

## Cron command
Currently scheduled to run nightly on the EC2 with this command:
`0 1 * * * /bin/sh /home/ec2-user/database-job-jar/run_db_job.sh`

run_db_job.sh located under /database-job/src/main/java/resources

## Website infrastructure
* EC2 server (runs ‘server’ spring application and database-job as scheduled cron): https://console.aws.amazon.com/ec2/v2/home?region=us-east-1#InstanceDetails:instanceId=i-064f11c98fabf4bac
* MySQL database: https://console.aws.amazon.com/rds/home?region=us-east-1#database:id=summer-of-jake-db-2;is-cluster=false
* Cloud bucket (strava kmls, pictures, uploaded geojsons): https://console.cloud.google.com/storage/browser?referrer=search&project=summer-of-jake-adventure-map&prefix=
* Code pipeline: https://console.aws.amazon.com/codesuite/codepipeline/pipelines/summer-of-jake-backend-pipeline/view?region=us-east-1
  -> synced to updates on: https://github.com/jakestrauss/summer-of-jake-backend-server
* Code deploy: https://console.aws.amazon.com/codesuite/codedeploy/applications/summer-of-jake-website?region=us-east-1
* AWS Amplify: https://console.aws.amazon.com/amplify/home?region=us-east-1#/d36vgv5ul1f93o
  -> synced to updates on: https://github.com/jakestrauss/summer-of-jake-react

* Google Maps Javascript API: https://developers.google.com/maps/documentation/javascript/datalayerogle.com/maps/documentation/javascript/datalayer
