package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.BandwidthRestClient;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Gather DTMF parameters and results.
 *
 * @author vpotapenko
 */
public class Gather extends BaseModelObject {

    public Gather(BandwidthRestClient client, JSONObject jsonObject) {
        super(client, jsonObject);
    }

    @Override
    protected String getUri() {
        return client.getUserResourceInstanceUri(BandwidthConstants.CALLS_URI_PATH + "/" +
            getCall() + "/" + BandwidthConstants.GATHER_URI_PATH, getId());
    }

    /**
     * Changes state to completed.
     *
     * @throws IOException
     */
    public void complete() throws IOException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("state", "completed");
        client.post(getUri(), params);

        JSONObject jsonObject = client.getObject(getUri());
        updateProperties(jsonObject);
    }

    public String getState() {
        return getPropertyAsString("state");
    }

    public String getReason() {
        return getPropertyAsString("reason");
    }

    public String getCall() {
        return getPropertyAsString("call");
    }

    public String getDigits() {
        return getPropertyAsString("digits");
    }

    public Date getCreatedTime() {
        return getPropertyAsDate("createdTime");
    }

    public Date getCompletedTime() {
        return getPropertyAsDate("completedTime");
    }

    @Override
    public String toString() {
        return "Gather{" +
                "completedTime=" + getCompletedTime() +
                ", createdTime=" + getCreatedTime() +
                ", digits='" + getDigits() + '\'' +
                ", call='" + getCall() + '\'' +
                ", reason='" + getReason() + '\'' +
                ", state='" + getState() + '\'' +
                ", id='" + getId() + '\'' +
                '}';
    }
}
