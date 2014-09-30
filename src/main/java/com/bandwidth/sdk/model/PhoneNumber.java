package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.BandwidthRestClient;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Information about your phone number.
 *
 * @author vpotapenko
 */
public class PhoneNumber extends BaseModelObject {

    public static PhoneNumber getPhoneNumber(String phoneNumberId) throws IOException {
        assert(phoneNumberId != null);

        BandwidthRestClient client = BandwidthRestClient.getInstance();
        String phoneNumberUri = client.getUserResourceInstanceUri(BandwidthConstants.PHONE_NUMBER_URI_PATH, phoneNumberId);
        JSONObject phoneNumberObj = client.getObject(phoneNumberUri);
        PhoneNumber number = new PhoneNumber(client, client.getUserResourceUri(
                BandwidthConstants.PHONE_NUMBER_URI_PATH), phoneNumberObj);
        return number;
    }

    public PhoneNumber(BandwidthRestClient client, String parentUri, JSONObject jsonObject) {
        super(client, parentUri, jsonObject);
    }

    /**
     * Makes changes to a number you have.
     *
     * @throws IOException
     */
    public void commit() throws IOException {
        Map<String, Object> params = new HashMap<String, Object>();

        String applicationId = getPropertyAsString("applicationId");
        if (applicationId != null) params.put("applicationId", applicationId);

        String name = getName();
        if (name != null) params.put("name", name);

        String fallbackNumber = getFallbackNumber();
        if (fallbackNumber != null) params.put("fallbackNumber", fallbackNumber);

        String uri = getUri();
        client.post(uri, params);

        JSONObject object = client.getObject(uri);
        updateProperties(object);
    }

    /**
     * Removes a number from your account so you can no longer make or receive calls,
     * or send or receive messages with it. When you remove a number from your account,
     * it will not immediately become available for re-use, so be careful.
     *
     * @throws IOException
     */
    public void delete() throws IOException {
        client.delete(getUri());
    }

    public String getApplication() {
        return getPropertyAsString("application");
    }

    public String getNumber() {
        return getPropertyAsString("number");
    }

    public String getNationalNumber() {
        return getPropertyAsString("nationalNumber");
    }

    public String getName() {
        return getPropertyAsString("name");
    }

    public String getCity() {
        return getPropertyAsString("city");
    }

    public String getState() {
        return getPropertyAsString("state");
    }

    public String getNumberState() {
        return getPropertyAsString("numberState");
    }

    public String getFallbackNumber() {
        return getPropertyAsString("fallbackNumber");
    }

    public Double getPrice() {
        return getPropertyAsDouble("price");
    }

    public Date getCreatedTime() {
        return getPropertyAsDate("createdTime");
    }

    public void setApplicationId(String applicationId) {
        putProperty("applicationId", applicationId);
    }

    public void setName(String name) {
        putProperty("name", name);
    }

    public void setFallbackNumber(String fallbackNumber) {
        putProperty("fallbackNumber", fallbackNumber);
    }

    @Override
    public String toString() {
        return "PhoneNumber{" +
                "id='" + getId() + '\'' +
                ", application='" + getApplication() + '\'' +
                ", number='" + getNumber() + '\'' +
                ", nationalNumber='" + getNationalNumber() + '\'' +
                ", name='" + getName() + '\'' +
                ", city='" + getCity() + '\'' +
                ", state='" + getState() + '\'' +
                ", numberState='" + getNumberState() + '\'' +
                ", fallbackNumber='" + getFallbackNumber() + '\'' +
                ", price=" + getPrice() +
                ", createdTime=" + getCreatedTime() +
                '}';
    }
}
