package com.bandwidth.sdk.model;

import com.bandwidth.sdk.MockRestClient;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class PhoneNumberTest {

    @Test
    public void shouldBeCreatedFromJson() throws Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\n" +
                "    \"numberState\": \"enabled\",\n" +
                "    \"id\": \"n-bdllkjjddr5vuvglfluxdwi\",\n" +
                "    \"application\": \"https://api.com/v1/users/userId/applications/a-id1\",\n" +
                "    \"price\": \"0.00\",\n" +
                "    \"createdTime\": \"2013-03-07T21:35:18Z\",\n" +
                "    \"state\": \"NC\",\n" +
                "    \"number\": \"+number\",\n" +
                "    \"nationalNumber\": \"(111) 111-8026\",\n" +
                "    \"city\": \"GREENSBORO\"\n" +
                "  }");
        PhoneNumber number = new PhoneNumber(null, "parentUri", jsonObject);
        assertThat(number.getId(), equalTo("n-bdllkjjddr5vuvglfluxdwi"));
        assertThat(number.getNumber(), equalTo("+number"));
        assertThat(number.getCity(), equalTo("GREENSBORO"));
    }

    @Test
    public void shouldCommitOnServerProperties() throws Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\n" +
                "    \"numberState\": \"enabled\",\n" +
                "    \"id\": \"n-bdllkjjddr5vuvglfluxdwi\",\n" +
                "    \"application\": \"https://api.com/v1/users/userId/applications/a-id1\",\n" +
                "    \"price\": \"0.00\",\n" +
                "    \"createdTime\": \"2013-03-07T21:35:18Z\",\n" +
                "    \"state\": \"NC\",\n" +
                "    \"number\": \"+number\",\n" +
                "    \"nationalNumber\": \"(111) 111-8026\",\n" +
                "    \"city\": \"GREENSBORO\"\n" +
                "  }");

        MockRestClient mockRestClient = new MockRestClient();
        mockRestClient.result = jsonObject;

        PhoneNumber number = new PhoneNumber(mockRestClient, "parentUri", jsonObject);
        number.setApplicationId("appId");
        number.setName("NewName");
        number.commit();

        assertThat(mockRestClient.requests.get(0).name, equalTo("post"));
        assertThat(mockRestClient.requests.get(0).uri, equalTo("parentUri/n-bdllkjjddr5vuvglfluxdwi"));
        assertThat(mockRestClient.requests.get(0).params.get("applicationId").toString(), equalTo("appId"));
        assertThat(mockRestClient.requests.get(0).params.get("name").toString(), equalTo("NewName"));
    }

    @Test
    public void shouldBeDeletable() throws Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\n" +
                "    \"numberState\": \"enabled\",\n" +
                "    \"id\": \"n-bdllkjjddr5vuvglfluxdwi\",\n" +
                "    \"application\": \"https://api.com/v1/users/userId/applications/a-id1\",\n" +
                "    \"price\": \"0.00\",\n" +
                "    \"createdTime\": \"2013-03-07T21:35:18Z\",\n" +
                "    \"state\": \"NC\",\n" +
                "    \"number\": \"+number\",\n" +
                "    \"nationalNumber\": \"(111) 111-8026\",\n" +
                "    \"city\": \"GREENSBORO\"\n" +
                "  }");

        MockRestClient mockRestClient = new MockRestClient();
        mockRestClient.result = jsonObject;

        PhoneNumber number = new PhoneNumber(mockRestClient, "parentUri", jsonObject);
        number.delete();

        assertThat(mockRestClient.requests.get(0).name, equalTo("delete"));
        assertThat(mockRestClient.requests.get(0).uri, equalTo("parentUri/n-bdllkjjddr5vuvglfluxdwi"));
    }
}