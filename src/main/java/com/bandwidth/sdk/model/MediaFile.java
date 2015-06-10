package com.bandwidth.sdk.model;

import java.io.File;
import java.io.IOException;

import org.json.simple.JSONObject;

import com.bandwidth.sdk.BandwidthClient;
import com.bandwidth.sdk.BandwidthConstants;

/**
 * Information about media file.
 *
 * @author vpotapenko
 */
public class MediaFile extends ResourceBase {

    public MediaFile(final BandwidthClient client, final JSONObject jsonObject) {
        super(client, jsonObject);
    }
    
    @Override
    protected void setUp(final JSONObject jsonObject) {
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
     * @throws IOException unexpected error.
     */
    public void downloadTo(final File destFile) throws IOException {
        client.download(getUri(), destFile);
    }

    /**
     * Deletes media file permanently.
     *
     * @throws IOException unexpected error.
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
