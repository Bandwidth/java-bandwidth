package com.bandwidth.sdk.model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class MessageTest extends BaseModelTest{

    @Before
    public void setUp(){
        super.setUp();
    }
    @Test
    public void shouldBeCreatedFromJson() throws Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\n" +
                "  \"to\": \"+number1\",\n" +
                "  \"id\": \"m-ckobmmd4fgqumyhssgd6lqy\",\n" +
                "  \"time\": \"2013-10-02T12:15:41Z\",\n" +
                "  \"text\": \"Hello judith\",\n" +
                "  \"direction\": \"in\",\n" +
                "  \"state\": \"received\",\n" +
                "  \"from\": \"+number2\",\n" +
                "  \"messageId\": \"m-ckobmmd4fgqumyhssgd6lqy\",\n" +
                "  \"media\": []\n" +
                "}");
        Message message = new Message(mockRestClient, jsonObject);
        assertThat(message.getId(), equalTo("m-ckobmmd4fgqumyhssgd6lqy"));
        assertThat(message.getFrom(), equalTo("+number2"));
        assertThat(message.getMessageId(), equalTo("m-ckobmmd4fgqumyhssgd6lqy"));
        assertThat(message.getText(), equalTo("Hello judith"));
    }
}