package com.bandwidth.sdk.bridges;

import com.bandwidth.sdk.BandwidthRestClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author vpotapenko
 */
public class BandwidthBridges {

    private final BandwidthRestClient client;

    public BandwidthBridges(BandwidthRestClient client) {
        this.client = client;
    }

    public List<BandwidthBridge> getBridges() throws IOException {
        JSONArray array = client.getRestDriver().requestBridges();

        List<BandwidthBridge> bridges = new ArrayList<BandwidthBridge>();
        for (Object obj : array) {
            bridges.add(BandwidthBridge.from(client, (JSONObject) obj));
        }
        return bridges;
    }
}
