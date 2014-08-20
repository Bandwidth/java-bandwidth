package com.bandwidth.sdk.model;

import com.bandwidth.sdk.driver.IRestDriver;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;

/**
 * @author vpotapenko
 */
public class MediaFile extends BaseModelObject {

    public MediaFile(IRestDriver driver, String parentUri, JSONObject jsonObject) {
        super(driver, parentUri, jsonObject);
    }

    @Override
    public String getUri() {
        return StringUtils.join(new String[]{
                parentUri,
                getMediaName()
        }, '/');
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

    public void downloadTo(File destFile) throws IOException {
        driver.downloadFileTo(getUri(), destFile);
    }

    public void delete() throws IOException {
        driver.delete(getUri());
    }

    @Override
    public String toString() {
        return "MediaFile{" +
                "mediaName='" + getMediaName() + '\'' +
                ", contentLength='" + getContentLength() + '\'' +
                ", content='" + getContent() + '\'' +
                '}';
    }
}
