package com.bandwidth.sdk.model.events;

/**
 * EventTypes.
 * 
 * @author smitchell
 */
public enum EventType {
	INCOMINGCALL("incomingcall"), 
	ANSWER("answer"), 
	SPEAK("speak"), 
	PLAYBACK("playback"), 
	GATHER("gather"), 
	HANGUP("hangup"), 
	DTMF("dtmf"), 
	REJECT("reject"), 
	RECORDING("recording"), 
	SMS("sms"), 
	TIMEOUT("timeout"), 
	UNKNOWN("unknown");

	private final String val;

	private EventType(String val) {
		this.val = val;
	}

	public String toString() {
		return val;
	}

	public static EventType getEnum(String type) {
		if (INCOMINGCALL.toString().equalsIgnoreCase(type))
			return INCOMINGCALL;
		else if (ANSWER.toString().equalsIgnoreCase(type))
			return ANSWER;
		else if (SPEAK.toString().equals(type))
			return SPEAK;
		else if (PLAYBACK.toString().equals(type))
			return PLAYBACK;
		else if (GATHER.toString().equalsIgnoreCase(type))
			return GATHER;
		else if (HANGUP.toString().equals(type))
			return HANGUP;
		else if (DTMF.toString().equals(type))
			return DTMF;
		else if (REJECT.toString().equals(type))
			return REJECT;
		else if (RECORDING.toString().equals(type))
			return RECORDING;
		else if (SMS.toString().equals(type))
			return SMS;
		else if (TIMEOUT.toString().equals(type))
			return TIMEOUT;
		else
			return UNKNOWN;
	}
}