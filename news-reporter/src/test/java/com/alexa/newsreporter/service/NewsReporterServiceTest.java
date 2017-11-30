package com.alexa.newsreporter.service;

import com.alexa.newsreporter.gateway.NewsApiClient;
import com.amazonaws.util.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NewsReporterServiceTest {

    private NewsReporterService newsReporterService;

    @Mock
    private NewsApiClient mockNewsApiClient;

    @Before
    public void setUp() throws Exception {
        newsReporterService = spy(new NewsReporterService());
        when(newsReporterService.getNewsApiClient()).thenReturn(mockNewsApiClient);
    }

    @Test
    public void shouldGetHeadlineTitleList() throws IOException, JSONException {
        when(mockNewsApiClient.getTopHeadlines("fox-news")).thenReturn(Optional.of(headlinesResponseString()));

        List<String> headlineTitles = newsReporterService.getTopHeadlinesFromFox();

        assertThat(headlineTitles).hasSize(2);
        assertThat(headlineTitles).contains("Some news report 1");
        assertThat(headlineTitles).contains("Some news report 2");
    }

    private String headlinesResponseString() {
        return "{\n" +
                "\"status\": \"ok\",\n" +
                "\"articles\": [\n" +
                "{\n" +
                "\"source\": {\n" +
                "\"id\": \"fox-news\",\n" +
                "\"name\": \"Fox News\"\n" +
                "},\n" +
                "\"author\": \"Fox News\",\n" +
                "\"title\": \"Some news report 1\",\n" +
                "\"description\": \"Detailed desc of report1\",\n" +
                "\"url\": \"some-valid-url\",\n" +
                "\"urlToImage\": \"some-image\",\n" +
                "\"publishedAt\": null\n" +
                "},\n" +
                "{\n" +
                "\"source\": {\n" +
                "\"id\": \"fox-news\",\n" +
                "\"name\": \"Fox News\"\n" +
                "},\n" +
                "\"author\": \"Fox News\",\n" +
                "\"title\": \"Some news report 2\",\n" +
                "\"description\": \"Detailed desc of report2\",\n" +
                "\"url\": \"Valid url 2\",\n" +
                "\"urlToImage\": \"valid image 2\",\n" +
                "\"publishedAt\": null\n" +
                "}\t\n" +
                "]}";
    }

}