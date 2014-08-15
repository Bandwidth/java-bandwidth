package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthRestClient;
import com.bandwidth.sdk.driver.MockRestDriver;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;

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
        assertThat(mockRestDriver.requests.get(0).params.get("state").toString(), equalTo("completed"));
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
        assertThat(mockRestDriver.requests.get(0).params.get("mute").toString(), equalTo("true"));
    }
}