package com.bandwidth.sdk.driver;

import com.bandwidth.sdk.BandwidthConstants;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author vpotapenko
 */
public class HttpRestDriver implements IRestDriver {

    private final String userId;
    private final String token;
    private final String secret;

    private HttpClient httpClient;

    public HttpRestDriver(String userId, String token, String secret) {
        this.userId = userId;
        this.token = token;
        this.secret = secret;

        httpClient = new DefaultHttpClient();
    }

    /**
     * For testing
     */
    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public JSONObject requestAccountInfo() throws IOException {
        String path = getAccountPath();
        RestResponse response = request(path, HttpMethod.GET);
        if (response.isError()) throw new IOException(response.getResponseText());

        if (response.isJson()) {
            try {
                return (JSONObject) new JSONParser().parse(response.getResponseText());
            } catch (org.json.simple.parser.ParseException e) {
                throw new IOException(e);
            }
        } else {
            throw new IOException("Response is not a JSON format.");
        }
    }

    @Override
    public JSONArray requestAccountTransactions(Map<String, Object> params) throws IOException {
        RestResponse response = request(getAccountTransactionPath(), HttpMethod.GET, params);
        if (response.isError()) throw new IOException(response.getResponseText());

        if (response.isJson()) {
            try {
                return (JSONArray) new JSONParser().parse(response.getResponseText());
            } catch (org.json.simple.parser.ParseException e) {
                throw new IOException(e);
            }
        } else {
            throw new IOException("Response is not a JSON format.");
        }
    }

    @Override
    public JSONArray requestApplications(Map<String, Object> params) throws IOException {
        RestResponse response = request(getApplicationsPath(), HttpMethod.GET, params);
        if (response.isError()) throw new IOException(response.getResponseText());

        if (response.isJson()) {
            try {
                return (JSONArray) new JSONParser().parse(response.getResponseText());
            } catch (org.json.simple.parser.ParseException e) {
                throw new IOException(e);
            }
        } else {
            throw new IOException("Response is not a JSON format.");
        }
    }

    @Override
    public JSONObject createApplication(Map<String, Object> params) throws IOException {
        RestResponse response = request(getApplicationsPath(), HttpMethod.POST, params);
        if (response.isError()) throw new IOException(response.getResponseText());

        String location = response.getLocation();
        if (location != null) {
            response = request(location, HttpMethod.GET);
            if (response.isError()) throw new IOException(response.getResponseText());

            if (response.isJson()) {
                try {
                    return (JSONObject) new JSONParser().parse(response.getResponseText());
                } catch (org.json.simple.parser.ParseException e) {
                    throw new IOException(e);
                }
            } else {
                throw new IOException("Response is not a JSON format.");
            }
        } else {
            throw new IOException("There is no location of new application.");
        }
    }

    @Override
    public JSONObject requestApplicationById(String id) throws IOException {
        RestResponse response = request(getApplicationPath(id), HttpMethod.GET);
        if (response.isError())
            throw new IOException(response.getResponseText());

        if (response.isJson()) {
            try {
                return (JSONObject) new JSONParser().parse(response.getResponseText());
            } catch (org.json.simple.parser.ParseException e) {
                throw new IOException(e);
            }
        } else {
            throw new IOException("Response is not a JSON format.");
        }
    }

    @Override
    public void deleteApplication(String id) throws IOException {
        RestResponse response = request(getApplicationPath(id), HttpMethod.DELETE);
        if (response.isError()) throw new IOException(response.getResponseText());
    }

    @Override
    public void updateApplication(String id, Map<String, Object> params) throws IOException {
        RestResponse response = request(getApplicationPath(id), HttpMethod.POST, params);
        if (response.isError()) throw new IOException(response.getResponseText());
    }

    @Override
    public JSONArray requestLocalAvailableNumbers(Map<String, Object> params) throws IOException {
        RestResponse response = request(getLocalAvailableNumbersPath(), HttpMethod.GET, params);
        if (response.isError()) throw new IOException(response.getResponseText());

        if (response.isJson()) {
            try {
                return (JSONArray) new JSONParser().parse(response.getResponseText());
            } catch (org.json.simple.parser.ParseException e) {
                throw new IOException(e);
            }
        } else {
            throw new IOException("Response is not a JSON format.");
        }
    }

