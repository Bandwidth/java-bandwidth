package com.bandwidth.sdk.bridges;

import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.BandwidthRestClient;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author vpotapenko
 */
public class BandwidthBridge {

    private final BandwidthRestClient client;

    private String id;
    private BridgeState state;
    private String[] callIds;
    private String calls;
    private boolean bridgeAudio;
    private Date completedTime;
    private Date createdTime;
    private Date activatedTime;

    private BandwidthBridge(BandwidthRestClient client) {
        this.client = client;
    }

    public static BandwidthBridge from(BandwidthRestClient client, JSONObject jsonObject) {
        BandwidthBridge bridge = new BandwidthBridge(client);
        bridge.id = (String) jsonObject.get("id");
        bridge.state = BridgeState.from((String) jsonObject.get("state"));
        bridge.calls = (String) jsonObject.get("calls");
        bridge.bridgeAudio = jsonObject.get("bridgeAudio").equals("true");

        if (jsonObject.containsKey("callIds")) {
            JSONArray jsonArray = (JSONArray) jsonObject.get("callIds");
            bridge.callIds = new String[jsonArray.size()];
            for (int i = 0; i < bridge.callIds.length; i++) {
                bridge.callIds[i] = (String) jsonArray.get(i);
            }
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(BandwidthConstants.TRANSACTION_DATE_TIME_PATTERN);
        try {
            String time = (String) jsonObject.get("completedTime");
            if (StringUtils.isNotEmpty(time)) bridge.completedTime = dateFormat.parse(time);

            time = (String) jsonObject.get("createdTime");
            if (StringUtils.isNotEmpty(time)) bridge.createdTime = dateFormat.parse(time);

            time = (String) jsonObject.get("activatedTime");
            if (StringUtils.isNotEmpty(time)) bridge.activatedTime = dateFormat.parse(time);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return bridge;
    }

    public void setCallIds(String[] callIds) {
        this.callIds = callIds;
    }

    public void setBridgeAudio(boolean bridgeAudio) {
        this.bridgeAudio = bridgeAudio;
    }

    public void commit() throws IOException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("bridgeAudio", String.valueOf(bridgeAudio));
        params.put("callIds", callIds == null ? Collections.emptyList() : Arrays.asList(callIds));

        client.getRestDriver().updateBridge(id, params);
    }

    public String getId() {
        return id;
    }

    public BridgeState getState() {
        return state;
    }

    public String[] getCallIds() {
        return callIds;
    }

    public String getCalls() {
        return calls;
    }

    public boolean isBridgeAudio() {
        return bridgeAudio;
    }

    public Date getCompletedTime() {
        return completedTime;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public Date getActivatedTime() {
        return activatedTime;
    }

    @Override
    public String toString() {
        return "BandwidthBridge{" +
                "id='" + id + '\'' +
                ", state=" + state +
                ", callIds=" + Arrays.toString(callIds) +
                ", calls='" + calls + '\'' +
                ", bridgeAudio=" + bridgeAudio +
                ", completedTime=" + completedTime +
                ", createdTime=" + createdTime +
                ", activatedTime=" + activatedTime +
                '}';
    }
}
