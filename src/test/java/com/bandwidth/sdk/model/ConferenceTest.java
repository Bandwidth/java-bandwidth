package com.bandwidth.sdk.model;

import com.bandwidth.sdk.MockRestClient;
import com.bandwidth.sdk.RestResponse;
import com.bandwidth.sdk.TestsHelper;

import org.json.simple.JSONArray;
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
        Conference conference = new Conference(mockRestClient, jsonObject);
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
        mockRestClient.result = jsonObject;

        Conference conference = new Conference(mockRestClient, jsonObject);
        conference.complete();

        assertThat(mockRestClient.requests.get(0).name, equalTo("post"));
        assertThat(mockRestClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/conferences/{conferenceId}"));
                assertThat(mockRestClient.requests.get(0).params.get("state").toString(), equalTo("completed"));
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
        mockRestClient.result = jsonObject;

        Conference conference = new Conference(mockRestClient, jsonObject);
        conference.mute();

        assertThat(mockRestClient.requests.get(0).name, equalTo("post"));
        assertThat(mockRestClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/conferences/{conferenceId}"));
        assertThat(mockRestClient.requests.get(0).params.get("mute").toString(), equalTo("true"));
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
        mockRestClient.result = jsonObject;

        Conference conference = new Conference(mockRestClient, jsonObject);

        conference.conferenceAudioBuilder().sentence("Hello").locale(SentenceLocale.German).create();
        assertThat(mockRestClient.requests.get(0).name, equalTo("post"));
        assertThat(mockRestClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/conferences/{conferenceId}/audio"));
        assertThat(mockRestClient.requests.get(0).params.get("sentence").toString(), equalTo("Hello"));
        assertThat(mockRestClient.requests.get(0).params.get("locale").toString(), equalTo("de_DE"));
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
        mockRestClient.arrayResult = (org.json.simple.JSONArray) new JSONParser().parse("[\n" +
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
        Conference conference = new Conference(mockRestClient, jsonObject);
        List<ConferenceMember> members = conference.getMembers();
        assertThat(members.size(), equalTo(3));
        assertThat(members.get(1).getId(), equalTo("{memberId2}"));
        assertThat(mockRestClient.requests.get(0).name, equalTo("getArray"));
        assertThat(mockRestClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/conferences/{conferenceId}/members"));
    }
    
    @Test
    public void shouldCreateNewConference() throws Exception {
        mockRestClient.result = (JSONObject) new JSONParser().parse("{\"id\":\"conf-id1\",\"createdTime\":\"2014-08-14T14:10:24Z\",\"state\":\"created\",\"from\":\"+number\",\"activeMembers\":0}");

        //Conference conference = conferences.newConferenceBuilder().from("fromNumber").callbackUrl("url").create();
        
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("from", "+number");
        params.put("callbackUrl", "http://my.callback.url");
        
        Conference conference = Conference.createConference(mockRestClient, params);
        assertThat(conference.getId(), equalTo("conf-id1"));
        assertThat(conference.getFrom(), equalTo("+number"));

        assertThat(mockRestClient.requests.get(0).name, equalTo("create"));
        assertThat(mockRestClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/conferences"));
        assertThat(mockRestClient.requests.get(0).params.get("from").toString(), equalTo("+number"));
        assertThat(mockRestClient.requests.get(0).params.get("callbackUrl").toString(), equalTo("http://my.callback.url"));
    }
    

    @Test
    public void shouldGetConferenceById() throws Exception {
        mockRestClient.result = (JSONObject) new JSONParser().parse("{\"id\":\"conf-id1\",\"createdTime\":\"2014-08-14T14:10:24Z\",\"state\":\"created\",\"from\":\"+number\",\"activeMembers\":0}");

        Conference conference = Conference.getConference(mockRestClient, "conf-id1");
        assertThat(conference.getId(), equalTo("conf-id1"));
        assertThat(conference.getFrom(), equalTo("+number"));

        assertThat(mockRestClient.requests.get(0).name, equalTo("getObject"));
        assertThat(mockRestClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/conferences/conf-id1"));
    }
    
    
    
}