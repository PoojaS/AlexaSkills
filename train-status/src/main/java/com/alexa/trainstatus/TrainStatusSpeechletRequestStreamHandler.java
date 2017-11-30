package com.alexa.trainstatus;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

import java.util.HashSet;
import java.util.Set;

public class TrainStatusSpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {

    private static final Set<String> supportedApplicationIds;
    static {
        //TODO (Pooja): relevant supported application id
        supportedApplicationIds = new HashSet<String>();
        supportedApplicationIds.add("amzn1.ask.skill.e93c85c9-132c-4293-8ad2-7ce4d2cad428");
    }

    public TrainStatusSpeechletRequestStreamHandler() {
        super(new TrainStatusSpeechlet(), supportedApplicationIds);
    }
}
