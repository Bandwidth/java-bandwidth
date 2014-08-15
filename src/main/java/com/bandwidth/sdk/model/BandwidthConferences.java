package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthRestClient;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author vpotapenko
 */
public class BandwidthConferences {

    private final BandwidthRestClient client;

    public BandwidthConferences(BandwidthRestClient client) {
        this.client = client;
    }

    public BandwidthConference getConferenceById(String id) throws IOException {
        JSONObject jsonObject = client.getRestDriver().requestConferenceById(id);
        return BandwidthConference.from(jsonObject);
    }

    public NewConferenceBuilder newConference() {
        return new NewConferenceBuilder();
    }

    private BandwidthConference createConference(Map<String, Object> params) throws IOException {
        JSONObject jsonObject = client.getRestDriver().createConference(params);
        return BandwidthConference.from(jsonObject);
    }

    public class NewConferenceBuilder {

        private final Map<String, Object> params = new HashMap<String, Object>();

        public BandwidthConference commit() throws IOException {
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
