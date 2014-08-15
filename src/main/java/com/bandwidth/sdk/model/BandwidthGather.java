package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.BandwidthRestClient;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author vpotapenko
 */
public class BandwidthGather {

    private final BandwidthRestClient client;
    private final String callId;

    private String id;
    private String state;
    private String reason;
    private String call;
    private String digits;

    private Date createdTime;
    private Date completedTime;

    public static BandwidthGather from(BandwidthRestClient client, String callId, JSONObject jsonObject) {
        BandwidthGather gather = new BandwidthGather(client, callId);

        updateProperties(jsonObject, gather);

        return gather;
    }

    private static void updateProperties(JSONObject jsonObject, BandwidthGather gather) {
        gather.id = (String) jsonObject.get("id");
        gather.state = (String) jsonObject.get("state");
        gather.reason = (String) jsonObject.get("reason");
        gather.call = (String) jsonObject.get("call");
        gather.digits = (String) jsonObject.get("digits");

        SimpleDateFormat dateFormat = new SimpleDateFormat(BandwidthConstants.TRANSACTION_DATE_TIME_PATTERN);
        try {
            String time = (String) jsonObject.get("createdTime");
            if (StringUtils.isNotEmpty(time)) gather.createdTime = dateFormat.parse(time);

            time = (String) jsonObject.get("completedTime");
            if (StringUtils.isNotEmpty(time)) gather.completedTime = dateFormat.parse(time);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private BandwidthGather(BandwidthRestClient client, String callId) {
        this.client = client;
        this.callId = callId;
    }

    public void complete() throws IOException{
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("state", "completed");
        client.getRestDriver().updateCallGather(callId, id, params);

        JSONObject jsonObject = client.getRestDriver().requestCallGatherById(callId, id);
        updateProperties(jsonObject, this);
    }

    public String getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public String getReason() {
        return reason;
    }

    public String getCall() {
        return call;
    }

    public String getDigits() {
        return digits;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public Date getCompletedTime() {
        return completedTime;
    }

    @Override
    public String toString() {
        return "BandwidthGather{" +
                "completedTime=" + completedTime +
                ", createdTime=" + createdTime +
                ", digits='" + digits + '\'' +
                ", call='" + call + '\'' +
                ", reason='" + reason + '\'' +
                ", state='" + state + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
