package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.BandwidthClient;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.Date;

/**
 * Information about recording
 *
 * @author vpotapenko
 */
public class Recording extends ResourceBase {
	
	/**
	 * Factory method for Recording list, returns list of Recording objects with default page, size
	 * @return
	 * @throws IOException
	 */
    public static ResourceList<Recording> list() throws IOException {
    	
    	// default page size is 25
     	return list(0, 25);
    }
    
    /**
     * Factory method for Recording list, returns list of Recording objects with page, size preference
     * @param page
     * @param size
     * @return
     * @throws IOException
     */
    public static ResourceList<Recording> list(int page, int size) throws IOException {
    	        
        return list(BandwidthClient.getInstance(), page, size);
    }
    
    /**
     * Factory method for Recording list, returns list of Recording objects with page, size preference
     * @param page
     * @param size
     * @return
     * @throws IOException
     */
    public static ResourceList<Recording> list(BandwidthClient client, int page, int size) throws IOException {
    	
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
    public static Recording get(String id) throws Exception {
    	
        return get(BandwidthClient.getInstance(), id);
    }
    
    /**
     * Recording factory method. Returns recording object from id
     * @param id
     * @return
     * @throws IOException
     */
    public static Recording get(BandwidthClient client, String id) throws Exception {
    	
        String recordingsUri = client.getUserResourceUri(BandwidthConstants.RECORDINGS_URI_PATH);
        String uri = StringUtils.join(new String[]{
                recordingsUri,
                id
        }, '/');
        JSONObject jsonObject = toJSONObject(client.get(uri, null));
        return new Recording(client, recordingsUri, jsonObject);
    }
    
    

	String parentUri;

    public Recording(BandwidthClient client, String parentUri, JSONObject jsonObject) {
        super(client, jsonObject);
        this.parentUri = parentUri;
    }
    
    public Recording(BandwidthClient client, JSONObject jsonObject) {
        super(client, jsonObject);
    }
    
    @Override
    protected void setUp(JSONObject jsonObject) {
        this.id = (String) jsonObject.get("id");
        updateProperties(jsonObject);
    }      
    
    
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
