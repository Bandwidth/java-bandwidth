package com.bandwidth.sdk.model;

import com.bandwidth.sdk.driver.IRestDriver;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.*;

/**
 * @author vpotapenko
 */
public class PhoneNumber extends BaseModelObject {

    public PhoneNumber(IRestDriver driver, String parentUri, JSONObject jsonObject) {
        super(driver, parentUri, jsonObject);
    }

    public void commit() throws IOException {
        Map<String, Object> params = new HashMap<String, Object>();

        String applicationId = getPropertyAsString("applicationId");
        if (applicationId != null) params.put("applicationId", applicationId);

        String name = getName();
        if (name != null) params.put("name", name);

        String fallbackNumber = getFallbackNumber();
        if (fallbackNumber != null) params.put("fallbackNumber", fallbackNumber);

        String uri = getUri();
        driver.post(uri, params);

        JSONObject object = driver.getObject(uri);
        updateProperties(object);
    }

    public void delete() throws IOException {
        driver.delete(getUri());
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
