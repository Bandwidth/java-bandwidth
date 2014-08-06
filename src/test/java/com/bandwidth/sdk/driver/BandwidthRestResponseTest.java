package com.bandwidth.sdk.driver;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class BandwidthRestResponseTest {

    @Test
    public void shouldParseUrlString() {
        BandwidthRestResponse response = new BandwidthRestResponse("https://api.catapult.inetwork.com/v1/users/userId/account", "some response text", 200);
        assertThat(response.getUrl(), equalTo("https://api.catapult.inetwork.com/v1/users/userId/account"));

        response = new BandwidthRestResponse("https://api.catapult.inetwork.com/v1/users?arg1=value1", "some response text", 200);
        assertThat(response.getUrl(), equalTo("https://api.catapult.inetwork.com/v1/users"));
        assertThat(response.getQueryString(), equalTo("arg1=value1"));
    }

    @Test
    public void shouldRecognizeErrorStatus() {
        BandwidthRestResponse response = new BandwidthRestResponse("https://api.catapult.inetwork.com/v1/", "some response text", 200);
        assertThat(response.getHttpStatus(), equalTo(200));
        assertThat(response.isError(), equalTo(false));

        response = new BandwidthRestResponse("https://api.catapult.inetwork.com/v1/", "some response text", 404);
        assertThat(response.getHttpStatus(), equalTo(404));
        assertThat(response.isError(), equalTo(true));
    }
}