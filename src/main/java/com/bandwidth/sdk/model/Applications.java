package com.bandwidth.sdk.model;

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
public class Applications {

    private final BandwidthRestClient client;

    public Applications(BandwidthRestClient client) {
        this.client = client;
    }

    public QueryApplicationsBuilder queryApplicationsBuilder() {
        return new QueryApplicationsBuilder();
    }

    public Application getApplicationById(String id) throws IOException {
        JSONObject jsonObject = client.requestApplicationById(id);
        return Application.from(client, jsonObject);
    }

    public NewApplicationBuilder newApplicationBuilder(String name) {
        return new NewApplicationBuilder(name);
    }

    private List<Application> getApplications(Map<String, Object> params) throws IOException {
        JSONArray array = client.requestApplications(params);

        List<Application> applications = new ArrayList<Application>();
        for (Object obj : array) {
            applications.add(Application.from(client, (JSONObject) obj));
        }
        return applications;
    }

    private Application createApplication(Map<String, Object> params) throws IOException {
        JSONObject jsonObject = client.createApplication(params);
        return Application.from(client, jsonObject);
    }

    public class QueryApplicationsBuilder {

        private Integer page;
        private Integer size;

        public QueryApplicationsBuilder page(Integer page) {
            this.page = page;
            return this;
        }

        public QueryApplicationsBuilder size(Integer size) {
            this.size = size;
            return this;
        }

        public List<Application> list() throws IOException {
            Map<String, Object> params = new HashMap<String, Object>();

            if (page != null) params.put("page", String.valueOf(page));
            if (size != null) params.put("size", String.valueOf(size));

            return getApplications(params);
        }
    }

    public class NewApplicationBuilder {

        private String name;
        private String incomingCallUrl;
        private String incomingSmsUrl;
        private Boolean autoAnswer;

        private String incomingCallFallbackUrl;
        private Long incomingCallUrlCallbackTimeout;
        private Long incomingSmsUrlCallbackTimeout;
        private String callbackHttpMethod;

        public NewApplicationBuilder(String name) {
            this.name = name;
        }

        public NewApplicationBuilder incomingCallUrl(String incomingCallUrl) {
            this.incomingCallUrl = incomingCallUrl;
            return this;
        }

        public NewApplicationBuilder incomingSmsUrl(String incomingSmsUrl) {
            this.incomingSmsUrl = incomingSmsUrl;
            return this;
        }

        public NewApplicationBuilder autoAnswer(Boolean autoAnswer) {
            this.autoAnswer = autoAnswer;
            return this;
        }

        public NewApplicationBuilder incomingCallFallbackUrl(String incomingCallFallbackUrl) {
            this.incomingCallFallbackUrl = incomingCallFallbackUrl;
            return this;
        }

        public NewApplicationBuilder incomingCallUrlCallbackTimeout(Long incomingCallUrlCallbackTimeout) {
            this.incomingCallUrlCallbackTimeout = incomingCallUrlCallbackTimeout;
            return this;
        }

        public NewApplicationBuilder incomingSmsUrlCallbackTimeout(Long incomingSmsUrlCallbackTimeout) {
            this.incomingSmsUrlCallbackTimeout = incomingSmsUrlCallbackTimeout;
            return this;
        }

        public NewApplicationBuilder callbackHttpMethod(String callbackHttpMethod) {
            this.callbackHttpMethod = callbackHttpMethod;
            return this;
        }

        public Application create() throws IOException {
            Map<String, Object> params = new HashMap<String, Object>();

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

            return createApplication(params);
        }
    }
}
