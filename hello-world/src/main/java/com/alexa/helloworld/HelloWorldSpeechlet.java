package com.alexa.helloworld;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.speechlet.SpeechletV2;
import com.amazon.speech.ui.Card;
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
        PlainTextOutputSpeech speech = getSpeech(speechText);
        return SpeechletResponse.newAskResponse(speech,
                getReprompt(speech),
                getSimpleCard(speechText));
    }

    @Override
    public SpeechletResponse onIntent(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
        String speechText;
        Intent intent = requestEnvelope.getRequest().getIntent();
        String intentName = intent != null ? intent.getName() : null;
        if ("HelloWorldIntent".equals(intentName)) {
            speechText = "Hello there user!";
            return SpeechletResponse.newTellResponse(getSpeech(speechText), getSimpleCard(speechText));
        } else if ("HelpIntent".equals(intentName)) {
            speechText = "You can say hello to me";
            PlainTextOutputSpeech speech = getSpeech(speechText);
            return SpeechletResponse.newAskResponse(speech, getReprompt(speech), getSimpleCard(speechText));
        } else {
            speechText = "Unable to recognise what was said. Please ask again.";
            PlainTextOutputSpeech speech = getSpeech(speechText);
            return SpeechletResponse.newAskResponse(speech, getReprompt(speech), getSimpleCard(speechText));
        }
    }

    @Override
    public void onSessionEnded(SpeechletRequestEnvelope<SessionEndedRequest> requestEnvelope) {

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
}
