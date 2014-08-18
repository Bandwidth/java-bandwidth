package com.bandwidth.sdk.driver;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.Map;

/**
 * @author vpotapenko
 */
public interface IRestDriver {

    JSONArray getArray(String uri, Map<String, Object> params) throws IOException;

    JSONObject getObject(String uri) throws IOException;

    JSONObject create(String uri, Map<String, Object> params) throws IOException;

    void post(String uri, Map<String, Object> params) throws IOException;

    void delete(String uri) throws IOException;
}
