package com.bandwidth.sdk.driver;

import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author vpotapenko
 */
public class MockRestDriver implements IRestDriver {

    public final List<RestRequest> requests = new ArrayList<RestRequest>();

    public JSONObject result;

    @Override
    public JSONObject requestAccountInfo() throws IOException {
        requests.add(new RestRequest("requestAccountInfo"));
        return result;
    }

    public static class RestRequest {

        public final String name;
        public final Object[] params;

        public RestRequest(String name, Object... params) {
            this.name = name;
            this.params = params;
        }
    }
}
