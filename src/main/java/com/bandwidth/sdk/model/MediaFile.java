package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.BandwidthClient;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;

/**
 * Information about media file.
 *
 * @author vpotapenko
 */
public class MediaFile extends ResourceBase {

    public MediaFile(BandwidthClient client, JSONObject jsonObject) {
        super(client, jsonObject);
    }
    
    @Override
    protected void setUp(JSONObject jsonObject) {
        this.id = (String) jsonObject.get("id");
        updateProperties(jsonObject);
    }          
    

    public Long getContentLength() {
        return getPropertyAsLong("contentLength");
    }

    public String getMediaName() {
        return getPropertyAsString("mediaName");
    }

    public String getContent() {
        return getPropertyAsString("content");
    }

    /**
     * Downloads existing media file from server.
     *
     * @param destFile file for putting content. Will be overridden.
     * @throws IOException
     */
    public void downloadTo(File destFile) throws IOException {
        client.download(getUri(), destFile);
    }

    /**
     * Deletes media file permanently.
     *
     * @throws IOException
     */
    public void delete() throws IOException {
        client.delete(getUri());
    }

    @Override
    public String toString() {
        return "MediaFile{" +
                "mediaName='" + getMediaName() + '\'' +
                ", contentLength='" + getContentLength() + '\'' +
                ", content='" + getContent() + '\'' +
                '}';
    }

    protected String getUri() {
        return client.getUserResourceInstanceUri(BandwidthConstants.MEDIA_URI_PATH, getMediaName());
    }

}
