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
	 * @param json the json representation
	 */
	public AnswerEvent(final JSONObject json) {
		super(json);
		// TODO Auto-generated constructor stub
	}

	public void execute(final Visitor visitor) {
		visitor.processEvent(this);
	}

}
