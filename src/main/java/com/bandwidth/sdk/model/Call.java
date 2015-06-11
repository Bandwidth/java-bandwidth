package com.bandwidth.sdk.model;

import com.bandwidth.sdk.AppPlatformException;
import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.BandwidthClient;
import com.bandwidth.sdk.RestResponse;

import com.bandwidth.sdk.model.events.EventBase;
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
     * @return the call
     * @throws IOException unexpected error.
     */
    public static Call get(final String callId) throws Exception {

    	final BandwidthClient client = BandwidthClient.getInstance();
        
        return get(client, callId);
    }
    
    /**
     * Convenience factory method for Call, returns a Call object given an id
     * @param client the client
     * @param callId the call id
     * @return the call
     * @throws Exception error.
     */
    public static Call get(final BandwidthClient client, final String callId) throws Exception {
    	
        final String callsUri = client.getUserResourceInstanceUri(BandwidthConstants.CALLS_URI_PATH, callId);
        
        final JSONObject jsonObject = toJSONObject(client.get(callsUri, null));
        
        return new Call(client, jsonObject);
    }

    /**
     * Factory method for Call list, returns a list of Call objects with default page, size
     * @return the list of calls
     * @throws IOException unexpected error.
     */
    public static ResourceList<Call> list() throws IOException {
    	
    	// default page size is 25
     	return list(0, 25);
    }
    
    /**
     * Factor method for Call list, returns a list of Call objects with page, size preference 
     * @param page the page
     * @param size the page size
     * @return the list
     * @throws IOException unexpected error.
     */
    public static ResourceList<Call> list(final int page, final int size) throws IOException {
    	
        return list(BandwidthClient.getInstance(), page, size);
    }
    
    /**
     * Factor method for Call list, returns a list of Call objects with page, size preference 
     * @param client the client
     * @param page the page
     * @param size the page size
     * @return the list
     * @throws IOException unexpected error.
     */
    public static ResourceList<Call> list(final BandwidthClient client, final int page, final int size) throws IOException {
    	
        final String callUri = client.getUserResourceUri(BandwidthConstants.CALLS_URI_PATH);

        final ResourceList<Call> calls = 
        			new ResourceList<Call>(page, size, callUri, Call.class);
        
        calls.setClient(client);

        calls.initialize();
        
        return calls;
    }
    
    /**
     * Convenience factory method to make an outbound call
     * @param to the to number
     * @param from the from number
     * @return the call
     * @throws Exception error.
     */
    public static Call create(final String to, final String from) throws Exception {
    	return create(to, from, "none", null);
    }
        
    /**
     * Convenience method to dials a call from a phone number to a phone number
     * @param to the to number
     * @param from the from number
     * @param callbackUrl the callback URL
     * @param tag the call tag
     * @return the call
     * @throws IOException unexpected error.
     */
    public static Call create(final String to, final String from, final String callbackUrl, final String tag)  throws Exception
    {
    	assert(to != null && from != null);
    	    	
    	final Map<String, Object> params = new HashMap<String, Object>();
    	params.put("to", to);
    	params.put("from", from);
    	params.put("callbackUrl", callbackUrl);
    	params.put("tag", tag);
    	    	
    	final Call call = create(params);
    	    	
    	return call;
    }
    
    /**
     * Dials a call, from a phone number to a phone number.
     * @param params the call params
     * @return the call
     * @throws IOException unexpected error.
     */
    public static Call create(final Map <String, Object>params)  throws Exception
    {
    	assert (params != null);
    	
    	return create(BandwidthClient.getInstance(), params);
    }
    
    /**
     * Dials a call, from a phone number to a phone number.
     * @param client the client
     * @param params the call params
     * @return the call
     * @throws IOException unexpected error.
     */
    public static Call create(final BandwidthClient client, final Map <String, Object>params) 
            throws Exception {
    	assert (client != null && params != null);
        final String callUri = client.getUserResourceUri(BandwidthConstants.CALLS_URI_PATH);
    	final RestResponse response = client.post(callUri, params);

        // success here, otherwise an exception is generated
    	final String callId = response.getLocation().substring(client.getPath(callUri).length() + 1);

        return get(client, callId);
    }

    public Call(final BandwidthClient client, final JSONObject jsonObject) {
        super(client, jsonObject);
    }
    
    @Override
    protected void setUp(final JSONObject jsonObject) {
        this.id = (String) jsonObject.get("id");
        updateProperties(jsonObject);
    }      

    protected String getUri() {
        return client.getUserResourceInstanceUri(BandwidthConstants.CALLS_URI_PATH, getId());
    }

    public void speakSentence(final Map<String, Object> params) throws IOException, AppPlatformException {
		assert (params != null);
		final String audioUrl = getUri() + "/audio";
		client.post(audioUrl, params);
    }

    public void speakSentence(final String sentence) throws IOException, AppPlatformException {
    	speakSentence(sentence, null);
    }

    public void speakSentence(final String sentence, final String tag) throws IOException, AppPlatformException {
		final JSONObject params = new JSONObject();
		params.put("sentence", sentence);
		params.put("voice", "kate");
		params.put("gender", "female");
		params.put("locale", "en_US");
	
		if (tag != null)
		    params.put("tag", tag);
	
		speakSentence(params);
    }

    public void playRecording(final String recordingUrl) throws IOException, AppPlatformException {
		assert (recordingUrl != null);
	
		final String audioUrl = getUri() + "/audio";
	
		final JSONObject params = new JSONObject();
		params.put("fileUrl", recordingUrl);
	
		client.post(audioUrl, params);
    }

    public void playAudio(final Map<String, Object> params) throws IOException, AppPlatformException {
		assert (params != null);
	
		final String audioUrl = getUri() + "/audio";
	
		client.post(audioUrl, params);
    }

    public void createGather(final String promptSentence) throws IOException, AppPlatformException {
		
        assert (promptSentence != null);
		final String gatherUrl = getUri() + "/gather";
		final JSONObject params = new JSONObject();
	
		params.put("tag", getId());
		params.put("maxDigits", "1");
	
		final JSONObject prompt = new JSONObject();
		prompt.put("sentence", promptSentence);
		prompt.put("gender", "female");
		prompt.put("voice", "kate");
		prompt.put("locale", "en_US");
		prompt.put("bargeable", "true");
		params.put("prompt", prompt);
	
		client.post(gatherUrl, params);

    }

    public void createGather(final Map<String, Object> gatherParams,
	    final Map<String, Object> promptParams) throws IOException, AppPlatformException {
		
        final String gatherUrl = getUri() + "/gather";
		assert (gatherParams != null);
		if (promptParams != null && !promptParams.isEmpty()) {
		    gatherParams.put("prompt", promptParams);
		}
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
     * @return recordings the recordings
     * @throws IOException unexpected error.
     */
    public List<Recording> getRecordings() throws Exception {
        final String recordingsPath = StringUtils.join(new String[]{
                getUri(),
                "recordings"
        }, '/');
        final JSONArray array = toJSONArray(client.get(recordingsPath, null));

        final List<Recording> list = new ArrayList<Recording>();
        for (final Object object : array) {
            list.add(new Recording(client, recordingsPath, (JSONObject) object));
        }
        return list;
    }

    /**
     * Gets the events that occurred during the call.
     *
     * @return events the events list
     * @throws IOException unexpected error.
     */
    public List<EventBase> getEventsList() throws Exception {
        final String eventsPath = StringUtils.join(new String[]{
                getUri(),
                "events"
        }, '/');
        final JSONArray array = toJSONArray(client.get(eventsPath, null));

        final List<EventBase> list = new ArrayList<EventBase>();
        for (final Object object : array) {
            list.add(new EventBase((JSONObject) object));
        }
        return list;
    }

    /**
     * Gets information about one call event.
     *
     * @param eventId event id
     * @return information about event
     * @throws IOException unexpected error.
     */
    public EventBase getEvent(final String eventId) throws Exception {
        final String eventPath = StringUtils.join(new String[]{
                getUri(),
                "events",
                eventId
        }, '/');
        final JSONObject jsonObject = toJSONObject(client.get(eventPath, null));
        return new EventBase(jsonObject);
    }

    /**
     * Hang up a phone call.
     *
     * @throws IOException unexpected error.
     */
    public void hangUp() throws Exception {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("state", "completed");

        final String uri = getUri();
        client.post(uri, params);

        final JSONObject jsonObject = toJSONObject(client.get(uri, null));
        updateProperties(jsonObject);
    }

    /**
     * Answer an incoming phone call.
     *
     * @throws IOException unexpected error.
     */
    public void answerOnIncoming() throws Exception {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("state", "active");

        final String uri = getUri();
        client.post(uri, params);

        final JSONObject jsonObject = toJSONObject(client.get(uri, null));
        updateProperties(jsonObject);
    }

    /**
     * Reject an incoming phone call
     *
     * @throws IOException unexpected error.
     */
    public void rejectIncoming() throws Exception {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("state", "rejected");

        final String uri = getUri();
        client.post(uri, params);

        final JSONObject jsonObject = toJSONObject(client.get(uri, null));
        updateProperties(jsonObject);
    }

    /**
     * Turn call recording ON.
     *
     * @throws IOException unexpected error.
     */
    public void recordingOn() throws Exception {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("recordingEnabled", "true");

        final String uri = getUri();
        client.post(uri, params);

        final JSONObject jsonObject = toJSONObject(client.get(uri, null));
        updateProperties(jsonObject);
    }

    /**
     * Turn call recording OFF.
     *
     * @throws IOException unexpected error.
     */
    public void recordingOff() throws Exception {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("recordingEnabled", "false");

        final String uri = getUri();
        client.post(uri, params);

        final JSONObject jsonObject = toJSONObject(client.get(uri, null));
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
    public CallTransferBuilder callTransferBuilder(final String transferTo) {
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
     * @throws IOException unexpected error.
     * @throws AppPlatformException unexpected exception.
     */
    public void stopAudioFilePlaying() throws IOException, AppPlatformException {
        new CallAudioBuilder().fileUrl(StringUtils.EMPTY).create();
    }

    /**
     * Stop an audio sentence.
     *
     * @throws IOException unexpected error.
     * @throws AppPlatformException unexpected exception.
     */
    public void stopSentence() throws IOException, AppPlatformException {
        new CallAudioBuilder().sentence(StringUtils.EMPTY).create();
    }

    /**
     * Sends DTMF.
     *
     * @param dtmf DTMF value
     * @throws IOException unexpected error.
     * @throws AppPlatformException unexpected exception.
     */
    public void sendDtmf(final String dtmf) throws IOException, AppPlatformException {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("dtmfOut", dtmf);

        final String uri = StringUtils.join(new String[]{
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
     * @throws IOException unexpected error.
     */
    public Gather getGather(final String gatherId) throws Exception {
        final String gatherPath = StringUtils.join(new String[]{
                getUri(),
                "gather",
                gatherId
        }, '/');
        final JSONObject jsonObject = toJSONObject(client.get(gatherPath, null));
        return new Gather(client,jsonObject);
    }

    private void createGather(final Map<String, Object> params) throws IOException, AppPlatformException {
        final String uri = StringUtils.join(new String[]{
                getUri(),
                "gather"
        }, '/');
        client.post(uri, params);
    }

    private void createCallAudio(final Map<String, Object> params) throws IOException, AppPlatformException {
        final String audioPath = StringUtils.join(new String[]{
                getUri(),
                "audio"
        }, '/');
        client.post(audioPath, params);
    }

    private void transfer(final Map<String, Object> params) throws Exception {
        params.put("state", "transferring");

        final String uri = getUri();
        client.post(uri, params);

        final JSONObject jsonObject = toJSONObject(client.get(uri, null));
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

        private final Map<String, Object> params = new HashMap<String, Object>();
        private final Map<String, Object> whisperAudio = new HashMap<String, Object>();

        public CallTransferBuilder(final String number) {
            params.put("transferTo", number);
        }

        public CallTransferBuilder callbackUrl(final String callbackUrl) {
            params.put("callbackUrl", callbackUrl);
            return this;
        }

        public CallTransferBuilder transferCallerId(final String transferCallerId) {
            params.put("transferCallerId", transferCallerId);
            return this;
        }

        public CallTransferBuilder sentence(final String sentence) {
            whisperAudio.put("sentence", sentence);
            return this;
        }

        public CallTransferBuilder gender(final Gender gender) {
            whisperAudio.put("gender", gender.name());
            return this;
        }

        public CallTransferBuilder voice(final String voice) {
            whisperAudio.put("voice", voice);
            return this;
        }

        public CallTransferBuilder locale(final SentenceLocale locale) {
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

        public CallAudioBuilder fileUrl(final String fileUrl) {
            params.put("fileUrl", fileUrl);
            return this;
        }

        public CallAudioBuilder sentence(final String sentence) {
            params.put("sentence", sentence);
            return this;
        }

        public CallAudioBuilder gender(final Gender gender) {
            params.put("gender", gender.name());
            return this;
        }

        public CallAudioBuilder locale(final SentenceLocale locale) {
            params.put("locale", locale.restValue);
            return this;
        }

        public CallAudioBuilder voice(final String voice) {
            params.put("voice", voice);
            return this;
        }

        public CallAudioBuilder loopEnabled(final boolean loopEnabled) {
            params.put("loopEnabled", String.valueOf(loopEnabled));
            return this;
        }

        public void create() throws IOException, AppPlatformException {
            createCallAudio(params);
        }
    }

    public class CallGatherBuilder {

        private final Map<String, Object> params = new HashMap<String, Object>();
        private final Map<String, Object> prompt = new HashMap<String, Object>();

        public void create() throws IOException, AppPlatformException {
            if (!prompt.isEmpty()) params.put("prompt", prompt);

            createGather(params);
        }

        public CallGatherBuilder maxDigits(final int maxDigits) {
            params.put("maxDigits", String.valueOf(maxDigits));
            return this;
        }

        public CallGatherBuilder interDigitTimeout(final int maxDigits) {
            params.put("interDigitTimeout", String.valueOf(maxDigits));
            return this;
        }

        public CallGatherBuilder terminatingDigits(final String terminatingDigits) {
            params.put("terminatingDigits", terminatingDigits);
            return this;
        }

        public CallGatherBuilder suppressDtmf(final boolean suppressDtmf) {
            params.put("suppressDtmf", String.valueOf(suppressDtmf));
            return this;
        }

        public CallGatherBuilder tag(final String tag) {
            params.put("tag", tag);
            return this;
        }

        public CallGatherBuilder promptSentence(final String promptSentence) {
            prompt.put("sentence", promptSentence);
            return this;
        }

        public CallGatherBuilder promptGender(final Gender gender) {
            prompt.put("gender", gender.name());
            return this;
        }

        public CallGatherBuilder promptLocale(final SentenceLocale locale) {
            prompt.put("locale", locale.restValue);
            return this;
        }

        public CallGatherBuilder promptFileUrl(final String promptFileUrl) {
            prompt.put("fileUrl", promptFileUrl);
            return this;
        }

        public CallGatherBuilder promptLoopEnabled(final boolean promptLoopEnabled) {
            prompt.put("loopEnabled", String.valueOf(promptLoopEnabled));
            return this;
        }

        public CallGatherBuilder promptBargeable(final boolean promptBargeable) {
            prompt.put("bargeable", String.valueOf(promptBargeable));
            return this;
        }


    }
}
