package com.bandwidth.sdk.model;

import com.bandwidth.sdk.MockRestClient;
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

public class ApplicationsTest {

    private MockRestClient mockRestClient;
    private Applications applications;

    @Before
    public void setUp() {
        mockRestClient = TestsHelper.getClient();

        applications = new Applications(mockRestClient);
    }

    @Test
    public void shouldGetApplicationsList() throws ParseException, IOException {
        mockRestClient.arrayResult = (JSONArray) new JSONParser().parse("[{\"id\":\"id1\",\"incomingCallUrl\":\"https://postBack\",\"incomingSmsUrl\":\"https://message\",\"name\":\"App1\",\"autoAnswer\":false},{\"id\":\"id2\",\"incomingCallUrl\":\"http:///call/callback.json\",\"incomingSmsUrl\":\"http:///sms/callback.json\",\"name\":\"App2\",\"autoAnswer\":true}]");

        List<Application> applicationList = applications.queryApplicationsBuilder().page(1).list();
        assertThat(applicationList.size(), equalTo(2));
        assertThat(applicationList.get(0).getId(), equalTo("id1"));
        assertThat(applicationList.get(0).isAutoAnswer(), equalTo(false));
        assertThat(applicationList.get(0).getIncomingSmsUrl(), equalTo("https://message"));
        assertThat(applicationList.get(1).getId(), equalTo("id2"));
        assertThat(applicationList.get(1).isAutoAnswer(), equalTo(true));
        assertThat(applicationList.get(1).getIncomingCallUrl(), equalTo("http:///call/callback.json"));

        assertThat(mockRestClient.requests.get(0).name, equalTo("getArray"));
        assertThat(mockRestClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/applications"));
        assertThat((Integer) mockRestClient.requests.get(0).params.get("page"), equalTo(1));
    }

    @Test
    public void shouldCreateApplication() throws ParseException, IOException {
        mockRestClient.result = (JSONObject) new JSONParser().parse("{\"id\":\"id1\",\"incomingCallUrl\":\"https://postBack\",\"incomingSmsUrl\":\"https://message\",\"name\":\"App1\",\"autoAnswer\":false}");

        Application application = applications.newApplicationBuilder("App").create();
        assertThat(application.getId(), equalTo("id1"));
        assertThat(application.getName(), equalTo("App1"));
        assertThat(application.getIncomingCallUrl(), equalTo("https://postBack"));
        assertThat(application.getIncomingSmsUrl(), equalTo("https://message"));

        assertThat(mockRestClient.requests.get(0).name, equalTo("create"));
        assertThat(mockRestClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/applications"));
    }

//    @Test
//    public void shouldGetApplicationById() throws ParseException, IOException {
//        mockRestClient.result = (JSONObject) new JSONParser().parse("{\"id\":\"id1\",\"incomingCallUrl\":\"https://postBack\",\"incomingSmsUrl\":\"https://message\",\"name\":\"App1\",\"autoAnswer\":false}");
//TODO: move test to Application
//        Application application = applications.getApplication("id1");
//        assertThat(application.getId(), equalTo("id1"));
//        assertThat(application.getName(), equalTo("App1"));
//        assertThat(application.getIncomingCallUrl(), equalTo("https://postBack"));
//        assertThat(application.getIncomingSmsUrl(), equalTo("https://message"));
//
//        assertThat(mockRestClient.requests.get(0).name, equalTo("getObject"));
//        assertThat(mockRestClient.requests.get(0).uri, equalTo("parentUri/applications/id1"));
//    }
}