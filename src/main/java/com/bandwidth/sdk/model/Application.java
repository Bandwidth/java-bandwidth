package com.bandwidth.sdk.model;

import com.bandwidth.sdk.driver.IRestDriver;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.Map;

/**
 * Information about one of your applications.
 *
 * @author vpotapenko
 */
public class Application extends BaseModelObject {

    public Application(IRestDriver driver, String parentUri, JSONObject jsonObject) {
        super(driver, parentUri, jsonObject);
    }

    public String getName() {
        return getPropertyAsString("name");
    }

    public String getIncomingCallUrl() {
        return getPropertyAsString("incomingCallUrl");
    }

    public String getIncomingSmsUrl() {
        return getPropertyAsString("incomingSmsUrl");
    }

    public String getCallbackHttpMethod() {
        return getPropertyAsString("callbackHttpMethod");
    }

    public String getIncomingCallFallbackUrl() {
        return getPropertyAsString("incomingCallFallbackUrl");
    }

    public Long getIncomingCallUrlCallbackTimeout() {
        return getPropertyAsLong("incomingCallUrlCallbackTimeout");
    }

    public Long getIncomingSmsUrlCallbackTimeout() {
        return getPropertyAsLong("incomingSmsUrlCallbackTimeout");
    }

    public boolean isAutoAnswer() {
        return getPropertyAsBoolean("autoAnswer");
    }

    public void setName(String name) {
        if (name == null) throw new IllegalArgumentException();

        putProperty("name", name);
    }

    public void setIncomingCallUrl(String incomingCallUrl) {
        putProperty("incomingCallUrl", incomingCallUrl);
    }

    public void setIncomingSmsUrl(String incomingSmsUrl) {
        putProperty("incomingSmsUrl", incomingSmsUrl);
    }

    public void setAutoAnswer(boolean autoAnswer) {
        putProperty("autoAnswer", autoAnswer);
    }

    public void setIncomingCallFallbackUrl(String incomingCallFallbackUrl) {
        putProperty("incomingCallFallbackUrl", incomingCallFallbackUrl);
    }

    public void setIncomingCallUrlCallbackTimeout(Long incomingCallUrlCallbackTimeout) {
        putProperty("incomingCallUrlCallbackTimeout", incomingCallUrlCallbackTimeout);
    }

    public void setIncomingSmsUrlCallbackTimeout(Long incomingSmsUrlCallbackTimeout) {
        putProperty("incomingSmsUrlCallbackTimeout", incomingSmsUrlCallbackTimeout);
    }

    public void setCallbackHttpMethod(String callbackHttpMethod) {
        putProperty("callbackHttpMethod", callbackHttpMethod);
    }

    /**
     * Makes changes of the application.
     *
     * @throws IOException
     */
    public void commit() throws IOException {
        Map<String, Object> params = toMap();
        params.remove("id");

        driver.post(getUri(), params);
    }

    /**
     * Permanently deletes application.
     *
     * @throws IOException
     */
    public void delete() throws IOException {
        driver.delete(getUri());
    }

    @Override
    public String toString() {
        return "Application{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", incomingCallUrl='" + getIncomingCallUrl() + '\'' +
                ", incomingSmsUrl='" + getIncomingSmsUrl() + '\'' +
                ", autoAnswer=" + isAutoAnswer() +
                ", incomingCallUrlCallbackTimeout='" + getIncomingCallUrlCallbackTimeout() + '\'' +
                ", incomingSmsUrlCallbackTimeout='" + getIncomingSmsUrlCallbackTimeout() + '\'' +
                ", callbackHttpMethod='" + getCallbackHttpMethod() + '\'' +
                ", incomingCallFallbackUrl='" + getIncomingCallFallbackUrl() + '\'' +
                '}';
    }
}
