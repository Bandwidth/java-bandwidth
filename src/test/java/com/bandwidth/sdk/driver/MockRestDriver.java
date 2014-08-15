package com.bandwidth.sdk.driver;

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
public class MockRestDriver implements IRestDriver {

    public final List<RestRequest> requests = new ArrayList<RestRequest>();

    public JSONObject result;
    public JSONArray arrayResult;

    @Override
    public JSONObject requestAccountInfo() throws IOException {
        requests.add(new RestRequest("requestAccountInfo"));
        return result;
    }

    @Override
    public JSONArray requestAccountTransactions(Map<String, Object> params) {
        requests.add(new RestRequest("requestAccountTransactions", params));
        return arrayResult;
    }

    @Override
    public JSONArray requestApplications(Map<String, Object> params) throws IOException {
        requests.add(new RestRequest("requestApplications", params));
        return arrayResult;
    }

    @Override
    public JSONObject createApplication(Map<String, Object> params) throws IOException {
        requests.add(new RestRequest("createApplication"));
        return result;
    }

    @Override
    public JSONObject requestApplicationById(String id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        requests.add(new RestRequest("requestApplicationById", params));
        return result;
    }

    @Override
    public void deleteApplication(String id) throws IOException {
        requests.add(new RestRequest("deleteApplication"));
    }

    @Override
    public void updateApplication(String id, Map<String, Object> params) throws IOException {
        requests.add(new RestRequest("updateApplication", params));
    }

    @Override
    public JSONArray requestLocalAvailableNumbers(Map<String, Object> params) throws IOException {
        requests.add(new RestRequest("requestLocalAvailableNumbers", params));
        return arrayResult;
    }

    @Override
    public JSONArray requestTollFreeAvailableNumbers(Map<String, Object> params) throws IOException {
        requests.add(new RestRequest("requestTollFreeAvailableNumbers", params));
        return arrayResult;
    }

    @Override
    public JSONArray requestBridges() {
        requests.add(new RestRequest("requestBridges"));
        return arrayResult;
    }

    @Override
    public JSONObject createBridge(Map<String, Object> params) throws IOException {
        requests.add(new RestRequest("createBridge", params));
        return result;
    }

    @Override
    public JSONObject requestBridgeById(String id) throws IOException {
        requests.add(new RestRequest("requestBridgeById"));
        return result;
    }

    @Override
    public void updateBridge(String id, Map<String, Object> params) throws IOException {
        requests.add(new RestRequest("updateBridge", params));
    }

    @Override
    public void createBridgeAudio(String id, Map<String, Object> params) throws IOException {
        requests.add(new RestRequest("createBridgeAudio", params));
    }

    @Override
    public JSONArray requestCalls(Map<String, Object> params) throws IOException {
        requests.add(new RestRequest("requestCalls", params));
        return arrayResult;
    }

    @Override
    public JSONArray requestBridgeCalls(String id) {
        requests.add(new RestRequest("requestBridgeCalls"));
        return arrayResult;
    }

    @Override
    public JSONObject createCall(Map<String, Object> params) throws IOException {
        requests.add(new RestRequest("createCall", params));
        return result;
    }

    @Override
    public JSONObject requestCallById(String callId) {
        requests.add(new RestRequest("requestCallById"));
        return result;
    }

    @Override
    public void updateCall(String id, Map<String, Object> params) throws IOException {
        requests.add(new RestRequest("updateCall", params));
    }

    @Override
    public void createCallAudio(String id, Map<String, Object> params) {
        requests.add(new RestRequest("createCallAudio", params));
    }

    @Override
    public void sendCallDtmf(String id, Map<String, Object> params) throws IOException {
        requests.add(new RestRequest("sendCallDtmf", params));
    }

    @Override
    public JSONArray requestCallEvents(String id) throws IOException {
        requests.add(new RestRequest("requestCallEvents"));
        return arrayResult;
    }

    @Override
    public JSONObject requestCallEventById(String callId, String eventId) throws IOException {
        requests.add(new RestRequest("requestCallEventById"));
        return result;
    }

    @Override
    public JSONArray requestCallRecordings(String id) throws IOException {
        requests.add(new RestRequest("requestCallRecordings"));
        return arrayResult;
    }

    @Override
    public void createCallGather(String id, Map<String, Object> params) throws IOException {
        requests.add(new RestRequest("createCallGather", params));
    }

    @Override
    public JSONObject requestCallGatherById(String callId, String gatherId) throws IOException {
        requests.add(new RestRequest("requestCallGatherById"));
        return result;
    }

    @Override
    public void updateCallGather(String callId, String gatherId, Map<String, Object> params) {
        requests.add(new RestRequest("updateCallGather", params));
    }

    @Override
    public JSONObject createConference(Map<String, Object> params) throws IOException {
        requests.add(new RestRequest("createConference", params));
        return result;
    }

    @Override
    public JSONObject requestConferenceById(String id) throws IOException {
        requests.add(new RestRequest("requestConferenceById"));
        return result;
    }

    @Override
    public void updateConference(String id, Map<String, Object> params) throws IOException {
        requests.add(new RestRequest("updateConference", params));
    }

    @Override
    public void createConferenceAudio(String id, Map<String, Object> params) throws IOException {
        requests.add(new RestRequest("createConferenceAudio", params));
    }

    public static class RestRequest {

        public final String name;
        public final Map<String, Object> params;

        public RestRequest(String name) {
            this(name, null);
        }

        public RestRequest(String name, Map<String, Object> params) {
            this.name = name;
            this.params = params;
        }
    }
}
