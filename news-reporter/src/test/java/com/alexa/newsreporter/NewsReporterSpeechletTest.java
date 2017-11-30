package com.alexa.newsreporter;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.SimpleCard;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.fest.assertions.api.Assertions.assertThat;

public class NewsReporterSpeechletTest {

    private NewsReporterSpeechlet newsReporterSpeechlet;

    @Mock
    private SpeechletRequestEnvelope<IntentRequest> mockIntentEnvelope;

    @Mock
    private SpeechletRequestEnvelope<LaunchRequest> launchRequestEnvelope;

    @Before
    public void setUp() throws Exception {
        newsReporterSpeechlet = new NewsReporterSpeechlet();
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


}