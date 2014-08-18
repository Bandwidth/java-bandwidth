package com.bandwidth.sdk.model;

import com.bandwidth.sdk.driver.IRestDriver;
import org.apache.commons.lang3.StringUtils;
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
public class Bridges extends BaseModelObject {

    public Bridges(IRestDriver driver, String parentUri) {
        super(driver, parentUri, null);
    }

    public List<Bridge> getBridges() throws IOException {
        JSONArray array = driver.getArray(getUri(), null);

        String bridgesUri = getUri();
        List<Bridge> bridges = new ArrayList<Bridge>();
        for (Object obj : array) {
            bridges.add(new Bridge(driver, bridgesUri, (JSONObject) obj));
        }
        return bridges;
    }

    @Override
    public String getUri() {
        return StringUtils.join(new String[]{
                parentUri,
                "bridges"
        }, '/');
    }

    public Bridge getBridgeById(String id) throws IOException {
        String bridgesUri = getUri();
        String eventPath = StringUtils.join(new String[]{
                bridgesUri,
                id
        }, '/');
        JSONObject jsonObject = driver.getObject(eventPath);
        return new Bridge(driver, bridgesUri, jsonObject);
    }

    public NewBridgeBuilder newBridgeBuilder() {
        return new NewBridgeBuilder();
    }

    private Bridge createBridge(Map<String, Object> params) throws IOException {
        String bridgesUri = getUri();
        JSONObject jsonObject = driver.create(bridgesUri, params);
        return new Bridge(driver, bridgesUri, jsonObject);
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
