package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.BandwidthClient;
import com.bandwidth.sdk.RestResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.*;

/**
 * Information about call.
 *
 * @author vpotapenko
 */
public class Call extends ResourceBase {

	/**
     * Factory method for Call, returns information about an active or completed call.
     *
     * @param callId call id
     * @return information about a call
     * @throws IOException
     */
    public static Call get(String callId) throws Exception {

    	BandwidthClient client = BandwidthClient.getInstance();
        
        return get(client, callId);
    }
    
    /**
     * Convenience factory method for Call, returns a Call object given an id
     * @param client
     * @param callId
     */
    public static Call get(BandwidthClient client, String callId) throws Exception {
    	
        String callsUri = client.getUserResourceInstanceUri(BandwidthConstants.CALLS_URI_PATH, callId);
        
        JSONObject jsonObject = toJSONObject(client.get(callsUri, null));
        
        return new Call(client, jsonObject);
    }

    /**
     * Factory method for Call list, returns a list of Call objects with default page, size
     * @return
     * @throws IOException
     */
    public static ResourceList<Call> list() throws IOException {
    	
    	// default page size is 25
     	return list(0, 25);
    }
    
    /**
     * Factor method for Call list, returns a list of Call objects with page, size preference 
     * @param page
     * @param size
     * @return
     * @throws IOException
     */
    public static ResourceList<Call> list(int page, int size) throws IOException {
    	
        return list(BandwidthClient.getInstance(), page, size);
    }
    
    /**
     * Factor method for Call list, returns a list of Call objects with page, size preference 
     * @param page
     * @param size
     * @return
     * @throws IOException
     */
    public static ResourceList<Call> list(BandwidthClient client, int page, int size) throws IOException {
    	
        String callUri = client.getUserResourceUri(BandwidthConstants.CALLS_URI_PATH);

        ResourceList<Call> calls = 
        			new ResourceList<Call>(page, size, callUri, Call.class);
        
        calls.setClient(client);

        calls.initialize();
        
        return calls;
    }
    
    /**
     * Convenience factory method to make an outbound call
     * @param to
     * @param from
     * @return
     * @throws Exception
     */
    public static Call create(String to, String from) throws Exception {
    	
    	return create(to, from, "none", null);
    }
        
    /**
     * Convenience method to dials a call from a phone number to a phone number
     * @param to
     * @param from
     * @param callbackUrl
     * @param maps
     * @return
     * @throws IOException
     */
    public static Call create(String to, String from, String callbackUrl, String tag)  throws Exception
    {
    	assert(to != null && from != null);
    	    	
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("to", to);
    	params.put("from", from);
    	params.put("callbackUrl", callbackUrl);
    	params.put("tag", tag);
    	    	
    	Call call = create(params);
    	    	
    	return call;
    }
    
    /**
     * Dials a call, from a phone number to a phone number.
     * @param params
     * @return
     * @throws IOException
     */
    public static Call create(Map <String, Object>params)  throws Exception
    {
    	assert (params != null);
    	
    	return create(BandwidthClient.getInstance(), params);
    }
    
    /**
     * Dials a call, from a phone number to a phone number.
     * @param params
     * @return
     * @throws IOException
     */
    public static Call create(BandwidthClient client, Map <String, Object>params)  throws Exception
    {
    	assert (client != null && params != null);
    	    	
        String callUri = client.getUserResourceUri(BandwidthConstants.CALLS_URI_PATH);
    	
    	RestResponse response = client.post(callUri, params);
    	    	
    	// success here, otherwise an exception is generated
    	
    	String callId = response.getLocation().substring(client.getPath(callUri).length() + 1);
    	
    	Call call = get(client, callId);
    	    	    	
    	return call;
    }
    


    public Call(BandwidthClient client, JSONObject jsonObject) {
        super(client, jsonObject);
    }
    
    @Override
    protected void setUp(JSONObject jsonObject) {
        this.id = (String) jsonObject.get("id");
        updateProperties(jsonObject);
    }      

    protected String getUri() {
        return client.getUserResourceInstanceUri(BandwidthConstants.CALLS_URI_PATH, getId());

    }

    public void speakSentence(Map params) throws IOException {
		assert (params != null);
	
		String audioUrl = getUri() + "/audio";
	
		client.post(audioUrl, params);
    }

    public void speakSentence(String sentence) throws IOException {
    	speakSentence(sentence, null);
    }

