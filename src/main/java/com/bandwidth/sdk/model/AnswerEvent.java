/**
 * 
 */
package com.bandwidth.sdk.model;

import org.json.simple.JSONObject;

import com.bandwidth.sdk.BandwidthRestClient;

/**
 * @author smitchell
 * 
 */
public class AnswerEvent extends BaseEvent {

	/**
	 * @param client
	 * @param parentUri
	 * @param jsonObject
	 */
	public AnswerEvent(BandwidthRestClient client, String parentUri,
			JSONObject jsonObject) {
		super(client, parentUri, jsonObject);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param json
	 */
	public AnswerEvent(JSONObject json) {
		super(json);
		// TODO Auto-generated constructor stub
	}

	public void execute(Visitor visitor) {
		visitor.processEvent(this);
	}

}
