package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.BandwidthRestClient;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Point for <code>/v1/users/{userId}/calls</code>
 *
 * @author vpotapenko
 */
public class Calls extends BaseModelObject {

    public Calls(BandwidthRestClient client) {
        super(client, null);
    }

    /**
     * Creates builder for getting a list of active and historic calls you made or received.
     * <br>Example:<br>
     * <code>List<Call> list = calls.queryCallsBuilder().bridgeId("{bridgeId}").list();</code>
     *
     * @return list of calls
     */
    public QueryCallsBuilder queryCallsBuilder() {
        return new QueryCallsBuilder();
    }

    /**
     * Creates builder for making a phone call.
     * <br>Example:<br>
     * <code>Call call = calls.newCallBuilder().from("{number1}").to("{number2}").create();</code>
     *
     * @return new call
     */
    public NewCallBuilder newCallBuilder() {
        return new NewCallBuilder();
    }

    /**
     * Gets information about an active or completed call.
     *
     * @param callId call id
     * @return information about a call
     * @throws IOException
     */
    public Call getCall(String callId) throws IOException {
        String callsUri = getUri();
        String eventPath = StringUtils.join(new String[]{
                callsUri,
                callId
        }, '/');
        JSONObject jsonObject = client.getObject(eventPath);
        return new Call(client, jsonObject);
    }

    @Override
    protected String getUri() {
        return client.getUserResourceUri(BandwidthConstants.CALLS_URI_PATH);
//        return StringUtils.join(new String[]{
//                parentUri,
//                "calls"
//        }, '/');
    }

    private List<Call> getCalls(Map<String, Object> params) throws IOException {
        String callsUri = getUri();
        JSONArray jsonArray = client.getArray(callsUri, params);

        List<Call> calls = new ArrayList<Call>();
        for (Object obj : jsonArray) {
            calls.add(new Call(client, (JSONObject) obj));
        }
        return calls;
    }

    private Call newCall(Map<String, Object> params) throws IOException {
        String callsUri = getUri();
        JSONObject jsonObject = client.create(callsUri, params);
        return new Call(client, jsonObject);
    }

    public class QueryCallsBuilder {

        private final Map<String, Object> params = new HashMap<String, Object>();

        public QueryCallsBuilder bridgeId(String bridgeId) {
            params.put("bridgeId", bridgeId);
            return this;
        }

        public QueryCallsBuilder conferenceId(String conferenceId) {
            params.put("conferenceId", conferenceId);
            return this;
        }

        public QueryCallsBuilder from(String from) {
            params.put("from", from);
            return this;
        }

        public QueryCallsBuilder to(String to) {
            params.put("to", to);
            return this;
        }

        public QueryCallsBuilder page(int page) {
            params.put("page", String.valueOf(page));
            return this;
        }

        public QueryCallsBuilder size(int size) {
            params.put("size", String.valueOf(size));
            return this;
        }

        public List<Call> list() throws IOException {
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

        public Call create() throws IOException {
            return newCall(params);
        }

    }

}
