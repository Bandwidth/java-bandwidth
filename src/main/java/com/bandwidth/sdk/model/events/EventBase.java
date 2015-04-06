package com.bandwidth.sdk.model.events;

import com.bandwidth.sdk.AppPlatformException;
import com.bandwidth.sdk.model.ModelBase;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.Date;

/**
 * Information about event.
 *
 * @author vpotapenko
 */
public class EventBase extends ModelBase implements Event {
	
	protected EventType eventType;
	/**
	 * This method creates an event from a json string. Given an event from the App Plotform 
	 * API, the whole body can be passed in and the appropriate Event subclass will be returned.
	 * @param in the json representation
	 * @return the event
	 * @throws AppPlatformException API Exception
	 */
    public static Event createEventFromString(final String in) throws AppPlatformException {

		JSONObject jsonObj = null;
		try {
		    jsonObj = (JSONObject) new JSONParser().parse(in);
		} catch (final org.json.simple.parser.ParseException e) {
		    throw new AppPlatformException(e);
		}
	
		final EventType eventType = EventType.getEnum((String) jsonObj
			.get("eventType"));
	
		Event event = null;
		switch (eventType) {
			case INCOMINGCALL:
			    event = new IncomingCallEvent(jsonObj);
			    break;
		
			case ANSWER:
			    event = new AnswerEvent(jsonObj);
			    break;
		
			case SPEAK:
			    event = new SpeakEvent(jsonObj);
			    break;
		
			case PLAYBACK:
			    event = new PlaybackEvent(jsonObj);
			    break;
		
			case GATHER:
			    event = new GatherEvent(jsonObj);
			    break;
		
			case HANGUP:
			    event = new HangupEvent(jsonObj);
			    break;
		
			case DTMF:
			    event = new DtmfEvent(jsonObj);
			    break;
		
			case REJECT:
			    event = new RejectEvent(jsonObj);
			    break;
		
			case RECORDING:
			    event = new RecordingEvent(jsonObj);
			    break;
		
			case SMS:
			    event = new SmsEvent(jsonObj);
			    break;
		
			case TIMEOUT:
			    event = new TimeoutEvent(jsonObj);
			    break;
		
			default:
			    event = new EventBase(jsonObj);
		}

	return event;
    }

    public void execute(final Visitor visitor) {
    	visitor.processEvent(this);
    }


    public EventBase(final JSONObject json) {
		updateProperties(json);
        eventType = EventType.getEnum((String) json.get("eventType"));
    }
    
    public Date getTime() {
        final Long time = getPropertyAsLong("time");
        return new Date(time);
    }

    public Object getData() {
        return getProperty("data");
    }
    
    public String getProperty(final String property) {
    	return getPropertyAsString(property);
    }

    public void setProperty(final String name, final String value) {
    	putProperty(name, value);
    }
    
    public EventType getEventType() {
    	return eventType;
    }

    
    @Override
    public String toString() {
        return "Event{" +
                "id='" + getId() + '\'' +
                ", time=" + getTime() +
                ", data=" + getData() +
                '}';
    }
}
