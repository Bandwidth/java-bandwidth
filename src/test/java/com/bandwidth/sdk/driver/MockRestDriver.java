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
    public JSONArray getArray(String uri, Map<String, Object> params) throws IOException {
        requests.add(new RestRequest("getArray", uri, params));
        return arrayResult;
    }

    @Override
    public JSONObject getObject(String uri) throws IOException {
        requests.add(new RestRequest("getObject", uri, null));
        return result;
    }

    @Override
    public JSONObject create(String uri, Map<String, Object> params) throws IOException {
        requests.add(new RestRequest("create", uri, params));
        return result;
    }

    @Override
    public void post(String uri, Map<String, Object> params) throws IOException {
        requests.add(new RestRequest("post", uri, params));
    }

    @Override
    public void delete(String uri) throws IOException {
        requests.add(new RestRequest("delete", uri, null));
    }


    public static class RestRequest {

        public final String name;
        public final String uri;
        public final Map<String, Object> params;

        public RestRequest(String name, String uri, Map<String, Object> params) {
            this.name = name;
            this.uri = uri;
            this.params = params;
        }
    }
}
