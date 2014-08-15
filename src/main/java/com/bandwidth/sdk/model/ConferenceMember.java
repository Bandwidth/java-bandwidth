package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.BandwidthRestClient;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author vpotapenko
 */
public class ConferenceMember {

    private final BandwidthRestClient client;
    private final String conferenceId;

    private String id;
    private String call;
    private String state;

    private boolean hold;
    private boolean mute;
    private boolean joinTone;
    private boolean leavingTone;

    private Date addedTime;

    private ConferenceMember(BandwidthRestClient client, String conferenceId) {
        this.client = client;
        this.conferenceId = conferenceId;
    }

    public static ConferenceMember from(BandwidthRestClient client, String conferenceId, JSONObject jsonObject) {
        ConferenceMember member = new ConferenceMember(client, conferenceId);

        member.id = (String) jsonObject.get("id");
        member.call = (String) jsonObject.get("call");
        member.state = (String) jsonObject.get("state");
        member.hold = (Boolean) jsonObject.get("hold");
        member.mute = (Boolean) jsonObject.get("mute");
        member.joinTone = (Boolean) jsonObject.get("joinTone");
        member.leavingTone = (Boolean) jsonObject.get("leavingTone");

        SimpleDateFormat dateFormat = new SimpleDateFormat(BandwidthConstants.TRANSACTION_DATE_TIME_PATTERN);
        try {
            String time = (String) jsonObject.get("addedTime");
            if (StringUtils.isNotEmpty(time)) member.addedTime = dateFormat.parse(time);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return member;
    }

    public String getId() {
        return id;
    }

    public String getCall() {
        return call;
    }

    public String getState() {
        return state;
    }

    public boolean isHold() {
        return hold;
    }

    public boolean isMute() {
        return mute;
    }

    public boolean isJoinTone() {
        return joinTone;
    }

    public boolean isLeavingTone() {
        return leavingTone;
    }

    public Date getAddedTime() {
        return addedTime;
    }

    @Override
    public String toString() {
        return "ConferenceMember{" +
                "id='" + id + '\'' +
                ", call='" + call + '\'' +
                ", state='" + state + '\'' +
                ", hold=" + hold +
                ", mute=" + mute +
                ", joinTone=" + joinTone +
                ", leavingTone=" + leavingTone +
                ", addedTime=" + addedTime +
                '}';
    }
}