    @Override
    public JSONArray requestTollFreeAvailableNumbers(Map<String, Object> params) throws IOException {
        RestResponse response = request(getTollFreeAvailableNumbersPath(), HttpMethod.GET, params);
        if (response.isError()) throw new IOException(response.getResponseText());

        if (response.isJson()) {
            try {
                return (JSONArray) new JSONParser().parse(response.getResponseText());
            } catch (org.json.simple.parser.ParseException e) {
                throw new IOException(e);
            }
        } else {
            throw new IOException("Response is not a JSON format.");
        }
    }

    @Override
    public JSONArray requestBridges() throws IOException {
        RestResponse response = request(getBridgesPath(), HttpMethod.GET);
        if (response.isError()) throw new IOException(response.getResponseText());

        if (response.isJson()) {
            try {
                return (JSONArray) new JSONParser().parse(response.getResponseText());
            } catch (org.json.simple.parser.ParseException e) {
                throw new IOException(e);
            }
        } else {
            throw new IOException("Response is not a JSON format.");
        }
    }

    @Override
    public JSONObject createBridge(Map<String, Object> params) throws IOException {
        RestResponse response = request(getBridgesPath(), HttpMethod.POST, params);
        if (response.isError()) throw new IOException(response.getResponseText());

        String location = response.getLocation();
        if (location != null) {
            response = request(location, HttpMethod.GET);
            if (response.isError()) throw new IOException(response.getResponseText());

            if (response.isJson()) {
                try {
                    return (JSONObject) new JSONParser().parse(response.getResponseText());
                } catch (org.json.simple.parser.ParseException e) {
                    throw new IOException(e);
                }
            } else {
                throw new IOException("Response is not a JSON format.");
            }
        } else {
            throw new IOException("There is no location of new application.");
        }
    }

    @Override
    public JSONObject requestBridgeById(String id) throws IOException {
        RestResponse response = request(getBridgePath(id), HttpMethod.GET);
        if (response.isError()) throw new IOException(response.getResponseText());

        if (response.isJson()) {
            try {
                return (JSONObject) new JSONParser().parse(response.getResponseText());
            } catch (org.json.simple.parser.ParseException e) {
                throw new IOException(e);
            }
        } else {
            throw new IOException("Response is not a JSON format.");
        }
    }

    @Override
    public void updateBridge(String id, Map<String, Object> params) throws IOException {
        RestResponse response = request(getBridgePath(id), HttpMethod.POST, params);
        if (response.isError()) throw new IOException(response.getResponseText());
    }

    @Override
    public void createBridgeAudio(String id, Map<String, Object> params) throws IOException {
        RestResponse response = request(getBridgeAudioPath(id), HttpMethod.POST, params);
        if (response.isError()) throw new IOException(response.getResponseText());
    }

    @Override
    public JSONArray requestCalls(Map<String, Object> params) throws IOException {
        RestResponse response = request(getCallsPath(), HttpMethod.GET, params);
        if (response.isError()) throw new IOException(response.getResponseText());

        if (response.isJson()) {
            try {
                return (JSONArray) new JSONParser().parse(response.getResponseText());
            } catch (org.json.simple.parser.ParseException e) {
                throw new IOException(e);
            }
        } else {
            throw new IOException("Response is not a JSON format.");
        }
    }

    @Override
    public JSONArray requestBridgeCalls(String id) throws IOException {
        RestResponse response = request(getBridgeCallsPath(id), HttpMethod.GET);
        if (response.isError()) throw new IOException(response.getResponseText());

        if (response.isJson()) {
            try {
                return (JSONArray) new JSONParser().parse(response.getResponseText());
            } catch (org.json.simple.parser.ParseException e) {
                throw new IOException(e);
            }
        } else {
            throw new IOException("Response is not a JSON format.");
        }
    }

