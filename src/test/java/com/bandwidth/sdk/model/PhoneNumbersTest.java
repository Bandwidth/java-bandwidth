package com.bandwidth.sdk.model;

import com.bandwidth.sdk.MockRestClient;
import com.bandwidth.sdk.TestsHelper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class PhoneNumbersTest  extends BaseModelTest{

    private PhoneNumbers phoneNumbers;

    @Before
    public void setUp() {
        super.setUp();
        phoneNumbers = new PhoneNumbers(mockRestClient);
    }

    @Test
    public void shouldGetNumberList() throws Exception {
        mockRestClient.arrayResult = (JSONArray) new JSONParser().parse("[\n" +
                "  {\n" +
                "    \"numberState\": \"enabled\",\n" +
                "    \"id\": \"n-bdllkjjddr5vuvglfluxdwi\",\n" +
                "    \"application\": \"https://api.com/v1/users/userId/applications/a-id1\",\n" +
                "    \"price\": \"0.00\",\n" +
                "    \"createdTime\": \"2013-03-07T21:35:18Z\",\n" +
                "    \"state\": \"NC\",\n" +
                "    \"number\": \"+number\",\n" +
                "    \"nationalNumber\": \"(111) 111-8026\",\n" +
                "    \"city\": \"GREENSBORO\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"numberState\": \"enabled\",\n" +
                "    \"id\": \"n-lyamatqgkgcpeycv765pf3q\",\n" +
                "    \"price\": \"0.00\",\n" +
                "    \"createdTime\": \"2013-03-18T15:54:02Z\",\n" +
                "    \"name\": \"DIDify Number\",\n" +
                "    \"number\": \"+number2\",\n" +
                "    \"nationalNumber\": \"(999) 999-6131\"\n" +
                "  }\n" +
                "]");
        List<PhoneNumber> list = phoneNumbers.queryNumbersBuilder().size(2).list();
        assertThat(list.size(), equalTo(2));
        assertThat(list.get(0).getId(), equalTo("n-bdllkjjddr5vuvglfluxdwi"));

        assertThat(mockRestClient.requests.get(0).name, equalTo("getArray"));
        assertThat(mockRestClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/phoneNumbers"));
    }

    @Test
    public void shouldCreateNewNumber() throws Exception {
        mockRestClient.result = (JSONObject) new JSONParser().parse("{\n" +
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
        PhoneNumber number = phoneNumbers.newNumberBuilder().applicationId("appId").number("number").name("name").create();
        assertThat(number.getId(), equalTo("n-bdllkjjddr5vuvglfluxdwi"));

        assertThat(mockRestClient.requests.get(0).name, equalTo("create"));
        assertThat(mockRestClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/phoneNumbers"));
    }

    @Test
    public void shouldGetNumberById() throws Exception {
        mockRestClient.result = (JSONObject) new JSONParser().parse("{\n" +
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
        PhoneNumber number = phoneNumbers.getNumber("n-bdllkjjddr5vuvglfluxdwi");
        assertThat(number.getId(), equalTo("n-bdllkjjddr5vuvglfluxdwi"));

        assertThat(mockRestClient.requests.get(0).name, equalTo("getObject"));
        assertThat(mockRestClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/phoneNumbers/n-bdllkjjddr5vuvglfluxdwi"));
    }

    @Test
    public void shouldGetNumberByNumber() throws Exception {
        mockRestClient.result = (JSONObject) new JSONParser().parse("{\n" +
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
        PhoneNumber number = phoneNumbers.getNumber("+number");
        assertThat(number.getId(), equalTo("n-bdllkjjddr5vuvglfluxdwi"));

        assertThat(mockRestClient.requests.get(0).name, equalTo("getObject"));
        assertThat(mockRestClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/phoneNumbers/+number"));
    }
}