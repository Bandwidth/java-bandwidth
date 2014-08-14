package com.bandwidth.sdk.calls;

import com.bandwidth.sdk.BandwidthConstants;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author vpotapenko
 */
public class BandwidthRecording {

    private String id;
    private String media;
    private String call;
    private String state;

    private Date startTime;
    private Date endTime;

    public static BandwidthRecording from(JSONObject jsonObject) {
        BandwidthRecording recording = new BandwidthRecording();
        recording.id = (String) jsonObject.get("id");
        recording.media = (String) jsonObject.get("media");
        recording.call = (String) jsonObject.get("call");
        recording.state = (String) jsonObject.get("state");

        SimpleDateFormat dateFormat = new SimpleDateFormat(BandwidthConstants.TRANSACTION_DATE_TIME_PATTERN);
        try {
            String time = (String) jsonObject.get("startTime");
            if (StringUtils.isNotEmpty(time)) recording.startTime = dateFormat.parse(time);

            time = (String) jsonObject.get("endTime");
            if (StringUtils.isNotEmpty(time)) recording.endTime = dateFormat.parse(time);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return recording;
    }

    private BandwidthRecording() {
    }

    public String getId() {
        return id;
    }

    public String getMedia() {
        return media;
    }

    public String getCall() {
        return call;
    }

    public String getState() {
        return state;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "BandwidthRecording{" +
                "id='" + id + '\'' +
                ", media='" + media + '\'' +
                ", call='" + call + '\'' +
                ", state='" + state + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
