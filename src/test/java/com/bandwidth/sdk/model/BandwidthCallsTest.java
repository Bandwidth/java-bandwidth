package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthRestClient;
import com.bandwidth.sdk.driver.MockRestDriver;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class BandwidthCallsTest {

    private MockRestDriver mockRestDriver;
    private BandwidthCalls calls;

    @Before
    public void setUp() throws Exception {
        mockRestDriver = new MockRestDriver();

        BandwidthRestClient client = new BandwidthRestClient(null, null, null);
        client.setRestDriver(mockRestDriver);
        calls = new BandwidthCalls(client);
    }

    @Test
    public void shouldGetCallsList() throws Exception {
        mockRestDriver.arrayResult = (JSONArray) new JSONParser().parse("[{\"to\":\"+11111111111\",\"recordings\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls\\/recordings\",\"transcriptionEnabled\":false,\"direction\":\"in\",\"events\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls/events\",\"chargeableDuration\":360,\"state\":\"completed\",\"from\":\"+22222222222\",\"endTime\":\"2014-08-12T10:59:30Z\",\"id\":\"id1\",\"recordingEnabled\":false,\"startTime\":\"2014-08-12T10:54:29Z\",\"activeTime\":\"2014-08-12T10:54:29Z\",\"transcriptions\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls\\/transcriptions\"},{\"to\":\"+33333333333\",\"recordings\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls\\/recordings\",\"transcriptionEnabled\":false,\"direction\":\"out\",\"events\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls\\/events\",\"chargeableDuration\":360,\"state\":\"completed\",\"from\":\"+44444444444\",\"endTime\":\"2014-08-12T10:59:30Z\",\"id\":\"id2\",\"recordingEnabled\":false,\"startTime\":\"2014-08-12T10:54:29Z\",\"activeTime\":\"2014-08-12T10:54:29Z\",\"transcriptions\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls\\/transcriptions\"}]");

        List<BandwidthCall> callList = calls.getCalls().size(2).get();
        assertThat(callList.size(), equalTo(2));
        assertThat(callList.get(0).getId(), equalTo("id1"));
        assertThat(callList.get(0).getFrom(), equalTo("+22222222222"));
        assertThat(callList.get(1).getId(), equalTo("id2"));
        assertThat(callList.get(1).getFrom(), equalTo("+44444444444"));

        assertThat(mockRestDriver.requests.get(0).name, equalTo("requestCalls"));
        assertThat(mockRestDriver.requests.get(0).params.get("size").toString(), equalTo("2"));
    }

    @Test
    public void shouldCreateNewCall() throws Exception {
        mockRestDriver.result = (JSONObject) new JSONParser().parse("{\"to\":\"+11111111111\",\"recordings\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls\\/recordings\",\"transcriptionEnabled\":false,\"direction\":\"in\",\"events\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls/events\",\"chargeableDuration\":360,\"state\":\"completed\",\"from\":\"+22222222222\",\"endTime\":\"2014-08-12T10:59:30Z\",\"id\":\"id1\",\"recordingEnabled\":false,\"startTime\":\"2014-08-12T10:54:29Z\",\"activeTime\":\"2014-08-12T10:54:29Z\",\"transcriptions\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls\\/transcriptions\"}");

        BandwidthCall bandwidthCall = calls.newCall().from("from").to("to").bridgeId("bridgeId").callbackUrl("url").recordingEnabled(true).create();
        assertThat(bandwidthCall, notNullValue());

        assertThat(mockRestDriver.requests.get(0).name, equalTo("createCall"));
        assertThat(mockRestDriver.requests.get(0).params.get("from").toString(), equalTo("from"));
        assertThat(mockRestDriver.requests.get(0).params.get("to").toString(), equalTo("to"));
        assertThat(mockRestDriver.requests.get(0).params.get("bridgeId").toString(), equalTo("bridgeId"));
        assertThat(mockRestDriver.requests.get(0).params.get("callbackUrl").toString(), equalTo("url"));
        assertThat(mockRestDriver.requests.get(0).params.get("recordingEnabled").toString(), equalTo("true"));
    }

    @Test
    public void shouldGetCallById() throws Exception {
        mockRestDriver.result = (JSONObject) new JSONParser().parse("{\"to\":\"+11111111111\",\"recordings\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls\\/recordings\",\"transcriptionEnabled\":false,\"direction\":\"in\",\"events\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls/events\",\"chargeableDuration\":360,\"state\":\"completed\",\"from\":\"+22222222222\",\"endTime\":\"2014-08-12T10:59:30Z\",\"id\":\"id1\",\"recordingEnabled\":false,\"startTime\":\"2014-08-12T10:54:29Z\",\"activeTime\":\"2014-08-12T10:54:29Z\",\"transcriptions\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls\\/transcriptions\"}");

        BandwidthCall bandwidthCall = calls.getCallById("id1");
        assertThat(bandwidthCall, notNullValue());

        assertThat(mockRestDriver.requests.get(0).name, equalTo("requestCallById"));
    }
}