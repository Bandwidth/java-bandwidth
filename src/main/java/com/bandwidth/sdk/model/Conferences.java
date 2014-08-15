package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthRestClient;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author vpotapenko
 */
public class Conferences {

    private final BandwidthRestClient client;

    public Conferences(BandwidthRestClient client) {
        this.client = client;
    }

    public Conference getConferenceById(String id) throws IOException {
        JSONObject jsonObject = client.requestConferenceById(id);
        return Conference.from(client, jsonObject);
    }

    public NewConferenceBuilder newConferenceBuilder() {
        return new NewConferenceBuilder();
    }

    private Conference createConference(Map<String, Object> params) throws IOException {
        JSONObject jsonObject = client.createConference(params);
        return Conference.from(client, jsonObject);
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
