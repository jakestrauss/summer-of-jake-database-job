package com.summerofjake.job.strava.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.summerofjake.job.strava.api.exception.StravaAPIException;
import com.summerofjake.job.strava.api.exception.StravaUnauthorizedException;
import okhttp3.*;
import org.apache.tomcat.util.json.JSONParser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Properties;

public abstract class StravaApi {
    private static final int UNAUTHORIZED_CODE = 401;
    private static String CLIENT_ID;
    private static String CLIENT_SECRET;
    private static String REFRESH_TOKEN;
    protected ObjectMapper objectMapper;
    protected static final String STRAVA_BASE_URL = "https://www.strava.com/api/v3/";
    private static final String STRAVA_OAUTH_URL = "https://www.strava.com/oauth/";
    protected String accessToken;
    protected OkHttpClient client;

    public StravaApi() {
        //load in API keys from properties file
        try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {

            Properties prop = new Properties();
            prop.load(input);

            CLIENT_ID = prop.getProperty("strava.clientid");
            CLIENT_SECRET = prop.getProperty("strava.clientsecret");
            REFRESH_TOKEN = prop.getProperty("strava.refreshtoken");

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        this.client = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
        this.accessToken = retrieveAccessToken();
    }

    /*
     * Call to retrieve current Strava access token using refresh token
     */
    public String retrieveAccessToken() {
        String token = null;

        HttpUrl.Builder urlBuilder
                = HttpUrl.parse(STRAVA_OAUTH_URL + "token").newBuilder();
        String url = urlBuilder.build().toString();

        RequestBody formBody = new FormBody.Builder()
                .add("client_id", CLIENT_ID)
                .add("client_secret", CLIENT_SECRET)
                .add("grant_type", "refresh_token")
                .add("refresh_token", REFRESH_TOKEN)
                .add("f", "json")
                .build();

        Request request = new Request.Builder().url(url).post(formBody).build();

        Call call = client.newCall(request);
        ResponseBody responseBody = execute(call);

        try {
            JSONParser parser = new JSONParser(responseBody.string());
            LinkedHashMap map = (LinkedHashMap) parser.parse();
            token = (String) map.get("access_token");
        } catch (Exception e) {
            System.out.println("ParseException of access token call: " + e.getMessage());
        }

        return token;
    }

    public ResponseBody execute(Call call) throws StravaAPIException, StravaUnauthorizedException {
        Response response;
        try {
            response = call.execute();
        } catch (IOException e) {
            throw new StravaAPIException("A network error happened contacting Strava API", e);
        }

        if(response.isSuccessful()) {
            return response.body();
        } else if(response.code() == UNAUTHORIZED_CODE) {
            throw new StravaUnauthorizedException();
        } else {
            throw new StravaAPIException("Response was not successful, response code: " + response.code());
        }
    }
}
