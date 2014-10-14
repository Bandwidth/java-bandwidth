package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.BandwidthRestClient;
import com.bandwidth.sdk.RestResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.*;

/**
 * Information about a specific bridge
 *
 * @author vpotapenko
 */
public class Bridge extends BaseModelObject {
	
	
    /**
     * Convenience method to get information about a specific bridge. Returns a Bridge object given an id
     *
     * @param id bridge id
     * @return information about a specific bridge
     * @throws IOException
     */
    public static Bridge getBridge(String id) throws IOException {
        
        BandwidthRestClient client = BandwidthRestClient.getInstance();
        return getBridge(client, id); 
    }
    
    
    /**
     * Convenience method to return a bridge object given a client and an id
     * @param client
     * @param id
     * @return
     * @throws IOException
     */
    public static Bridge getBridge(BandwidthRestClient client, String id) throws IOException {
        
    	String bridgesUri =  client.getUserResourceUri(BandwidthConstants.BRIDGES_URI_PATH);

        String eventPath = StringUtils.join(new String[]{
                bridgesUri,
                id
        }, '/');

        JSONObject jsonObject = client.getObject(eventPath);
        return new Bridge(client, jsonObject);
    }
    

	
	/**
	 * Convenience factory method for Bridge, returns Bridge object from an id
	 * @param callId
	 * @return
	 * @throws IOException
	 */
	public static Bridge createBridge(String id) throws IOException {
		
		return Bridge.createBridge(id, null);
	}
	
	/**
	 * Convenience factory method to create a Bridge object from two Call objects
	 * @param call1
	 * @param call2
	 * @return
	 * @throws IOException
	 */
    public static Bridge createBridge(Call call1, Call call2)
    	    throws IOException 
    {
    	assert (call1 != null);

    	String callId1 = call1.getId();

    	String callId2 = call2.getId();

    	return Bridge.createBridge(callId1, callId2);
    }

    /**
     * Convenience factory method to create a Bridge object from two call ids
     * @param callId1
     * @param callId2
     * @return
     * @throws IOException
     */
    public static Bridge createBridge(String callId1, String callId2)
    	    throws IOException 
    {
    	assert (callId1 != null);

    	BandwidthRestClient client = BandwidthRestClient.getInstance();

    	return createBridge(client, callId1, callId2);
	}
    
    /**
     * Convenience method to create a Bridge object from two call ids
     * @param callId1
     * @param callId2
     * @return
     * @throws IOException
     */
    public static Bridge createBridge(BandwidthRestClient client, String callId1, String callId2)
    	    throws IOException 
    {
    	assert (callId1 != null);

    	HashMap<String, Object> params = new HashMap<String, Object>();

    	params.put("bridgeAudio", "true");
    	String[] callIds = new String[] { callId1, callId2 };
    	params.put("callIds", callIds == null ? Collections.emptyList()
    		: Arrays.asList(callIds));

    	return createBridge(client, params);
	}
    
    /**
     * Convenience factory method to create a Bridge object from a params maps
     * @param callId1
     * @param callId2
     * @return
     * @throws IOException
     */
    public static Bridge createBridge(BandwidthRestClient client, Map<String, Object>params)
    	    throws IOException {
    	assert (params != null);

    	String bridgesUri =  client.getUserResourceUri(BandwidthConstants.BRIDGES_URI_PATH);

    	RestResponse response = client.post(bridgesUri, params);

    	String bridgeId = response.getLocation().substring(
    		client.getPath(bridgesUri).length());

    	JSONObject callObj = client.getObjectFromLocation(response
    		.getLocation());

    	Bridge bridge = new Bridge(client, callObj);

    	return bridge;
	}
    
    
    /**
     * Factory method for Bridge list, returns list of Bridge objects with default page setting
     * @return
     * @throws IOException
     */
    public static ResourceList<Bridge> getBridges() throws IOException {
    	
    	// default page size is 25
     	return getBridges(0, 25);
    }
    
    /**
     * Factory method for Bridge list, returns list of Bridge objects with page, size preference
     * @param page
     * @param size
     * @return
     * @throws IOException
     */
    public static ResourceList<Bridge> getBridges(int page, int size) throws IOException {
    	
    	
        return getBridges(BandwidthRestClient.getInstance(), page, size);
    }
    
    /**
     * Factory method for Bridge list, returns list of Bridge objects with page, size preference
     * @param page
     * @param size
     * @return
     * @throws IOException
     */
    public static ResourceList<Bridge> getBridges(BandwidthRestClient client, int page, int size) throws IOException {
    	
        String resourceUri = client.getUserResourceUri(BandwidthConstants.BRIDGES_URI_PATH);

        ResourceList<Bridge> bridges = 
        			new ResourceList<Bridge>(page, size, resourceUri, Bridge.class);

        bridges.setClient(client);
        bridges.initialize();
        
        return bridges;
    }
    
    

