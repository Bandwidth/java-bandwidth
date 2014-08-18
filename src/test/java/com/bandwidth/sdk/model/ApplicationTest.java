package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthRestClient;
import com.bandwidth.sdk.driver.MockRestDriver;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class ApplicationTest {

    @Test
    public void shouldBeCreatedFromJson() throws ParseException {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"id\":\"id1\",\"autoAnswer\":true,\"incomingSmsUrl\":\"http:\\/\\/sms\\/callback.json\",\"name\":\"App1\",\"incomingCallUrl\":\"http:\\/\\/call\\/callback.json\"}");

        Application application = Application.from(null, jsonObject);

        assertThat(application.getId(), equalTo("id1"));
        assertThat(application.getName(), equalTo("App1"));
        assertThat(application.getIncomingCallUrl(), equalTo("http://call/callback.json"));
        assertThat(application.getIncomingSmsUrl(), equalTo("http://sms/callback.json"));
        assertThat(application.isAutoAnswer(), equalTo(true));
    }

    @Test
    public void shouldBeDeleted() throws ParseException, IOException {
        MockRestDriver mockRestDriver = new MockRestDriver();

        BandwidthRestClient client = new BandwidthRestClient(null, null, null);
        client.setRestDriver(mockRestDriver);

        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"id\":\"id1\",\"autoAnswer\":true,\"incomingSmsUrl\":\"http:\\/\\/sms\\/callback.json\",\"name\":\"App1\",\"incomingCallUrl\":\"http:\\/\\/call\\/callback.json\"}");
        Application application = Application.from(client, jsonObject);

        assertThat(application.getId(), equalTo("id1"));

        application.delete();

        assertThat(application.getId(), nullValue());

        assertThat(mockRestDriver.requests.get(0).name, equalTo("deleteApplication"));
    }

    @Test
    public void shouldUpdateAttributesOnServer() throws ParseException, IOException {
        MockRestDriver mockRestDriver = new MockRestDriver();

        BandwidthRestClient client = new BandwidthRestClient(null, null, null);
        client.setRestDriver(mockRestDriver);

        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"id\":\"id1\",\"autoAnswer\":true,\"incomingSmsUrl\":\"http:\\/\\/sms\\/callback.json\",\"name\":\"App1\",\"incomingCallUrl\":\"http:\\/\\/call\\/callback.json\"}");
        Application application = Application.from(client, jsonObject);

        assertThat(application.getId(), equalTo("id1"));
        application.setName("App2");
        application.setIncomingCallUrl("anotherUrl");
        application.commit();

        assertThat(mockRestDriver.requests.get(0).name, equalTo("updateApplication"));
    }

    @Test
    public void shouldRevertAttributesFromServer() throws ParseException, IOException {
        MockRestDriver mockRestDriver = new MockRestDriver();

        BandwidthRestClient client = new BandwidthRestClient(null, null, null);
        client.setRestDriver(mockRestDriver);

        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"id\":\"id1\",\"autoAnswer\":true,\"incomingSmsUrl\":\"http:\\/\\/sms\\/callback.json\",\"name\":\"App1\",\"incomingCallUrl\":\"http:\\/\\/call\\/callback.json\"}");
        Application application = Application.from(client, jsonObject);

        assertThat(application.getId(), equalTo("id1"));
        application.setName("App2");
        assertThat(application.getName(), equalTo("App2"));

        mockRestDriver.result = jsonObject;
        application.revert();

        assertThat(application.getName(), equalTo("App1"));
        assertThat(mockRestDriver.requests.get(0).name, equalTo("requestApplicationById"));
    }
}