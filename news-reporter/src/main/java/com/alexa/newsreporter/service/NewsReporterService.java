package com.alexa.newsreporter.service;

import com.alexa.newsreporter.gateway.NewsApiClient;
import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NewsReporterService {
    private NewsApiClient newsApiClient;

    //TODO(Pooja): Handle exceptions
    public List<String> getTopHeadlinesFromFox() throws IOException, JSONException {

        Optional<String> topHeadlines = getNewsApiClient().getTopHeadlines("fox-news");
        String headlines = topHeadlines.get();
        JSONObject jsonObject = getJsonObject(headlines).get();
        JSONArray articles = (JSONArray) jsonObject.get("articles");
        ArrayList<String> titles = new ArrayList<>();
        for( int i = 0; i < articles.length() ; i++){
            JSONObject articleJsonObject = (JSONObject) articles.get(i);
            titles.add(getJsonValue(articleJsonObject, "title").get());
        }
        return titles;
    }

    private Optional<String> getJsonValue(JSONObject jsonObject, String key) {
        try {
            return Optional.of((String) jsonObject.get(key));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private Optional<JSONObject> getJsonObject(String articleJsonString) {
        try {
            return Optional.of(new JSONObject(articleJsonString));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    NewsApiClient getNewsApiClient() {
        newsApiClient = newsApiClient != null ? newsApiClient : new NewsApiClient();
        return newsApiClient;
    }

}
