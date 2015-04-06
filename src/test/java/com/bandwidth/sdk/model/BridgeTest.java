package com.bandwidth.sdk.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.MockClient;
import com.bandwidth.sdk.RestResponse;
import com.bandwidth.sdk.TestsHelper;

public class BridgeTest {
    private MockClient mockClient;

    @Before
    public void setUp() {
       // mockClient = TestsHelper.getClient();
        // todo fix this to come from the TestsHelper factory
        mockClient = new MockClient();

    }


    @Test
    public void shouldBeCreatedFromJson() throws ParseException {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"id\":\"id1\",\"createdTime\":\"2014-08-11T11:18:48Z\",\"state\":\"created\",\"bridgeAudio\":true,\"calls\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/userId\\/bridges\\/bridgId\\/calls\"}");

        Bridge bridge = new Bridge(mockClient,jsonObject);
        assertThat(bridge.getId(), equalTo("id1"));
        assertThat(bridge.getCalls(), equalTo("https://api.catapult.inetwork.com/v1/users/userId/bridges/bridgId/calls"));
        assertThat(bridge.getState(), equalTo("created"));
    }

    @Test
    public void shouldUpdateAttributesOnServer() throws ParseException, IOException {

        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"id\":\"id1\",\"createdTime\":\"2014-08-11T11:18:48Z\",\"state\":\"created\",\"bridgeAudio\":true,\"calls\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/userId\\/bridges\\/bridgId\\/calls\"}");
        Bridge bridge = new Bridge(mockClient, jsonObject);

        assertThat(bridge.getId(), equalTo("id1"));
        bridge.setBridgeAudio(true);
        bridge.commit();

        assertThat(mockClient.requests.get(0).name, equalTo("post"));
        assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/bridges/id1"));
    }

    @Test
    public void shouldPostAudioOnServer() throws ParseException, IOException {

        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"id\":\"id1\",\"createdTime\":\"2014-08-11T11:18:48Z\",\"state\":\"created\",\"bridgeAudio\":true,\"calls\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/userId\\/bridges\\/bridgId\\/calls\"}");
        Bridge bridge = new Bridge(mockClient, jsonObject);

        bridge.newBridgeAudioBuilder().fileUrl("some url").create();

        assertThat(mockClient.requests.get(0).name, equalTo("post"));
        assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/bridges/id1/audio"));
        assertThat(mockClient.requests.get(0).params.get("fileUrl").toString(), equalTo("some url"));
    }

    @Test
    public void shouldStopAudioOnServer() throws ParseException, IOException {

        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"id\":\"id1\",\"createdTime\":\"2014-08-11T11:18:48Z\",\"state\":\"created\",\"bridgeAudio\":true,\"calls\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/userId\\/bridges\\/bridgId\\/calls\"}");
        Bridge bridge = new Bridge(mockClient, jsonObject);

        bridge.stopAudioFilePlaying();

        assertThat(mockClient.requests.get(0).name, equalTo("post"));
        assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/bridges/id1/audio"));
        assertThat(mockClient.requests.get(0).params.get("fileUrl").toString(), equalTo(""));
    }

    @Test
    public void shouldStopSentenceOnServer() throws ParseException, IOException {

        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"id\":\"id1\",\"createdTime\":\"2014-08-11T11:18:48Z\",\"state\":\"created\",\"bridgeAudio\":true,\"calls\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/userId\\/bridges\\/bridgId\\/calls\"}");
        Bridge bridge = new Bridge(mockClient, jsonObject);

        bridge.stopSentence();

        assertThat(mockClient.requests.get(0).name, equalTo("post"));
        assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/bridges/id1/audio"));
        assertThat(mockClient.requests.get(0).params.get("sentence").toString(), equalTo(""));
    }

    @Test
    public void shouldGetBridgeCalls() throws ParseException, Exception {

        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"id\":\"id1\",\"createdTime\":\"2014-08-11T11:18:48Z\",\"state\":\"created\",\"bridgeAudio\":true,\"calls\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/userId\\/bridges\\/bridgId\\/calls\"}");
        Bridge bridge = new Bridge(mockClient, jsonObject);

        RestResponse response = new RestResponse();
        response.setResponseText("[{\"to\":\"+11111111111\",\"recordings\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls\\/recordings\",\"transcriptionEnabled\":false,\"direction\":\"in\",\"events\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls/events\",\"chargeableDuration\":360,\"state\":\"completed\",\"from\":\"+22222222222\",\"endTime\":\"2014-08-12T10:59:30Z\",\"id\":\"id1\",\"recordingEnabled\":false,\"startTime\":\"2014-08-12T10:54:29Z\",\"activeTime\":\"2014-08-12T10:54:29Z\",\"transcriptions\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls\\/transcriptions\"},{\"to\":\"+33333333333\",\"recordings\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls\\/recordings\",\"transcriptionEnabled\":false,\"direction\":\"out\",\"events\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls\\/events\",\"chargeableDuration\":360,\"state\":\"completed\",\"from\":\"+44444444444\",\"endTime\":\"2014-08-12T10:59:30Z\",\"id\":\"id2\",\"recordingEnabled\":false,\"startTime\":\"2014-08-12T10:54:29Z\",\"activeTime\":\"2014-08-12T10:54:29Z\",\"transcriptions\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls\\/transcriptions\"}]");
        mockClient.setRestResponse(response);
        
        List<Call> bridgeCalls = bridge.getBridgeCalls();

        assertThat(bridgeCalls.get(0).getFrom(), equalTo("+22222222222"));
        assertThat(mockClient.requests.get(0).name, equalTo("get"));
        assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/bridges/id1/calls"));
    }
    
    @Test
    public void shouldGetBridgesList() throws ParseException, IOException {
        
        
        RestResponse restResponse = new RestResponse();
		
        restResponse.setResponseText("[\n" +
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
        restResponse.setContentType("application/json");
        restResponse.setStatus(201);
         
        mockClient.setRestResponse(restResponse);
        
        List<Bridge> bridgeList = Bridge.list(mockClient, 0, 5);
        assertThat(bridgeList.size(), equalTo(2));
        assertThat(bridgeList.get(0).getId(), equalTo("id1"));
        assertThat(bridgeList.get(0).getState(), equalTo("completed"));
        assertThat(bridgeList.get(0).isBridgeAudio(), equalTo(true));
        assertThat(bridgeList.get(0).getCalls(), equalTo("https://v1/users/userId/bridges/bridgeId/calls"));

        assertThat(mockClient.requests.get(0).name, equalTo("get"));
        assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/bridges"));
    }
    
    @Test
    public void shouldGetBridgeById() throws ParseException, Exception {
        
        RestResponse response = new RestResponse();
        response.setResponseText ("{\"id\":\"id1\",\"createdTime\":\"2014-08-11T11:18:48Z\",\"state\":\"created\",\"bridgeAudio\":true,\"calls\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/userId\\/bridges\\/bridgId\\/calls\"}");
        mockClient.setRestResponse(response);
        
        Bridge bridge = Bridge.get(mockClient, "id1");
        assertThat(bridge.getId(), equalTo("id1"));
        assertThat(bridge.getCalls(), equalTo("https://api.catapult.inetwork.com/v1/users/userId/bridges/bridgId/calls"));
        assertThat(bridge.getState(), equalTo("created"));

        assertThat(mockClient.requests.get(0).name, equalTo("get"));
        assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/bridges/id1"));
    }
    
    @Test
    public void shouldCreateBridge() throws ParseException, Exception {
        mockClient.result = (JSONObject) new JSONParser().parse
        		("{\"id\":\"id1\",\"createdTime\":\"2014-08-11T11:18:48Z\",\"state\":\"created\",\"bridgeAudio\":true,\"calls\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/userId\\/bridges\\/bridgId\\/calls\"}");

        RestResponse restResponse = new RestResponse();
		
        restResponse.setResponseText
			("{\"id\":\"id1\",\"createdTime\":\"2014-08-11T11:18:48Z\",\"state\":\"created\",\"bridgeAudio\":true,\"calls\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/userId\\/bridges\\/bridgId\\/calls\"}");
        
        restResponse.setContentType("application/json");
        String mockUri = mockClient.getUserResourceUri(BandwidthConstants.BRIDGES_URI_PATH) + "/id1";
        restResponse.setLocation(mockUri);
        restResponse.setStatus(201);
         
        mockClient.setRestResponse(restResponse);
        
        
        Bridge bridge = Bridge.create(mockClient, "id1", null);
        assertThat(bridge.getId(), equalTo("id1"));
        assertThat(bridge.getCalls(), equalTo("https://api.catapult.inetwork.com/v1/users/userId/bridges/bridgId/calls"));
        assertThat(bridge.getState(), equalTo("created"));

        //assertThat(mockClient.requests.get(0).name, equalTo("create"));
        //assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/bridges"));
    }

    
    

}