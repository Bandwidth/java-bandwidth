package com.bandwidth.sdk.bridges;

import com.bandwidth.sdk.BandwidthRestClient;
import com.bandwidth.sdk.driver.MockRestDriver;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class BandwidthBridgeTest {

    @Test
    public void shouldBeCreatedFromJson() throws ParseException {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"id\":\"id1\",\"createdTime\":\"2014-08-11T11:18:48Z\",\"state\":\"created\",\"bridgeAudio\":true,\"calls\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/userId\\/bridges\\/bridgId\\/calls\"}");

        BandwidthBridge bridge = BandwidthBridge.from(null, jsonObject);
        assertThat(bridge.getId(), equalTo("id1"));
        assertThat(bridge.getCalls(), equalTo("https://api.catapult.inetwork.com/v1/users/userId/bridges/bridgId/calls"));
        assertThat(bridge.getState(), equalTo(BridgeState.created));
    }

    @Test
    public void shouldUpdateAttributesOnServer() throws ParseException, IOException {
        MockRestDriver mockRestDriver = new MockRestDriver();

        BandwidthRestClient client = new BandwidthRestClient(null, null, null);
        client.setRestDriver(mockRestDriver);

        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"id\":\"id1\",\"createdTime\":\"2014-08-11T11:18:48Z\",\"state\":\"created\",\"bridgeAudio\":true,\"calls\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/userId\\/bridges\\/bridgId\\/calls\"}");
        BandwidthBridge bridge = BandwidthBridge.from(client, jsonObject);

        assertThat(bridge.getId(), equalTo("id1"));
        bridge.setBridgeAudio(true);
        bridge.commit();

        assertThat(mockRestDriver.requests.get(0).name, equalTo("updateBridge"));
        assertThat(mockRestDriver.requests.get(0).params.get("bridgeAudio").toString(), equalTo("true"));
    }

}