package com.bandwidth.sdk.model.events;

import com.bandwidth.sdk.model.events.EventBase;
import com.bandwidth.sdk.model.events.Visitor;
import org.json.simple.JSONObject;

public class SpeakEvent extends EventBase {

	public SpeakEvent(final JSONObject json) {
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

    public String getState() {
        return getPropertyAsString("state");
    }
}
