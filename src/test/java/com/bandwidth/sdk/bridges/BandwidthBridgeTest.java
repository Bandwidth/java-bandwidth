package com.bandwidth.sdk.bridges;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class BandwidthBridgeTest {

    @Test
    public void shouldBeCreatedFromJson() throws ParseException {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"id\":\"id1\",\"createdTime\":\"2014-08-11T11:18:48Z\",\"state\":\"created\",\"bridgeAudio\":true,\"calls\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/userId\\/bridges\\/bridgId\\/calls\"}");

        BandwidthBridge bridge = BandwidthBridge.from(null, jsonObject);
        assertThat(bridge.getId(), equalTo("id1"));
        assertThat(bridge.getCalls(), equalTo("https://api.catapult.inetwork.com/v1/users/userId/bridges/bridgId/calls"));
        assertThat(bridge.getState(), equalTo(BridgeState.created));
    }

}