    @Override
    public JSONObject createCall(Map<String, Object> params) throws IOException {
        RestResponse response = request(getCallsPath(), HttpMethod.POST, params);
        if (response.isError()) throw new IOException(response.getResponseText());

        String location = response.getLocation();
        if (location != null) {
            response = request(location, HttpMethod.GET);
            if (response.isError()) throw new IOException(response.getResponseText());

            if (response.isJson()) {
                try {
                    return (JSONObject) new JSONParser().parse(response.getResponseText());
                } catch (org.json.simple.parser.ParseException e) {
                    throw new IOException(e);
                }
            } else {
                throw new IOException("Response is not a JSON format.");
            }
        } else {
            throw new IOException("There is no location of new application.");
        }
    }

    @Override
    public JSONObject requestCallById(String callId) throws IOException {
        RestResponse response = request(getCallPath(callId), HttpMethod.GET);
        if (response.isError()) throw new IOException(response.getResponseText());

        if (response.isJson()) {
            try {
                return (JSONObject) new JSONParser().parse(response.getResponseText());
            } catch (org.json.simple.parser.ParseException e) {
                throw new IOException(e);
            }
        } else {
            throw new IOException("Response is not a JSON format.");
        }
    }

    @Override
    public void updateCall(String id, Map<String, Object> params) throws IOException {
        RestResponse response = request(getCallPath(id), HttpMethod.POST, params);
        if (response.isError()) throw new IOException(response.getResponseText());
    }

    @Override
    public void createCallAudio(String id, Map<String, Object> params) throws IOException {
        RestResponse response = request(getCallAudioPath(id), HttpMethod.POST, params);
        if (response.isError()) throw new IOException(response.getResponseText());
    }

    @Override
    public void sendCallDtmf(String id, Map<String, Object> params) throws IOException {
        RestResponse response = request(getCallDtmfPath(id), HttpMethod.POST, params);
        if (response.isError()) throw new IOException(response.getResponseText());
    }

    @Override
    public JSONArray requestCallEvents(String id) throws IOException {
        RestResponse response = request(getCallEventsPath(id), HttpMethod.GET);
        if (response.isError()) throw new IOException(response.getResponseText());

        if (response.isJson()) {
            try {
                return (JSONArray) new JSONParser().parse(response.getResponseText());
            } catch (org.json.simple.parser.ParseException e) {
                throw new IOException(e);
            }
        } else {
            throw new IOException("Response is not a JSON format.");
        }
    }

    @Override
    public JSONObject requestCallEventById(String callId, String eventId) throws IOException {
        RestResponse response = request(getCallEventPath(callId, eventId), HttpMethod.GET);
        if (response.isError()) throw new IOException(response.getResponseText());

        if (response.isJson()) {
            try {
                return (JSONObject) new JSONParser().parse(response.getResponseText());
            } catch (org.json.simple.parser.ParseException e) {
                throw new IOException(e);
            }
        } else {
            throw new IOException("Response is not a JSON format.");
        }
    }

    @Override
    public JSONArray requestCallRecordings(String id) throws IOException {
        RestResponse response = request(getCallRecordingsPath(id), HttpMethod.GET);
        if (response.isError()) throw new IOException(response.getResponseText());

        if (response.isJson()) {
            try {
                return (JSONArray) new JSONParser().parse(response.getResponseText());
            } catch (org.json.simple.parser.ParseException e) {
                throw new IOException(e);
            }
        } else {
            throw new IOException("Response is not a JSON format.");
        }
    }

    @Override
    public void createCallGather(String id, Map<String, Object> params) throws IOException {
        RestResponse response = request(getCallGatherPath(id), HttpMethod.POST, params);
        if (response.isError()) throw new IOException(response.getResponseText());
    }

    @Override
    public JSONObject requestCallGatherById(String callId, String gatherId) throws IOException {
        RestResponse response = request(getCallGatherPath(callId, gatherId), HttpMethod.GET);
        if (response.isError()) throw new IOException(response.getResponseText());

        if (response.isJson()) {
            try {
                return (JSONObject) new JSONParser().parse(response.getResponseText());
            } catch (org.json.simple.parser.ParseException e) {
                throw new IOException(e);
            }
        } else {
            throw new IOException("Response is not a JSON format.");
        }
    }

