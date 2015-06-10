package com.bandwidth.sdk.model;

import com.bandwidth.sdk.AppPlatformException;
import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.BandwidthClient;
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
public class Bridge extends ResourceBase {
	
	
    /**
     * Convenience method to get information about a specific bridge. Returns a Bridge object given an id
     *
     * @param id bridge id
     * @return information about a specific bridge
     * @throws IOException unexpected error.
     */
    public static Bridge get(final String id) throws Exception {
        
        final BandwidthClient client = BandwidthClient.getInstance();
        return get(client, id); 
    }
    
    
    /**
     * Convenience method to return a bridge object given a client and an id
     * @param client the client
     * @param id the call id
     * @return the Bridge
     * @throws IOException unexpected error.
     */
    public static Bridge get(final BandwidthClient client, final String id) throws Exception {
        assert(client != null);
    	final String bridgesUri =  client.getUserResourceInstanceUri(BandwidthConstants.BRIDGES_URI_PATH, id);
        final JSONObject jsonObject = toJSONObject(client.get(bridgesUri, null));
        return new Bridge(client, jsonObject);
        
    }
    

	
	/**
	 * Convenience factory method for Bridge, returns Bridge object from an id
	 * @param id the call id
     * @return the Bridge
	 * @throws IOException unexpected error.
	 */
	public static Bridge create(final String id) throws Exception {
		return Bridge.create(id, null);
	}
	
	/**
	 * Convenience factory method to create a Bridge object from two Call objects
	 * @param call1 the call id
     * @param call2 the call id
     * @return the Bridge
	 * @throws IOException unexpected error.
	 */
    public static Bridge create(final Call call1, final Call call2)
    	    throws Exception {
        assert (call1 != null);
    	final String callId1 = call1.getId();
    	final String callId2 = call2.getId();
    	return Bridge.create(callId1, callId2);
    }

    /**
     * Convenience factory method to create a Bridge object from two call ids
     * @param callId1 the call id
     * @param callId2 the call id
     * @return the Bridge
     * @throws IOException unexpected error.
     */
    public static Bridge create(final String callId1, final String callId2)
    	    throws Exception {
    	assert (callId1 != null);
    	final BandwidthClient client = BandwidthClient.getInstance();
    	return create(client, callId1, callId2);
	}
    
    /**
     * Convenience method to create a Bridge object from two call ids
     * @param client the client
     * @param callId1 the call id
     * @param callId2 the call id
     * @return the Bridge
     * @throws IOException unexpected error.
     */
    public static Bridge create(final BandwidthClient client, final String callId1, final String callId2)
    	    throws Exception 
    {
    	assert (callId1 != null);

    	final HashMap<String, Object> params = new HashMap<String, Object>();

    	params.put("bridgeAudio", "true");
    	final String[] callIds = new String[] { callId1, callId2 };
    	params.put("callIds", callIds == null ? Collections.emptyList()
    		: Arrays.asList(callIds));

    	return create(client, params);
	}
    
    /**
     * Convenience factory method to create a Bridge object from a params maps
     * @param client the client
     * @param params the params
     * @return the Bridge
     * @throws IOException unexpected error.
     */
    public static Bridge create(final BandwidthClient client, final Map<String, Object>params)
    	    throws Exception {
    	
        assert (client!= null && params != null);
    	final String bridgesUri =  client.getUserResourceUri(BandwidthConstants.BRIDGES_URI_PATH);
    	final RestResponse response = client.post(bridgesUri, params);
    	final JSONObject callObj = toJSONObject(client.get(response.getLocation(), null));
    	final Bridge bridge = new Bridge(client, callObj);
    	return bridge;
	}
    
    
    /**
     * Factory method for Bridge list, returns list of Bridge objects with default page setting
     * @return the list
     * @throws IOException unexpected error.
     */
    public static ResourceList<Bridge> list() throws IOException {
    	
    	// default page size is 25
     	return list(0, 25);
    }
    
