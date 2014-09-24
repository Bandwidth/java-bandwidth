package com.bandwidth.sdk;

import com.bandwidth.sdk.model.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Main point of using Bandwidth API.
 *
 * @author vpotapenko
 */
public class BandwidthRestClient {

    private static final String GET = "get";
    private static final String POST = "post";
    private static final String PUT = "put";
    private static final String DELETE = "delete";

    public static String BANDWIDTH_APPPLATFORM_USER_ID = "BANDWIDTH_APPPLATFORM_USER_ID";
    public static String BANDWIDTH_APPPLATFORM_API_TOKEN = "BANDWIDTH_APPPLATFORM_API_TOKEN";
    public static String BANDWIDTH_APPPLATFORM_API_SECRET = "BANDWIDTH_APPPLATFORM_API_SECRET";
    
    private final String usersUri;

    private final String token;
    private final String secret;

    private HttpClient httpClient;

    private Account account;
    private Applications applications;
    private AvailableNumbers availableNumbers;
    private Bridges bridges;
    private Calls calls;
    private Conferences conferences;
    private Errors errors;
    private Messages messages;
    private PhoneNumbers phoneNumbers;
    private Recordings recordings;
    private Media media;
    
    private static BandwidthRestClient INSTANCE;    
    
    public static BandwidthRestClient getInstance() {
	if (INSTANCE == null) {
	    Map<String, String> env = System.getenv();
	    for (String envName : env.keySet()) {
		System.out.format("%s=%s%n", envName, env.get(envName));

	    }

	    // TODO set these up as heroku configuration variables
	    String userId = env.get(BANDWIDTH_APPPLATFORM_USER_ID);
	    String apiToken = env.get(BANDWIDTH_APPPLATFORM_API_TOKEN);
	    String apiSecret = env.get(BANDWIDTH_APPPLATFORM_API_SECRET);

	    System.out.println("userId:" + userId);
	    System.out.println("apiToken:" + apiToken);
	    System.out.println("apiSecret:" + apiSecret);

	    INSTANCE = new BandwidthRestClient(userId, apiToken, apiSecret);
	}

	return INSTANCE;
    }
    

    public BandwidthRestClient(String userId, String token, String secret) {
        usersUri = String.format(BandwidthConstants.USERS_URI_PATH, userId);

        this.token = token;
        this.secret = secret;

        httpClient = new DefaultHttpClient();
    }

    /**
     * Gets point for <code>/v1/users/{userId}/account</code>
     *
     * @return point for account
     */
    public Account getAccount() {
        if (account == null) {
            account = new Account(this, usersUri);
        }
        return account;
    }

    /**
     * Gets point for <code>/v1/users/{userId}/applications</code>
     *
     * @return point for applications
     */
    public Applications getApplications() {
        if (applications == null) {
            applications = new Applications(this, usersUri);
        }
        return applications;
    }

    /**
     * Gets point for <code>/v1/availableNumbers</code>
     *
     * @return point for available numbers
     */
    public AvailableNumbers getAvailableNumbers() {
        if (availableNumbers == null) {
            availableNumbers = new AvailableNumbers(this);
        }
        return availableNumbers;
    }

    /**
     * Gets point for <code>/v1/users/{userId}/bridges</code>
     *
     * @return point for bridges
     */
    public Bridges getBridges() {
        if (bridges == null) {
            bridges = new Bridges(this, usersUri);
        }
        return bridges;
    }

    /**
     * Gets point for <code>/v1/users/{userId}/calls</code>
     *
     * @return point for calls
     */
    public Calls getCalls() {
        if (calls == null) {
            calls = new Calls(this, usersUri);
        }
        return calls;
    }

    /**
     * Gets point for <code>/v1/users/{userId}/conferences</code>
     *
     * @return point for conferences
     */
    public Conferences getConferences() {
        if (conferences == null) {
            conferences = new Conferences(this, usersUri);
        }
        return conferences;
    }

    /**
     * Gets point for <code>/v1/users/{userId}/errors</code>
     *
     * @return point for errors
     */
    public Errors getErrors() {
        if (errors == null) {
            errors = new Errors(this, usersUri);
        }
        return errors;
    }

    /**
     * Gets point for <code>/v1/users/{userId}/messages</code>
     *
     * @return point for messages
     */
    public Messages getMessages() {
        if (messages == null) {
            messages = new Messages(this, usersUri);
        }
        return messages;
    }

    /**
     * Gets point for <code>/v1/users/{userId}/phoneNumbers</code>
     *
     * @return point for phone numbers
     */
    public PhoneNumbers getPhoneNumbers() {
        if (phoneNumbers == null) {
            phoneNumbers = new PhoneNumbers(this, usersUri);
        }
        return phoneNumbers;
    }

    /**
     * Gets point for <code>/v1/users/{userId}/recordings</code>
     *
     * @return point for recordings
     */
    public Recordings getRecordings() {
        if (recordings == null) {
            recordings = new Recordings(this, usersUri);
        }
        return recordings;
    }

