package com.bandwidth.sdk.calls;

import com.bandwidth.sdk.BandwidthRestClient;
import com.bandwidth.sdk.driver.MockRestDriver;
import org.hamcrest.CoreMatchers;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class BandwidthGatherTest {

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

        BandwidthGather gather = BandwidthGather.from(null, null, jsonObject);
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
        MockRestDriver mockRestDriver = new MockRestDriver();
        mockRestDriver.result = jsonObject;

        BandwidthRestClient client = new BandwidthRestClient("", "", "");
        client.setRestDriver(mockRestDriver);

        BandwidthGather gather = BandwidthGather.from(client, null, jsonObject);
        gather.complete();

        assertThat(mockRestDriver.requests.get(0).name, equalTo("updateCallGather"));
        assertThat(mockRestDriver.requests.get(0).params.get("state").toString(), equalTo("completed"));
    }
}