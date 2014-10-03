package com.bandwidth.sdk.model;

import com.bandwidth.sdk.MockRestClient;
import com.bandwidth.sdk.TestsHelper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ApplicationTest {

    private MockRestClient mockRestClient;

    @Before
    public void setUp(){
        mockRestClient = TestsHelper.getClient();

    }

    @Test
    public void shouldBeCreatedFromJson() throws ParseException {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"id\":\"id1\",\"autoAnswer\":true,\"incomingSmsUrl\":\"http:\\/\\/sms\\/callback.json\",\"name\":\"App1\",\"incomingCallUrl\":\"http:\\/\\/call\\/callback.json\"}");

        Application application = new Application(null, jsonObject);

        assertThat(application.getId(), equalTo("id1"));
        assertThat(application.getName(), equalTo("App1"));
        assertThat(application.getIncomingCallUrl(), equalTo("http://call/callback.json"));
        assertThat(application.getIncomingSmsUrl(), equalTo("http://sms/callback.json"));
        assertThat(application.isAutoAnswer(), equalTo(true));
    }

    @Test
    public void shouldBeDeleted() throws ParseException, IOException {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"id\":\"id1\",\"autoAnswer\":true,\"incomingSmsUrl\":\"http:\\/\\/sms\\/callback.json\",\"name\":\"App1\",\"incomingCallUrl\":\"http:\\/\\/call\\/callback.json\"}");
        Application application = new Application(mockRestClient, jsonObject);

        assertThat(application.getId(), equalTo("id1"));

        application.delete();

        assertThat(mockRestClient.requests.get(0).name, equalTo("delete"));
        assertThat(mockRestClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/applications/id1"));
    }

    @Test
    public void shouldUpdateAttributesOnServer() throws ParseException, IOException {

        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"id\":\"id1\",\"autoAnswer\":true,\"incomingSmsUrl\":\"http:\\/\\/sms\\/callback.json\",\"name\":\"App1\",\"incomingCallUrl\":\"http:\\/\\/call\\/callback.json\"}");
        Application application = new Application(mockRestClient, jsonObject);

        assertThat(application.getId(), equalTo("id1"));
        application.setName("App2");
        application.setIncomingCallUrl("anotherUrl");
        application.commit();

        assertThat(mockRestClient.requests.get(0).name, equalTo("post"));
        assertThat(mockRestClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/applications/id1"));
        assertThat(mockRestClient.requests.get(0).params.get("name").toString(), equalTo("App2"));
    }
}