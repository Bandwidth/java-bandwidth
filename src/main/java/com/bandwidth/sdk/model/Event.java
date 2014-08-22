package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthRestClient;
import org.json.simple.JSONObject;

import java.util.Date;

/**
 * Information about event.
 *
 * @author vpotapenko
 */
public class Event extends BaseModelObject {

    public Event(BandwidthRestClient client, String parentUri, JSONObject jsonObject) {
        super(client, parentUri, jsonObject);
    }

    public Date getTime() {
        Long time = getPropertyAsLong("time");
        return new Date(time);
    }

    public Object getData() {
        return getProperty("data");
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
