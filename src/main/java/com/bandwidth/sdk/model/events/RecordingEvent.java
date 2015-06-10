package com.bandwidth.sdk.model.events;

import org.json.simple.JSONObject;

public class RecordingEvent extends EventBase {

	public RecordingEvent(final JSONObject json) {
		super(json);
	}
	
	public void execute(final Visitor visitor) {
		visitor.processEvent(this);
	}

    public String getState() {
        return getPropertyAsString("state");
    }

    public String getStatus() {
        return getPropertyAsString("status");
    }

    public String getCallId() {
        return getPropertyAsString("callId");
    }

    public String getRecordingId() {
        return getPropertyAsString("recordingId");
    }

    public String getRecordingUri() {
        return getPropertyAsString("recordingUri");
    }

    public String getStartTime() {
        return getProperty("startTime");
    }

    public String getEndTime() {
        return getProperty("endTime");
    }
}
