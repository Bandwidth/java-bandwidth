package com.bandwidth.sdk.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;

import com.bandwidth.sdk.RestResponse;
import com.bandwidth.sdk.TestsHelper;

public class RecordingTest extends BaseModelTest {

    @Before
    public void setUp(){
        super.setUp();
    }

    @Test
    public void shouldGetRecordingList() throws Exception {
        RestResponse restResponse = new RestResponse();
		
        restResponse.setResponseText("[\n" +
                "{\n" +
                "    \"endTime\": \"2013-02-08T13:17:12Z\",\n" +
                "    \"id\": \"{recordingId1}\",\n" +
                "    \"media\": \"https://.../v1/users/.../media/{callId1}-1.wav\",\n" +
                "    \"call\": \"https://.../v1/users/.../calls/{callId1}\",\n" +
                "    \"startTime\": \"2013-02-08T13:15:47Z\",\n" +
                "    \"state\": \"complete\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"endTime\": \"2013-02-08T14:05:15Z\",\n" +
                "    \"id\": \"{recordingId2}\",\n" +
                "    \"media\": \"https://.../v1/users/.../media/{callId1}-2.wav\",\n" +
                "    \"call\": \"https://.../v1/users/.../calls/{callId1}\",\n" +
                "    \"startTime\": \"2013-02-08T14:03:47Z\",\n" +
                "    \"state\": \"complete\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"endTime\": \"2013-02-08T13:34:07Z\",\n" +
                "    \"id\": \"{recordingId3}\",\n" +
                "    \"media\": \"https://.../v1/users/.../media/{callId2}-1.wav\",\n" +
                "    \"call\": \"https://.../v1/users/.../calls/{call2}\",\n" +
                "    \"startTime\": \"2013-02-08T13:28:47Z\",\n" +
                "    \"state\": \"complete\"\n" +
                "  }\n" +
                "]");

        restResponse.setContentType("application/json");
        restResponse.setStatus(201);
         
        mockRestClient.setRestResponse(restResponse);

        
        List<Recording> list = Recording.getRecordings(mockRestClient, 0, 5);
        
        assertThat(list.size(), equalTo(3));
        assertThat(list.get(0).getId(), equalTo("{recordingId1}"));
        assertThat(list.get(1).getCall(), equalTo("https://.../v1/users/.../calls/{callId1}"));
        assertThat(list.get(2).getState(), equalTo("complete"));

        assertThat(mockRestClient.requests.get(0).name, equalTo("get"));
        assertThat(mockRestClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/recordings"));
        assertThat(mockRestClient.requests.get(0).params.get("page").toString(), equalTo("0"));
        assertThat(mockRestClient.requests.get(0).params.get("size").toString(), equalTo("5"));
    }

    
    @Test
    public void shouldGetRecordingById() throws Exception {
        mockRestClient.result = (JSONObject) new JSONParser().parse("{\n" +
                "  \"endTime\": \"2013-02-08T14:05:15Z\",\n" +
                "  \"id\": \"{recordingId2}\",\n" +
                "  \"media\": \"https://.../v1/users/.../media/{callId1}-2.wav\",\n" +
                "  \"call\": \"https://.../v1/users/.../calls/{callId1}\",\n" +
                "  \"startTime\": \"2013-02-08T14:03:47Z\",\n" +
                "  \"state\": \"complete\"\n" +
                "}");

        Recording recording = Recording.getRecording(mockRestClient, "{recordingId2}");
        assertThat(recording.getId(), equalTo("{recordingId2}"));
        assertThat(recording.getState(), equalTo("complete"));

        assertThat(mockRestClient.requests.get(0).name, equalTo("getObject"));
        assertThat(mockRestClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/recordings/{recordingId2}"));
    }
    
}