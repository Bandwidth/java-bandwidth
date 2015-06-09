package com.bandwidth.sdk.model.events;

import org.json.simple.JSONObject;

public class IncomingCallEvent extends EventBase {

	public IncomingCallEvent(final JSONObject json) {
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

    public String getCallState() {
        return getPropertyAsString("callState");
    }

    public String getApplicationId() {
        return getPropertyAsString("applicationId");
    }

}
