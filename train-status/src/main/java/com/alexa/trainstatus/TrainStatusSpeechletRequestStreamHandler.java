package com.alexa.trainstatus;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

import java.util.HashSet;
import java.util.Set;

public class TrainStatusSpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {

    private static final Set<String> supportedApplicationIds;
    static {
        //TODO (Pooja): relevant supported application id
        supportedApplicationIds = new HashSet<String>();
    }

    public TrainStatusSpeechletRequestStreamHandler() {
        super(new TrainStatusSpeechlet(), supportedApplicationIds);
    }
}
