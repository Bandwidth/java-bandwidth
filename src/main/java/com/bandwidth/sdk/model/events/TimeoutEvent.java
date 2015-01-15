package com.bandwidth.sdk.model.events;

import org.json.simple.JSONObject;

public class TimeoutEvent extends EventBase {

	public TimeoutEvent(JSONObject json) {
		super(json);
		// TODO Auto-generated constructor stub
	}

	public void execute(Visitor visitor) {
		visitor.processEvent(this);
	}

}
