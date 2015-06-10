package com.bandwidth.sdk.model.events;

import org.json.simple.JSONObject;

public class GatherEvent extends EventBase{

	public GatherEvent(final JSONObject json) {
		super(json);
	}

	public void execute(final Visitor visitor) {
		visitor.processEvent(this);
	}

    public String getState() {
        return getPropertyAsString("state");
    }

    public String getDigits() {
        return getPropertyAsString("digits");
    }

    public String getReason() {
        return getPropertyAsString("reason");
    }

    public String getCallId() {
        return getPropertyAsString("callId");
    }

    public String getGatherId() {
        return getPropertyAsString("gatherId");
    }

}
