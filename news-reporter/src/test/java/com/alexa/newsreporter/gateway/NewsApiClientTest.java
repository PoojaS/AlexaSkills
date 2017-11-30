package com.alexa.newsreporter.gateway;

import com.amazonaws.util.json.JSONException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Optional;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NewsApiClientTest {

    private NewsApiClient newsApiClient;

    @Mock
    private OkHttpClient mockHttpClient;

    private Request mockRequest;

    @Before
    public void setUp() throws Exception {
        newsApiClient = new NewsApiClient(mockHttpClient);
        mockRequest = new Request.Builder().url("https://newsapi" +
                ".org/v2/top-headlines?sources=fox-news&apiKey={API_KEY}").build();
    }

    @Test
    public void shouldReturnApiHeadlinesApiResponse() throws Exception {
        Call mockCall = mock(Call.class);
        when(mockHttpClient.newCall(any(Request.class))).thenReturn(new MockCall());

        Optional<String> topHeadlines = newsApiClient.getTopHeadlines("fox-news");

        assertThat(topHeadlines.get()).isNotNull();
        assertThat(topHeadlines.get()).isEqualTo(headlinesResponseString());
    }

    private Response getMockHeadlinesResponse() throws JSONException {
        String headlines = headlinesResponseString();
        ResponseBody body = ResponseBody.create(MediaType.parse("application/json"), headlines);
        return new Response.Builder().request(mockRequest).protocol(Protocol.HTTP_1_0).code(1).message("Http " +
                "Headlines call").body
                (body).build();
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
                "\"title\": \"A news report\",\n" +
                "\"description\": \"Some detailed description of news report\",\n" +
                "\"url\": \"http://www.foxnews.com/entertainment/2017/11/30/news-report.html\",\n" +
                "\"urlToImage\": \"//something/media2.foxnews.com/something.jpg?ve=1\",\n" +
                "\"publishedAt\": null\n" +
                "}]}";
    }

    class MockCall implements Call {

        @Override
        public Request request() {
            return mockRequest;
        }

        @Override
        public Response execute() throws IOException {
            try {
                return getMockHeadlinesResponse();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void enqueue(Callback responseCallback) {

        }

        @Override
        public void cancel() {

        }

        @Override
        public boolean isExecuted() {
            return false;
        }

        @Override
        public boolean isCanceled() {
            return false;
        }

        @Override
        public Call clone() {
            return null;
        }
    }
}