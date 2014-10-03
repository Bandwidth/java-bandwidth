package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthRestClient;
import org.json.simple.JSONObject;

/**
 * @author vpotapenko
 */
public class ErrorDetail extends BaseModelObject {

    public ErrorDetail(BandwidthRestClient client, String parentUri, JSONObject jsonObject) {
        super(client, parentUri, jsonObject);
    }

    @Override
    protected String getUri() {
        return null;
    }

    public String getName() {
        return getPropertyAsString("name");
    }

    public String getValue() {
        return getPropertyAsString("value");
    }

    @Override
    public String toString() {
        return "ErrorDetail{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", value='" + getValue() + '\'' +
                '}';
    }
}
