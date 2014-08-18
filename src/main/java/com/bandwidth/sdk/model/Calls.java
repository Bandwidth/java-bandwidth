package com.bandwidth.sdk.model;

import com.bandwidth.sdk.driver.IRestDriver;
import org.apache.commons.lang3.StringUtils;
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
public class Calls extends BaseModelObject {

    public Calls(IRestDriver driver, String parentUri) {
        super(driver, parentUri, null);
    }

    @Override
    public String getUri() {
        return StringUtils.join(new String[]{
                parentUri,
                "calls"
        }, '/');
    }

    public QueryCallsBuilder queryCallsBuilder() {
        return new QueryCallsBuilder();
    }

    public NewCallBuilder newCallBuilder() {
        return new NewCallBuilder();
    }

    public Call getCallById(String callId) throws IOException {
        String callsUri = getUri();
        String eventPath = StringUtils.join(new String[]{
                callsUri,
                callId
        }, '/');
        JSONObject jsonObject = driver.getObject(eventPath);
        return new Call(driver, callsUri, jsonObject);
    }

    private List<Call> getCalls(Map<String, Object> params) throws IOException {
        String callsUri = getUri();
        JSONArray jsonArray = driver.getArray(callsUri, params);

        List<Call> calls = new ArrayList<Call>();
        for (Object obj : jsonArray) {
            calls.add(new Call(driver, callsUri, (JSONObject) obj));
        }
        return calls;
    }

    private Call newCall(Map<String, Object> params) throws IOException {
        String callsUri = getUri();
        JSONObject jsonObject = driver.create(callsUri, params);
        return new Call(driver, callsUri, jsonObject);
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
