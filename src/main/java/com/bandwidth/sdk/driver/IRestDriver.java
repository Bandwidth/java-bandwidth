package com.bandwidth.sdk.driver;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.Map;

/**
 * @author vpotapenko
 */
public interface IRestDriver {

    JSONObject requestAccountInfo() throws IOException;

    JSONArray requestAccountTransactions(Map<String, Object> params) throws IOException;

    JSONArray requestApplications(Map<String, Object> params) throws IOException;

    JSONObject createApplication(Map<String, Object> params) throws IOException;

    JSONObject requestApplicationById(String id) throws IOException;

    void deleteApplication(String id) throws IOException;

    void updateApplication(String id, Map<String, Object> params) throws IOException;

    JSONArray requestLocalAvailableNumbers(Map<String, Object> params) throws IOException;

    JSONArray requestTollFreeAvailableNumbers(Map<String, Object> params) throws IOException;

    JSONArray requestBridges() throws IOException;

    JSONObject createBridge(Map<String, Object> params) throws IOException;

    JSONObject requestBridgeById(String id) throws IOException;

    void updateBridge(String id, Map<String, Object> params) throws IOException;

    void createBridgeAudio(String id, Map<String, Object> params) throws IOException;

    JSONArray requestCalls(Map<String, Object> params) throws IOException;

    JSONArray requestBridgeCalls(String id) throws IOException;

    JSONObject createCall(Map<String, Object> params) throws IOException;

    JSONObject requestCallById(String callId) throws IOException;

    void updateCall(String id, Map<String, Object> params) throws IOException;

    void createCallAudio(String id, Map<String, Object> params) throws IOException;

    void sendCallDtmf(String id, Map<String, Object> params) throws IOException;

    JSONArray requestCallEvents(String id) throws IOException;

    JSONObject requestCallEventById(String callId, String eventId) throws IOException;

    JSONArray requestCallRecordings(String id) throws IOException;

    void createCallGather(String id, Map<String, Object> params) throws IOException;

    JSONObject requestCallGatherById(String callId, String gatherId) throws IOException;

    void updateCallGather(String callId, String gatherId, Map<String, Object> params) throws IOException;

    JSONObject createConference(Map<String, Object> params) throws IOException;

    JSONObject requestConferenceById(String id) throws IOException;
}
