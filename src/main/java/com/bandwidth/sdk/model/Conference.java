package com.bandwidth.sdk.model;

import com.bandwidth.sdk.driver.IRestDriver;
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
public class Conference extends BaseModelObject {

    public Conference(IRestDriver driver, String parentUri, JSONObject jsonObject) {
        super(driver, parentUri, jsonObject);
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
    public void complete() throws IOException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("state", "completed");

        String uri = getUri();
        driver.post(uri, params);

        JSONObject jsonObject = driver.getObject(uri);
        updateProperties(jsonObject);
    }

    /**
     * Prevent all members from speaking.
     *
     * @throws IOException
     */
    public void mute() throws IOException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("mute", String.valueOf(true));

        String uri = getUri();
        driver.post(uri, params);

        JSONObject jsonObject = driver.getObject(uri);
        updateProperties(jsonObject);
    }

    /**
     * Gets list all members from a conference. If a member had already hung up or removed from conference it will be displayed as completed.
     *
     * @return list of members
     * @throws IOException
     */
    public List<ConferenceMember> getMembers() throws IOException {
        String membersPath = StringUtils.join(new String[]{
                getUri(),
                "members"
        }, '/');
        JSONArray array = driver.getArray(membersPath, null);

        List<ConferenceMember> members = new ArrayList<ConferenceMember>();
        for (Object obj : array) {
            members.add(new ConferenceMember(driver, membersPath, (JSONObject) obj));
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
        driver.post(audioPath, params);
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
