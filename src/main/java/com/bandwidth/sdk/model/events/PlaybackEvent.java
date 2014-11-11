package com.bandwidth.sdk.model.events;

import org.json.simple.JSONObject;

public class PlaybackEvent extends EventBase {

//	public PlaybackEvent(BandwidthRestClient client, String parentUri,
//			JSONObject jsonObject) {
//		super(client, parentUri, jsonObject);
//		// TODO Auto-generated constructor stub
//	}

	public PlaybackEvent(JSONObject json) {
		super(json);
		// TODO Auto-generated constructor stub
	}

	public void execute(Visitor visitor) {
		visitor.processEvent(this);
	}

}
