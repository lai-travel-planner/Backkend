package com.example.flagcamp.Service;


import com.example.flagcamp.entity.Response.Place;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import org.apache.http.impl.client.CloseableHttpClient;

@Service
public class PlaceService {
    private static final String TOKEN = "Bearer YOUR_TWITCH_ACCESS_TOKEN";
    private static final String CLIENT_ID = "YOUR_TWITCH_CLIENT_ID";
    // private static final String TOP_Place_URL = "https://api.twitch.tv/helix/games/top?first=%s";
    private static final String PLACE_SEARCH_URL_TEMPLATE = "https://api.twitch.tv/helix/games?name=%s";
    private static final int DEFAULT_PLACE_LIMIT = 10;

    // Build the request URL which will be used when calling Twitch APIs, e.g. https://api.twitch.tv/helix/games/top when trying to get top games.
    private String buildPlaceURL(String url, String placeName, int limit) {
        if (placeName.equals("")) {
            return String.format(url, limit);
        } else {
            try {
                // Encode special characters in URL, e.g. Rick Sun -> Rick%20Sun
                placeName = URLEncoder.encode(placeName, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return String.format(url, placeName);
        }
    }

    // Send HTTP request to Twitch Backend based on the given URL, and returns the body of the HTTP response returned from Twitch backend.
    private String searchGoogleMap(String url) throws GoogleMapException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // Define the response handler to parse and return HTTP response body returned from Google Map
        ResponseHandler<String> responseHandler = response -> {
            int responseCode = response.getStatusLine().getStatusCode();
            if (responseCode != 200) {
                System.out.println("Response status: " + response.getStatusLine().getReasonPhrase());
                throw new GoogleMapException("Failed to get result from GoogleMap API");
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                throw new GoogleMapException("Failed to get result from GoogleMap API");
            }
            JSONObject obj = new JSONObject(EntityUtils.toString(entity));
            return obj.getJSONArray("data").toString();
        };

        try {
            // Define the HTTP request, TOKEN and CLIENT_ID are used for user authentication on Twitch backend
            HttpGet request = new HttpGet(url);
            request.setHeader("Authorization", TOKEN);
            request.setHeader("Client-Id", CLIENT_ID);
            return httpclient.execute(request, responseHandler);
        } catch (IOException e) {
            e.printStackTrace();
            throw new GoogleMapException("Failed to get result from Twitch API");
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Convert JSON format data returned from Twitch to an Arraylist of Game objects
    private List<Place> getPlaceList(String data) throws GoogleMapException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return Arrays.asList(mapper.readValue(data, Place[].class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new GoogleMapException("Failed to parse game data from Twitch API");
        }
    }

    // Integrate search() and getGameList() together, returns the dedicated game based on the game name.
    public Place searchPlace(String placeName) throws GoogleMapException {
        List<Place> gameList = getPlaceList(searchGoogleMap(buildPlaceURL(PLACE_SEARCH_URL_TEMPLATE, placeName, 0)));
        if (gameList.size() != 0) {
            return gameList.get(0);
        }
        return null;
    }




}


