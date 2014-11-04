package com.bandwidth.sdk;


import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class BandwidthClientTest {

    private MockClient client;

    @Before
    public void setUp() throws Exception {
        client = new MockClient();
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
    
    @Test
    public void getPathForPartial() throws Exception{
        String partial = "users";
        String[] parts = new String[]{
                BandwidthConstants.API_ENDPOINT,
                BandwidthConstants.API_VERSION,
                partial
        };
        String uri = StringUtils.join(parts, "/");
        String path = client.getPath(uri);

        // a full uri should be returned
        assertEquals(path, uri);
    }
    
    @Test
    public void getPathForFullUri() throws Exception{
        String[] parts = new String[]{
                BandwidthConstants.API_ENDPOINT,
                BandwidthConstants.API_VERSION,
                "users"
        };
        String uri = StringUtils.join(parts, "/");
        String path = client.getPath(uri);

        // uri is already a full uri, so it should be returned
        assertEquals(path, uri);
    }

}