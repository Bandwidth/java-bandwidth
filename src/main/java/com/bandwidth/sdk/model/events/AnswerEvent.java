/**
 * 
 */
package com.bandwidth.sdk.model.events;

import org.json.simple.JSONObject;

/**
 * @author smitchell
 * 
 */
public class AnswerEvent extends EventBase {

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