    public void speakSentence(String sentence, String tag) throws IOException {
		JSONObject params = new JSONObject();
		params.put("sentence", sentence);
		params.put("voice", "kate");
		params.put("gender", "female");
		params.put("locale", "en_US");
	
		if (tag != null)
		    params.put("tag", tag);
	
		speakSentence(params);
    }

    public void playRecording(String recordingUrl) throws IOException {
		assert (recordingUrl != null);
	
		String audioUrl = getUri() + "/audio";
	
		JSONObject params = new JSONObject();
		params.put("fileUrl", recordingUrl);
	
		client.post(audioUrl, params);
    }

    public void playAudio(Map<String, Object> params) throws IOException {
		assert (params != null);
	
		String audioUrl = getUri() + "/audio";
	
		client.post(audioUrl, params);
    }

    public void createGather(String promptSentence) throws IOException {
		assert (promptSentence != null);
	
		String gatherUrl = getUri() + "/gather";
	
		JSONObject params = new JSONObject();
	
		params.put("tag", getId());
		params.put("maxDigits", "1");
	
		JSONObject prompt = new JSONObject();
		prompt.put("sentence", promptSentence);
		prompt.put("gender", "female");
		prompt.put("voice", "kate");
		prompt.put("locale", "en_US");
		prompt.put("bargeable", "true");
	
		params.put("prompt", prompt);
	
		client.post(gatherUrl, params);

    }

    public void createGather(Map<String, Object> gatherParams,
	    Map<String, Object> promptParams) throws IOException {
		String gatherUrl = getUri() + "/gather";
		assert (gatherParams != null);

		if (promptParams != null && !promptParams.isEmpty())
		    gatherParams.put("prompt", promptParams);
	
		getClient().post(gatherUrl, gatherParams);
    }
    

    public String getDirection() {
        return getPropertyAsString("direction");
    }

    public String getState() {
        return getPropertyAsString("state");
    }

    public String getFrom() {
        return getPropertyAsString("from");
    }

    public String getTo() {
        return getPropertyAsString("to");
    }

    public String getCallbackUrl() {
        return getPropertyAsString("callbackUrl");
    }

    public String getEvents() {
        return getPropertyAsString("events");
    }

    public Date getStartTime() {
        return getPropertyAsDate("startTime");
    }

    public Date getActiveTime() {
        return getPropertyAsDate("activeTime");
    }

    public Date getEndTime() {
        return getPropertyAsDate("endTime");
    }

    public Long getChargeableDuration() {
        return getPropertyAsLong("chargeableDuration");
    }

    public boolean isRecordingEnabled() {
        return getPropertyAsBoolean("recordingEnabled");
    }

    /**
     * Retrieve all recordings related to the call.
     *
     * @return recordings
     * @throws IOException
     */
    public List<Recording> getRecordings() throws Exception {
        String recordingsPath = StringUtils.join(new String[]{
                getUri(),
                "recordings"
        }, '/');
        JSONArray array = toJSONArray(client.get(recordingsPath, null));

        List<Recording> list = new ArrayList<Recording>();
        for (Object object : array) {
            list.add(new Recording(client, recordingsPath, (JSONObject) object));
        }
        return list;
    }

    /**
     * Gets the events that occurred during the call.
     *
     * @return events
     * @throws IOException
     */
    public List<EventBase> getEventsList() throws Exception {
        String eventsPath = StringUtils.join(new String[]{
                getUri(),
                "events"
        }, '/');
        JSONArray array = toJSONArray(client.get(eventsPath, null));

        List<EventBase> list = new ArrayList<EventBase>();
        for (Object object : array) {
            list.add(new EventBase((JSONObject) object));
        }
        return list;
    }

    /**
     * Gets information about one call event.
     *
     * @param eventId event id
     * @return information about event
     * @throws IOException
     */
    public EventBase getEvent(String eventId) throws Exception {
        String eventPath = StringUtils.join(new String[]{
                getUri(),
                "events",
                eventId
        }, '/');
        JSONObject jsonObject = toJSONObject(client.get(eventPath, null));
        String eventsPath = StringUtils.join(new String[]{
                getUri(),
                "events"
        }, '/');
        return new EventBase(jsonObject);
    }

    /**
     * Hang up a phone call.
     *
     * @throws IOException
     */
    public void hangUp() throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("state", "completed");

        String uri = getUri();
        client.post(uri, params);

