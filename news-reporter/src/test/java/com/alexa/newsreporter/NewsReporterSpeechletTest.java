package com.alexa.newsreporter;

import com.alexa.newsreporter.service.NewsReporterService;
import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.SimpleCard;
import com.amazonaws.util.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NewsReporterSpeechletTest {

    private NewsReporterSpeechlet newsReporterSpeechlet;

    @Mock
    private SpeechletRequestEnvelope<IntentRequest> mockIntentEnvelope;

    @Mock
    private SpeechletRequestEnvelope<LaunchRequest> launchRequestEnvelope;

    @Mock
    private NewsReporterService mockNewsReporterService;

    @Before
    public void setUp() throws Exception {
        newsReporterSpeechlet = spy(new NewsReporterSpeechlet());
    }

    @Test
    public void shouldReturnASimpleCardOnLaunchOfSkill() throws Exception {
        SpeechletResponse speechletResponse = newsReporterSpeechlet.onLaunch(launchRequestEnvelope);

        SimpleCard responseCard = (SimpleCard) speechletResponse.getCard();
        assertThat(responseCard.getTitle()).isEqualTo("News");
        assertThat(responseCard.getContent()).isEqualTo("Welcome to the Alexa News Reporter Skill, you can say which source you want your news " +
                "from");

        PlainTextOutputSpeech promptSpeech = (PlainTextOutputSpeech) speechletResponse.getReprompt().getOutputSpeech();
        assertThat(promptSpeech.getText()).isEqualTo("Welcome to the Alexa News Reporter Skill, you can say which source you want your news " +
                "from");

        PlainTextOutputSpeech responseSpeech = (PlainTextOutputSpeech) speechletResponse.getOutputSpeech();
        assertThat(responseSpeech.getText()).isEqualTo("Welcome to the Alexa News Reporter Skill, you can say which source you want your news " +
                "from");
    }

    @Test
    public void shouldReceiveNewsReporterIntent() throws IOException, JSONException {
        IntentRequest intentRequestMock = mock(IntentRequest.class);
        when(mockIntentEnvelope.getRequest()).thenReturn(intentRequestMock);
        when(intentRequestMock.getIntent()).thenReturn(new Intent.Builder()
                .withName("NewsReporterIntent")
                .build());
        when(newsReporterSpeechlet.getNewsReporterService()).thenReturn(mockNewsReporterService);

        newsReporterSpeechlet.onIntent(mockIntentEnvelope);

        verify(mockNewsReporterService).getTopHeadlinesFromFox();
    }



}