package com.bandwidth.sdk.model;

import com.bandwidth.sdk.driver.IRestDriver;
import org.apache.commons.lang3.StringUtils;
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
public class Recordings extends BaseModelObject {

    public Recordings(IRestDriver driver, String parentUri) {
        super(driver, parentUri, null);
    }

    @Override
    public String getUri() {
        return StringUtils.join(new String[]{
                parentUri,
                "recordings"
        }, '/');
    }

    public QueryRecordingsBuilder queryRecordingsBuilder() {
        return new QueryRecordingsBuilder();
    }

    public Recording getRecordingById(String id) throws IOException {
        String recordingsUri = getUri();
        String uri = StringUtils.join(new String[]{
                recordingsUri,
                id
        }, '/');
        JSONObject jsonObject = driver.getObject(uri);
        return new Recording(driver, recordingsUri, jsonObject);
    }

    private List<Recording> getRecordings(Map<String, Object> params) throws IOException {
        String uri = getUri();
        JSONArray array = driver.getArray(uri, params);

        List<Recording> recordings = new ArrayList<Recording>();
        for (Object obj : array) {
            recordings.add(new Recording(driver, uri, (JSONObject) obj));
        }
        return recordings;
    }

    public class QueryRecordingsBuilder {

        private final Map<String, Object> params = new HashMap<String, Object>();

        public QueryRecordingsBuilder page(int page) {
            params.put("page", page);
            return this;
        }

        public QueryRecordingsBuilder size(int size) {
            params.put("size", size);
            return this;
        }

        public List<Recording> list() throws IOException {
            return getRecordings(params);
        }
    }
}
