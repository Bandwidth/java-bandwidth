package com.bandwidth.sdk.model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ConferenceMemberTest extends BaseModelTest {

    @Test
    public void shouldBeCreatedFromJson() throws Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\n" +
                "      \"addedTime\": \"2013-07-12T15:54:47Z\",\n" +
                "      \"hold\": false,\n" +
                "      \"id\": \"{memberId1}\",\n" +
                "      \"mute\": false,\n" +
                "      \"state\": \"active\",\n" +
                "      \"joinTone\": false,\n" +
                "      \"leavingTone\": false,\n" +
                "      \"call\": \"https://localhost:8444/v1/users/{userId}/calls/{callId1}\"\n" +
                "  }");
        ConferenceMember member = new ConferenceMember(mockClient, jsonObject);

        assertThat(member.getId(), equalTo("{memberId1}"));
        assertThat(member.getCall(), equalTo("https://localhost:8444/v1/users/{userId}/calls/{callId1}"));
        assertThat(member.isHold(), equalTo(false));
        assertThat(member.getState(), equalTo("active"));
    }
}