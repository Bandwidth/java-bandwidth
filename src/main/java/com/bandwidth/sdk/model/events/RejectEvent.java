package com.bandwidth.sdk.model.events;

import org.json.simple.JSONObject;

public class RejectEvent extends EventBase {

	public RejectEvent(final JSONObject json) {
		super(json);
	}

	public void execute(final Visitor visitor) {
		visitor.processEvent(this);
	}

    public String getTo() {
        return getPropertyAsString("to");
    }

    public String getFrom() {
        return getPropertyAsString("from");
    }

    public String getCallId() {
        return getPropertyAsString("callId");
    }

    public String getCallUri() {
        return getPropertyAsString("callUri");
    }

    public String getCause() {
        return getPropertyAsString("cause");
    }

}
