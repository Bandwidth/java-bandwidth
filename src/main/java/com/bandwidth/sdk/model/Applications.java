package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.BandwidthRestClient;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Point for <code>/v1/users/{userId}/applications</code>
 *
 * @author vpotapenko
 */
public class Applications extends BaseModelObject {


    public Applications(BandwidthRestClient client){
        super(client, null);
    }

    /**
     * Creates builder for getting applications.
     * <br>Example:<br>
     * <code>List<Application> list = applications.queryApplicationsBuilder().page(2).size(100).list();</code>
     * @return
     */
    public QueryApplicationsBuilder queryApplicationsBuilder() {
        return new QueryApplicationsBuilder();
    }

//    /**
//     * Gets information about one of your applications.
//     *
//     * @param id application id
//     * @return application
//     * @throws IOException
//     */
//    public Application getApplication(String id) throws IOException {
//        String applicationsUri = getUri();
//        String uri = StringUtils.join(new String[]{
//                applicationsUri,
//                id
//        }, '/');
//        JSONObject jsonObject = client.getObject(uri);
//        return new Application(client, applicationsUri, jsonObject);
//    }

    /**
     * Creates builder for creating an application that can handle calls and messages for one of your phone number. Many phone numbers can share an application.
     * <br>Example:<br>
     *     <code>Application application = applications.newApplicationBuilder("appName").incomingCallUrl("http://some_url.com/method").create();</code>
     *
     * @param name application name
     * @return builder for creating application
     */
    public NewApplicationBuilder newApplicationBuilder(String name) {
        return new NewApplicationBuilder(name);
    }

    private List<Application> getApplications(Map<String, Object> params) throws IOException {
        String applicationsUri = getUri();
        JSONArray array = client.getArray(applicationsUri, params);

        List<Application> applications = new ArrayList<Application>();
        for (Object obj : array) {
            applications.add(new Application(client, (JSONObject) obj));
        }
        return applications;
    }

    @Override
    protected String getUri() {
        return client.getUserResourceUri(BandwidthConstants.APPLICATIONS_URI_PATH);
//        return StringUtils.join(new String[]{
//                parentUri,
//                "applications"
//        }, '/');
    }

    private Application createApplication(Map<String, Object> params) throws IOException {
        String applicationsUri = getUri();
        JSONObject jsonObject = client.create(applicationsUri, params);
        return new Application(client, jsonObject);
    }

    public class QueryApplicationsBuilder {

        private Map<String, Object> params = new HashMap<String, Object>();

        public QueryApplicationsBuilder page(int page) {
            params.put("page", page);
            return this;
        }

        public QueryApplicationsBuilder size(int size) {
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