    @Override
    public void updateCallGather(String callId, String gatherId, Map<String, Object> params) throws IOException {
        RestResponse response = request(getCallGatherPath(callId, gatherId), HttpMethod.POST, params);
        if (response.isError()) throw new IOException(response.getResponseText());
    }

    @Override
    public JSONObject createConference(Map<String, Object> params) throws IOException {
        RestResponse response = request(getConferencesPath(), HttpMethod.POST, params);
        if (response.isError()) throw new IOException(response.getResponseText());

        String location = response.getLocation();
        if (location != null) {
            response = request(location, HttpMethod.GET);
            if (response.isError()) throw new IOException(response.getResponseText());

            if (response.isJson()) {
                try {
                    return (JSONObject) new JSONParser().parse(response.getResponseText());
                } catch (org.json.simple.parser.ParseException e) {
                    throw new IOException(e);
                }
            } else {
                throw new IOException("Response is not a JSON format.");
            }
        } else {
            throw new IOException("There is no location of new application.");
        }
    }

    @Override
    public JSONObject requestConferenceById(String id) throws IOException {
        RestResponse response = request(getConferencePath(id), HttpMethod.GET);
        if (response.isError()) throw new IOException(response.getResponseText());

        if (response.isJson()) {
            try {
                return (JSONObject) new JSONParser().parse(response.getResponseText());
            } catch (org.json.simple.parser.ParseException e) {
                throw new IOException(e);
            }
        } else {
            throw new IOException("Response is not a JSON format.");
        }
    }

    @Override
    public void updateConference(String id, Map<String, Object> params) throws IOException {
        RestResponse response = request(getConferencePath(id), HttpMethod.POST, params);
        if (response.isError()) throw new IOException(response.getResponseText());
    }

    private String getConferencePath(String id) {
        String[] parts = new String[]{
                BandwidthConstants.API_ENDPOINT,
                BandwidthConstants.API_VERSION,
                String.format(BandwidthConstants.CONFERENCES_PATH, userId),
                id
        };
        return StringUtils.join(parts, '/');
    }

    private String getConferencesPath() {
        String[] parts = new String[]{
                BandwidthConstants.API_ENDPOINT,
                BandwidthConstants.API_VERSION,
                String.format(BandwidthConstants.CONFERENCES_PATH, userId),
        };
        return StringUtils.join(parts, '/');
    }

    private String getCallGatherPath(String callId, String gatherId) {
        String[] parts = new String[]{
                BandwidthConstants.API_ENDPOINT,
                BandwidthConstants.API_VERSION,
                String.format(BandwidthConstants.CALL_GATHER_PATH, userId, callId),
                gatherId
        };
        return StringUtils.join(parts, '/');
    }

    private String getCallGatherPath(String id) {
        String[] parts = new String[]{
                BandwidthConstants.API_ENDPOINT,
                BandwidthConstants.API_VERSION,
                String.format(BandwidthConstants.CALL_GATHER_PATH, userId, id),
        };
        return StringUtils.join(parts, '/');
    }

    private String getCallRecordingsPath(String id) {
        String[] parts = new String[]{
                BandwidthConstants.API_ENDPOINT,
                BandwidthConstants.API_VERSION,
                String.format(BandwidthConstants.CALL_RECORDINGS_PATH, userId, id),
        };
        return StringUtils.join(parts, '/');
    }

    private String getCallEventPath(String callId, String eventId) {
        String[] parts = new String[]{
                BandwidthConstants.API_ENDPOINT,
                BandwidthConstants.API_VERSION,
                String.format(BandwidthConstants.CALL_EVENTS_PATH, userId, callId),
                eventId
        };
        return StringUtils.join(parts, '/');
    }

    private String getCallEventsPath(String id) {
        String[] parts = new String[]{
                BandwidthConstants.API_ENDPOINT,
                BandwidthConstants.API_VERSION,
                String.format(BandwidthConstants.CALL_EVENTS_PATH, userId, id),
        };
        return StringUtils.join(parts, '/');
    }

