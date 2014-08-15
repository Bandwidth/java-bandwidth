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
public class Conference {

    private final BandwidthRestClient client;

    private String id;
    private String from;
    private String callbackUrl;
    private String fallbackUrl;

    private ConferenceState state;

    private Long activeMembers;
    private Long callbackTimeout;

    private Date completedTime;
    private Date createdTime;

    private Conference(BandwidthRestClient client) {
        this.client = client;
    }

    public static Conference from(BandwidthRestClient client, JSONObject jsonObject) {
        Conference conference = new Conference(client);
        updateProperties(jsonObject, conference);

        return conference;
    }

    private static void updateProperties(JSONObject jsonObject, Conference conference) {
        conference.id = (String) jsonObject.get("id");
        conference.from = (String) jsonObject.get("from");
        conference.callbackUrl = (String) jsonObject.get("callbackUrl");
        conference.fallbackUrl = (String) jsonObject.get("fallbackUrl");

        conference.activeMembers = (Long) jsonObject.get("activeMembers");
        conference.callbackTimeout = (Long) jsonObject.get("callbackTimeout");

        conference.state = ConferenceState.valueOf((String) jsonObject.get("state"));

        SimpleDateFormat dateFormat = new SimpleDateFormat(BandwidthConstants.TRANSACTION_DATE_TIME_PATTERN);
        try {
            String time = (String) jsonObject.get("completedTime");
            if (StringUtils.isNotEmpty(time)) conference.completedTime = dateFormat.parse(time);

            time = (String) jsonObject.get("createdTime");
            if (StringUtils.isNotEmpty(time)) conference.createdTime = dateFormat.parse(time);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public String getId() {
        return id;
    }

    public String getFrom() {
        return from;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public String getFallbackUrl() {
        return fallbackUrl;
    }

    public ConferenceState getState() {
        return state;
    }

    public Long getActiveMembers() {
        return activeMembers;
    }

    public Long getCallbackTimeout() {
        return callbackTimeout;
    }

    public Date getCompletedTime() {
        return completedTime;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void complete() throws IOException {
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("state", ConferenceState.completed.name());
        client.updateConference(id, params);

        JSONObject jsonObject = client.requestConferenceById(id);
        updateProperties(jsonObject, this);
    }

    public void mute() throws IOException {
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("mute", String.valueOf(true));
        client.updateConference(id, params);

        JSONObject jsonObject = client.requestConferenceById(id);
        updateProperties(jsonObject, this);
    }

    @Override
    public String toString() {
        return "Conference{" +
                "id='" + id + '\'' +
                ", from='" + from + '\'' +
                ", callbackUrl='" + callbackUrl + '\'' +
                ", fallbackUrl='" + fallbackUrl + '\'' +
                ", state=" + state +
                ", activeMembers=" + activeMembers +
                ", callbackTimeout=" + callbackTimeout +
                ", completedTime=" + completedTime +
                ", createdTime=" + createdTime +
                '}';
    }
}
