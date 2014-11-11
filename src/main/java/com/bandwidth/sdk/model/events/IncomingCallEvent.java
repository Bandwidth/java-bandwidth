package com.bandwidth.sdk.model.events;

import org.json.simple.JSONObject;

public class IncomingCallEvent extends EventBase {

//	public IncomingCallEvent(BandwidthRestClient client, String parentUri,
//			JSONObject jsonObject) {
//		super(client, parentUri, jsonObject);
//		// TODO Auto-generated constructor stub
//	}

	public IncomingCallEvent(JSONObject json) {
		super(json);
		// TODO Auto-generated constructor stub
	}

	public void execute(Visitor visitor) {
		visitor.processEvent(this);
	}

}
