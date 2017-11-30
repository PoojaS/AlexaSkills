package com.alexa.newsreporter;

import com.alexa.newsreporter.service.NewsReporterService;
import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
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
import com.amazonaws.util.json.JSONException;

import java.io.IOException;
import java.util.List;

public class NewsReporterSpeechlet implements SpeechletV2 {
    private NewsReporterService newsReporterService;

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
        String speechText;
        Intent intent = requestEnvelope.getRequest().getIntent();
        String intentName = intent != null ? intent.getName() : null;
        List<String> topHeadlinesFromFox;
        if ("NewsReporterIntent".equals(intentName)) {
            try {
                topHeadlinesFromFox = getNewsReporterService().getTopHeadlinesFromFox();
            } catch (Exception e) {
                return SpeechletResponse.newTellResponse(getSpeech("Sorry I am unable to fetch the news. Please try " +
                        "in a while"));
            }
            String topHeadlines = String.join(", ", topHeadlinesFromFox);
            return SpeechletResponse.newTellResponse(getSpeech(topHeadlines), getSimpleCard(topHeadlines));
        }
        speechText = "Unable to recognise what was said. Please ask again.";
        PlainTextOutputSpeech speech = getSpeech(speechText);
        return SpeechletResponse.newAskResponse(speech, getReprompt(speech), getSimpleCard(speechText));

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

    NewsReporterService getNewsReporterService() {
        if (newsReporterService == null) {
            return new NewsReporterService();
        }
        return newsReporterService;
    }

}
