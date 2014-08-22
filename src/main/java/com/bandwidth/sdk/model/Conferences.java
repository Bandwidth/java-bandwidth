package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthRestClient;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Point for <code>/v1/users/{userId}/conferences</code>
 *
 * @author vpotapenko
 */
public class Conferences extends BaseModelObject {

    public Conferences(BandwidthRestClient client, String parentUri) {
        super(client, parentUri, null);
    }

    /**
     * Retrieves the conference information.
     *
     * @param id conference id
     * @return conference information.
     * @throws IOException
     */
    public Conference getConference(String id) throws IOException {
        String conferencesUri = getUri();
        String conferenceUri = StringUtils.join(new String[]{
                conferencesUri,
                id
        }, '/');
        JSONObject jsonObject = client.getObject(conferenceUri);
        return new Conference(client, conferencesUri, jsonObject);
    }

    /**
     * Creates builder for creating a conference with no members.
     * <br>Example:<br>
     * <code>Conference conference = conferences.newConferenceBuilder().from("{number}").create();</code>
     *
     * @return new builder
     */
    public NewConferenceBuilder newConferenceBuilder() {
        return new NewConferenceBuilder();
    }

    @Override
    protected String getUri() {
        return StringUtils.join(new String[]{
                parentUri,
                "conferences"
        }, '/');
    }

    private Conference createConference(Map<String, Object> params) throws IOException {
        String conferencesUri = getUri();
        JSONObject jsonObject = client.create(conferencesUri, params);
        return new Conference(client, conferencesUri, jsonObject);
    }

    public class NewConferenceBuilder {

        private final Map<String, Object> params = new HashMap<String, Object>();

        public Conference create() throws IOException {
            return createConference(params);
        }

        public NewConferenceBuilder from(String from) {
            params.put("from", from);
            return this;
        }

        public NewConferenceBuilder callbackUrl(String callbackUrl) {
            params.put("callbackUrl", callbackUrl);
            return this;
        }

        public NewConferenceBuilder fallbackUrl(String fallbackUrl) {
            params.put("fallbackUrl", fallbackUrl);
            return this;
        }

        public NewConferenceBuilder callbackTimeout(int callbackTimeout) {
            params.put("callbackTimeout", callbackTimeout);
            return this;
        }

    }
}
