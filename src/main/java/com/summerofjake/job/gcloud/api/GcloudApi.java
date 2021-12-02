package com.summerofjake.job.gcloud.api;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.google.common.collect.Lists;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GcloudApi {
    private static final String PROJECT_ID = "summer-of-jake-adventure-map";
    private static final String BUCKET_NAME = "uploaded-kmls";
    private static final String GCLOUD_CREDENTIALS_PATH =
            System.getProperty("GCLOUD_CREDENTIALS_PATH") != null ? System.getProperty("GCLOUD_CREDENTIALS_PATH") :
                    "/Users/straussj/Documents/react_projects/summerofjakemisc/summer-of-jake-adventure-map-966979f68045.json";
    public static final String BASE_FILE_PATH = "src/main/resources/strava_kmls/";

    public static void uploadKml(String kmlFileName) throws IOException {
        uploadObject(PROJECT_ID, BUCKET_NAME, kmlFileName, BASE_FILE_PATH + kmlFileName);
    }

    //From https://cloud.google.com/storage/docs/samples/storage-upload-file
    private static void uploadObject(
            String projectId, String bucketName, String objectName, String filePath) throws IOException {
        // The ID of your GCP project
        // String projectId = "your-project-id";

        // The ID of your GCS bucket
        // String bucketName = "your-unique-bucket-name";

        // The ID of your GCS object
        // String objectName = "your-object-name";

        // The path to your file to upload
        // String filePath = "path/to/your/file"

        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(GCLOUD_CREDENTIALS_PATH))
                .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));

        Storage storage = StorageOptions.newBuilder().setCredentials(credentials)
                .setProjectId(projectId).build().getService();
        BlobId blobId = BlobId.of(bucketName, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        storage.create(blobInfo, Files.readAllBytes(Paths.get(filePath)));

        System.out.println(
                "File " + filePath + " uploaded to bucket " + bucketName + " as " + objectName);
    }
}