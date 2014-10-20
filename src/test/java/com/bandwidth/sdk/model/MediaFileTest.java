package com.bandwidth.sdk.model;

import com.bandwidth.sdk.MockClient;
import com.bandwidth.sdk.TestsHelper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class MediaFileTest extends BaseModelTest {
	
    private MockClient mockClient;

    @Before
    public void setUp(){
    	super.setUp();
        mockClient = new MockClient();
    }
	

    @Test
    public void shouldBeCreatedFromJson() throws Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\n" +
                "    \"contentLength\": 561276,\n" +
                "    \"mediaName\": \"{mediaName1}\",\n" +
                "    \"content\": \"https://api.com/v1/users/users/{userId}/media/{mediaName1}\"\n" +
                "  }");
        MediaFile mediaFile = new MediaFile(mockClient,jsonObject);
        assertThat(mediaFile.getContentLength(), equalTo(561276l));
        assertThat(mediaFile.getContent(), equalTo("https://api.com/v1/users/users/{userId}/media/{mediaName1}"));
        assertThat(mediaFile.getMediaName(), equalTo("{mediaName1}"));
        assertThat(mediaFile.getUri(), equalTo("users/" + TestsHelper.TEST_USER_ID + "/media/{mediaName1}"));
    }

    @Test
    public void shouldDownloadContent() throws Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\n" +
                "    \"contentLength\": 561276,\n" +
                "    \"mediaName\": \"{mediaName1}\",\n" +
                "    \"content\": \"https://api.com/v1/users/users/{userId}/media/{mediaName1}\"\n" +
                "  }");
        MediaFile mediaFile = new MediaFile(mockClient, jsonObject);
        mediaFile.downloadTo(new File("path_to_file"));

        assertThat(mockClient.requests.get(0).name, equalTo("downloadFileTo"));
        assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/media/{mediaName1}"));
        assertThat(mockClient.requests.get(0).params.get("filePath").toString(), equalTo("path_to_file"));
    }

    @Test
    public void shouldBeDeletable() throws Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\n" +
                "    \"contentLength\": 561276,\n" +
                "    \"mediaName\": \"{mediaName1}\",\n" +
                "    \"content\": \"https://api.com/v1/users/users/{userId}/media/{mediaName1}\"\n" +
                "  }");
        MediaFile mediaFile = new MediaFile(mockClient, jsonObject);
        mediaFile.delete();

        assertThat(mockClient.requests.get(0).name, equalTo("delete"));
        assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/media/{mediaName1}"));
    }
}