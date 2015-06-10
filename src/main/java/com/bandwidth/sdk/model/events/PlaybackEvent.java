package com.bandwidth.sdk.model.events;

import org.json.simple.JSONObject;

public class PlaybackEvent extends EventBase {

	public PlaybackEvent(final JSONObject json) {
		super(json);
	}

	public void execute(final Visitor visitor) {
		visitor.processEvent(this);
	}

    public String getCallId() {
        return getPropertyAsString("callId");
    }

    public String getCallUri() {
        return getPropertyAsString("callUri");
    }

    public String getStatus() {
        return getPropertyAsString("status");
    }

}
