package com.bandwidth.sdk.applications;

import com.bandwidth.sdk.BandwidthRestClient;
import com.bandwidth.sdk.driver.MockRestDriver;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class BandwidthApplicationsTest {

    private MockRestDriver mockRestDriver;
    private BandwidthApplications applications;

    @Before
    public void setUp() {
        mockRestDriver = new MockRestDriver();

        BandwidthRestClient client = new BandwidthRestClient(null, null, null);
        client.setRestDriver(mockRestDriver);

        applications = new BandwidthApplications(client);
    }

    @Test
    public void shouldGetApplicationsList() throws ParseException, IOException {
        mockRestDriver.arrayResult = (JSONArray) new JSONParser().parse("[{\"id\":\"id1\",\"incomingCallUrl\":\"https://postBack\",\"incomingSmsUrl\":\"https://message\",\"name\":\"App1\",\"autoAnswer\":false},{\"id\":\"id2\",\"incomingCallUrl\":\"http:///call/callback.json\",\"incomingSmsUrl\":\"http:///sms/callback.json\",\"name\":\"App2\",\"autoAnswer\":true}]");

        List<BandwidthApplication> applicationList = applications.getApplications().get();
        assertThat(applicationList.size(), equalTo(2));
        assertThat(applicationList.get(0).getId(), equalTo("id1"));
        assertThat(applicationList.get(0).isAutoAnswer(), equalTo(false));
        assertThat(applicationList.get(0).getIncomingSmsUrl(), equalTo("https://message"));
        assertThat(applicationList.get(1).getId(), equalTo("id2"));
        assertThat(applicationList.get(1).isAutoAnswer(), equalTo(true));
        assertThat(applicationList.get(1).getIncomingCallUrl(), equalTo("http:///call/callback.json"));

        assertThat(mockRestDriver.requests.get(0).name, equalTo("requestApplications"));
    }

    @Test
    public void shouldCreateApplication() throws ParseException, IOException {
        mockRestDriver.result = (JSONObject) new JSONParser().parse("{\"id\":\"id1\",\"incomingCallUrl\":\"https://postBack\",\"incomingSmsUrl\":\"https://message\",\"name\":\"App1\",\"autoAnswer\":false}");

        BandwidthApplication application = applications.newApplication("App").create();
        assertThat(application.getId(), equalTo("id1"));
        assertThat(application.getName(), equalTo("App1"));
        assertThat(application.getIncomingCallUrl(), equalTo("https://postBack"));
        assertThat(application.getIncomingSmsUrl(), equalTo("https://message"));

        assertThat(mockRestDriver.requests.get(0).name, equalTo("createApplication"));
    }
}