    public Bridge(BandwidthRestClient client, JSONObject jsonObject) {
        super(client, jsonObject);
    }


    @Override
    protected String getUri() {
        return client.getUserResourceInstanceUri(BandwidthConstants.BRIDGES_URI_PATH, getId());
    }

    /**
     * Gets list of calls that are on the bridge
     *
     * @return list of calls
     * @throws IOException
     */
    public List<Call> getBridgeCalls() throws IOException {
        String callsPath = StringUtils.join(new String[]{
                getUri(),
                "calls"
        }, '/');
        JSONArray jsonArray = client.getArray(callsPath, null);

        List<Call> callList = new ArrayList<Call>();
        for (Object obj : jsonArray) {
            callList.add(new Call(client, (JSONObject) obj));
        }
        return callList;
    }

    /**
     * Sets call ids
     *
     * @param callIds new value
     */
    public void setCallIds(String[] callIds) {
        putProperty("callIds", Arrays.asList(callIds));
    }

    /**
     * Sets bridge audio
     * @param bridgeAudio new value
     */
    public void setBridgeAudio(boolean bridgeAudio) {
        putProperty("bridgeAudio", bridgeAudio);
    }

    /**
     * Makes changes ob the bridge.
     *
     * @throws IOException
     */
    public void commit() throws IOException {
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("bridgeAudio", isBridgeAudio());
        String[] callIds = getCallIds();
        params.put("callIds", callIds == null ? Collections.emptyList() : Arrays.asList(callIds));

        client.post(getUri(), params);
    }

    public String getState() {
        return getPropertyAsString("state");
    }

    public String[] getCallIds() {
        return getPropertyAsStringArray("callIds");
    }

    public String getCalls() {
        return getPropertyAsString("calls");
    }

    public boolean isBridgeAudio() {
        return getPropertyAsBoolean("bridgeAudio");
    }

    public Date getCompletedTime() {
        return getPropertyAsDate("completedTime");
    }

    public Date getCreatedTime() {
        return getPropertyAsDate("createdTime");
    }

    public Date getActivatedTime() {
        return getPropertyAsDate("activatedTime");
    }

    /**
     * Creates new builder for playing an audio file or speaking a sentence in a bridge.
     * <br>Example:<br>
     * <code>bridge.newBridgeAudioBuilder().sentence("Hello").create();</code>
     *
     * @return new builder
     */
    public NewBridgeAudioBuilder newBridgeAudioBuilder() {
        return new NewBridgeAudioBuilder();
    }

    /**
     * Stop an audio file playing.
     *
     * @throws IOException
     */
    public void stopAudioFilePlaying() throws IOException {
        new NewBridgeAudioBuilder().fileUrl(StringUtils.EMPTY).create();
    }

    /**
     * Stop an audio sentence.
     *
     * @throws IOException
     */
    public void stopSentence() throws IOException {
        new NewBridgeAudioBuilder().sentence(StringUtils.EMPTY).create();
    }

    private void createAudio(Map<String, Object> params) throws IOException {
        String audioPath = StringUtils.join(new String[]{
                getUri(),
                "audio"
        }, '/');
        client.post(audioPath, params);
    }

    @Override
    public String toString() {
        return "Bridge{" +
                "id='" + getId() + '\'' +
                ", state=" + getState() +
                ", callIds=" + Arrays.toString(getCallIds()) +
                ", calls='" + getCalls() + '\'' +
                ", bridgeAudio=" + isBridgeAudio() +
                ", completedTime=" + getCompletedTime() +
                ", createdTime=" + getCreatedTime() +
                ", activatedTime=" + getActivatedTime() +
                '}';
    }

    public class NewBridgeAudioBuilder {

        private final Map<String, Object> params = new HashMap<String, Object>();

        public NewBridgeAudioBuilder fileUrl(String fileUrl) {
            params.put("fileUrl", fileUrl);
            return this;
        }

        public NewBridgeAudioBuilder sentence(String sentence) {
            params.put("sentence", sentence);
            return this;
        }

        public NewBridgeAudioBuilder gender(Gender gender) {
            params.put("gender", gender.name());
            return this;
        }

        public NewBridgeAudioBuilder locale(SentenceLocale locale) {
            params.put("locale", locale.restValue);
            return this;
        }

        public NewBridgeAudioBuilder voice(String voice) {
            params.put("voice", voice);
            return this;
        }

        public NewBridgeAudioBuilder loopEnabled(boolean loopEnabled) {
            params.put("loopEnabled", String.valueOf(loopEnabled));
            return this;
        }

        public void create() throws IOException {
            createAudio(params);
        }
    }
}
