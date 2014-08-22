package com.bandwidth.sdk.model;

import com.bandwidth.sdk.MockRestClient;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ConferenceTest {

    @Test
    public void shouldBeCreatedFromJson() throws Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\n" +
                "    \"activeMembers\": 0,\n" +
                "    \"createdTime\": \"2013-07-12T15:22:47Z\",\n" +
                "    \"from\": \"+19703255647\",\n" +
                "    \"id\": \"{conferenceId}\",\n" +
                "    \"state\": \"created\"\n" +
                "}");
        Conference conference = new Conference(null, null, jsonObject);
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
        MockRestClient mockRestClient = new MockRestClient();
        mockRestClient.result = jsonObject;

        Conference conference = new Conference(mockRestClient, "parentUri", jsonObject);
        conference.complete();

        assertThat(mockRestClient.requests.get(0).name, equalTo("post"));
        assertThat(mockRestClient.requests.get(0).uri, equalTo("parentUri/{conferenceId}"));
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
        MockRestClient mockRestClient = new MockRestClient();
        mockRestClient.result = jsonObject;

        Conference conference = new Conference(mockRestClient, "parentUri", jsonObject);
        conference.mute();

        assertThat(mockRestClient.requests.get(0).name, equalTo("post"));
        assertThat(mockRestClient.requests.get(0).uri, equalTo("parentUri/{conferenceId}"));
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
        MockRestClient mockRestClient = new MockRestClient();
        mockRestClient.result = jsonObject;

        Conference conference = new Conference(mockRestClient, "parentUri", jsonObject);

        conference.conferenceAudioBuilder().sentence("Hello").locale(SentenceLocale.German).create();
        assertThat(mockRestClient.requests.get(0).name, equalTo("post"));
        assertThat(mockRestClient.requests.get(0).uri, equalTo("parentUri/{conferenceId}/audio"));
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
        MockRestClient mockRestClient = new MockRestClient();
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
        Conference conference = new Conference(mockRestClient, "parentUri", jsonObject);
        List<ConferenceMember> members = conference.getMembers();
        assertThat(members.size(), equalTo(3));
        assertThat(members.get(1).getId(), equalTo("{memberId2}"));
        assertThat(mockRestClient.requests.get(0).name, equalTo("getArray"));
        assertThat(mockRestClient.requests.get(0).uri, equalTo("parentUri/{conferenceId}/members"));
    }
}