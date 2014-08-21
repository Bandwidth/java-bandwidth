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
 * Point for <code>/v1/users/{userId}/bridges</code>
 *
 * @author vpotapenko
 */
public class Bridges extends BaseModelObject {

    public Bridges(IRestDriver driver, String parentUri) {
        super(driver, parentUri, null);
    }

    /**
     * Gets list of bridges for a given user.
     *
     * @return list of bridges
     * @throws IOException
     */
    public List<Bridge> getBridges() throws IOException {
        JSONArray array = driver.getArray(getUri(), null);

        String bridgesUri = getUri();
        List<Bridge> bridges = new ArrayList<Bridge>();
        for (Object obj : array) {
            bridges.add(new Bridge(driver, bridgesUri, (JSONObject) obj));
        }
        return bridges;
    }

    /**
     * Gets information about a specific bridge.
     *
     * @param id bridge id
     * @return information about a specific bridge
     * @throws IOException
     */
    public Bridge getBridge(String id) throws IOException {
        String bridgesUri = getUri();
        String eventPath = StringUtils.join(new String[]{
                bridgesUri,
                id
        }, '/');
        JSONObject jsonObject = driver.getObject(eventPath);
        return new Bridge(driver, bridgesUri, jsonObject);
    }

    /**
     * Creates builder for creating new bridge.
     * <br>Example:<br>
     *     <code>Bridge bridge = bridges.newBridgeBuilder().addCallId("{call1}").addCallId("{call2}").create();</code>
     * @return
     */
    public NewBridgeBuilder newBridgeBuilder() {
        return new NewBridgeBuilder();
    }

    @Override
    protected String getUri() {
        return StringUtils.join(new String[]{
                parentUri,
                "bridges"
        }, '/');
    }

    private Bridge createBridge(Map<String, Object> params) throws IOException {
        String bridgesUri = getUri();
        JSONObject jsonObject = driver.create(bridgesUri, params);
        return new Bridge(driver, bridgesUri, jsonObject);
    }

    public class NewBridgeBuilder {

        private Boolean bridgeAudio;
        private List<String> callIds = new ArrayList<String>();

        public NewBridgeBuilder bridgeAudio(boolean bridgeAudio) {
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
