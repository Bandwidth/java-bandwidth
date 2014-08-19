package com.bandwidth.sdk;

import com.bandwidth.sdk.driver.MockRestDriver;
import com.bandwidth.sdk.model.NumberInfo;
import org.hamcrest.CoreMatchers;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertThat;

public class BandwidthRestClientTest {

    private MockRestDriver mockRestDriver;
    private BandwidthRestClient client;

    @Before
    public void setUp() throws Exception {
        mockRestDriver = new MockRestDriver();
        client = new BandwidthRestClient("userId", "token", "secret");
        client.setRestDriver(mockRestDriver);
    }

    @Test
    public void shouldGetNumberInfo() throws Exception {
        mockRestDriver.result = (org.json.simple.JSONObject) new JSONParser().parse("{\n" +
                "  \"created\": \"2013-09-23T16:31:15Z\",\n" +
                "  \"name\": \"Name\",\n" +
                "  \"number\": \"{number}\",\n" +
                "  \"updated\": \"2013-09-23T16:42:18Z\"\n" +
                "}");
        NumberInfo number = client.getNumberInfoByNumber("number");
        assertThat(number.getName(), CoreMatchers.equalTo("Name"));
        assertThat(number.getNumber(), CoreMatchers.equalTo("{number}"));

        assertThat(mockRestDriver.requests.get(0).name, CoreMatchers.equalTo("getObject"));
        assertThat(mockRestDriver.requests.get(0).uri, CoreMatchers.equalTo("phoneNumbers/numberInfo/number"));
    }
}