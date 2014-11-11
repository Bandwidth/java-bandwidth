package com.bandwidth.sdk.model.events;

import org.json.simple.JSONObject;

public class DtmfEvent extends EventBase {

//	public DtmfEvent(BandwidthRestClient client, String parentUri,
//			JSONObject jsonObject) {
//		super(client, parentUri, jsonObject);
//		// TODO Auto-generated constructor stub
//	}

	public DtmfEvent(JSONObject json) {
		super(json);
		// TODO Auto-generated constructor stub
	}
	
	public void execute(Visitor visitor) {
		visitor.processEvent(this);
	}
	
}
