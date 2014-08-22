package com.bandwidth.sdk;

import com.bandwidth.sdk.model.NumberInfo;
import org.hamcrest.CoreMatchers;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertThat;

public class BandwidthRestClientTest {

    private MockRestClient client;

    @Before
    public void setUp() throws Exception {
        client = new MockRestClient();
    }

    @Test
    public void shouldGetNumberInfo() throws Exception {
        client.result = (org.json.simple.JSONObject) new JSONParser().parse("{\n" +
                "  \"created\": \"2013-09-23T16:31:15Z\",\n" +
                "  \"name\": \"Name\",\n" +
                "  \"number\": \"{number}\",\n" +
                "  \"updated\": \"2013-09-23T16:42:18Z\"\n" +
                "}");
        NumberInfo number = client.getNumberInfoByNumber("number");
        assertThat(number.getName(), CoreMatchers.equalTo("Name"));
        assertThat(number.getNumber(), CoreMatchers.equalTo("{number}"));

        assertThat(client.requests.get(0).name, CoreMatchers.equalTo("getObject"));
        assertThat(client.requests.get(0).uri, CoreMatchers.equalTo("phoneNumbers/numberInfo/number"));
    }
}