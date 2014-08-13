package com.bandwidth.sdk.calls;

import org.json.simple.JSONObject;

import java.util.Date;

/**
 * @author vpotapenko
 */
public class BandwidthEvent {

    private String id;
    private Date time;
    private Object data;

    public static BandwidthEvent from(JSONObject jsonObject) {
        BandwidthEvent event = new BandwidthEvent();
        event.id = (String) jsonObject.get("id");
        event.data = jsonObject.get("data");

        Long time = (Long) jsonObject.get("time");
        if (time != null) event.time = new Date(time);

        return event;
    }

    private BandwidthEvent() {
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
        return "BandwidthEvent{" +
                "id='" + id + '\'' +
                ", time=" + time +
                ", data=" + data +
                '}';
    }
}
