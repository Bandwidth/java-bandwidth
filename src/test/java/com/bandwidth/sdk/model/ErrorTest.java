package com.bandwidth.sdk.model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ErrorTest extends BaseModelTest {

    @Test
    public void shouldBeCreatedFromJson() throws Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\n" +
                "  \"message\": \"The callback server took too long to respond\",\n" +
                "  \"id\": \"ue-asvdtalmmhka2i63uzt66ma\",\n" +
                "  \"category\": \"unavailable\",\n" +
                "  \"time\": \"2014-08-18T18:26:03Z\",\n" +
                "  \"details\": [\n" +
                "    {\n" +
                "      \"id\": \"ued-fe55qxtkv73fjkf2643ocoa\",\n" +
                "      \"name\": \"callId\",\n" +
                "      \"value\": \"c-bxu3tvqqde2bgavzw5awu2i\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"ued-5driawmrhgg4rtnwzl5dkoq\",\n" +
                "      \"name\": \"callbackUrl\",\n" +
                "      \"value\": \"http://call/callback.json\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"ued-d2suixvquntgpotdncyzqwy\",\n" +
                "      \"name\": \"callbackMethod\",\n" +
                "      \"value\": \"POST\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"ued-eq5lncjtcjrela3begu2vmi\",\n" +
                "      \"name\": \"callbackEvent\",\n" +
                "      \"value\": \"hangup\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"ued-a7am5v3s6hu57mybsgd7lyy\",\n" +
                "      \"name\": \"callbackTimeout\",\n" +
                "      \"value\": \"10000\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"code\": \"callback-server-timeout\"\n" +
                "}");
        Error error = new Error(null, "parentUri", jsonObject);

        assertThat(error.getId(), equalTo("ue-asvdtalmmhka2i63uzt66ma"));
        assertThat(error.getCategory(), equalTo("unavailable"));
        assertThat(error.getMessage(), equalTo("The callback server took too long to respond"));

        List<ErrorDetail> details = error.getDetails();
        assertThat(details.size(), equalTo(5));
        assertThat(details.get(0).getId(), equalTo("ued-fe55qxtkv73fjkf2643ocoa"));
        assertThat(details.get(0).getName(), equalTo("callId"));
        assertThat(details.get(0).getValue(), equalTo("c-bxu3tvqqde2bgavzw5awu2i"));
        assertThat(details.get(1).getValue(), equalTo("http://call/callback.json"));
        assertThat(details.get(1).getName(), equalTo("callbackUrl"));
    }
}