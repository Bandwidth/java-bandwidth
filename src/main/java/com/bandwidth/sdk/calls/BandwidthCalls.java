package com.bandwidth.sdk.calls;

import com.bandwidth.sdk.BandwidthRestClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author vpotapenko
 */
public class BandwidthCalls {

    private final BandwidthRestClient client;

    public BandwidthCalls(BandwidthRestClient client) {
        this.client = client;
    }

    public CallsListBuilder getCalls() {
        return new CallsListBuilder();
    }

    public NewCallBuilder newCall() {
        return new NewCallBuilder();
    }

    private List<BandwidthCall> getCalls(Map<String, Object> params) throws IOException {
        JSONArray jsonArray = client.getRestDriver().requestCalls(params);

        List<BandwidthCall> calls = new ArrayList<BandwidthCall>();
        for (Object obj : jsonArray) {
            calls.add(BandwidthCall.from(client, (JSONObject) obj));
        }
        return calls;
    }

    private BandwidthCall newCall(Map<String, Object> params) throws IOException {
        JSONObject jsonObject = client.getRestDriver().createCall(params);
        return BandwidthCall.from(client, jsonObject);
    }

    public class CallsListBuilder {

        private final Map<String, Object> params = new HashMap<String, Object>();

        public CallsListBuilder bridgeId(String bridgeId) {
            params.put("bridgeId", bridgeId);
            return this;
        }

        public CallsListBuilder conferenceId(String conferenceId) {
            params.put("conferenceId", conferenceId);
            return this;
        }

        public CallsListBuilder from(String from) {
            params.put("from", from);
            return this;
        }

        public CallsListBuilder to(String to) {
            params.put("to", to);
            return this;
        }

        public CallsListBuilder page(int page) {
            params.put("page", String.valueOf(page));
            return this;
        }

        public CallsListBuilder size(int size) {
            params.put("size", String.valueOf(size));
            return this;
        }

        public List<BandwidthCall> get() throws IOException {
            return getCalls(params);
        }
    }

    public class NewCallBuilder {

        private final Map<String, Object> params = new HashMap<String, Object>();

        public NewCallBuilder callbackUrl(String callbackUrl) {
            params.put("callbackUrl", callbackUrl);
            return this;
        }

        public NewCallBuilder from(String from) {
            params.put("from", from);
            return this;
        }

        public NewCallBuilder to(String to) {
            params.put("to", to);
            return this;
        }

        public NewCallBuilder recordingEnabled(boolean recordingEnabled) {
            params.put("recordingEnabled", recordingEnabled);
            return this;
        }

        public NewCallBuilder bridgeId(String bridgeId) {
            params.put("bridgeId", bridgeId);
            return this;
        }

        public BandwidthCall create() throws IOException {
            return newCall(params);
        }

    }

}
