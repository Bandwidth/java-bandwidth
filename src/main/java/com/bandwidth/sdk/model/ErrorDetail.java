package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthClient;
import org.json.simple.JSONObject;

/**
 * @author vpotapenko
 */
public class ErrorDetail extends ResourceBase {

	String parentUri;
    public ErrorDetail(final BandwidthClient client, final String parentUri, final JSONObject jsonObject) {
        super(client, jsonObject);
        
        this.parentUri = parentUri;
    }

    @Override
    protected void setUp(final JSONObject jsonObject) {
        this.id = (String) jsonObject.get("id");
        updateProperties(jsonObject);
    }      
    
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
