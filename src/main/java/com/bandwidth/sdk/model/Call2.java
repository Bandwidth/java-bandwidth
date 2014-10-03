package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthConstants;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by sbarstow on 10/3/14.
 */
public class Call2 extends Resource {

    private String direction;


    public Call2(JSONObject jsonObject){
        super(jsonObject);
    }

    @Override
    protected void setUp(JSONObject jsonObject) {
        this.id = (String) jsonObject.get("id");
        this.direction = (String) jsonObject.get("direction");
    }


    @Override
    public Call2 get(String id) throws Exception {
        return new Call2(toJSONObject(client.get(String.format(BandwidthConstants.CALLS_URI_PATH, "%s", id), null)));
    }

    @Override
    public List<Call2> list(Map<String, Object> query) throws Exception {
        List<Call2> calls = new ArrayList<Call2>();
        for (Object obj : toJSONArray(client.get(BandwidthConstants.CALLS_URI_PATH, query))) {
            calls.add(new Call2((JSONObject) obj));
        }
        return calls;
    }

    @Override
    public String create(Map<String, Object> params) throws IOException {
        return client.post(BandwidthConstants.CALLS_URI_PATH, params).getResponseText();
    }

    public String getDirection() {
        return direction;
    }

//    public String getState() {
//        return getPropertyAsString("state");
//    }

//    public String getFrom() {
//        return getPropertyAsString("from");
//    }
//
//    public String getTo() {
//        return getPropertyAsString("to");
//    }
//
//    public String getCallbackUrl() {
//        return getPropertyAsString("callbackUrl");
//    }
//
//    public String getEvents() {
//        return getPropertyAsString("events");
//    }
//
//    public Date getStartTime() {
//        return getPropertyAsDate("startTime");
//    }
//
//    public Date getActiveTime() {
//        return getPropertyAsDate("activeTime");
//    }
//
//    public Date getEndTime() {
//        return getPropertyAsDate("endTime");
//    }
//
//    public Long getChargeableDuration() {
//        return getPropertyAsLong("chargeableDuration");
//    }
//
//    public boolean isRecordingEnabled() {
//        return getPropertyAsBoolean("recordingEnabled");
//    }
    //other methods like hangup, etc.
}
