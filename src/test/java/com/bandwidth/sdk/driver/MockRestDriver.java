package com.bandwidth.sdk.driver;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
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
    public JSONArray requestAccountTransactions(Map<String, String> params) {
        requests.add(new RestRequest("requestAccountTransactions", params));
        return arrayResult;
    }

    @Override
    public JSONArray requestApplications(Map<String, String> params) throws IOException {
        requests.add(new RestRequest("requestApplications", params));
        return arrayResult;
    }

    public static class RestRequest {

        public final String name;
        public final Map<String, String> params;

        public RestRequest(String name) {
            this(name, null);
        }

        public RestRequest(String name, Map<String, String> params) {
            this.name = name;
            this.params = params;
        }
    }
}
