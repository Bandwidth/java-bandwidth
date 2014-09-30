package com.bandwidth.sdk;

import com.bandwidth.sdk.model.NumberInfo;
import org.hamcrest.CoreMatchers;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
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

    @Test
    public void shouldGetProperUserResourceUri() throws Exception {
        String resourceUri = client.getUserResourceUri(BandwidthConstants.CALLS_URI_PATH);
        assertEquals("users/userId/calls", resourceUri);
    }

    @Test
    public void shouldGetProperResourceInstanceUri() throws Exception {
        String resourceInstanceUri = client.getUserResourceInstanceUri(BandwidthConstants.CALLS_URI_PATH, "myId");
        assertEquals("users/userId/calls/myId", resourceInstanceUri);
    }

    @Test(expected=IllegalArgumentException.class)
    public void getResourceUriShouldFailWithNullPath() throws Exception{
        client.getUserResourceUri(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void getResourceInstanceUriShouldFailWithNullPath() throws Exception{
        client.getUserResourceInstanceUri(null, "myId");
    }

    @Test(expected=IllegalArgumentException.class)
    public void getResourceInstanceUriShouldFailWithEmptyPath() throws Exception{
        client.getUserResourceInstanceUri("", "myId");
    }

    @Test(expected=IllegalArgumentException.class)
    public void getResourceInstanceUriShouldFailWithNullId() throws Exception{
        client.getUserResourceInstanceUri("path", null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void getResourceInstanceUriShouldFailWithEmptyId() throws Exception{
        client.getUserResourceInstanceUri("path", "");
    }

}