package com.bandwidth.sdk.model;

import com.bandwidth.sdk.driver.IRestDriver;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author vpotapenko
 */
public class Media extends BaseModelObject {

    public Media(IRestDriver driver, String parentUri) {
        super(driver, parentUri, null);
    }

    @Override
    public String getUri() {
        return StringUtils.join(new String[]{
                parentUri,
                "media"
        }, '/');
    }

    public List<MediaFile> getMediaFiles() throws IOException {
        String uri = getUri();
        JSONArray array = driver.getArray(uri, null);

        List<MediaFile> mediaFiles = new ArrayList<MediaFile>();
        for (Object obj : array) {
            mediaFiles.add(new MediaFile(driver, uri, (JSONObject) obj));
        }
        return mediaFiles;
    }

    public MediaFile upload(String name, String filePath, String contentType) throws IOException {
        String uri = StringUtils.join(new String[] {
                getUri(),
                name
        }, '/');
        driver.uploadFile(uri, filePath, contentType);

        List<MediaFile> mediaFiles = getMediaFiles();
        for (MediaFile mediaFile : mediaFiles) {
            if (StringUtils.equals(mediaFile.getMediaName(), name)) return mediaFile;
        }
        return null;
    }
}
