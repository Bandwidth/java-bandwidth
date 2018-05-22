package com.bandwidth.sdk;


import com.bandwidth.sdk.model.NumberInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.hamcrest.CoreMatchers;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;
import org.json.simple.JSONObject;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class BandwidthClientTest {

    private MockClient client;

    @Before
    public void setUp() throws Exception {
        client = new MockClient();
    }

    @Test
    public void shouldGetNumberInfo() throws Exception {
        final JSONObject jsonObject = (org.json.simple.JSONObject) new JSONParser().parse("{\n" +
                "  \"created\": \"2013-09-23T16:31:15Z\",\n" +
                "  \"name\": \"Name\",\n" +
                "  \"number\": \"{number}\",\n" +
                "  \"updated\": \"2013-09-23T16:42:18Z\"\n" +
                "}");

        client.result = jsonObject;
        final RestResponse response = new RestResponse();
        response.setResponseText(jsonObject.toString());
        client.setRestResponse(response);


        final NumberInfo number = client.getNumberInfoByNumber("number");
        assertThat(number.getName(), CoreMatchers.equalTo("Name"));
        assertThat(number.getNumber(), CoreMatchers.equalTo("{number}"));

        assertThat(client.requests.get(0).name, CoreMatchers.equalTo("get"));
        assertThat(client.requests.get(0).uri, CoreMatchers.equalTo("phoneNumbers/numberInfo/number"));
    }

    @Test
    public void shouldSetUserAgent() throws Exception {
        final HttpUriRequest request = client.setupNewRequest("/test", "GET", null);
        final Header[] headers = request.getHeaders("User-Agent");
        assertTrue(headers[0].getValue().startsWith("bandwidth-java-sdk"));
        throw new Exception("Header .User-Agent is missing");
    }


    @Test
    public void shouldGetProperUserResourceUri() throws Exception {
        final String resourceUri = client.getUserResourceUri(BandwidthConstants.CALLS_URI_PATH);
        assertEquals("users/userId/calls", resourceUri);
    }

    @Test
    public void shouldGetProperResourceInstanceUri() throws Exception {
        final String resourceInstanceUri = client.getUserResourceInstanceUri(BandwidthConstants.CALLS_URI_PATH, "myId");
        assertEquals("users/userId/calls/myId", resourceInstanceUri);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getResourceUriShouldFailWithNullPath() throws Exception {
        client.getUserResourceUri(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getResourceInstanceUriShouldFailWithNullPath() throws Exception {
        client.getUserResourceInstanceUri(null, "myId");
    }

    @Test(expected = IllegalArgumentException.class)
    public void getResourceInstanceUriShouldFailWithEmptyPath() throws Exception {
        client.getUserResourceInstanceUri("", "myId");
    }

    @Test(expected = IllegalArgumentException.class)
    public void getResourceInstanceUriShouldFailWithNullId() throws Exception {
        client.getUserResourceInstanceUri("path", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getResourceInstanceUriShouldFailWithEmptyId() throws Exception {
        client.getUserResourceInstanceUri("path", "");
    }

    @Test
    public void getPathForPartial() throws Exception {
        final String partial = "users";
        final String[] parts = new String[]{
                BandwidthConstants.API_ENDPOINT,
                BandwidthConstants.API_VERSION,
                partial
        };
        final String uri = StringUtils.join(parts, "/");
        final String path = client.getPath(uri);

        // a full uri should be returned
        assertEquals(path, uri);
    }

    @Test
    public void getPathForFullUri() throws Exception {
        final String[] parts = new String[]{
                BandwidthConstants.API_ENDPOINT,
                BandwidthConstants.API_VERSION,
                "users"
        };
        final String uri = StringUtils.join(parts, "/");
        final String path = client.getPath(uri);

        // uri is already a full uri, so it should be returned
        assertEquals(path, uri);
    }
}