    /**
     * Factory method for Bridge list, returns list of Bridge objects with page, size preference
     * @param page the page
     * @param size the page size
     * @return the list
     * @throws IOException unexpected error.
     */
    public static ResourceList<Bridge> list(final int page, final int size) throws IOException {
    	
    	
        return list(BandwidthClient.getInstance(), page, size);
    }
    
    /**
     * Factory method for Bridge list, returns list of Bridge objects with page, size preference
     * @param client the client
     * @param page the page
     * @param size the page size
     * @return the list
     * @throws IOException unexpected error.
     */
    public static ResourceList<Bridge> list(final BandwidthClient client, final int page, final int size) throws IOException {
    	
        final String resourceUri = client.getUserResourceUri(BandwidthConstants.BRIDGES_URI_PATH);

        final ResourceList<Bridge> bridges = 
        			new ResourceList<Bridge>(page, size, resourceUri, Bridge.class);

        bridges.setClient(client);
        bridges.initialize();
        
        return bridges;
    }
    
    

    public Bridge(final BandwidthClient client, final JSONObject jsonObject) {
        super(client, jsonObject);
    }
    
    @Override
    protected void setUp(final JSONObject jsonObject) {
        this.id = (String) jsonObject.get("id");
        updateProperties(jsonObject);
    }    


    protected String getUri() {
        return client.getUserResourceInstanceUri(BandwidthConstants.BRIDGES_URI_PATH, getId());
    }

    /**
     * Gets list of calls that are on the bridge
     *
     * @return list of calls
     * @throws IOException unexpected error.
     */
    public List<Call> getBridgeCalls() throws Exception {
        final String callsPath = StringUtils.join(new String[]{
                getUri(),
                "calls"
        }, '/');
        final JSONArray jsonArray = toJSONArray(client.get(callsPath, null));

        final List<Call> callList = new ArrayList<Call>();
        for (final Object obj : jsonArray) {
            callList.add(new Call(client, (JSONObject) obj));
        }
        return callList;
    }

    /**
     * Sets call ids
     *
     * @param callIds new value
     */
    public void setCallIds(final String[] callIds) {
        putProperty("callIds", Arrays.asList(callIds));
    }

    /**
     * Sets bridge audio
     * @param bridgeAudio new value
     */
    public void setBridgeAudio(final boolean bridgeAudio) {
        putProperty("bridgeAudio", bridgeAudio);
    }

    /**
     * Makes changes ob the bridge.
     *
     * @throws IOException unexpected error.
     */
    public void commit() throws IOException, AppPlatformException {
        final Map<String, Object> params = new HashMap<String, Object>();

        params.put("bridgeAudio", isBridgeAudio());
        final String[] callIds = getCallIds();
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
     * @throws IOException unexpected error.
     */
    public void stopAudioFilePlaying() throws IOException, AppPlatformException {
        new NewBridgeAudioBuilder().fileUrl(StringUtils.EMPTY).create();
    }

    /**
     * Stop an audio sentence.
     *
     * @throws IOException unexpected error.
     */
    public void stopSentence() throws IOException, AppPlatformException {
        new NewBridgeAudioBuilder().sentence(StringUtils.EMPTY).create();
    }

    private void createAudio(final Map<String, Object> params) throws IOException, AppPlatformException {
        final String audioPath = StringUtils.join(new String[]{
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

        public NewBridgeAudioBuilder fileUrl(final String fileUrl) {
            params.put("fileUrl", fileUrl);
            return this;
        }

        public NewBridgeAudioBuilder sentence(final String sentence) {
            params.put("sentence", sentence);
            return this;
        }

        public NewBridgeAudioBuilder gender(final Gender gender) {
            params.put("gender", gender.name());
            return this;
        }

        public NewBridgeAudioBuilder locale(final SentenceLocale locale) {
            params.put("locale", locale.restValue);
            return this;
        }

        public NewBridgeAudioBuilder voice(final String voice) {
            params.put("voice", voice);
            return this;
        }

        public NewBridgeAudioBuilder loopEnabled(final boolean loopEnabled) {
            params.put("loopEnabled", String.valueOf(loopEnabled));
            return this;
        }

        public void create() throws IOException, AppPlatformException {
            createAudio(params);
        }
    }
}
