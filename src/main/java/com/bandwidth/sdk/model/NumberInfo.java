package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthRestClient;
import org.json.simple.JSONObject;

import java.util.Date;

/**
 * Information about number.
 *
 * @author vpotapenko
 */
public class NumberInfo extends BaseModelObject {

    public NumberInfo(BandwidthRestClient client, String parentUri, JSONObject jsonObject) {
        super(client, parentUri, jsonObject);
    }

    public String getName() {
        return getPropertyAsString("name");
    }

    public String getNumber() {
        return getPropertyAsString("number");
    }

    public Date getCreated() {
        return getPropertyAsDate("created");
    }

    public Date getUpdated() {
        return getPropertyAsDate("updated");
    }

    @Override
    public String toString() {
        return "NumberInfo{" +
                "name='" + getName() + '\'' +
                ", number='" + getNumber() + '\'' +
                ", created=" + getCreated() +
                ", updated=" + getUpdated() +
                '}';
    }
}
