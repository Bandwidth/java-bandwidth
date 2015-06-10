package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.RestResponse;
import com.bandwidth.sdk.TestsHelper;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ConferenceTest extends BaseModelTest{

    @Test
    public void shouldBeCreatedFromJson() throws Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\n" +
                "    \"activeMembers\": 0,\n" +
                "    \"createdTime\": \"2013-07-12T15:22:47Z\",\n" +
                "    \"from\": \"+19703255647\",\n" +
                "    \"id\": \"{conferenceId}\",\n" +
                "    \"state\": \"created\"\n" +
                "}");
        Conference conference = new Conference(mockClient, jsonObject);
        assertThat(conference.getId(), equalTo("{conferenceId}"));
        assertThat(conference.getState(), equalTo("created"));
        assertThat(conference.getFrom(), equalTo("+19703255647"));
    }

    @Test
    public void shouldCompleteConference() throws Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\n" +
                "    \"activeMembers\": 0,\n" +
                "    \"createdTime\": \"2013-07-12T15:22:47Z\",\n" +
                "    \"from\": \"+19703255647\",\n" +
                "    \"id\": \"{conferenceId}\",\n" +
                "    \"state\": \"created\"\n" +
                "}");
        mockClient.result = jsonObject;


        RestResponse response = new RestResponse();
        response.setResponseText(jsonObject.toString());
        mockClient.setRestResponse(response);


        Conference conference = new Conference(mockClient, jsonObject);
        conference.complete();

        assertThat(mockClient.requests.get(0).name, equalTo("post"));
        assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/conferences/{conferenceId}"));
                assertThat(mockClient.requests.get(0).params.get("state").toString(), equalTo("completed"));
    }

    @Test
    public void shouldMuteConference() throws Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\n" +
                "    \"activeMembers\": 0,\n" +
                "    \"createdTime\": \"2013-07-12T15:22:47Z\",\n" +
                "    \"from\": \"+19703255647\",\n" +
                "    \"id\": \"{conferenceId}\",\n" +
                "    \"state\": \"created\"\n" +
                "}");
        mockClient.result = jsonObject;

        RestResponse response = new RestResponse();
        response.setResponseText(jsonObject.toString());
        mockClient.setRestResponse(response);


        Conference conference = new Conference(mockClient, jsonObject);
        conference.mute();

        assertThat(mockClient.requests.get(0).name, equalTo("post"));
        assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/conferences/{conferenceId}"));
        assertThat(mockClient.requests.get(0).params.get("mute").toString(), equalTo("true"));
    }

    @Test
    public void shouldCreateAudio() throws Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\n" +
                "    \"activeMembers\": 0,\n" +
                "    \"createdTime\": \"2013-07-12T15:22:47Z\",\n" +
                "    \"from\": \"+19703255647\",\n" +
                "    \"id\": \"{conferenceId}\",\n" +
                "    \"state\": \"created\"\n" +
                "}");
        mockClient.result = jsonObject;

        RestResponse response = new RestResponse();
        response.setResponseText(jsonObject.toString());
        mockClient.setRestResponse(response);


        Conference conference = new Conference(mockClient, jsonObject);

        conference.conferenceAudioBuilder().sentence("Hello").locale(SentenceLocale.German).create();
        assertThat(mockClient.requests.get(0).name, equalTo("post"));
        assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/conferences/{conferenceId}/audio"));
        assertThat(mockClient.requests.get(0).params.get("sentence").toString(), equalTo("Hello"));
        assertThat(mockClient.requests.get(0).params.get("locale").toString(), equalTo("de_DE"));
    }

    @Test
    public void shouldGetMembers() throws Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\n" +
                "    \"activeMembers\": 0,\n" +
                "    \"createdTime\": \"2013-07-12T15:22:47Z\",\n" +
                "    \"from\": \"+19703255647\",\n" +
                "    \"id\": \"{conferenceId}\",\n" +
                "    \"state\": \"created\"\n" +
                "}");
        mockClient.arrayResult = (org.json.simple.JSONArray) new JSONParser().parse("[\n" +
                "  {\n" +
                "      \"addedTime\": \"2013-07-12T15:54:47Z\",\n" +
                "      \"hold\": false,\n" +
                "      \"id\": \"{memberId1}\",\n" +
                "      \"mute\": false,\n" +
                "      \"state\": \"active\",\n" +
                "      \"joinTone\": false,\n" +
                "      \"leavingTone\": false,\n" +
                "      \"call\": \"https://localhost:8444/v1/users/{userId}/calls/{callId1}\"\n" +
                "  },\n" +
                "  {\n" +
                "      \"addedTime\": \"2013-07-12T15:55:12Z\",\n" +
                "      \"hold\": false,\n" +
                "      \"id\": \"{memberId2}\",\n" +
                "      \"mute\": false,\n" +
                "      \"state\": \"active\",\n" +
                "      \"joinTone\": false,\n" +
                "      \"leavingTone\": false,\n" +
                "      \"call\": \"https://localhost:8444/v1/users/{userId}/calls/{callId2}\"\n" +
                "  },\n" +
                "  {\n" +
                "      \"addedTime\": \"2013-07-12T15:56:12Z\",\n" +
                "      \"hold\": false,\n" +
                "      \"id\": \"{memberId3}\",\n" +
                "      \"mute\": false,\n" +
                "      \"removedTime\": \"2013-07-12T15:56:59-02\",\n" +
                "      \"state\": \"completed\",\n" +
                "      \"joinTone\": false,\n" +
                "      \"leavingTone\": false,\n" +
                "      \"call\": \"https://localhost:8444/v1/users/{userId}/calls/{callId3}\"\n" +
                "  }\n" +
                "]");


        RestResponse response = new RestResponse();
        response.setResponseText(mockClient.arrayResult.toString());
        mockClient.setRestResponse(response);


        Conference conference = new Conference(mockClient, jsonObject);



        List<ConferenceMember> members = conference.getMembers();
        assertThat(members.size(), equalTo(3));
        assertThat(members.get(1).getId(), equalTo("{memberId2}"));
        assertThat(mockClient.requests.get(0).name, equalTo("get"));
        assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/conferences/{conferenceId}/members"));
    }
    
    @Test
    public void shouldCreateNewConference() throws Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(
                "{\"id\":\"conf-id1\",\"createdTime\":\"2014-08-14T14:10:24Z\",\"state\":\"created\",\"from\":\"+number\",\"activeMembers\":0}");

        mockClient.result = jsonObject;
        RestResponse response = new RestResponse();
        response.setResponseText(jsonObject.toString());

        response.setContentType("application/json");
        String mockUri = mockClient.getUserResourceUri(BandwidthConstants.CONFERENCES_URI_PATH) + "/id";
        response.setLocation(mockUri);
        response.setStatus(201);


        mockClient.setRestResponse(response);

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("from", "+number");
        params.put("callbackUrl", "http://my.callback.url");
        
        Conference conference = Conference.createConference(mockClient, params);
        assertThat(conference.getId(), equalTo("conf-id1"));
        assertThat(conference.getFrom(), equalTo("+number"));

        assertThat(mockClient.requests.get(0).name, equalTo("post"));
        assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/conferences"));
        assertThat(mockClient.requests.get(0).params.get("from").toString(), equalTo("+number"));
        assertThat(mockClient.requests.get(0).params.get("callbackUrl").toString(), equalTo("http://my.callback.url"));
    }
    

    @Test
    public void shouldGetConferenceById() throws Exception {
        JSONObject jsonObject =  (JSONObject) new JSONParser().parse("{\"id\":\"conf-id1\",\"createdTime\":\"2014-08-14T14:10:24Z\",\"state\":\"created\",\"from\":\"+number\",\"activeMembers\":0}");

        mockClient.result = jsonObject;
        RestResponse response = new RestResponse();
        response.setResponseText(jsonObject.toString());
        mockClient.setRestResponse(response);
        Conference conference = Conference.getConference(mockClient, "conf-id1");
        assertThat(conference.getId(), equalTo("conf-id1"));
        assertThat(conference.getFrom(), equalTo("+number"));

        assertThat(mockClient.requests.get(0).name, equalTo("get"));
        assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/conferences/conf-id1"));
    }
    
    
    
}