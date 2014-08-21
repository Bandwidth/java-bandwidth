package com.bandwidth.sdk.model;

import com.bandwidth.sdk.driver.MockRestDriver;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class RecordingsTest {

    private MockRestDriver mockRestDriver;
    private Recordings recordings;

    @Before
    public void setUp() throws Exception {
        mockRestDriver = new MockRestDriver();
        recordings = new Recordings(mockRestDriver, "parentUri");
    }

    @Test
    public void shouldGetRecordingList() throws Exception {
        mockRestDriver.arrayResult = (JSONArray) new JSONParser().parse("[\n" +
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

        List<Recording> list = recordings.queryRecordingsBuilder().size(10).page(5).list();
        assertThat(list.size(), equalTo(3));
        assertThat(list.get(0).getId(), equalTo("{recordingId1}"));
        assertThat(list.get(1).getCall(), equalTo("https://.../v1/users/.../calls/{callId1}"));
        assertThat(list.get(2).getState(), equalTo("complete"));

        assertThat(mockRestDriver.requests.get(0).name, equalTo("getArray"));
        assertThat(mockRestDriver.requests.get(0).uri, equalTo("parentUri/recordings"));
        assertThat(mockRestDriver.requests.get(0).params.get("page").toString(), equalTo("5"));
        assertThat(mockRestDriver.requests.get(0).params.get("size").toString(), equalTo("10"));
    }

    @Test
    public void shouldGetRecordingById() throws Exception {
        mockRestDriver.result = (JSONObject) new JSONParser().parse("{\n" +
                "  \"endTime\": \"2013-02-08T14:05:15Z\",\n" +
                "  \"id\": \"{recordingId2}\",\n" +
                "  \"media\": \"https://.../v1/users/.../media/{callId1}-2.wav\",\n" +
                "  \"call\": \"https://.../v1/users/.../calls/{callId1}\",\n" +
                "  \"startTime\": \"2013-02-08T14:03:47Z\",\n" +
                "  \"state\": \"complete\"\n" +
                "}");

        Recording recording = recordings.getRecording("{recordingId2}");
        assertThat(recording.getId(), equalTo("{recordingId2}"));
        assertThat(recording.getState(), equalTo("complete"));

        assertThat(mockRestDriver.requests.get(0).name, equalTo("getObject"));
        assertThat(mockRestDriver.requests.get(0).uri, equalTo("parentUri/recordings/{recordingId2}"));
    }
}