    private String getCallDtmfPath(String id) {
        String[] parts = new String[]{
                BandwidthConstants.API_ENDPOINT,
                BandwidthConstants.API_VERSION,
                String.format(BandwidthConstants.CALL_DTMF_PATH, userId, id),
        };
        return StringUtils.join(parts, '/');
    }

    private String getCallAudioPath(String id) {
        String[] parts = new String[]{
                BandwidthConstants.API_ENDPOINT,
                BandwidthConstants.API_VERSION,
                String.format(BandwidthConstants.CALL_AUDIO_PATH, userId, id),
        };
        return StringUtils.join(parts, '/');
    }

    private String getCallPath(String id) {
        String[] parts = new String[]{
                BandwidthConstants.API_ENDPOINT,
                BandwidthConstants.API_VERSION,
                String.format(BandwidthConstants.CALLS_PATH, userId),
                id
        };
        return StringUtils.join(parts, '/');
    }

    private String getCallsPath() {
        String[] parts = new String[]{
                BandwidthConstants.API_ENDPOINT,
                BandwidthConstants.API_VERSION,
                String.format(BandwidthConstants.CALLS_PATH, userId),
        };
        return StringUtils.join(parts, '/');
    }

    private String getBridgeCallsPath(String id) {
        String[] parts = new String[]{
                BandwidthConstants.API_ENDPOINT,
                BandwidthConstants.API_VERSION,
                String.format(BandwidthConstants.BRIDGE_CALLS_PATH, userId, id),
        };
        return StringUtils.join(parts, '/');
    }

    private String getBridgeAudioPath(String id) {
        String[] parts = new String[]{
                BandwidthConstants.API_ENDPOINT,
                BandwidthConstants.API_VERSION,
                String.format(BandwidthConstants.BRIDGE_AUDIO_PATH, userId, id),
        };
        return StringUtils.join(parts, '/');
    }

    private String getBridgePath(String id) {
        String[] parts = new String[]{
                BandwidthConstants.API_ENDPOINT,
                BandwidthConstants.API_VERSION,
                String.format(BandwidthConstants.BRIDGES_PATH, userId),
                id
        };
        return StringUtils.join(parts, '/');
    }

    private String getBridgesPath() {
        String[] parts = new String[]{
                BandwidthConstants.API_ENDPOINT,
                BandwidthConstants.API_VERSION,
                String.format(BandwidthConstants.BRIDGES_PATH, userId),
        };
        return StringUtils.join(parts, '/');
    }

    private String getTollFreeAvailableNumbersPath() {
        String[] parts = new String[]{
                BandwidthConstants.API_ENDPOINT,
                BandwidthConstants.API_VERSION,
                BandwidthConstants.TOLL_FREE_AVAILABLE_NUMBERS_PATH,
        };
        return StringUtils.join(parts, '/');
    }

    private String getLocalAvailableNumbersPath() {
        String[] parts = new String[]{
                BandwidthConstants.API_ENDPOINT,
                BandwidthConstants.API_VERSION,
                BandwidthConstants.LOCAL_AVAILABLE_NUMBERS_PATH,
        };
        return StringUtils.join(parts, '/');
    }

    private String getApplicationPath(String id) {
        String[] parts = new String[]{
                BandwidthConstants.API_ENDPOINT,
                BandwidthConstants.API_VERSION,
                String.format(BandwidthConstants.APPLICATIONS_PATH, userId),
                id
        };
        return StringUtils.join(parts, '/');
    }

    private String getApplicationsPath() {
        String[] parts = new String[]{
                BandwidthConstants.API_ENDPOINT,
                BandwidthConstants.API_VERSION,
                String.format(BandwidthConstants.APPLICATIONS_PATH, userId)
        };
        return StringUtils.join(parts, '/');
    }

    private String getAccountTransactionPath() {
        String[] parts = new String[]{
                BandwidthConstants.API_ENDPOINT,
                BandwidthConstants.API_VERSION,
                String.format(BandwidthConstants.ACCOUNT_TRANSACTIONS_PATH, userId)
        };
        return StringUtils.join(parts, '/');
    }

