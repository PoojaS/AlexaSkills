package com.alexa.trainstatus;

import com.alexa.trainstatus.service.TrainService;
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

public class TrainStatusSpeechlet implements SpeechletV2 {

    private static final String TRAIN_NUMBER = "trainNumber";
    private TrainService trainService;

    @Override
    public void onSessionStarted(SpeechletRequestEnvelope<SessionStartedRequest> requestEnvelope) {

    }

    @Override
    public SpeechletResponse onLaunch(SpeechletRequestEnvelope<LaunchRequest> requestEnvelope) {
        String speechText = "Welcome to the Alexa Train Status Skill, you can say your train number";
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
        if ("TrainStatusIntent".equals(intentName)) {
            Slot trainNumberSlot = intent.getSlot(TRAIN_NUMBER);
            //TODO: Add exception if slot not available
            String trainNumber = trainNumberSlot.getValue();
            getTrainService().getTrainStatus(trainNumber);
            speechText = "Received Train Number : " + trainNumber;
            return SpeechletResponse.newTellResponse(getSpeech(speechText), getSimpleCard(speechText));
        }
        speechText = "Unable to recognise what was said. Please ask again.";
        PlainTextOutputSpeech speech = getSpeech(speechText);
        return SpeechletResponse.newAskResponse(speech, getReprompt(speech), getSimpleCard(speechText));

    }

    private Reprompt getReprompt(PlainTextOutputSpeech speech) {
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(speech);
        return reprompt;
    }

    private Card getSimpleCard(String speechText) {
        SimpleCard simpleCard = new SimpleCard();
        simpleCard.setTitle("TrainStatus");
        simpleCard.setContent(speechText);
        return simpleCard;
    }

    private PlainTextOutputSpeech getSpeech(String speechText) {
        PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
        outputSpeech.setText(speechText);
        return outputSpeech;
    }

    @Override
    public void onSessionEnded(SpeechletRequestEnvelope<SessionEndedRequest> requestEnvelope) {

    }

    TrainService getTrainService() {
        if (trainService == null) {
            return new TrainService();
        }
        return trainService;
    }
}
