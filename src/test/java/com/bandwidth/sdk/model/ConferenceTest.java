package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthRestClient;
import com.bandwidth.sdk.driver.MockRestDriver;
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
        Conference conference = Conference.from(null, jsonObject);
        assertThat(conference.getId(), equalTo("{conferenceId}"));
        assertThat(conference.getState(), equalTo(ConferenceState.created));
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
        MockRestDriver mockRestDriver = new MockRestDriver();
        mockRestDriver.result = jsonObject;

        BandwidthRestClient client = new BandwidthRestClient("", "", "");
        client.setRestDriver(mockRestDriver);

        Conference conference = Conference.from(client, jsonObject);
        conference.complete();

        assertThat(mockRestDriver.requests.get(0).name, equalTo("updateConference"));
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
        MockRestDriver mockRestDriver = new MockRestDriver();
        mockRestDriver.result = jsonObject;

        BandwidthRestClient client = new BandwidthRestClient("", "", "");
        client.setRestDriver(mockRestDriver);

        Conference conference = Conference.from(client, jsonObject);
        conference.mute();

        assertThat(mockRestDriver.requests.get(0).name, equalTo("updateConference"));
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
        MockRestDriver mockRestDriver = new MockRestDriver();
        mockRestDriver.result = jsonObject;

        BandwidthRestClient client = new BandwidthRestClient("", "", "");
        client.setRestDriver(mockRestDriver);

        Conference conference = Conference.from(client, jsonObject);

        conference.conferenceAudioBuilder().sentence("Hello").locale(SentenceLocale.German).create();
        assertThat(mockRestDriver.requests.get(0).name, equalTo("createConferenceAudio"));
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
        MockRestDriver mockRestDriver = new MockRestDriver();
        mockRestDriver.arrayResult = (org.json.simple.JSONArray) new JSONParser().parse("[\n" +
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
        BandwidthRestClient client = new BandwidthRestClient("", "", "");
        client.setRestDriver(mockRestDriver);

        Conference conference = Conference.from(client, jsonObject);
        List<ConferenceMember> members = conference.getMembers();
        assertThat(members.size(), equalTo(3));
        assertThat(members.get(1).getId(), equalTo("{memberId2}"));
        assertThat(mockRestDriver.requests.get(0).name, equalTo("requestConferenceMembers"));
    }
}