package com.bandwidth.sdk.model;

import com.bandwidth.sdk.AppPlatformException;
import com.bandwidth.sdk.MockClient;
import com.bandwidth.sdk.RestResponse;
import com.bandwidth.sdk.TestsHelper;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class RecordingTest extends BaseModelTest {
	
	MockClient mockClient;

    @Before
    public void setUp(){
        super.setUp();
        
        mockClient = new MockClient();
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
         
        mockClient.setRestResponse(restResponse);

        
        List<Recording> list = Recording.list(mockClient, 0, 5);
        
        assertThat(list.size(), equalTo(3));
        assertThat(list.get(0).getId(), equalTo("{recordingId1}"));
        assertThat(list.get(1).getCall(), equalTo("https://.../v1/users/.../calls/{callId1}"));
        assertThat(list.get(2).getState(), equalTo("complete"));

        assertThat(mockClient.requests.get(0).name, equalTo("get"));
        assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/recordings"));
        assertThat(mockClient.requests.get(0).params.get("page").toString(), equalTo("0"));
        assertThat(mockClient.requests.get(0).params.get("size").toString(), equalTo("5"));
    }

    
    @Test
    public void shouldGetRecordingById() throws Exception {
        
        RestResponse response = new RestResponse();
        response.setResponseText("{\n" +
                "  \"endTime\": \"2013-02-08T14:05:15Z\",\n" +
                "  \"id\": \"{recordingId2}\",\n" +
                "  \"media\": \"https://.../v1/users/.../media/{callId1}-2.wav\",\n" +
                "  \"call\": \"https://.../v1/users/.../calls/{callId1}\",\n" +
                "  \"startTime\": \"2013-02-08T14:03:47Z\",\n" +
                "  \"state\": \"complete\"\n" +
                "}");
        mockClient.setRestResponse(response);

        Recording recording = Recording.get(mockClient, "{recordingId2}");
        assertThat(recording.getId(), equalTo("{recordingId2}"));
        assertThat(recording.getState(), equalTo("complete"));

        assertThat(mockClient.requests.get(0).name, equalTo("get"));
        assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/recordings/{recordingId2}"));
    }

    @Test(expected = AppPlatformException.class)
    public void shouldFailGetRecordingById() throws Exception {

        RestResponse response = new RestResponse();
        response.setStatus(HttpStatus.SC_BAD_REQUEST);
        mockClient.setRestResponse(response);

        Recording.get(mockClient, "{recordingId2}");
    }
    
}
