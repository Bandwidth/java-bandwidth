package com.bandwidth.sdk.model;

import com.bandwidth.sdk.MockRestClient;
import org.hamcrest.CoreMatchers;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class GatherTest extends BaseModelTest {

    @Test
    public void shouldBeCreatedFromJson() throws Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\n" +
                "  \"id\": \"gtr-kj4xloaq5vbpfxyeypndgxa\",\n" +
                "  \"state\": \"completed\",\n" +
                "  \"reason\": \"max-digits\",\n" +
                "  \"createdTime\": \"2014-02-12T19:33:56Z\",\n" +
                "  \"completedTime\": \"2014-02-12T19:33:59Z\",\n" +
                "  \"call\": \"https://api.catapult.inetwork.com/v1/users/u-xa2n3oxk6it4efbglisna6a/calls/c-isw3qup6gvr3ywcsentygnq\",\n" +
                "  \"digits\": \"123\"\n" +
                "}");

        Gather gather = new Gather(mockRestClient, jsonObject);
        assertThat(gather.getId(), CoreMatchers.equalTo("gtr-kj4xloaq5vbpfxyeypndgxa"));
        assertThat(gather.getCall(), CoreMatchers.equalTo("https://api.catapult.inetwork.com/v1/users/u-xa2n3oxk6it4efbglisna6a/calls/c-isw3qup6gvr3ywcsentygnq"));
        assertThat(gather.getState(), CoreMatchers.equalTo("completed"));
        assertThat(gather.getDigits(), CoreMatchers.equalTo("123"));
    }

    @Test
    public void shouldUpdateGather() throws Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\n" +
                "  \"id\": \"gtr-kj4xloaq5vbpfxyeypndgxa\",\n" +
                "  \"state\": \"completed\",\n" +
                "  \"reason\": \"max-digits\",\n" +
                "  \"createdTime\": \"2014-02-12T19:33:56Z\",\n" +
                "  \"completedTime\": \"2014-02-12T19:33:59Z\",\n" +
                "  \"call\": \"https://api.catapult.inetwork.com/v1/users/u-xa2n3oxk6it4efbglisna6a/calls/c-isw3qup6gvr3ywcsentygnq\",\n" +
                "  \"digits\": \"123\"\n" +
                "}");
        mockRestClient.result = jsonObject;

        Gather gather = new Gather(mockRestClient, jsonObject);
        gather.complete();

        assertThat(mockRestClient.requests.get(0).name, equalTo("post"));
        assertThat(mockRestClient.requests.get(0).uri, equalTo("parentUri/gtr-kj4xloaq5vbpfxyeypndgxa"));
        assertThat(mockRestClient.requests.get(0).params.get("state").toString(), equalTo("completed"));
    }
}