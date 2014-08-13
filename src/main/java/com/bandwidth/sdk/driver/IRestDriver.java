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
}
