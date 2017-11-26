package com.alexa.helloworld;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.speechlet.SpeechletV2;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;

public class HelloWorldSpeechlet implements SpeechletV2 {
    @Override
    public void onSessionStarted(SpeechletRequestEnvelope<SessionStartedRequest> requestEnvelope) {

    }

    @Override
    public SpeechletResponse onLaunch(SpeechletRequestEnvelope<LaunchRequest> requestEnvelope) {
        String speechText = "Welcome to the Alexa Skills, you can say hello";
        return SpeechletResponse.newAskResponse(getSpeech(speechText),
                              getReprompt(getSpeech(speechText)),
                              getSimpleCard(speechText));
    }

    private SimpleCard getSimpleCard(String speechText) {
        SimpleCard card = new SimpleCard();
        card.setTitle("Hello World");
        card.setContent(speechText);
        return card;
    }

    private Reprompt getReprompt(PlainTextOutputSpeech speech) {
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(speech);
        return reprompt;
    }

    private PlainTextOutputSpeech getSpeech(String speechText) {
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);
        return speech;
    }

    @Override
    public SpeechletResponse onIntent(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
        return null;
    }

    @Override
    public void onSessionEnded(SpeechletRequestEnvelope<SessionEndedRequest> requestEnvelope) {

    }
}
