package com.bandwidth.sdk.calls;

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
import java.util.Objects;

/**
 * @author vpotapenko
 */
public class BandwidthCall {

    private final BandwidthRestClient client;

    private String id;

    private Direction direction;
    private State state;

    private String from;
    private String to;
    private String callbackUrl;
    private String events;

    private Date startTime;
    private Date activeTime;
    private Date endTime;

    private Long chargeableDuration;
    private boolean recordingEnabled;

    public static BandwidthCall from(BandwidthRestClient client, JSONObject jsonObject) {
        BandwidthCall call = new BandwidthCall(client);
        updateProperties(jsonObject, call);
        return call;
    }

    private static void updateProperties(JSONObject jsonObject, BandwidthCall call) {
        call.id = (String) jsonObject.get("id");
        call.direction = Direction.valueOf((String) jsonObject.get("direction"));
        call.state = State.byName((String) jsonObject.get("state"));
        call.from = (String) jsonObject.get("from");
        call.to = (String) jsonObject.get("to");
        call.callbackUrl = (String) jsonObject.get("callbackUrl");
        call.events = (String) jsonObject.get("events");
        call.chargeableDuration = (Long) jsonObject.get("chargeableDuration");
        call.recordingEnabled = Objects.equals(jsonObject.get("recordingEnabled"), Boolean.TRUE);

        SimpleDateFormat dateFormat = new SimpleDateFormat(BandwidthConstants.TRANSACTION_DATE_TIME_PATTERN);
        try {
            String time = (String) jsonObject.get("startTime");
            if (StringUtils.isNotEmpty(time)) call.startTime = dateFormat.parse(time);

            time = (String) jsonObject.get("activeTime");
            if (StringUtils.isNotEmpty(time)) call.activeTime = dateFormat.parse(time);

            time = (String) jsonObject.get("endTime");
            if (StringUtils.isNotEmpty(time)) call.endTime = dateFormat.parse(time);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private BandwidthCall(BandwidthRestClient client) {
        this.client = client;
    }

    public String getId() {
        return id;
    }

    public Direction getDirection() {
        return direction;
    }

    public State getState() {
        return state;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public String getEvents() {
        return events;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getActiveTime() {
        return activeTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Long getChargeableDuration() {
        return chargeableDuration;
    }

    public boolean isRecordingEnabled() {
        return recordingEnabled;
    }

    public void hangUp() throws IOException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("state", State.completed.name());

        client.getRestDriver().updateCall(id, params);

        JSONObject jsonObject = client.getRestDriver().requestCallById(id);
        updateProperties(jsonObject, this);
    }

    @Override
    public String toString() {
        return "BandwidthCall{" +
                "id='" + id + '\'' +
                ", direction=" + direction +
                ", state=" + state +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", callbackUrl='" + callbackUrl + '\'' +
                ", events='" + events + '\'' +
                ", startTime=" + startTime +
                ", activeTime=" + activeTime +
                ", endTime=" + endTime +
                ", chargeableDuration=" + chargeableDuration +
                ", recordingEnabled=" + recordingEnabled +
                '}';
    }
}
