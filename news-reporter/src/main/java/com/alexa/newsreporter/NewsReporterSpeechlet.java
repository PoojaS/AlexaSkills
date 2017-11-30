package com.alexa.newsreporter;

import com.amazon.speech.json.SpeechletRequestEnvelope;
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

public class NewsReporterSpeechlet implements SpeechletV2 {
    @Override
    public void onSessionStarted(SpeechletRequestEnvelope<SessionStartedRequest> requestEnvelope) {

    }

    @Override
    public SpeechletResponse onLaunch(SpeechletRequestEnvelope<LaunchRequest> requestEnvelope) {
        String speechText = "Welcome to the Alexa News Reporter Skill, you can say which source you want your news " +
                "from";
        PlainTextOutputSpeech speech = getSpeech(speechText);
        return SpeechletResponse.newAskResponse(speech,
                getReprompt(speech),
                getSimpleCard(speechText));

    }

    @Override
    public SpeechletResponse onIntent(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
        return null;
    }

    @Override
    public void onSessionEnded(SpeechletRequestEnvelope<SessionEndedRequest> requestEnvelope) {

    }

    //TODO: Move to a common module
    private Reprompt getReprompt(PlainTextOutputSpeech speech) {
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(speech);
        return reprompt;
    }

    private Card getSimpleCard(String speechText) {
        SimpleCard simpleCard = new SimpleCard();
        simpleCard.setTitle("News");
        simpleCard.setContent(speechText);
        return simpleCard;
    }

    private PlainTextOutputSpeech getSpeech(String speechText) {
        PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
        outputSpeech.setText(speechText);
        return outputSpeech;
    }
}
