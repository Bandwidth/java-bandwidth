package com.bandwidth.sdk.model;

import org.hamcrest.CoreMatchers;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;

import static org.junit.Assert.assertThat;

public class EventTest {

    @Test
    public void shouldBeCreatedFromJson() throws Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"id\":\"ce-hsdbdbdhd\",\"time\":1407916959116,\"name\":\"error\",\"data\":\"Call Id wasn't found on FreeSWITCH anymore\"}");

        Event event = Event.from(jsonObject);
        assertThat(event.getId(), CoreMatchers.equalTo("ce-hsdbdbdhd"));
        assertThat(event.getData().toString(), CoreMatchers.equalTo("Call Id wasn't found on FreeSWITCH anymore"));

    }
}