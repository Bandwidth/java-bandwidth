package com.bandwidth.sdk.model.events;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.bandwidth.sdk.AppPlatformException;
import com.bandwidth.sdk.model.ModelBase;

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

            case TRANSCRIPTION:
                event = new TranscriptionEvent(jsonObj);
                break;

            case SMS:
                event = new SmsEvent(jsonObj);
                break;

            case TIMEOUT:
                event = new TimeoutEvent(jsonObj);
                break;

            case CONFERENCE:
                event = new ConferenceEvent(jsonObj);
                break;

            case CONFERENCE_MEMBER:
                event = new ConferenceMemberEvent(jsonObj);
                break;

            case CONFERENCE_PLAYBACK:
                event = new ConferencePlaybackEvent(jsonObj);
                break;

            case CONFERENCE_SPEAK:
                event = new ConferenceSpeakEvent(jsonObj);
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

    public String getId() {
        return getPropertyAsString("id");
    }

    public String getTime() {
        return getProperty("time");
    }

    public EventType getEventType() {
        return eventType;
    }

    public String getTag() {
        return getPropertyAsString("tag");
    }

    public String getProperty(final String property) {
        return getPropertyAsString(property);
    }

    public void setProperty(final String name, final String value) {
        putProperty(name, value);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Event");
        sb.append(properties);
        return sb.toString();
    }
}
