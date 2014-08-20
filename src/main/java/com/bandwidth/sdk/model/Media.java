package com.bandwidth.sdk.model;

import com.bandwidth.sdk.driver.IRestDriver;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Point for <code>/v1/users/{userId}/media</code>
 *
 * @author vpotapenko
 */
public class Media extends BaseModelObject {

    public Media(IRestDriver driver, String parentUri) {
        super(driver, parentUri, null);
    }

    /**
     * Gets a list of your media files.
     *
     * @return list of media files.
     * @throws IOException
     */
    public List<MediaFile> getMediaFiles() throws IOException {
        String uri = getUri();
        JSONArray array = driver.getArray(uri, null);

        List<MediaFile> mediaFiles = new ArrayList<MediaFile>();
        for (Object obj : array) {
            mediaFiles.add(new MediaFile(driver, uri, (JSONObject) obj));
        }
        return mediaFiles;
    }

    /**
     * Uploads media file.
     *
     * @param mediaName new name of media file
     * @param file source file for uploading
     * @param contentType MIME type of file or <code>null</code>
     * @return new media file object
     * @throws IOException
     */
    public MediaFile upload(String mediaName, File file, String contentType) throws IOException {
        String uri = StringUtils.join(new String[]{
                getUri(),
                mediaName
        }, '/');
        driver.uploadFile(uri, file, contentType);

        List<MediaFile> mediaFiles = getMediaFiles();
        for (MediaFile mediaFile : mediaFiles) {
            if (StringUtils.equals(mediaFile.getMediaName(), mediaName)) return mediaFile;
        }
        return null;
    }

    /**
     * Downloads existing media file from server.
     *
     * @param mediaName name of media
     * @param file file for putting content. Will be overridden.
     * @throws IOException
     */
    public void download(String mediaName, File file) throws IOException {
        String uri = StringUtils.join(new String[]{
                getUri(),
                mediaName
        }, '/');
        driver.downloadFileTo(uri, file);
    }

    @Override
    protected String getUri() {
        return StringUtils.join(new String[]{
                parentUri,
                "media"
        }, '/');
    }


}
