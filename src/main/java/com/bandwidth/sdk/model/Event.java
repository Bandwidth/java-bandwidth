package com.bandwidth.sdk.model;

import org.json.simple.JSONObject;

import java.util.Date;

/**
 * @author vpotapenko
 */
public class Event {

    private String id;
    private Date time;
    private Object data;

    public static Event from(JSONObject jsonObject) {
        Event event = new Event();
        event.id = (String) jsonObject.get("id");
        event.data = jsonObject.get("data");

        Long time = (Long) jsonObject.get("time");
        if (time != null) event.time = new Date(time);

        return event;
    }

    private Event() {
    }

    public String getId() {
        return id;
    }

    public Date getTime() {
        return time;
    }

    public Object getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", time=" + time +
                ", data=" + data +
                '}';
    }
}
