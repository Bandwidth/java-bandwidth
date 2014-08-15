package com.bandwidth.sdk.conferences;

import com.bandwidth.sdk.BandwidthConstants;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author vpotapenko
 */
public class BandwidthConference {

    private String id;
    private String from;
    private String callbackUrl;
    private String fallbackUrl;

    private State state;

    private Long activeMembers;
    private Long callbackTimeout;

    private Date completedTime;
    private Date createdTime;

    private BandwidthConference() {
    }

    public static BandwidthConference from(JSONObject jsonObject) {
        BandwidthConference conference = new BandwidthConference();

        conference.id = (String) jsonObject.get("id");
        conference.from = (String) jsonObject.get("from");
        conference.callbackUrl = (String) jsonObject.get("callbackUrl");
        conference.fallbackUrl = (String) jsonObject.get("fallbackUrl");

        conference.activeMembers = (Long) jsonObject.get("activeMembers");
        conference.callbackTimeout = (Long) jsonObject.get("callbackTimeout");

        conference.state = State.valueOf((String) jsonObject.get("state"));

        SimpleDateFormat dateFormat = new SimpleDateFormat(BandwidthConstants.TRANSACTION_DATE_TIME_PATTERN);
        try {
            String time = (String) jsonObject.get("completedTime");
            if (StringUtils.isNotEmpty(time)) conference.completedTime = dateFormat.parse(time);

            time = (String) jsonObject.get("createdTime");
            if (StringUtils.isNotEmpty(time)) conference.createdTime = dateFormat.parse(time);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return conference;
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

    public State getState() {
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

    @Override
    public String toString() {
        return "BandwidthConference{" +
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
