package com.bandwidth.sdk.model;

import com.bandwidth.sdk.driver.IRestDriver;
import org.apache.commons.lang3.StringUtils;
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
public class Applications extends BaseModelObject {

    public Applications(IRestDriver driver, String parentUri) {
        super(driver, parentUri, null);
    }

    public QueryApplicationsBuilder queryApplicationsBuilder() {
        return new QueryApplicationsBuilder();
    }

    public Application getApplicationById(String id) throws IOException {
        String applicationsUri = getUri();
        String uri = StringUtils.join(new String[]{
                applicationsUri,
                id
        }, '/');
        JSONObject jsonObject = driver.getObject(uri);
        return new Application(driver, applicationsUri, jsonObject);
    }

    @Override
    public String getUri() {
        return StringUtils.join(new String[]{
                parentUri,
                "applications"
        }, '/');
    }

    public NewApplicationBuilder newApplicationBuilder(String name) {
        return new NewApplicationBuilder(name);
    }

    private List<Application> getApplications(Map<String, Object> params) throws IOException {
        String applicationsUri = getUri();
        JSONArray array = driver.getArray(applicationsUri, params);

        List<Application> applications = new ArrayList<Application>();
        for (Object obj : array) {
            applications.add(new Application(driver, applicationsUri, (JSONObject) obj));
        }
        return applications;
    }

    private Application createApplication(Map<String, Object> params) throws IOException {
        String applicationsUri = getUri();
        JSONObject jsonObject = driver.create(applicationsUri, params);
        return new Application(driver, applicationsUri, jsonObject);
    }

    public class QueryApplicationsBuilder {

        private Map<String, Object> params = new HashMap<String, Object>();

        public QueryApplicationsBuilder page(Integer page) {
            params.put("page", page);
            return this;
        }

        public QueryApplicationsBuilder size(Integer size) {
            params.put("size", size);
            return this;
        }

        public List<Application> list() throws IOException {
            return getApplications(params);
        }
    }

    public class NewApplicationBuilder {

        private final Map<String, Object> params = new HashMap<String, Object>();

        public NewApplicationBuilder(String name) {
            params.put("name", name);
        }

        public NewApplicationBuilder incomingCallUrl(String incomingCallUrl) {
            params.put("incomingCallUrl", incomingCallUrl);
            return this;
        }

        public NewApplicationBuilder incomingSmsUrl(String incomingSmsUrl) {
            params.put("incomingSmsUrl", incomingSmsUrl);
            return this;
        }

        public NewApplicationBuilder autoAnswer(Boolean autoAnswer) {
            params.put("autoAnswer", autoAnswer);
            return this;
        }

        public NewApplicationBuilder incomingCallFallbackUrl(String incomingCallFallbackUrl) {
            params.put("incomingCallFallbackUrl", incomingCallFallbackUrl);
            return this;
        }

        public NewApplicationBuilder incomingCallUrlCallbackTimeout(Long incomingCallUrlCallbackTimeout) {
            params.put("incomingCallUrlCallbackTimeout", incomingCallUrlCallbackTimeout);
            return this;
        }

        public NewApplicationBuilder incomingSmsUrlCallbackTimeout(Long incomingSmsUrlCallbackTimeout) {
            params.put("incomingSmsUrlCallbackTimeout", incomingSmsUrlCallbackTimeout);
            return this;
        }

        public NewApplicationBuilder callbackHttpMethod(String callbackHttpMethod) {
            params.put("callbackHttpMethod", callbackHttpMethod);
            return this;
        }

        public Application create() throws IOException {
            return createApplication(params);
        }
    }
}
