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
}
