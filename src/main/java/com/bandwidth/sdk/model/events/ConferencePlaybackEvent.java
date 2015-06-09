package com.bandwidth.sdk.model.events;

import org.json.simple.JSONObject;

public class ConferencePlaybackEvent extends EventBase {

	public ConferencePlaybackEvent(final JSONObject json) {
		super(json);
	}
	
	public void execute(final Visitor visitor) {
		visitor.processEvent(this);
	}

    public String getConferenceId() {
        return getPropertyAsString("conferenceId");
    }

    public String getConferenceUri() {
        return getPropertyAsString("conferenceUri");
    }

    public String getStatus() {
        return getPropertyAsString("status");
    }

}
