#!/bin/sh

cd /home/ec2-user/database-job-jar/; java -jar -DGCLOUD_CREDENTIALS_PATH=/home/ec2-user/credentials/gcloud/summer-of-jake-adventure-map-966979f68045.json /home/ec2-user/database-job-jar/database-job-1.0-SNAPSHOT-jar-with-dependencies.jar > /home/ec2-user/database-job-logs/$(date +%m_%d_%Y).log 2>&1 &