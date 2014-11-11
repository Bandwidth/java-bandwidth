package com.bandwidth.sdk.model.events;

import org.json.simple.JSONObject;

public class RecordingEvent extends EventBase {

//	public RecordingEvent(BandwidthRestClient client, String parentUri,
//			JSONObject jsonObject) {
//		super(client, parentUri, jsonObject);
//		// TODO Auto-generated constructor stub
//	}

	public RecordingEvent(JSONObject json) {
		super(json);
		// TODO Auto-generated constructor stub
	}
	
	public void execute(Visitor visitor) {
		visitor.processEvent(this);
	}

}
