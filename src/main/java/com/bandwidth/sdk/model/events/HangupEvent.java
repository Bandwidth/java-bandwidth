package com.bandwidth.sdk.model.events;

import org.json.simple.JSONObject;

public class HangupEvent extends EventBase{

	public HangupEvent(JSONObject json) {
		super(json);
		// TODO Auto-generated constructor stub
	}

	public void execute(Visitor visitor) {
		visitor.processEvent(this);
	}

}
