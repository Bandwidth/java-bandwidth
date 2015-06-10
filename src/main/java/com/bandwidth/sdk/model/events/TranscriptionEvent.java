package com.bandwidth.sdk.model.events;

import org.json.simple.JSONObject;

public class TranscriptionEvent extends EventBase {

	public TranscriptionEvent(final JSONObject json) {
		super(json);
	}
	
	public void execute(final Visitor visitor) {
		visitor.processEvent(this);
	}

    public String getTranscriptionId() {
        return getPropertyAsString("transcriptionId");
    }

    public String getState() {
        return getPropertyAsString("state");
    }

    public String getStatus() {
        return getPropertyAsString("status");
    }

    public String getTextSize() {
        return getPropertyAsString("textSize");
    }

    public String getText() {
        return getPropertyAsString("text");
    }

    public String getTextUrl() {
        return getPropertyAsString("textUrl");
    }

    public String getTranscriptionUri() {
        return getPropertyAsString("transcriptionUri");
    }













}
