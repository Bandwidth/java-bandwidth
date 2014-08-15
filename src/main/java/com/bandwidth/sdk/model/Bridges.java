package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthRestClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author vpotapenko
 */
public class Bridges {

    private final BandwidthRestClient client;

    public Bridges(BandwidthRestClient client) {
        this.client = client;
    }

    public List<Bridge> getBridges() throws IOException {
        JSONArray array = client.requestBridges();

        List<Bridge> bridges = new ArrayList<Bridge>();
        for (Object obj : array) {
            bridges.add(Bridge.from(client, (JSONObject) obj));
        }
        return bridges;
    }

    public Bridge getBridgeById(String id) throws IOException {
        JSONObject jsonObject = client.requestBridgeById(id);
        return Bridge.from(client, jsonObject);
    }

    public NewBridgeBuilder newBridge() {
        return new NewBridgeBuilder();
    }

    private Bridge createBridge(Map<String, Object> params) throws IOException {
        JSONObject jsonObject = client.createBridge(params);
        return Bridge.from(client, jsonObject);
    }

    public class NewBridgeBuilder {

        private Boolean bridgeAudio;
        private List<String> callIds = new ArrayList<String>();

        public NewBridgeBuilder bridgeAudio(Boolean bridgeAudio) {
            this.bridgeAudio = bridgeAudio;
            return this;
        }

        public NewBridgeBuilder addCallId(String callId) {
            callIds.add(callId);
            return this;
        }

        public Bridge create() throws IOException {
            Map<String, Object> params = new HashMap<String, Object>();
            if (bridgeAudio != null) params.put("bridgeAudio", String.valueOf(bridgeAudio));
            if (!callIds.isEmpty()) params.put("callIds", callIds);

            return createBridge(params);
        }
    }
}