    /**
     * Gets point for <code>/v1/users/{userId}/media</code>
     *
     * @return point for media
     */
    public Media getMedia() {
        if (media == null) {
            media = new Media(this, usersUri);
        }
        return media;
    }

    /**
     * Returns API url with userid 
     * 
     * @return usersUri
     */
    public String getUserUri() {
    	return usersUri;
    }


    /**
     * Returns information about this number.
     *
     * @param number searching number
     * @return information about the number
     * @throws IOException
     */
    public NumberInfo getNumberInfoByNumber(String number) throws IOException {
        String uri = StringUtils.join(new String[]{
                "phoneNumbers",
                "numberInfo",
                number
        }, '/');
        JSONObject object = getObject(uri);
        return new NumberInfo(this, uri, object);
    }


    public JSONArray getArray(String uri, Map<String, Object> params) throws IOException {
        String path = getPath(uri);
        RestResponse response = request(path, GET, params);
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

    public JSONObject getObject(String uri) throws IOException {
        String path = getPath(uri);
        RestResponse response = request(path, GET);
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

    public JSONObject create(String uri, Map<String, Object> params) throws IOException {
        String path = getPath(uri);
        RestResponse response = request(path, POST, params);
        if (response.isError()) throw new IOException(response.getResponseText());

        String location = response.getLocation();
        if (location != null) {
            response = request(location, GET);
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

    public RestResponse post(String uri, Map<String, Object> params) throws IOException {
        String path = getPath(uri);
        RestResponse response = request(path, POST, params);
        if (response.isError()) throw new IOException(response.getResponseText());
        
        return response;
    }

    public void delete(String uri) throws IOException {
        String path = getPath(uri);
        RestResponse response = request(path, DELETE);
        if (response.isError()) throw new IOException(response.getResponseText());
    }

    public void uploadFile(String uri, File sourceFile, String contentType) throws IOException {
        String path = getPath(uri);

        HttpPut request = (HttpPut) setupRequest(path, PUT, null);
        request.setEntity(contentType == null ? new FileEntity(sourceFile) : new FileEntity(sourceFile, ContentType.parse(contentType)));

        performRequest(request);
    }

    public void downloadFileTo(String uri, File destFile) throws IOException {
        String path = getPath(uri);

        HttpGet request = (HttpGet) setupRequest(path, GET, Collections.<String, Object>emptyMap());
        HttpResponse response;

        OutputStream outputStream = null;
        try {
            response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();

            StatusLine status = response.getStatusLine();
            int statusCode = status.getStatusCode();
            if (statusCode >= 400) throw new IOException(EntityUtils.toString(entity));

            outputStream = new BufferedOutputStream(new FileOutputStream(destFile));
            entity.writeTo(outputStream);
        } catch (final ClientProtocolException e1) {
            throw new IOException(e1);
        } catch (final IOException e1) {
            throw new IOException(e1);
        } finally {
            try {
                if (outputStream != null) outputStream.close();
            } catch (IOException ignore) {
            }
        }
    }

    public String getPath(String uri) {
        String[] parts = new String[]{
                BandwidthConstants.API_ENDPOINT,
                BandwidthConstants.API_VERSION,
                uri,
        };
        return StringUtils.join(parts, '/');
    }


    private RestResponse request(final String path, String method) throws IOException {
        return request(path, method, null);
    }

    private RestResponse request(final String path, String method,
                                 Map<String, Object> paramList) throws IOException {
        if (paramList == null) paramList = Collections.emptyMap();

        HttpUriRequest request = setupRequest(path, method, paramList);
        return performRequest(request);
    }

    private RestResponse performRequest(HttpUriRequest request) throws IOException {
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

            RestResponse restResponse = new RestResponse(responseBody, statusCode);

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
            throw new IOException(e1);
        } catch (final IOException e1) {
            throw new IOException(e1);
        }
    }

    private HttpUriRequest setupRequest(String path, String method, final Map<String, Object> params) {
        HttpUriRequest request = buildMethod(method, path, params);

        request.addHeader(new BasicHeader("Accept", "application/json"));
        request.addHeader(new BasicHeader("Accept-Charset", "utf-8"));
        String s = token + ":" + secret;
        String auth = new String(Base64.encodeBase64(s.getBytes()));
        request.setHeader(new BasicHeader("Authorization", "Basic " + auth));

        return request;
    }

    private HttpUriRequest buildMethod(String method, final String path, final Map<String, Object> params) {
        if (StringUtils.equals(method, GET)) {
            return generateGetRequest(path, params);
        } else if (StringUtils.equals(method, POST)) {
            return generatePostRequest(path, params);
        } else if (StringUtils.equals(method, PUT)) {
            return generatePutRequest(path, params);
        } else if (StringUtils.equals(method, DELETE)) {
            return generateDeleteRequest(path);
        } else {
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

        HttpPut put = new HttpPut(uri);
        if (paramMap != null) {
            String s = JSONObject.toJSONString(paramMap);
            put.setEntity(new StringEntity(s, ContentType.APPLICATION_JSON));
        }

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
