package com.alexa.helloworld;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

import java.util.HashSet;
import java.util.Set;

public class HelloWorldSpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {

    private static final Set<String> supportedApplicationIds;
    static {
        //TODO (Pooja): relevant supported application id
        supportedApplicationIds = new HashSet<String>();
    }

    public HelloWorldSpeechletRequestStreamHandler() {
        super(new HelloWorldSpeechlet(), supportedApplicationIds);
    }
}
