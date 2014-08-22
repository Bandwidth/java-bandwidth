package com.bandwidth.sdk.model;

import com.bandwidth.sdk.MockRestClient;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ConferencesTest {

    private MockRestClient mockRestClient;
    private Conferences conferences;

    @Before
    public void setUp() throws Exception {
        mockRestClient = new MockRestClient();

        conferences = new Conferences(mockRestClient, "parentUri");
    }

    @Test
    public void shouldCreateNewConference() throws Exception {
        mockRestClient.result = (JSONObject) new JSONParser().parse("{\"id\":\"conf-id1\",\"createdTime\":\"2014-08-14T14:10:24Z\",\"state\":\"created\",\"from\":\"+number\",\"activeMembers\":0}");

        Conference conference = conferences.newConferenceBuilder().from("fromNumber").callbackUrl("url").create();
        assertThat(conference.getId(), equalTo("conf-id1"));
        assertThat(conference.getFrom(), equalTo("+number"));

        assertThat(mockRestClient.requests.get(0).name, equalTo("create"));
        assertThat(mockRestClient.requests.get(0).uri, equalTo("parentUri/conferences"));
        assertThat(mockRestClient.requests.get(0).params.get("from").toString(), equalTo("fromNumber"));
        assertThat(mockRestClient.requests.get(0).params.get("callbackUrl").toString(), equalTo("url"));
    }

    @Test
    public void shouldGetConferenceById() throws Exception {
        mockRestClient.result = (JSONObject) new JSONParser().parse("{\"id\":\"conf-id1\",\"createdTime\":\"2014-08-14T14:10:24Z\",\"state\":\"created\",\"from\":\"+number\",\"activeMembers\":0}");

        Conference conference = conferences.getConference("conf-id1");
        assertThat(conference.getId(), equalTo("conf-id1"));
        assertThat(conference.getFrom(), equalTo("+number"));

        assertThat(mockRestClient.requests.get(0).name, equalTo("getObject"));
        assertThat(mockRestClient.requests.get(0).uri, equalTo("parentUri/conferences/conf-id1"));
    }
}