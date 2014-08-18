package com.bandwidth.sdk.model;

import com.bandwidth.sdk.driver.MockRestDriver;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class BridgeTest {

    @Test
    public void shouldBeCreatedFromJson() throws ParseException {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"id\":\"id1\",\"createdTime\":\"2014-08-11T11:18:48Z\",\"state\":\"created\",\"bridgeAudio\":true,\"calls\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/userId\\/bridges\\/bridgId\\/calls\"}");

        Bridge bridge = new Bridge(null, null, jsonObject);
        assertThat(bridge.getId(), equalTo("id1"));
        assertThat(bridge.getCalls(), equalTo("https://api.catapult.inetwork.com/v1/users/userId/bridges/bridgId/calls"));
        assertThat(bridge.getState(), equalTo("created"));
    }

    @Test
    public void shouldUpdateAttributesOnServer() throws ParseException, IOException {
        MockRestDriver mockRestDriver = new MockRestDriver();

        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"id\":\"id1\",\"createdTime\":\"2014-08-11T11:18:48Z\",\"state\":\"created\",\"bridgeAudio\":true,\"calls\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/userId\\/bridges\\/bridgId\\/calls\"}");
        Bridge bridge = new Bridge(mockRestDriver, "parentUri", jsonObject);

        assertThat(bridge.getId(), equalTo("id1"));
        bridge.setBridgeAudio(true);
        bridge.commit();

        assertThat(mockRestDriver.requests.get(0).name, equalTo("post"));
        assertThat(mockRestDriver.requests.get(0).uri, equalTo("parentUri/id1"));
    }

    @Test
    public void shouldPostAudioOnServer() throws ParseException, IOException {
        MockRestDriver mockRestDriver = new MockRestDriver();

        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"id\":\"id1\",\"createdTime\":\"2014-08-11T11:18:48Z\",\"state\":\"created\",\"bridgeAudio\":true,\"calls\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/userId\\/bridges\\/bridgId\\/calls\"}");
        Bridge bridge = new Bridge(mockRestDriver, "parentUri", jsonObject);

        bridge.newBridgeAudioBuilder().fileUrl("some url").create();

        assertThat(mockRestDriver.requests.get(0).name, equalTo("post"));
        assertThat(mockRestDriver.requests.get(0).uri, equalTo("parentUri/id1/audio"));
        assertThat(mockRestDriver.requests.get(0).params.get("fileUrl").toString(), equalTo("some url"));
    }

    @Test
    public void shouldStopAudioOnServer() throws ParseException, IOException {
        MockRestDriver mockRestDriver = new MockRestDriver();

        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"id\":\"id1\",\"createdTime\":\"2014-08-11T11:18:48Z\",\"state\":\"created\",\"bridgeAudio\":true,\"calls\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/userId\\/bridges\\/bridgId\\/calls\"}");
        Bridge bridge = new Bridge(mockRestDriver, "parentUri", jsonObject);

        bridge.stopAudioFilePlaying();

        assertThat(mockRestDriver.requests.get(0).name, equalTo("post"));
        assertThat(mockRestDriver.requests.get(0).uri, equalTo("parentUri/id1/audio"));
        assertThat(mockRestDriver.requests.get(0).params.get("fileUrl").toString(), equalTo(""));
    }

    @Test
    public void shouldStopSentenceOnServer() throws ParseException, IOException {
        MockRestDriver mockRestDriver = new MockRestDriver();

        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"id\":\"id1\",\"createdTime\":\"2014-08-11T11:18:48Z\",\"state\":\"created\",\"bridgeAudio\":true,\"calls\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/userId\\/bridges\\/bridgId\\/calls\"}");
        Bridge bridge = new Bridge(mockRestDriver, "parentUri", jsonObject);

        bridge.stopSentence();

        assertThat(mockRestDriver.requests.get(0).name, equalTo("post"));
        assertThat(mockRestDriver.requests.get(0).uri, equalTo("parentUri/id1/audio"));
        assertThat(mockRestDriver.requests.get(0).params.get("sentence").toString(), equalTo(""));
    }

    @Test
    public void shouldGetBridgeCalls() throws ParseException, IOException {
        MockRestDriver mockRestDriver = new MockRestDriver();

        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"id\":\"id1\",\"createdTime\":\"2014-08-11T11:18:48Z\",\"state\":\"created\",\"bridgeAudio\":true,\"calls\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/userId\\/bridges\\/bridgId\\/calls\"}");
        Bridge bridge = new Bridge(mockRestDriver, "parentUri", jsonObject);

        mockRestDriver.arrayResult = (JSONArray) new JSONParser().parse("[{\"to\":\"+11111111111\",\"recordings\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls\\/recordings\",\"transcriptionEnabled\":false,\"direction\":\"in\",\"events\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls/events\",\"chargeableDuration\":360,\"state\":\"completed\",\"from\":\"+22222222222\",\"endTime\":\"2014-08-12T10:59:30Z\",\"id\":\"id1\",\"recordingEnabled\":false,\"startTime\":\"2014-08-12T10:54:29Z\",\"activeTime\":\"2014-08-12T10:54:29Z\",\"transcriptions\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls\\/transcriptions\"},{\"to\":\"+33333333333\",\"recordings\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls\\/recordings\",\"transcriptionEnabled\":false,\"direction\":\"out\",\"events\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls\\/events\",\"chargeableDuration\":360,\"state\":\"completed\",\"from\":\"+44444444444\",\"endTime\":\"2014-08-12T10:59:30Z\",\"id\":\"id2\",\"recordingEnabled\":false,\"startTime\":\"2014-08-12T10:54:29Z\",\"activeTime\":\"2014-08-12T10:54:29Z\",\"transcriptions\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls\\/transcriptions\"}]");
        List<Call> bridgeCalls = bridge.getBridgeCalls();

        assertThat(bridgeCalls.get(0).getFrom(), equalTo("+22222222222"));
        assertThat(mockRestDriver.requests.get(0).name, equalTo("getArray"));
        assertThat(mockRestDriver.requests.get(0).uri, equalTo("parentUri/id1/calls"));
    }

}