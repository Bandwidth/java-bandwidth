package com.bandwidth.sdk.model;

import org.json.simple.JSONObject;

import com.bandwidth.sdk.BandwidthRestClient;

public class SpeakEvent extends BaseEvent {

	public SpeakEvent(BandwidthRestClient client, String parentUri,
			JSONObject jsonObject) {
		super(client, parentUri, jsonObject);
		// TODO Auto-generated constructor stub
	}

	public SpeakEvent(JSONObject json) {
		super(json);
		// TODO Auto-generated constructor stub
	}

	public void execute(Visitor visitor) {
		visitor.processEvent(this);
	}

}
