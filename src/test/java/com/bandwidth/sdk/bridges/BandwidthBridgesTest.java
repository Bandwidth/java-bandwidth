package com.bandwidth.sdk.bridges;

import com.bandwidth.sdk.BandwidthRestClient;
import com.bandwidth.sdk.driver.MockRestDriver;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class BandwidthBridgesTest {

    private MockRestDriver mockRestDriver;
    private BandwidthBridges bridges;

    @Before
    public void setUp() throws Exception {
        mockRestDriver = new MockRestDriver();

        BandwidthRestClient client = new BandwidthRestClient(null, null, null);
        client.setRestDriver(mockRestDriver);

        bridges = new BandwidthBridges(client);
    }

    @Test
    public void shouldGetBridgesList() throws ParseException, IOException {
        mockRestDriver.arrayResult = (JSONArray) new JSONParser().parse("[\n" +
                "  {\n" +
                "    \"id\": \"id1\",\n" +
                "    \"state\": \"completed\",\n" +
                "    \"bridgeAudio\": \"true\",\n" +
                "    \"calls\":\"https://v1/users/userId/bridges/bridgeId/calls\",\n" +
                "    \"createdTime\": \"2013-04-22T13:55:30Z\",\n" +
                "    \"activatedTime\": \"2013-04-22T13:55:30Z\",\n" +
                "    \"completedTime\": \"2013-04-22T13:56:30Z\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"id2\",\n" +
                "    \"state\": \"completed\",\n" +
                "    \"bridgeAudio\": \"true\",\n" +
                "    \"calls\":\"https://v1/users/userId/bridges/bridgeId/calls\",\n" +
                "    \"createdTime\": \"2013-04-22T13:58:30Z\",\n" +
                "    \"activatedTime\": \"2013-04-22T13:58:30Z\",\n" +
                "    \"completedTime\": \"2013-04-22T13:59:30Z\"\n" +
                "  }\n" +
                "]");

        List<BandwidthBridge> bridgeList = bridges.getBridges();
        assertThat(bridgeList.size(), equalTo(2));
        assertThat(bridgeList.get(0).getId(), equalTo("id1"));
        assertThat(bridgeList.get(0).getState(), equalTo(BridgeState.completed));
        assertThat(bridgeList.get(0).isBridgeAudio(), equalTo(true));
        assertThat(bridgeList.get(0).getCalls(), equalTo("https://v1/users/userId/bridges/bridgeId/calls"));

        assertThat(mockRestDriver.requests.get(0).name, equalTo("requestBridges"));
    }
}