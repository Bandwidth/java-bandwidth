package com.bandwidth.sdk.conferences;

import com.bandwidth.sdk.BandwidthRestClient;
import com.bandwidth.sdk.driver.MockRestDriver;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class BandwidthConferencesTest {

    private MockRestDriver mockRestDriver;
    private BandwidthConferences conferences;

    @Before
    public void setUp() throws Exception {
        mockRestDriver = new MockRestDriver();
        BandwidthRestClient client = new BandwidthRestClient(null, null, null);
        client.setRestDriver(mockRestDriver);

        conferences = new BandwidthConferences(client);
    }

    @Test
    public void shouldCreateNewConference() throws Exception {
        mockRestDriver.result = (JSONObject) new JSONParser().parse("{\"id\":\"conf-id1\",\"createdTime\":\"2014-08-14T14:10:24Z\",\"state\":\"created\",\"from\":\"+number\",\"activeMembers\":0}");

        BandwidthConference conference = conferences.newConference().from("fromNumber").callbackUrl("url").commit();
        assertThat(conference.getId(), equalTo("conf-id1"));
        assertThat(conference.getFrom(), equalTo("+number"));

        assertThat(mockRestDriver.requests.get(0).name, equalTo("createConference"));
        assertThat(mockRestDriver.requests.get(0).params.get("from").toString(), equalTo("fromNumber"));
        assertThat(mockRestDriver.requests.get(0).params.get("callbackUrl").toString(), equalTo("url"));
    }

    @Test
    public void shouldGetConferenceById() throws Exception {
        mockRestDriver.result = (JSONObject) new JSONParser().parse("{\"id\":\"conf-id1\",\"createdTime\":\"2014-08-14T14:10:24Z\",\"state\":\"created\",\"from\":\"+number\",\"activeMembers\":0}");

        BandwidthConference conference = conferences.getConferenceById("conf-id1");
        assertThat(conference.getId(), equalTo("conf-id1"));
        assertThat(conference.getFrom(), equalTo("+number"));

        assertThat(mockRestDriver.requests.get(0).name, equalTo("requestConferenceById"));
    }
}