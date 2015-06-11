package com.bandwidth.sdk.model;

import com.bandwidth.sdk.AppPlatformException;
import com.bandwidth.sdk.BandwidthClient;
import com.bandwidth.sdk.BandwidthConstants;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Point for <code>/v1/users/{userId}/media</code>
 *
 * @author vpotapenko
 */
public class Media extends ResourceBase {

    /**
     * Gets information about a previously sent or received MediaFile.
     *
     * @param id media file id
     * @return information about media file
     * @throws IOException unexpected error.
     */
    public static MediaFile get(final String id) throws Exception {
    	
        return get(BandwidthClient.getInstance(), id);
    }
    
    /**
     * Gets information about a previously sent or received MediaFile.
     * @param client the client.
     * @param id MediaFile id
     * @return information about MediaFile
     * @throws IOException unexpected error.
     */
    public static MediaFile get(final BandwidthClient client, final String id) throws Exception {
    	
        final String mediaUri = client.getUserResourceInstanceUri(BandwidthConstants.MEDIA_URI_PATH, id);

        final JSONObject jsonObject = toJSONObject(client.get(mediaUri, null));
        return new MediaFile(client, jsonObject);
    }
	
	
    /**
     * Factory method for MediaFile list, returns a list of MediaFile object with default page, size
     * @return the list
     * @throws IOException unexpected error.
     */
    public static ResourceList<MediaFile> list() throws IOException {
    	
    	// default page size is 25
     	return list(0, 25);
    }
    
    /**
     * Factory method for MediaFile list, returns a list of MediaFile objects with page, size preference
     * @param page the page
     * @param size the page size
     * @return the list
     * @throws IOException unexpected error.
     */
    public static ResourceList<MediaFile> list(final int page, final int size) throws IOException {
    	
        
        return list(BandwidthClient.getInstance(), page, size);
    }
    
    /**
     * Factory method for MediaFile list, returns a list of MediaFile objects with page, size preference
     * @param client the client.
     * @param page the page
     * @param size the page size
     * @return the list
     * @throws IOException unexpected error.
     */
    public static ResourceList<MediaFile> list(final BandwidthClient client, final int page, final int size) throws IOException {
    	
        final String mediaUri = client.getUserResourceUri(BandwidthConstants.MEDIA_URI_PATH);

        final ResourceList<MediaFile> mediaFiles = 
        			new ResourceList<MediaFile>(page, size, mediaUri, MediaFile.class);
        mediaFiles.setClient(client);
        mediaFiles.initialize();
        
        return mediaFiles;
    }

    /**
     * Factory method for a Media object. Use the returned media object to upload or download a file.
     * @return the Media
     */
    public static Media create() {

        return new Media(BandwidthClient.getInstance());
    }

    public Media(final BandwidthClient client) {
        super(client, null);
    }
    
    @Override
    protected void setUp(final JSONObject jsonObject) {
    	
    	if (jsonObject != null) {
    		this.id = (String) jsonObject.get("id");
    		updateProperties(jsonObject);
    	}
    	// TODO handle the null case
    }          
        

    /**
     * Uploads media file.
     *
     * @param mediaName new name of media file
     * @param file source file for uploading
     * @param contentType MIME type of file or <code>null</code>
     * @return new media file object
     * @throws IOException unexpected error.
     * @throws AppPlatformException unexpected exception.
     */
    public MediaFile upload(final String mediaName, final File file, final MediaMimeType contentType)
            throws IOException, AppPlatformException {

        final String uri = StringUtils.join(new String[]{
                getUri(),
                mediaName
        }, '/');        
        client.upload(uri, file, contentType.toString());

        final List<MediaFile> mediaFiles = list(client, 0, 25);
        for (final MediaFile mediaFile : mediaFiles) {
            if (StringUtils.equals(mediaFile.getMediaName(), mediaName)) return mediaFile;
        }
        return null;
    }

    /**
     * Downloads existing media file from server.
     *
     * @param mediaName name of media
     * @param file file for putting content. Will be overridden.
     * @throws IOException unexpected error.
     */
    public void download(final String mediaName, final File file) throws IOException {

        final String uri = client.getUserResourceInstanceUri(BandwidthConstants.MEDIA_URI_PATH, mediaName);        

        client.download(uri, file);
    }

    protected String getUri() {
  
        return client.getUserResourceUri(BandwidthConstants.MEDIA_URI_PATH);
    }


}