        JSONObject jsonObject = toJSONObject(client.get(uri, null));
        updateProperties(jsonObject);
    }

    /**
     * Answer an incoming phone call.
     *
     * @throws IOException
     */
    public void answerOnIncoming() throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("state", "active");

        String uri = getUri();
        client.post(uri, params);

        JSONObject jsonObject = toJSONObject(client.get(uri, null));
        updateProperties(jsonObject);
    }

    /**
     * Reject an incoming phone call
     *
     * @throws IOException
     */
    public void rejectIncoming() throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("state", "rejected");

        String uri = getUri();
        client.post(uri, params);

        JSONObject jsonObject = toJSONObject(client.get(uri, null));
        updateProperties(jsonObject);
    }

    /**
     * Turn call recording ON.
     *
     * @throws IOException
     */
    public void recordingOn() throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("recordingEnabled", "true");

        String uri = getUri();
        client.post(uri, params);

        JSONObject jsonObject = toJSONObject(client.get(uri, null));
        updateProperties(jsonObject);
    }

    /**
     * Turn call recording OFF.
     *
     * @throws IOException
     */
    public void recordingOff() throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("recordingEnabled", "false");

        String uri = getUri();
        client.post(uri, params);

        JSONObject jsonObject = toJSONObject(client.get(uri, null));
        updateProperties(jsonObject);
    }

    /**
     * Creates builder for transferring call.
     * <br>Example:<br>
     * <code>call.callTransferBuilder("{number}").sentence("hello").create();</code>
     *
     * @param transferTo number for transferring
     * @return new builder
     */
    public CallTransferBuilder callTransferBuilder(String transferTo) {
        return new CallTransferBuilder(transferTo);
    }

    /**
     * Creates new builder for playing an audio file or speaking a sentence in a call.
     * <br>Example:<br>
     * <code>call.newAudioBuilder().sentence("Hello").create();</code>
     *
     * @return new builder
     */
    public CallAudioBuilder newAudioBuilder() {
        return new CallAudioBuilder();
    }

    /**
     * Stop an audio file playing.
     *
     * @throws IOException
     */
    public void stopAudioFilePlaying() throws IOException {
        new CallAudioBuilder().fileUrl(StringUtils.EMPTY).create();
    }

    /**
     * Stop an audio sentence.
     *
     * @throws IOException
     */
    public void stopSentence() throws IOException {
        new CallAudioBuilder().sentence(StringUtils.EMPTY).create();
    }

    /**
     * Sends DTMF.
     *
     * @param dtmf DTMF value
     * @throws IOException
     */
    public void sendDtmf(String dtmf) throws IOException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("dtmfOut", dtmf);

        String uri = StringUtils.join(new String[]{
                getUri(),
                "dtmf"
        }, '/');
        client.post(uri, params);
    }

    /**
     * Creates a new builder for collecting a series of DTMF digits from a phone call with an optional prompt. This request returns immediately. When gather finishes, an event with the results will be posted to the callback URL.
     * <br>Example:<br>
     * <code>call.callGatherBuilder().maxDigits(5).promptFileUrl("url_to_file").create();</code>
     *
     * @return new builder
     */
    public CallGatherBuilder callGatherBuilder() {
        return new CallGatherBuilder();
    }

    /**
     * Gets the gather DTMF parameters and results.
     *
     * @param gatherId gather id
     * @return gather DTMF parameters and results
     * @throws IOException
     */
    public Gather getGather(String gatherId) throws Exception {
        String gatherPath = StringUtils.join(new String[]{
                getUri(),
                "gather",
                gatherId
        }, '/');
        JSONObject jsonObject = toJSONObject(client.get(gatherPath, null));
        String gathersPath = StringUtils.join(new String[]{
                getUri(),
                "events"
        }, '/');
        return new Gather(client,jsonObject);
    }

    private void createGather(Map<String, Object> params) throws IOException {
        String uri = StringUtils.join(new String[]{
                getUri(),
                "gather"
        }, '/');
        client.post(uri, params);
    }

    private void createCallAudio(Map<String, Object> params) throws IOException {
        String audioPath = StringUtils.join(new String[]{
                getUri(),
                "audio"
        }, '/');
        client.post(audioPath, params);
    }

    private void transfer(Map<String, Object> params) throws Exception {
        params.put("state", "transferring");

        String uri = getUri();
        client.post(uri, params);

        JSONObject jsonObject = toJSONObject(client.get(uri, null));
        updateProperties(jsonObject);
    }

    @Override
    public String toString() {
        return "Call{" +
                "id='" + getId() + '\'' +
                ", direction=" + getDirection() +
                ", state=" + getState() +
                ", from='" + getFrom() + '\'' +
                ", to='" + getTo() + '\'' +
                ", callbackUrl='" + getCallbackUrl() + '\'' +
                ", events='" + getEvents() + '\'' +
                ", startTime=" + getStartTime() +
                ", activeTime=" + getActiveTime() +
                ", endTime=" + getEndTime() +
                ", chargeableDuration=" + getChargeableDuration() +
                ", recordingEnabled=" + isRecordingEnabled() +
                '}';
    }

    public class CallTransferBuilder {

        private Map<String, Object> params = new HashMap<String, Object>();
        private Map<String, Object> whisperAudio = new HashMap<String, Object>();

        public CallTransferBuilder(String number) {
            params.put("transferTo", number);
        }

        public CallTransferBuilder callbackUrl(String callbackUrl) {
            params.put("callbackUrl", callbackUrl);
            return this;
        }

        public CallTransferBuilder transferCallerId(String transferCallerId) {
            params.put("transferCallerId", transferCallerId);
            return this;
        }

        public CallTransferBuilder sentence(String sentence) {
            whisperAudio.put("sentence", sentence);
            return this;
        }

        public CallTransferBuilder gender(Gender gender) {
            whisperAudio.put("gender", gender.name());
            return this;
        }

        public CallTransferBuilder voice(String voice) {
            whisperAudio.put("voice", voice);
            return this;
        }

        public CallTransferBuilder locale(SentenceLocale locale) {
            whisperAudio.put("locale", locale);
            return this;
        }

        public void create() throws Exception {
            if (!whisperAudio.isEmpty()) {
                params.put("whisperAudio", whisperAudio);
            }
            transfer(params);
        }
    }

    public class CallAudioBuilder {

        private final Map<String, Object> params = new HashMap<String, Object>();

        public CallAudioBuilder fileUrl(String fileUrl) {
            params.put("fileUrl", fileUrl);
            return this;
        }

        public CallAudioBuilder sentence(String sentence) {
            params.put("sentence", sentence);
            return this;
        }

        public CallAudioBuilder gender(Gender gender) {
            params.put("gender", gender.name());
            return this;
        }

        public CallAudioBuilder locale(SentenceLocale locale) {
            params.put("locale", locale.restValue);
            return this;
        }

        public CallAudioBuilder voice(String voice) {
            params.put("voice", voice);
            return this;
        }

        public CallAudioBuilder loopEnabled(boolean loopEnabled) {
            params.put("loopEnabled", String.valueOf(loopEnabled));
            return this;
        }

        public void create() throws IOException {
            createCallAudio(params);
        }
    }

    public class CallGatherBuilder {

        private Map<String, Object> params = new HashMap<String, Object>();
        private Map<String, Object> prompt = new HashMap<String, Object>();

        public void create() throws IOException {
            if (!prompt.isEmpty()) params.put("prompt", prompt);

            createGather(params);
        }

        public CallGatherBuilder maxDigits(int maxDigits) {
            params.put("maxDigits", String.valueOf(maxDigits));
            return this;
        }

        public CallGatherBuilder interDigitTimeout(int maxDigits) {
            params.put("interDigitTimeout", String.valueOf(maxDigits));
            return this;
        }

        public CallGatherBuilder terminatingDigits(String terminatingDigits) {
            params.put("terminatingDigits", terminatingDigits);
            return this;
        }

        public CallGatherBuilder suppressDtmf(boolean suppressDtmf) {
            params.put("suppressDtmf", String.valueOf(suppressDtmf));
            return this;
        }

        public CallGatherBuilder tag(String tag) {
            params.put("tag", tag);
            return this;
        }

        public CallGatherBuilder promptSentence(String promptSentence) {
            prompt.put("sentence", promptSentence);
            return this;
        }

        public CallGatherBuilder promptGender(Gender gender) {
            prompt.put("gender", gender.name());
            return this;
        }

        public CallGatherBuilder promptLocale(SentenceLocale locale) {
            prompt.put("locale", locale.restValue);
            return this;
        }

        public CallGatherBuilder promptFileUrl(String promptFileUrl) {
            prompt.put("fileUrl", promptFileUrl);
            return this;
        }

        public CallGatherBuilder promptLoopEnabled(boolean promptLoopEnabled) {
            prompt.put("loopEnabled", String.valueOf(promptLoopEnabled));
            return this;
        }

        public CallGatherBuilder promptBargeable(boolean promptBargeable) {
            prompt.put("bargeable", String.valueOf(promptBargeable));
            return this;
        }


    }
}
