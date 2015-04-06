package com.bandwidth.sdk.model.events;

import com.bandwidth.sdk.model.events.EventBase;
import com.bandwidth.sdk.model.events.Visitor;
import org.json.simple.JSONObject;

public class SpeakEvent extends EventBase {

	public SpeakEvent(final JSONObject json) {
		super(json);
		// TODO Auto-generated constructor stub
	}

	public void execute(final Visitor visitor) {
		visitor.processEvent(this);
	}

}
