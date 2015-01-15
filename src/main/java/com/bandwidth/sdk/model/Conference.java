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
 * Information about conference.
 *
 * @author vpotapenko
 */
public class Conference extends ResourceBase {
	
    /**
     * Retrieves the conference information.
     *
     * @param id conference id
     * @return conference information.
     * @throws IOException
     */
    public static Conference getConference(String id) throws Exception {
        BandwidthClient client = BandwidthClient.getInstance();

        return getConference(client, id);
    }
    
    /**
     * Retrieves the conference information.
     *
     * @param id conference id
     * @return conference information.
     * @throws IOException
     */
    public static Conference getConference(BandwidthClient client, String id) throws Exception {
        String conferencesUri = client.getUserResourceUri(BandwidthConstants.CONFERENCES_URI_PATH);
        String conferenceUri = StringUtils.join(new String[]{
                conferencesUri,
                id
        }, '/');
        JSONObject jsonObject = toJSONObject(client.get(conferenceUri, null));
        return new Conference(client, jsonObject);
    }

    /**
	 * Factory method to create a conference given a set of params
	 * @param params
	 * @return
	 * @throws IOException
	 */
    public static Conference createConference(Map<String, Object> params) throws Exception {

    	return createConference(BandwidthClient.getInstance(), params);
    }
    
	/**
	 * Factory method to create a conference given a set of params and a client object
	 * @param params
	 * @return
	 * @throws IOException
	 */
    public static Conference createConference(BandwidthClient client, Map<String, Object> params) throws Exception {
        String conferencesUri = client.getUserResourceUri(BandwidthConstants.CONFERENCES_URI_PATH);
        RestResponse response = client.post(conferencesUri, params);

        if (response.getStatus() > 400) {
            throw new AppPlatformException(response.getResponseText());
        }

        String id = response.getLocation().substring(client.getPath(conferencesUri).length() + 1);

        Conference conference = getConference(client, id);


        return conference;
    }


    public Conference(BandwidthClient client, JSONObject jsonObject) {
        super(client, jsonObject);
    }

    @Override
    protected void setUp(JSONObject jsonObject) {
        this.id = (String) jsonObject.get("id");
        updateProperties(jsonObject);
    }


    protected String getUri() {
        return client.getUserResourceInstanceUri(BandwidthConstants.CONFERENCES_URI_PATH, getId());
    }

    public String getFrom() {
        return getPropertyAsString("from");
    }

    public String getCallbackUrl() {
        return getPropertyAsString("callbackUrl");
    }

    public String getFallbackUrl() {
        return getPropertyAsString("fallbackUrl");
    }

    public String getState() {
        return getPropertyAsString("state");
    }

    public Long getActiveMembers() {
        return getPropertyAsLong("activeMembers");
    }

    public Long getCallbackTimeout() {
        return getPropertyAsLong("callbackTimeout");
    }

    public Date getCompletedTime() {
        return getPropertyAsDate("completedTime");
    }

    public Date getCreatedTime() {
        return getPropertyAsDate("createdTime");
    }

    /**
     * Terminates conference.
     *
     * @throws IOException
     */
    public void complete() throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("state", "completed");

        String uri = getUri();
        client.post(uri, params);

        JSONObject jsonObject = toJSONObject(client.get(uri, null));
        updateProperties(jsonObject);
    }

    /**
     * Prevent all members from speaking.
     *
     * @throws IOException
     */
    public void mute() throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("mute", String.valueOf(true));

        String uri = getUri();
        client.post(uri, params);

        JSONObject jsonObject = toJSONObject(client.get(uri, null));
        updateProperties(jsonObject);
    }

    /**
     * Gets list all members from a conference. If a member had already hung up or removed from conference it will be displayed as completed.
     *
     * @return list of members
     * @throws IOException
     */
    public List<ConferenceMember> getMembers() throws Exception {
        String membersPath = StringUtils.join(new String[]{
                getUri(),
                "members"
        }, '/');
        JSONArray array = toJSONArray(client.get(membersPath, null));

        List<ConferenceMember> members = new ArrayList<ConferenceMember>();
        for (Object obj : array) {
            members.add(new ConferenceMember(client, (JSONObject) obj));
        }
        return members;
    }

    /**
     * Creates new builder for playing an audio file or speaking a sentence in a conference.
     * <br>Example:<br>
     * <code>conference.conferenceAudioBuilder().fileUrl("url_to_file").create();</code>
     *
     * @return new builder
     */
    public ConferenceAudioBuilder conferenceAudioBuilder() {
        return new ConferenceAudioBuilder();
    }

    private void createConferenceAudio(Map<String, Object> params) throws IOException {
        String audioPath = StringUtils.join(new String[]{
                getUri(),
                "audio"
        }, '/');
        client.post(audioPath, params);
    }

    @Override
    public String toString() {
        return "Conference{" +
                "id='" + getId() + '\'' +
                ", from='" + getFrom() + '\'' +
                ", callbackUrl='" + getCallbackUrl() + '\'' +
                ", fallbackUrl='" + getFallbackUrl() + '\'' +
                ", state=" + getState() +
                ", activeMembers=" + getActiveMembers() +
                ", callbackTimeout=" + getCallbackTimeout() +
                ", completedTime=" + getCompletedTime() +
                ", createdTime=" + getCreatedTime() +
                '}';
    }

    public class ConferenceAudioBuilder {

        private final Map<String, Object> params = new HashMap<String, Object>();

        public ConferenceAudioBuilder fileUrl(String fileUrl) {
            params.put("fileUrl", fileUrl);
            return this;
        }

        public ConferenceAudioBuilder sentence(String sentence) {
            params.put("sentence", sentence);
            return this;
        }

        public ConferenceAudioBuilder gender(Gender gender) {
            params.put("gender", gender.name());
            return this;
        }

        public ConferenceAudioBuilder locale(SentenceLocale locale) {
            params.put("locale", locale.restValue);
            return this;
        }

        public ConferenceAudioBuilder voice(String voice) {
            params.put("voice", voice);
            return this;
        }

        public void create() throws IOException {
            createConferenceAudio(params);
        }
    }
}
