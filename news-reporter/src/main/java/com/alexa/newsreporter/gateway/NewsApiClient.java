package com.alexa.newsreporter.gateway;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Optional;

public class NewsApiClient {

    private OkHttpClient httpClient;

    OkHttpClient getHttpClient() {
        httpClient = httpClient != null ? httpClient : new OkHttpClient();
        return httpClient;
    }

    public Optional<String> getTopHeadlines(String source) throws IOException {
//        source = "fox-news";
        String newsApiHeadlinesEndPoint = "https://newsapi.org/v2/top-headlines?sources=" + source + "&apiKey={API_KEY}";
        Request request = new Request.Builder().url(newsApiHeadlinesEndPoint).build();
        Response response;
        response = getHttpClient().newCall(request).execute();
        return response != null && response.body() != null
                                            ? Optional.of(response.body().string()) : Optional.empty();
    }

}
