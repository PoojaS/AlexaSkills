package com.alexa.helloworld;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.SimpleCard;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HelloWorldSpeechletTest {

    private HelloWorldSpeechlet helloWorldSpeechlet;

    @Mock
    private SpeechletRequestEnvelope<LaunchRequest> launchRequestEnvelope;

    @Mock
    private SpeechletRequestEnvelope<IntentRequest> intentRequestEnvelope;

    @Before
    public void setUp() throws Exception {
        helloWorldSpeechlet = new HelloWorldSpeechlet();
    }

    @Test
    public void shouldReturnASimpleCardOnLaunchOfSkill() throws Exception {
        SpeechletResponse speechletResponse = helloWorldSpeechlet.onLaunch(launchRequestEnvelope);

        SimpleCard responseCard = (SimpleCard) speechletResponse.getCard();
        assertThat(responseCard.getTitle()).isEqualTo("Hello World");
        assertThat(responseCard.getContent()).isEqualTo("Welcome to the Alexa Skills, you can say hello");

        PlainTextOutputSpeech promptSpeech = (PlainTextOutputSpeech) speechletResponse.getReprompt().getOutputSpeech();
        assertThat(promptSpeech.getText()).isEqualTo("Welcome to the Alexa Skills, you can say hello");

        PlainTextOutputSpeech responseSpeech = (PlainTextOutputSpeech) speechletResponse.getOutputSpeech();
        assertThat(responseSpeech.getText()).isEqualTo("Welcome to the Alexa Skills, you can say hello");
    }

    @Test
    public void shouldReturnASimpleCardOnHelloWorldIntentOfSkill() throws Exception {
        IntentRequest intentRequestMock = mock(IntentRequest.class);
        when(intentRequestEnvelope.getRequest()).thenReturn(intentRequestMock);
        when(intentRequestMock.getIntent()).thenReturn(new Intent.Builder().withName("HelloWorldIntent").build());
        SpeechletResponse speechletResponse = helloWorldSpeechlet.onIntent(intentRequestEnvelope);

        SimpleCard responseCard = (SimpleCard) speechletResponse.getCard();
        assertThat(responseCard.getTitle()).isEqualTo("Hello World");
        assertThat(responseCard.getContent()).isEqualTo("Hello there user!");

        PlainTextOutputSpeech responseSpeech = (PlainTextOutputSpeech) speechletResponse.getOutputSpeech();
        assertThat(responseSpeech.getText()).isEqualTo("Hello there user!");
    }

    @Test
    public void shouldReturnASimpleCardWithHelpTextOnHelpIntentOfSkill() throws Exception {
        IntentRequest intentRequestMock = mock(IntentRequest.class);
        when(intentRequestEnvelope.getRequest()).thenReturn(intentRequestMock);
        when(intentRequestMock.getIntent()).thenReturn(new Intent.Builder().withName("HelpIntent").build());
        SpeechletResponse speechletResponse = helloWorldSpeechlet.onIntent(intentRequestEnvelope);

        SimpleCard responseCard = (SimpleCard) speechletResponse.getCard();
        assertThat(responseCard.getTitle()).isEqualTo("Hello World");
        assertThat(responseCard.getContent()).isEqualTo("You can say hello to me");

        PlainTextOutputSpeech responseSpeech = (PlainTextOutputSpeech) speechletResponse.getOutputSpeech();
        assertThat(responseSpeech.getText()).isEqualTo("You can say hello to me");
    }

    @Test
    public void shouldReturnAUnrecognisedResponseWhenUnknownIntentOfSkillReceived() throws Exception {
        IntentRequest intentRequestMock = mock(IntentRequest.class);
        when(intentRequestEnvelope.getRequest()).thenReturn(intentRequestMock);
        when(intentRequestMock.getIntent()).thenReturn(new Intent.Builder().withName("UknownIntent").build());
        SpeechletResponse speechletResponse = helloWorldSpeechlet.onIntent(intentRequestEnvelope);

        SimpleCard responseCard = (SimpleCard) speechletResponse.getCard();
        assertThat(responseCard.getTitle()).isEqualTo("Hello World");
        assertThat(responseCard.getContent()).isEqualTo("Unable to recognise what was said. Please ask again.");

        PlainTextOutputSpeech responseSpeech = (PlainTextOutputSpeech) speechletResponse.getOutputSpeech();
        assertThat(responseSpeech.getText()).isEqualTo("Unable to recognise what was said. Please ask again.");
    }
}