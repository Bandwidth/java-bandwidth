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
     * @throws IOException unexpected error.
     */
    public static Conference getConference(final String id) throws Exception {
        final BandwidthClient client = BandwidthClient.getInstance();

        return getConference(client, id);
    }
    
    /**
     * Retrieves the conference information.
     *
     * @param client the client
     * @param id the conference id.
     * @return id the conference id.
     * @throws IOException unexpected error.
     */
    public static Conference getConference(final BandwidthClient client, final String id) throws Exception {
        final String conferencesUri = client.getUserResourceUri(BandwidthConstants.CONFERENCES_URI_PATH);
        final String conferenceUri = StringUtils.join(new String[]{
                conferencesUri,
                id
        }, '/');
        final JSONObject jsonObject = toJSONObject(client.get(conferenceUri, null));
        return new Conference(client, jsonObject);
    }

    /**
	 * Factory method to create a conference given a set of params
	 * @param params the params
	 * @return the conference
	 * @throws IOException unexpected error.
	 */
    public static Conference createConference(final Map<String, Object> params) throws Exception {

    	return createConference(BandwidthClient.getInstance(), params);
    }
    
	/**
	 * Factory method to create a conference given a set of params and a client object
	 * @param client the bandwidth client configuration.
	 * @param params the params
	 * @return the conference
	 * @throws IOException unexpected error.
	 */
    public static Conference createConference(final BandwidthClient client, final Map<String, Object> params) throws Exception {
        final String conferencesUri = client.getUserResourceUri(BandwidthConstants.CONFERENCES_URI_PATH);
        final RestResponse response = client.post(conferencesUri, params);

        final String id = response.getLocation().substring(client.getPath(conferencesUri).length() + 1);

        return getConference(client, id);
    }


    public Conference(final BandwidthClient client, final JSONObject jsonObject) {
        super(client, jsonObject);
    }

    @Override
    protected void setUp(final JSONObject jsonObject) {
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
     * @throws IOException unexpected error.
     */
    public void complete() throws Exception {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("state", "completed");

        final String uri = getUri();
        client.post(uri, params);

        final JSONObject jsonObject = toJSONObject(client.get(uri, null));
        updateProperties(jsonObject);
    }

    /**
     * Prevent all members from speaking.
     *
     * @throws IOException unexpected error.
     */
    public void mute() throws Exception {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("mute", String.valueOf(true));

        final String uri = getUri();
        client.post(uri, params);

        final JSONObject jsonObject = toJSONObject(client.get(uri, null));
        updateProperties(jsonObject);
    }

    /**
     * Gets list all members from a conference. If a member had already hung up or removed from conference it will be displayed as completed.
     *
     * @return list of members
     * @throws IOException unexpected error.
     */
    public List<ConferenceMember> getMembers() throws Exception {
        final String membersPath = StringUtils.join(new String[]{
                getUri(),
                "members"
        }, '/');
        final JSONArray array = toJSONArray(client.get(membersPath, null));

        final List<ConferenceMember> members = new ArrayList<ConferenceMember>();
        for (final Object obj : array) {
            members.add(new ConferenceMember(client, (JSONObject) obj));
        }
        return members;
    }

    /**
     * Adds a call to the conference as a conference member. The call must be created with the conference ID.
     *
     * @param params parameter map for the request. see http://ap.bandwidth.com/docs/rest-api/conferences/#resourcePOSTv1usersuserIdconferencesconferenceIdmembers
     * @return the model for the conference member
     * @throws Exception
     */
    public ConferenceMember addMember(Map<String, Object> params) throws Exception {
        final String membersPath = StringUtils.join(new String[]{
                getUri(),
                "members"
        }, '/');

        final RestResponse response = client.post(membersPath, params);
        final JSONObject memberObj = toJSONObject(client.get(response.getLocation(), null));
        return new ConferenceMember(client, memberObj);
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

    private void createConferenceAudio(final Map<String, Object> params) throws IOException, AppPlatformException {
        final String audioPath = StringUtils.join(new String[]{
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

        public ConferenceAudioBuilder fileUrl(final String fileUrl) {
            params.put("fileUrl", fileUrl);
            return this;
        }

        public ConferenceAudioBuilder sentence(final String sentence) {
            params.put("sentence", sentence);
            return this;
        }

        public ConferenceAudioBuilder gender(final Gender gender) {
            params.put("gender", gender.name());
            return this;
        }

        public ConferenceAudioBuilder locale(final SentenceLocale locale) {
            params.put("locale", locale.restValue);
            return this;
        }

        public ConferenceAudioBuilder voice(final String voice) {
            params.put("voice", voice);
            return this;
        }

        public void create() throws IOException, AppPlatformException {
            createConferenceAudio(params);
        }
    }
}
