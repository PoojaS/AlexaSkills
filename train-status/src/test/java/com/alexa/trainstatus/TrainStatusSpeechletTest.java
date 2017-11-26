package com.alexa.trainstatus;

import com.alexa.trainstatus.service.TrainService;
import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.IntentRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

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

    @Before
    public void setUp() throws Exception {
        trainStatusSpeechlet = spy(new TrainStatusSpeechlet());
    }

    @Test
    public void shouldReceiveTrainNumberInIntent() {
        IntentRequest intentRequestMock = mock(IntentRequest.class);
        when(mockIntentEnvelope.getRequest()).thenReturn(intentRequestMock);
        Slot trainNumberSlot = new Slot.Builder().withName("trainNumber").withValue("123456").build();
        when(intentRequestMock.getIntent()).thenReturn(new Intent.Builder()
                                                                 .withName("TrainStatusIntent")
                                                                 .withSlot(trainNumberSlot)
                                                                 .build());
        when(trainStatusSpeechlet.getTrainService()).thenReturn(trainServiceMock);

        trainStatusSpeechlet.onIntent(mockIntentEnvelope);

        verify(trainServiceMock).getTrainStatus("123456");
    }
}