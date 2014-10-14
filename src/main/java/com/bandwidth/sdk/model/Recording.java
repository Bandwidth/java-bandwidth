package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.BandwidthRestClient;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.Date;

/**
 * Information about recording
 *
 * @author vpotapenko
 */
public class Recording extends BaseModelObject {
	
	/**
	 * Factory method for Recording list, returns list of Recording objects with default page, size
	 * @return
	 * @throws IOException
	 */
    public static ResourceList<Recording> getRecordings() throws IOException {
    	
    	// default page size is 25
     	return getRecordings(0, 25);
    }
    
    /**
     * Factory method for Recording list, returns list of Recording objects with page, size preference
     * @param page
     * @param size
     * @return
     * @throws IOException
     */
    public static ResourceList<Recording> getRecordings(int page, int size) throws IOException {
    	        
        return getRecordings(BandwidthRestClient.getInstance(), page, size);
    }
    
    /**
     * Factory method for Recording list, returns list of Recording objects with page, size preference
     * @param page
     * @param size
     * @return
     * @throws IOException
     */
    public static ResourceList<Recording> getRecordings(BandwidthRestClient client, int page, int size) throws IOException {
    	
        String recordingUri = client.getUserResourceUri(BandwidthConstants.RECORDINGS_URI_PATH);

        ResourceList<Recording> recordings = 
        			new ResourceList<Recording>(page, size, recordingUri, Recording.class);
        recordings.setClient(client);
        recordings.initialize();
        
        return recordings;
    }
    
    
    /**
     * Recording factory method. Returns recording object from id
     * @param id
     * @return
     * @throws IOException
     */
    public static Recording getRecording(String id) throws IOException {
    	
        return getRecording(BandwidthRestClient.getInstance(), id);
    }
    
    /**
     * Recording factory method. Returns recording object from id
     * @param id
     * @return
     * @throws IOException
     */
    public static Recording getRecording(BandwidthRestClient client, String id) throws IOException {
    	
        String recordingsUri = client.getUserResourceUri(BandwidthConstants.RECORDINGS_URI_PATH);
        String uri = StringUtils.join(new String[]{
                recordingsUri,
                id
        }, '/');
        JSONObject jsonObject = client.getObject(uri);
        return new Recording(client, recordingsUri, jsonObject);
    }
    
    

	

    public Recording(BandwidthRestClient client, String parentUri, JSONObject jsonObject) {
        super(client, parentUri, jsonObject);
    }
    
    public Recording(BandwidthRestClient client, JSONObject jsonObject) {
        super(client, jsonObject);
    }
    

    @Override
    protected String getUri() {
        return null;
    }

    public String getMedia() {
        return getPropertyAsString("media");
    }

    public String getCall() {
        return getPropertyAsString("call");
    }

    public String getState() {
        return getPropertyAsString("state");
    }

    public Date getStartTime() {
        return getPropertyAsDate("startTime");
    }

    public Date getEndTime() {
        return getPropertyAsDate("endTime");
    }

    @Override
    public String toString() {
        return "Recording{" +
                "id='" + getId() + '\'' +
                ", media='" + getMedia() + '\'' +
                ", call='" + getCall() + '\'' +
                ", state='" + getState() + '\'' +
                ", startTime=" + getStartTime() +
                ", endTime=" + getEndTime() +
                '}';
    }
}
