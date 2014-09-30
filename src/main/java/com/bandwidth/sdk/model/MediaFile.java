package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthRestClient;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;

/**
 * Information about media file.
 *
 * @author vpotapenko
 */
public class MediaFile extends BaseModelObject {

    public MediaFile(BandwidthRestClient client, String parentUri, JSONObject jsonObject) {
        super(client, parentUri, jsonObject);
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
        client.downloadFileTo(getUri(), destFile);
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

    @Override
    protected String getUri() {
        //TODO: what should this be?
        return null;
//        return StringUtils.join(new String[]{
//                parentUri,
//                getMediaName()
//        }, '/');
    }

}
