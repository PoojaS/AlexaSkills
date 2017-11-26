package com.alexa.trainstatus;

import com.alexa.trainstatus.service.TrainService;
import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.SimpleCard;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrainStatusSpeechletTest {

    private TrainStatusSpeechlet trainStatusSpeechlet;

    @Mock
    private SpeechletRequestEnvelope<IntentRequest> mockIntentEnvelope;

    @Mock
    private TrainService trainServiceMock;

    @Mock
    private SpeechletRequestEnvelope<LaunchRequest> launchRequestEnvelope;

    @Before
    public void setUp() throws Exception {
        trainStatusSpeechlet = spy(new TrainStatusSpeechlet());
    }

    @Test
    public void shouldReturnASimpleCardOnLaunchOfSkill() throws Exception {
        SpeechletResponse speechletResponse = trainStatusSpeechlet.onLaunch(launchRequestEnvelope);

        SimpleCard responseCard = (SimpleCard) speechletResponse.getCard();
        assertThat(responseCard.getTitle()).isEqualTo("TrainStatus");
        assertThat(responseCard.getContent()).isEqualTo("Welcome to the Alexa Train Status Skill, you can say your " +
                "train number");

        PlainTextOutputSpeech promptSpeech = (PlainTextOutputSpeech) speechletResponse.getReprompt().getOutputSpeech();
        assertThat(promptSpeech.getText()).isEqualTo("Welcome to the Alexa Train Status Skill, you can say your " +
                "train number");

        PlainTextOutputSpeech responseSpeech = (PlainTextOutputSpeech) speechletResponse.getOutputSpeech();
        assertThat(responseSpeech.getText()).isEqualTo("Welcome to the Alexa Train Status Skill, you can say your " +
                "train number");
    }

    @Test
    public void shouldReceiveTrainNumberInIntent() {
        IntentRequest intentRequestMock = mock(IntentRequest.class);
        when(mockIntentEnvelope.getRequest()).thenReturn(intentRequestMock);
        Slot trainNumberSlot = new Slot.Builder().withName("TrainNumber").withValue("123456").build();
        when(intentRequestMock.getIntent()).thenReturn(new Intent.Builder()
                                                                 .withName("TrainStatusIntent")
                                                                 .withSlot(trainNumberSlot)
                                                                 .build());
        when(trainStatusSpeechlet.getTrainService()).thenReturn(trainServiceMock);

        trainStatusSpeechlet.onIntent(mockIntentEnvelope);

        verify(trainServiceMock).getTrainStatus("123456");
    }

    @Test
    public void shouldReturnAUnrecognisedResponseWhenUnknownIntentOfSkillReceived() throws Exception {
        IntentRequest intentRequestMock = mock(IntentRequest.class);
        when(mockIntentEnvelope.getRequest()).thenReturn(intentRequestMock);
        when(intentRequestMock.getIntent()).thenReturn(new Intent.Builder().withName("UknownIntent").build());
        SpeechletResponse speechletResponse = trainStatusSpeechlet.onIntent(mockIntentEnvelope);

        SimpleCard responseCard = (SimpleCard) speechletResponse.getCard();
        assertThat(responseCard.getTitle()).isEqualTo("TrainStatus");
        assertThat(responseCard.getContent()).isEqualTo("Unable to recognise what was said. Please ask again.");

        PlainTextOutputSpeech responseSpeech = (PlainTextOutputSpeech) speechletResponse.getOutputSpeech();
        assertThat(responseSpeech.getText()).isEqualTo("Unable to recognise what was said. Please ask again.");
    }

}