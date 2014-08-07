package com.bandwidth.sdk.applications;

import com.bandwidth.sdk.BandwidthRestClient;
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
public class BandwidthApplications {

    private final BandwidthRestClient client;

    public BandwidthApplications(BandwidthRestClient client) {
        this.client = client;
    }

    public ApplicationsListBuilder getApplications() {
        return new ApplicationsListBuilder(this);
    }

    private List<BandwidthApplication> getApplications(Map<String, String> params) throws IOException {
        JSONArray array = client.getRestDriver().requestApplications(params);

        List<BandwidthApplication> applications = new ArrayList<BandwidthApplication>();
        for (Object obj : array) {
            applications.add(BandwidthApplication.from(client, (JSONObject) obj));
        }
        return applications;
    }

    public ApplicationBuilder newApplication(String name) {
        return new ApplicationBuilder(this, name);
    }

    private BandwidthApplication createApplication(Map<String, String> params) throws IOException {
        JSONObject jsonObject = client.getRestDriver().createApplication(params);
        return BandwidthApplication.from(client, jsonObject);
    }

    public static class ApplicationsListBuilder {

        private final BandwidthApplications applications;

        private Integer page;
        private Integer size;

        public ApplicationsListBuilder(BandwidthApplications applications) {
            this.applications = applications;
        }

        public ApplicationsListBuilder page(Integer page) {
            this.page = page;
            return this;
        }

        public ApplicationsListBuilder size(Integer size) {
            this.size = size;
            return this;
        }

        public List<BandwidthApplication> get() throws IOException {
            Map<String, String> params = new HashMap<String, String>();

            if (page != null) params.put("page", String.valueOf(page));
            if (size != null) params.put("size", String.valueOf(size));

            return applications.getApplications(params);
        }
    }

    public static class ApplicationBuilder {

        private final BandwidthApplications applications;

        private String name;
        private String incomingCallUrl;
        private String incomingSmsUrl;
        private Boolean autoAnswer;

        private String incomingCallFallbackUrl;
        private Long incomingCallUrlCallbackTimeout;
        private Long incomingSmsUrlCallbackTimeout;
        private String callbackHttpMethod;

        public ApplicationBuilder(BandwidthApplications applications, String name) {
            this.applications = applications;
            this.name = name;
        }

        public ApplicationBuilder incomingCallUrl(String incomingCallUrl) {
            this.incomingCallUrl = incomingCallUrl;
            return this;
        }

        public ApplicationBuilder incomingSmsUrl(String incomingSmsUrl) {
            this.incomingSmsUrl = incomingSmsUrl;
            return this;
        }

        public ApplicationBuilder autoAnswer(Boolean autoAnswer) {
            this.autoAnswer = autoAnswer;
            return this;
        }

        public ApplicationBuilder incomingCallFallbackUrl(String incomingCallFallbackUrl) {
            this.incomingCallFallbackUrl = incomingCallFallbackUrl;
            return this;
        }

        public ApplicationBuilder incomingCallUrlCallbackTimeout(Long incomingCallUrlCallbackTimeout) {
            this.incomingCallUrlCallbackTimeout = incomingCallUrlCallbackTimeout;
            return this;
        }

        public ApplicationBuilder incomingSmsUrlCallbackTimeout(Long incomingSmsUrlCallbackTimeout) {
            this.incomingSmsUrlCallbackTimeout = incomingSmsUrlCallbackTimeout;
            return this;
        }

        public ApplicationBuilder callbackHttpMethod(String callbackHttpMethod) {
            this.callbackHttpMethod = callbackHttpMethod;
            return this;
        }

        public BandwidthApplication create() throws IOException {
            Map<String, String> params = new HashMap<String, String>();

            params.put("name", name);

            if (incomingCallUrl != null) params.put("incomingCallUrl", incomingCallUrl);
            if (incomingSmsUrl != null) params.put("incomingSmsUrl", incomingSmsUrl);
            if (autoAnswer != null) params.put("autoAnswer", String.valueOf(autoAnswer));
            if (incomingCallFallbackUrl != null) params.put("incomingCallFallbackUrl", incomingCallFallbackUrl);
            if (incomingCallUrlCallbackTimeout != null)
                params.put("incomingCallUrlCallbackTimeout", String.valueOf(incomingCallUrlCallbackTimeout));
            if (incomingSmsUrlCallbackTimeout != null)
                params.put("incomingSmsUrlCallbackTimeout", String.valueOf(incomingSmsUrlCallbackTimeout));
            if (callbackHttpMethod != null) params.put("callbackHttpMethod", callbackHttpMethod);

            return applications.createApplication(params);
        }
    }
}
