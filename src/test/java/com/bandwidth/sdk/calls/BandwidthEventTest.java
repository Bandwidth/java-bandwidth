package com.bandwidth.sdk.calls;

import org.hamcrest.CoreMatchers;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;

import static org.junit.Assert.*;

public class BandwidthEventTest {

    @Test
    public void shouldBeCreatedFromJson() throws Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"id\":\"ce-hsdbdbdhd\",\"time\":1407916959116,\"name\":\"error\",\"data\":\"Call Id wasn't found on FreeSWITCH anymore\"}");

        BandwidthEvent event = BandwidthEvent.from(jsonObject);
        assertThat(event.getId(), CoreMatchers.equalTo("ce-hsdbdbdhd"));
        assertThat(event.getData().toString(), CoreMatchers.equalTo("Call Id wasn't found on FreeSWITCH anymore"));

    }
}