package com.alexa.helloworld;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.SimpleCard;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class HelloWorldSpeechletTest {

    private HelloWorldSpeechlet helloWorldSpeechlet;
    private SpeechletRequestEnvelope<LaunchRequest> requestEnvelope;

    @Before
    public void setUp() throws Exception {
        helloWorldSpeechlet = new HelloWorldSpeechlet();
    }

    @Test
    public void shouldReturnASimpleCardOnLaunchOfSkill() throws Exception {
        SpeechletResponse speechletResponse = helloWorldSpeechlet.onLaunch(requestEnvelope);

        SimpleCard responseCard = (SimpleCard) speechletResponse.getCard();
        assertThat(responseCard.getTitle()).isEqualTo("Hello World");
        assertThat(responseCard.getContent()).isEqualTo("Welcome to the Alexa Skills, you can say hello");

        PlainTextOutputSpeech promptSpeech = (PlainTextOutputSpeech) speechletResponse.getReprompt().getOutputSpeech();
        assertThat(promptSpeech.getText()).isEqualTo("Welcome to the Alexa Skills, you can say hello");

        PlainTextOutputSpeech responseSpeech = (PlainTextOutputSpeech) speechletResponse.getOutputSpeech();
        assertThat(responseSpeech.getText()).isEqualTo("Welcome to the Alexa Skills, you can say hello");
    }
}