    private String getAccountPath() {
        String[] parts = new String[]{
                BandwidthConstants.API_ENDPOINT,
                BandwidthConstants.API_VERSION,
                String.format(BandwidthConstants.ACCOUNT_PATH, userId)
        };
        return StringUtils.join(parts, '/');
    }

    private RestResponse request(final String path, HttpMethod method) throws IOException {
        return request(path, method, Collections.<String, Object>emptyMap());
    }

    private RestResponse request(final String path, HttpMethod method,
                                 final Map<String, Object> paramList) throws IOException {

        HttpUriRequest request = setupRequest(path, method, paramList);

        HttpResponse response;
        try {
            response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();

            String responseBody = "";

            if (entity != null) {
                responseBody = EntityUtils.toString(entity);
            }

            StatusLine status = response.getStatusLine();
            int statusCode = status.getStatusCode();

            RestResponse restResponse = new RestResponse(request.getURI().toString(), responseBody, statusCode);

            Header[] headers = response.getHeaders("Content-Type");
            if (headers.length > 0) {
                restResponse.setContentType(headers[0].getValue());
            }

            headers = response.getHeaders("Location");
            if (headers.length > 0) {
                restResponse.setLocation(headers[0].getValue());
            }

            return restResponse;

        } catch (final ClientProtocolException e1) {
            throw new RuntimeException(e1);
        } catch (final IOException e1) {
            throw new RuntimeException(e1);
        }
    }

    public HttpUriRequest setupRequest(String path, HttpMethod method, final Map<String, Object> params) {
        HttpUriRequest request = buildMethod(method, path, params);

        request.addHeader(new BasicHeader("Accept", "application/json"));
        request.addHeader(new BasicHeader("Accept-Charset", "utf-8"));
        String s = token + ":" + secret;
        String auth = new String(Base64.encodeBase64(s.getBytes()));
        request.setHeader(new BasicHeader("Authorization", "Basic " + auth));

        return request;
    }

    private HttpUriRequest buildMethod(HttpMethod method, final String path, final Map<String, Object> params) {
        switch (method) {
            case GET:
                return generateGetRequest(path, params);
            case POST:
                return generatePostRequest(path, params);
            case PUT:
                return generatePutRequest(path, params);
            case DELETE:
                return generateDeleteRequest(path);
            default:
                throw new RuntimeException("Must not be here.");
        }
    }

    private HttpGet generateGetRequest(final String path, final Map<String, Object> paramMap) {
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        for (String key : paramMap.keySet()) {
            pairs.add(new BasicNameValuePair(key, paramMap.get(key).toString()));
        }
        URI uri = buildUri(path, pairs);
        return new HttpGet(uri);
    }

    private HttpPost generatePostRequest(final String path, final Map<String, Object> paramMap) {
        URI uri = buildUri(path);

        String s = JSONObject.toJSONString(paramMap);

        HttpPost post = new HttpPost(uri);
        post.setEntity(new StringEntity(s, ContentType.APPLICATION_JSON));

        return post;
    }

    private HttpPut generatePutRequest(final String path, final Map<String, Object> paramMap) {
        URI uri = buildUri(path);

        String s = JSONObject.toJSONString(paramMap);

        HttpPut put = new HttpPut(uri);
        put.setEntity(new StringEntity(s, ContentType.APPLICATION_JSON));

        return put;
    }

    private HttpDelete generateDeleteRequest(final String path) {
        URI uri = buildUri(path);
        return new HttpDelete(uri);
    }

    private URI buildUri(final String path) {
        return buildUri(path, null);
    }

    private URI buildUri(final String path, final List<NameValuePair> queryStringParams) {
        StringBuilder sb = new StringBuilder();
        sb.append(path);

        if (queryStringParams != null && queryStringParams.size() > 0) {
            sb.append("?");
            sb.append(URLEncodedUtils.format(queryStringParams, "UTF-8"));
        }

        URI uri;
        try {
            uri = new URI(sb.toString());
        } catch (final URISyntaxException e) {
            throw new IllegalStateException("Invalid uri", e);
        }

        return uri;
    }
}
