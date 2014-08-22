package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthRestClient;
import org.json.simple.JSONObject;

import java.util.Date;

/**
 * Information about recording
 *
 * @author vpotapenko
 */
public class Recording extends BaseModelObject {

    public Recording(BandwidthRestClient client, String parentUri, JSONObject jsonObject) {
        super(client, parentUri, jsonObject);
    }

    public String getMedia() {
        return getPropertyAsString("media");
    }

    public String getCall() {
        return getPropertyAsString("call");
    }

    public String getState() {
        return getPropertyAsString("state");
    }

    public Date getStartTime() {
        return getPropertyAsDate("startTime");
    }

    public Date getEndTime() {
        return getPropertyAsDate("endTime");
    }

    @Override
    public String toString() {
        return "Recording{" +
                "id='" + getId() + '\'' +
                ", media='" + getMedia() + '\'' +
                ", call='" + getCall() + '\'' +
                ", state='" + getState() + '\'' +
                ", startTime=" + getStartTime() +
                ", endTime=" + getEndTime() +
                '}';
    }
}
