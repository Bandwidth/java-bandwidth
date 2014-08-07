package com.bandwidth.sdk.applications;

import com.bandwidth.sdk.BandwidthRestClient;
import org.json.simple.JSONObject;

import java.io.IOException;

/**
 * @author vpotapenko
 */
public class BandwidthApplication {

    private final BandwidthRestClient client;

    private String id;
    private String name;
    private String incomingCallUrl;
    private String incomingSmsUrl;
    private boolean autoAnswer;

    private String incomingCallFallbackUrl;
    private Long incomingCallUrlCallbackTimeout;
    private Long incomingSmsUrlCallbackTimeout;
    private String callbackHttpMethod;

    public static BandwidthApplication from(BandwidthRestClient client, JSONObject jsonObject) {
        BandwidthApplication application = new BandwidthApplication(client);

        application.id = (String) jsonObject.get("id");
        application.name = (String) jsonObject.get("name");
        application.incomingCallUrl = (String) jsonObject.get("incomingCallUrl");
        application.incomingSmsUrl = (String) jsonObject.get("incomingSmsUrl");
        application.autoAnswer = (Boolean) jsonObject.get("autoAnswer");
        application.incomingCallUrlCallbackTimeout = (Long) jsonObject.get("incomingCallUrlCallbackTimeout");
        application.incomingSmsUrlCallbackTimeout = (Long) jsonObject.get("incomingSmsUrlCallbackTimeout");
        application.callbackHttpMethod = (String) jsonObject.get("callbackHttpMethod");
        application.incomingCallFallbackUrl = (String) jsonObject.get("incomingCallFallbackUrl");

        return application;
    }

    private BandwidthApplication(BandwidthRestClient client) {
        this.client = client;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIncomingCallUrl() {
        return incomingCallUrl;
    }

    public String getIncomingSmsUrl() {
        return incomingSmsUrl;
    }

    public boolean isAutoAnswer() {
        return autoAnswer;
    }

    public void delete() throws IOException {
        if (id == null) return;

        client.getRestDriver().deleteApplication(getId());
        id = null;
    }

    @Override
    public String toString() {
        return "BandwidthApplication{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", incomingCallUrl='" + incomingCallUrl + '\'' +
                ", incomingSmsUrl='" + incomingSmsUrl + '\'' +
                ", autoAnswer=" + autoAnswer +
                ", incomingCallUrlCallbackTimeout='" + incomingCallUrlCallbackTimeout + '\'' +
                ", incomingSmsUrlCallbackTimeout='" + incomingSmsUrlCallbackTimeout + '\'' +
                ", callbackHttpMethod='" + callbackHttpMethod + '\'' +
                ", incomingCallFallbackUrl='" + incomingCallFallbackUrl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BandwidthApplication)) return false;

        BandwidthApplication that = (BandwidthApplication) o;

        if (autoAnswer != that.autoAnswer) return false;
        if (callbackHttpMethod != null ? !callbackHttpMethod.equals(that.callbackHttpMethod) : that.callbackHttpMethod != null)
            return false;
        if (!id.equals(that.id)) return false;
        if (incomingCallFallbackUrl != null ? !incomingCallFallbackUrl.equals(that.incomingCallFallbackUrl) : that.incomingCallFallbackUrl != null)
            return false;
        if (incomingCallUrl != null ? !incomingCallUrl.equals(that.incomingCallUrl) : that.incomingCallUrl != null)
            return false;
        if (incomingCallUrlCallbackTimeout != null ? !incomingCallUrlCallbackTimeout.equals(that.incomingCallUrlCallbackTimeout) : that.incomingCallUrlCallbackTimeout != null)
            return false;
        if (incomingSmsUrl != null ? !incomingSmsUrl.equals(that.incomingSmsUrl) : that.incomingSmsUrl != null)
            return false;
        if (incomingSmsUrlCallbackTimeout != null ? !incomingSmsUrlCallbackTimeout.equals(that.incomingSmsUrlCallbackTimeout) : that.incomingSmsUrlCallbackTimeout != null)
            return false;
        if (!name.equals(that.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (incomingCallUrl != null ? incomingCallUrl.hashCode() : 0);
        result = 31 * result + (incomingSmsUrl != null ? incomingSmsUrl.hashCode() : 0);
        result = 31 * result + (autoAnswer ? 1 : 0);
        result = 31 * result + (incomingCallFallbackUrl != null ? incomingCallFallbackUrl.hashCode() : 0);
        result = 31 * result + (incomingCallUrlCallbackTimeout != null ? incomingCallUrlCallbackTimeout.hashCode() : 0);
        result = 31 * result + (incomingSmsUrlCallbackTimeout != null ? incomingSmsUrlCallbackTimeout.hashCode() : 0);
        result = 31 * result + (callbackHttpMethod != null ? callbackHttpMethod.hashCode() : 0);
        return result;
    }
}
