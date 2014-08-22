package com.bandwidth.sdk.model;

import com.bandwidth.sdk.MockRestClient;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class MediaTest {

    private MockRestClient mockRestClient;
    private Media media;

    @Before
    public void setUp() throws Exception {
        mockRestClient = new MockRestClient();
        media = new Media(mockRestClient, "parentUri");
    }

    @Test
    public void shouldGetMediaFiles() throws Exception {
        mockRestClient.arrayResult = (JSONArray) new JSONParser().parse("[\n" +
                "  {\n" +
                "    \"contentLength\": 561276,\n" +
                "    \"mediaName\": \"{mediaName1}\",\n" +
                "    \"content\": \"https://api.com/v1/users/users/{userId}/media/{mediaName1}\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"contentLength\": 2703360,\n" +
                "    \"mediaName\": \"{mediaName2}\",\n" +
                "    \"content\": \"https://api.com/v1/users/users/{userId}/media/{mediaName2}\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"contentLength\": 588,\n" +
                "    \"mediaName\": \"{mediaName3}\",\n" +
                "    \"content\": \"https://api.com/v1/users/users/{userId}/media/{mediaName3}\"\n" +
                "  }\n" +
                "]");

        List<MediaFile> mediaFiles = media.getMediaFiles();
        assertThat(mediaFiles.size(), equalTo(3));
        assertThat(mediaFiles.get(0).getMediaName(), equalTo("{mediaName1}"));
        assertThat(mediaFiles.get(0).getUri(), equalTo("parentUri/media/{mediaName1}"));

        assertThat(mockRestClient.requests.get(0).name, equalTo("getArray"));
        assertThat(mockRestClient.requests.get(0).uri, equalTo("parentUri/media"));
    }

    @Test
    public void shouldUploadMediaFile() throws Exception {
        mockRestClient.arrayResult = (JSONArray) new JSONParser().parse("[\n" +
                "  {\n" +
                "    \"contentLength\": 561276,\n" +
                "    \"mediaName\": \"{mediaName1}\",\n" +
                "    \"content\": \"https://api.com/v1/users/users/{userId}/media/{mediaName1}\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"contentLength\": 2703360,\n" +
                "    \"mediaName\": \"{mediaName2}\",\n" +
                "    \"content\": \"https://api.com/v1/users/users/{userId}/media/{mediaName2}\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"contentLength\": 588,\n" +
                "    \"mediaName\": \"{mediaName3}\",\n" +
                "    \"content\": \"https://api.com/v1/users/users/{userId}/media/{mediaName3}\"\n" +
                "  }\n" +
                "]");

        MediaFile mediaFile = media.upload("{mediaName3}", new File("path_to_file"), null);
        assertThat(mediaFile.getUri(), equalTo("parentUri/media/{mediaName3}"));

        assertThat(mockRestClient.requests.get(0).name, equalTo("uploadFile"));
        assertThat(mockRestClient.requests.get(0).uri, equalTo("parentUri/media/{mediaName3}"));
        assertThat(mockRestClient.requests.get(0).params.get("filePath").toString(), equalTo("path_to_file"));
    }
}