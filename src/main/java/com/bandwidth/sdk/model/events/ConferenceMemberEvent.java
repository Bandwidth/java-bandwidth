package com.bandwidth.sdk.model.events;

import org.json.simple.JSONObject;

public class ConferenceMemberEvent extends EventBase {

	public ConferenceMemberEvent(final JSONObject json) {
		super(json);
	}
	
	public void execute(final Visitor visitor) {
		visitor.processEvent(this);
	}

    public String getActiveMembers() {
        return getPropertyAsString("activeMembers");
    }

    public String getConferenceId() {
        return getPropertyAsString("conferenceId");
    }

    public String getCallId() {
        return getPropertyAsString("callId");
    }

    public String getMemberId() {
        return getPropertyAsString("memberId");
    }

    public String getMemberUri() {
        return getPropertyAsString("memberUri");
    }

    public String getState() {
        return getPropertyAsString("state");
    }

    public Boolean getHold() {
        return getPropertyAsBoolean("hold");
    }

    public Boolean getMute() {
        return getPropertyAsBoolean("mute");
    }
}
