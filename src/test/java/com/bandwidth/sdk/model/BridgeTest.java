package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.MockRestClient;
import com.bandwidth.sdk.RestResponse;
import com.bandwidth.sdk.TestsHelper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class BridgeTest {
    private MockRestClient mockRestClient;

    @Before
    public void setUp() {
        mockRestClient = TestsHelper.getClient();
    }


    @Test
    public void shouldBeCreatedFromJson() throws ParseException {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"id\":\"id1\",\"createdTime\":\"2014-08-11T11:18:48Z\",\"state\":\"created\",\"bridgeAudio\":true,\"calls\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/userId\\/bridges\\/bridgId\\/calls\"}");

        Bridge bridge = new Bridge(mockRestClient,jsonObject);
        assertThat(bridge.getId(), equalTo("id1"));
        assertThat(bridge.getCalls(), equalTo("https://api.catapult.inetwork.com/v1/users/userId/bridges/bridgId/calls"));
        assertThat(bridge.getState(), equalTo("created"));
    }

    @Test
    public void shouldUpdateAttributesOnServer() throws ParseException, IOException {

        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"id\":\"id1\",\"createdTime\":\"2014-08-11T11:18:48Z\",\"state\":\"created\",\"bridgeAudio\":true,\"calls\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/userId\\/bridges\\/bridgId\\/calls\"}");
        Bridge bridge = new Bridge(mockRestClient, jsonObject);

        assertThat(bridge.getId(), equalTo("id1"));
        bridge.setBridgeAudio(true);
        bridge.commit();

        assertThat(mockRestClient.requests.get(0).name, equalTo("post"));
        assertThat(mockRestClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/bridges/id1"));
    }

    @Test
    public void shouldPostAudioOnServer() throws ParseException, IOException {

        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"id\":\"id1\",\"createdTime\":\"2014-08-11T11:18:48Z\",\"state\":\"created\",\"bridgeAudio\":true,\"calls\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/userId\\/bridges\\/bridgId\\/calls\"}");
        Bridge bridge = new Bridge(mockRestClient, jsonObject);

        bridge.newBridgeAudioBuilder().fileUrl("some url").create();

        assertThat(mockRestClient.requests.get(0).name, equalTo("post"));
        assertThat(mockRestClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/bridges/id1/audio"));
        assertThat(mockRestClient.requests.get(0).params.get("fileUrl").toString(), equalTo("some url"));
    }

    @Test
    public void shouldStopAudioOnServer() throws ParseException, IOException {

        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"id\":\"id1\",\"createdTime\":\"2014-08-11T11:18:48Z\",\"state\":\"created\",\"bridgeAudio\":true,\"calls\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/userId\\/bridges\\/bridgId\\/calls\"}");
        Bridge bridge = new Bridge(mockRestClient, jsonObject);

        bridge.stopAudioFilePlaying();

        assertThat(mockRestClient.requests.get(0).name, equalTo("post"));
        assertThat(mockRestClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/bridges/id1/audio"));
        assertThat(mockRestClient.requests.get(0).params.get("fileUrl").toString(), equalTo(""));
    }

    @Test
    public void shouldStopSentenceOnServer() throws ParseException, IOException {

        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"id\":\"id1\",\"createdTime\":\"2014-08-11T11:18:48Z\",\"state\":\"created\",\"bridgeAudio\":true,\"calls\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/userId\\/bridges\\/bridgId\\/calls\"}");
        Bridge bridge = new Bridge(mockRestClient, jsonObject);

        bridge.stopSentence();

        assertThat(mockRestClient.requests.get(0).name, equalTo("post"));
        assertThat(mockRestClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/bridges/id1/audio"));
        assertThat(mockRestClient.requests.get(0).params.get("sentence").toString(), equalTo(""));
    }

    @Test
    public void shouldGetBridgeCalls() throws ParseException, IOException {
        MockRestClient mockRestClient = new MockRestClient();

        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"id\":\"id1\",\"createdTime\":\"2014-08-11T11:18:48Z\",\"state\":\"created\",\"bridgeAudio\":true,\"calls\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/userId\\/bridges\\/bridgId\\/calls\"}");
        Bridge bridge = new Bridge(mockRestClient, jsonObject);

        mockRestClient.arrayResult = (JSONArray) new JSONParser().parse("[{\"to\":\"+11111111111\",\"recordings\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls\\/recordings\",\"transcriptionEnabled\":false,\"direction\":\"in\",\"events\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls/events\",\"chargeableDuration\":360,\"state\":\"completed\",\"from\":\"+22222222222\",\"endTime\":\"2014-08-12T10:59:30Z\",\"id\":\"id1\",\"recordingEnabled\":false,\"startTime\":\"2014-08-12T10:54:29Z\",\"activeTime\":\"2014-08-12T10:54:29Z\",\"transcriptions\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls\\/transcriptions\"},{\"to\":\"+33333333333\",\"recordings\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls\\/recordings\",\"transcriptionEnabled\":false,\"direction\":\"out\",\"events\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls\\/events\",\"chargeableDuration\":360,\"state\":\"completed\",\"from\":\"+44444444444\",\"endTime\":\"2014-08-12T10:59:30Z\",\"id\":\"id2\",\"recordingEnabled\":false,\"startTime\":\"2014-08-12T10:54:29Z\",\"activeTime\":\"2014-08-12T10:54:29Z\",\"transcriptions\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/calls\\/transcriptions\"}]");
        List<Call> bridgeCalls = bridge.getBridgeCalls();

        assertThat(bridgeCalls.get(0).getFrom(), equalTo("+22222222222"));
        assertThat(mockRestClient.requests.get(0).name, equalTo("getArray"));
        assertThat(mockRestClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/bridges/id1/calls"));
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
         
        mockRestClient.setRestResponse(restResponse);
        
        List<Bridge> bridgeList = Bridge.getBridges(mockRestClient, 0, 5);
        assertThat(bridgeList.size(), equalTo(2));
        assertThat(bridgeList.get(0).getId(), equalTo("id1"));
        assertThat(bridgeList.get(0).getState(), equalTo("completed"));
        assertThat(bridgeList.get(0).isBridgeAudio(), equalTo(true));
        assertThat(bridgeList.get(0).getCalls(), equalTo("https://v1/users/userId/bridges/bridgeId/calls"));

      //  assertThat(mockRestClient.requests.get(0).name, equalTo("getArray"));
      //  assertThat(mockRestClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/bridges"));
    }
    
    @Test
    public void shouldGetBridgeById() throws ParseException, IOException {
        mockRestClient.result = (JSONObject) new JSONParser().parse("{\"id\":\"id1\",\"createdTime\":\"2014-08-11T11:18:48Z\",\"state\":\"created\",\"bridgeAudio\":true,\"calls\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/userId\\/bridges\\/bridgId\\/calls\"}");
        
        Bridge bridge = Bridge.getBridge(mockRestClient, "id1");
        assertThat(bridge.getId(), equalTo("id1"));
        assertThat(bridge.getCalls(), equalTo("https://api.catapult.inetwork.com/v1/users/userId/bridges/bridgId/calls"));
        assertThat(bridge.getState(), equalTo("created"));

     //   assertThat(mockRestClient.requests.get(0).name, equalTo("getObject"));
     //   assertThat(mockRestClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/bridges/id1"));
    }
    
    @Test
    public void shouldCreateBridge() throws ParseException, IOException {
        mockRestClient.result = (JSONObject) new JSONParser().parse
        		("{\"id\":\"id1\",\"createdTime\":\"2014-08-11T11:18:48Z\",\"state\":\"created\",\"bridgeAudio\":true,\"calls\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/userId\\/bridges\\/bridgId\\/calls\"}");

        RestResponse restResponse = new RestResponse();
		
        restResponse.setResponseText
			("{\"id\":\"id1\",\"createdTime\":\"2014-08-11T11:18:48Z\",\"state\":\"created\",\"bridgeAudio\":true,\"calls\":\"https:\\/\\/api.catapult.inetwork.com\\/v1\\/users\\/userId\\/bridges\\/bridgId\\/calls\"}");
        
        restResponse.setContentType("application/json");
        String mockUri = mockRestClient.getUserResourceUri(BandwidthConstants.BRIDGES_URI_PATH) + "/id1";
        restResponse.setLocation(mockUri);
        restResponse.setStatus(201);
         
        mockRestClient.setRestResponse(restResponse);
        
        
        Bridge bridge = Bridge.createBridge(mockRestClient, "id1", null);
        assertThat(bridge.getId(), equalTo("id1"));
        assertThat(bridge.getCalls(), equalTo("https://api.catapult.inetwork.com/v1/users/userId/bridges/bridgId/calls"));
        assertThat(bridge.getState(), equalTo("created"));

        //assertThat(mockRestClient.requests.get(0).name, equalTo("create"));
        //assertThat(mockRestClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/bridges"));
    }

    
    

}