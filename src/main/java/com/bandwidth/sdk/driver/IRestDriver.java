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

    JSONArray requestAccountTransactions(Map<String, String> params) throws IOException;

    JSONArray requestApplications(Map<String, String> params) throws IOException;

    JSONObject createApplication(Map<String, String> params) throws IOException;

    JSONObject requestApplicationById(String id) throws IOException;

    void deleteApplication(String id) throws IOException;

    void updateApplication(String id, Map<String, String> params) throws IOException;

    JSONArray requestLocalAvailableNumbers(Map<String, String> params) throws IOException;
}
