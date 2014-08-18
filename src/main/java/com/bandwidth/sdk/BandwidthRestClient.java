package com.bandwidth.sdk;

import com.bandwidth.sdk.driver.HttpRestDriver;
import com.bandwidth.sdk.driver.IRestDriver;
import com.bandwidth.sdk.model.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.Map;

/**
 * @author vpotapenko
 */
public class BandwidthRestClient {

    private final String parentUri;

    private IRestDriver restDriver;

    public BandwidthRestClient(String userId, String token, String secret) {
        parentUri = String.format(BandwidthConstants.MAIN_URI_PATH, userId);
        restDriver = new HttpRestDriver(userId, token, secret);
    }

    public Account getAccount() {
        return new Account(restDriver, parentUri);
    }

    public Applications getApplications() {
        return new Applications(restDriver, parentUri);
    }

    public AvailableNumbers getAvailableNumbers() {
        return new AvailableNumbers(restDriver);
    }

    public Bridges getBridges() {
        return new Bridges(restDriver, parentUri);
    }

    public Calls getCalls() {
        return new Calls(restDriver, parentUri);
    }

    public Conferences getConferences() {
        return new Conferences(restDriver, parentUri);
    }

    /**
     * For testing
     */
    public void setRestDriver(IRestDriver restDriver) {
        this.restDriver = restDriver;
    }




    public JSONArray requestBridges() throws IOException {
        return restDriver.requestBridges();
    }

    public JSONObject requestBridgeById(String id) throws IOException {
        return restDriver.requestBridgeById(id);
    }

    public JSONObject createBridge(Map<String, Object> params) throws IOException {
        return restDriver.createBridge(params);
    }

    public JSONObject requestAccountInfo() throws IOException {
        return restDriver.requestAccountInfo();
    }

    public JSONArray requestAccountTransactions(Map<String, Object> params) throws IOException {
        return restDriver.requestAccountTransactions(params);
    }

    public void deleteApplication(String id) throws IOException {
        restDriver.deleteApplication(id);
    }

    public JSONObject requestApplicationById(String id) throws IOException {
        return restDriver.requestApplicationById(id);
    }

    public void updateApplication(String id, Map<String, Object> params) throws IOException {
        restDriver.updateApplication(id, params);
    }

    public JSONArray requestApplications(Map<String, Object> params) throws IOException {
        return restDriver.requestApplications(params);
    }

    public JSONObject createApplication(Map<String, Object> params) throws IOException {
        return restDriver.createApplication(params);
    }

    public JSONArray requestLocalAvailableNumbers(Map<String, Object> params) throws IOException {
        return restDriver.requestLocalAvailableNumbers(params);
    }

    public JSONArray requestTollFreeAvailableNumbers(Map<String, Object> params) throws IOException {
        return restDriver.requestTollFreeAvailableNumbers(params);
    }

    public JSONArray requestBridgeCalls(String id) throws IOException {
        return restDriver.requestBridgeCalls(id);
    }

    public void updateBridge(String id, Map<String, Object> params) throws IOException {
        restDriver.updateBridge(id, params);
    }

    public void createBridgeAudio(String id, Map<String, Object> params) throws IOException {
        restDriver.createBridgeAudio(id, params);
    }

    public JSONArray requestCallRecordings(String callId) throws IOException {
        return restDriver.requestCallRecordings(callId);
    }

    public JSONArray requestCallEvents(String id) throws IOException {
        return restDriver.requestCallEvents(id);
    }

    public JSONObject requestCallEventById(String callId, String eventId) throws IOException {
        return restDriver.requestCallEventById(callId, eventId);
    }

    public void updateCall(String id, Map<String, Object> params) throws IOException {
        restDriver.updateCall(id, params);
    }

    public JSONObject requestCallById(String id) throws IOException {
        return restDriver.requestCallById(id);
    }

    public void createCallAudio(String callId, Map<String, Object> params) throws IOException {
        restDriver.createCallAudio(callId, params);
    }

    public void sendCallDtmf(String callId, Map<String, Object> params) throws IOException {
        restDriver.sendCallDtmf(callId, params);
    }

    public void createCallGather(String callId, Map<String, Object> params) throws IOException {
        restDriver.createCallGather(callId, params);
    }

    public JSONObject requestCallGatherById(String callId, String gatherId) throws IOException {
        return restDriver.requestCallGatherById(callId, gatherId);
    }

    public JSONArray requestCalls(Map<String, Object> params) throws IOException {
        return restDriver.requestCalls(params);
    }

    public JSONObject createCall(Map<String, Object> params) throws IOException {
        return restDriver.createCall(params);
    }

    public JSONObject requestConferenceById(String id) throws IOException {
        return restDriver.requestConferenceById(id);
    }

    public JSONObject createConference(Map<String, Object> params) throws IOException {
        return restDriver.createConference(params);
    }

    public void updateCallGather(String callId, String gatherId, Map<String, Object> params) throws IOException {
        restDriver.updateCallGather(callId, gatherId, params);
    }

    public void updateConference(String id, Map<String, Object> params) throws IOException {
        restDriver.updateConference(id, params);
    }

    public void createConferenceAudio(String id, Map<String, Object> params) throws IOException {
        restDriver.createConferenceAudio(id, params);
    }

    public JSONArray requestConferenceMembers(String id) throws IOException {
        return restDriver.requestConferenceMembers(id);
    }
}
