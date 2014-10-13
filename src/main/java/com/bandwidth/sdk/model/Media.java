package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.BandwidthRestClient;
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

    public Media(BandwidthRestClient client) {
        super(client, null);
    }
    
    /**
     * Factory method for MediaFile list, returns a list of MediaFile object with default page, size
     * @return
     * @throws IOException
     */
    public static ResourceList<MediaFile> getMediaFiles() throws IOException {
    	
    	// default page size is 25
     	return getMediaFiles(0, 25);
    }
    
    /**
     * Factory method for MediaFile list, returns a list of MediaFile objects with page, size preference
     * @param page
     * @param size
     * @return
     * @throws IOException
     */
    public static ResourceList<MediaFile> getMediaFiles(int page, int size) throws IOException {
    	
        String mediaUri = BandwidthRestClient.getInstance().getUserResourceUri(BandwidthConstants.MEDIA_URI_PATH);

        ResourceList<MediaFile> mediaFiles = 
        			new ResourceList<MediaFile>(page, size, mediaUri, MediaFile.class);

        mediaFiles.initialize();
        
        return mediaFiles;
    }
    
    /**
     * Gets a list of your media files.
     * For test only
     * 
     * TODO - refactor tests to use static method. Must provide someway to use the mock client for the 
     * static methods
     * 
     *
     * @return list of media files.
     * @throws IOException
     */
    public List<MediaFile> getMediaFiles(BandwidthRestClient client) throws IOException {
        String uri = getUri();
        JSONArray array = client.getArray(uri, null);

        List<MediaFile> mediaFiles = new ArrayList<MediaFile>();
        for (Object obj : array) {
            mediaFiles.add(new MediaFile(client, (JSONObject) obj));
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
        client.uploadFile(uri, file, contentType);

        // TODO - refactor to use the static method. Must provide a way to use singleton with tests.
        List<MediaFile> mediaFiles = getMediaFiles(client);
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
        client.downloadFileTo(uri, file);
    }

    @Override
    protected String getUri() {
        return client.getUserResourceUri(BandwidthConstants.MEDIA_URI_PATH);
//        return StringUtils.join(new String[]{
//                parentUri,
//                "media"
//        }, '/');
    }


}
