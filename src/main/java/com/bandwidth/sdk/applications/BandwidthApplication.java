package com.bandwidth.sdk.applications;

import com.bandwidth.sdk.BandwidthRestClient;
import org.json.simple.JSONObject;

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

    private boolean deleted;

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

    public boolean isDeleted() {
        return deleted;
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
                ", deleted=" + deleted +
                '}';
    }
}
