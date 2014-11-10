package com.bandwidth.sdk;

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
 * Created by sbarstow on 10/3/14.
 */
public class BandwidthClient implements Client{

    protected static final String GET = "get";
    protected static final String POST = "post";
    protected static final String PUT = "put";
    protected static final String DELETE = "delete";


    protected String token;
    protected String secret;
    protected String apiVersion;
    protected String apiEndpoint;
    protected String usersUri;

    protected HttpClient httpClient;

    public static String BANDWIDTH_USER_ID = "BANDWIDTH_USER_ID";
    public static String BANDWIDTH_API_TOKEN = "BANDWIDTH_API_TOKEN";
    public static String BANDWIDTH_API_SECRET = "BANDWIDTH_API_SECRET";
    public static String BANDWIDTH_API_ENDPOINT = "BANDWIDTH_API_ENDPOINT";
    public static String BANDWIDTH_API_VERSION = "BANDWIDTH_API_VERSION";

    public static String BANDWIDTH_SYSPROP_USER_ID = "com.bandwidth.userId";
    public static String BANDWIDTH_SYSPROP_API_TOKEN = "com.bandwidth.apiToken";
    public static String BANDWIDTH_SYSPROP_API_SECRET = "com.bandwidth.apiSecret";
    public static String BANDWIDTH_SYSPROP_API_ENDPOINT = "com.bandwidth.apiEndpoint";
    public static String BANDWIDTH_SYSPROP_API_VERSION = "com.bandwidth.apiSecret";


    protected static BandwidthClient INSTANCE;


    public static BandwidthClient getInstance() {
        if (INSTANCE == null) {
            Map<String, String> env = System.getenv();

            String userId = env.get(BANDWIDTH_USER_ID);
            String apiToken = env.get(BANDWIDTH_API_TOKEN);
            String apiSecret = env.get(BANDWIDTH_API_SECRET);
            String apiEndpoint = env.get(BANDWIDTH_API_ENDPOINT);
            String apiVersion = env.get(BANDWIDTH_API_VERSION);

            if (userId == null || apiToken == null || apiSecret == null) {
                userId = System.getProperty(BANDWIDTH_SYSPROP_USER_ID);
                apiToken = System.getProperty(BANDWIDTH_SYSPROP_API_TOKEN);
                apiSecret = System.getProperty(BANDWIDTH_SYSPROP_API_SECRET);
                apiEndpoint = System.getProperty(BANDWIDTH_SYSPROP_API_ENDPOINT);
                apiVersion = System.getProperty(BANDWIDTH_SYSPROP_API_VERSION);
            }

            INSTANCE = new BandwidthClient(userId, apiToken, apiSecret, apiEndpoint, apiVersion);
        }
        return INSTANCE;
    }
    

    protected BandwidthClient(String userId, String apiToken, String apiSecret, String apiEndpoint, String apiVersion){
        usersUri = String.format(BandwidthConstants.USERS_URI_PATH, userId);
        this.token = apiToken;
        this.secret = apiSecret;

        this.apiEndpoint = apiEndpoint;
        this.apiVersion = apiVersion;

        if (apiEndpoint == null || apiVersion == null)
        {
            this.apiEndpoint = BandwidthConstants.API_ENDPOINT;
            this.apiVersion = BandwidthConstants.API_VERSION;
        }

        httpClient = new DefaultHttpClient();

    }

    public void setCredentials(String userId, String apiToken, String apiSecret) {
        usersUri = String.format(BandwidthConstants.USERS_URI_PATH, userId);
        this.token = apiToken;
        this.secret = apiSecret;
    }

    public void setEndpointandVersion(String apiEndpoint, String apiVersion) {
        this.apiEndpoint = apiEndpoint;
        this.apiVersion = apiVersion;
    }


    public String getUserResourceUri(String path){
        if(StringUtils.isEmpty(path))
            throw new IllegalArgumentException("Path cannot be null");
        return StringUtils.join(new String[] {getUserUri(), path}, "/");
    }

    public String getUserResourceInstanceUri(String path, String instanceId){
        if(StringUtils.isEmpty(path) || StringUtils.isEmpty(instanceId))
            throw new IllegalArgumentException("Path and Instance Id cannot be null");
        return getUserResourceUri(path) + "/" + instanceId;
    }

    public String getBaseResourceUri(String path){
        if(StringUtils.isEmpty(path))
            throw new IllegalArgumentException("Path cannot be null");
        return path + "/";
    }
    
    public String getPath(String uri) {
        String[] parts = new String[]{
                apiEndpoint,
                apiVersion,
                null
        };
        
        String base = StringUtils.join(parts, "/");

        if (!uri.contains(base)) {
            parts[2] = uri;
            return StringUtils.join(parts, "/");
        } else {
            return uri;
        }
    }
    
    
    /**
     * Returns API url with userid 
     * 
     * @return usersUri
     */
    public String getUserUri() {
    	return usersUri;
    }
        

    public RestResponse post(String uri, Map<String, Object> params) throws IOException {
        return request(getPath(uri), POST, params);
    }

    public RestResponse get(String uri, Map<String, Object> params) throws Exception {
        String path = getPath(uri);
        
        RestResponse response = request(path, GET, params);
        if (response.isError()) throw new IOException(response.getResponseText());

        return response;

    }

    public RestResponse put(String uri, Map<String, Object> params) throws IOException {
        return request(getPath(uri), PUT, params);
    }

    public RestResponse delete(String uri) throws IOException {
        return request(getPath(uri), DELETE);
    }


    protected RestResponse request(final String path, String method) throws IOException {
        return request(path, method, null);
    }

    protected RestResponse request(final String path, String method,
                                   Map<String, Object> paramList) throws IOException {
        if (paramList == null) 
        	paramList = Collections.emptyMap();

        HttpUriRequest request = setupRequest(path, method, paramList);
        return performRequest(request);
    }

    protected RestResponse performRequest(HttpUriRequest request) throws IOException {
        HttpResponse response;
        try {
            response = httpClient.execute(request);
            RestResponse restResponse = RestResponse.createRestResponse(response); 
            
            return restResponse;

        } catch (final ClientProtocolException e1) {
            throw new IOException(e1);
        } catch (final IOException e1) {
            throw new IOException(e1);
        }
    }

    protected HttpUriRequest setupRequest(String path, String method, final Map<String, Object> params) {
        HttpUriRequest request = buildMethod(method, path, params);

        request.addHeader(new BasicHeader("Accept", "application/json"));
        request.addHeader(new BasicHeader("Accept-Charset", "utf-8"));
        String s = token + ":" + secret;
        String auth = new String(Base64.encodeBase64(s.getBytes()));
        request.setHeader(new BasicHeader("Authorization", "Basic " + auth));

        return request;
    }

    protected HttpUriRequest buildMethod(String method, final String path, final Map<String, Object> params) {
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

    protected HttpGet generateGetRequest(final String path, final Map<String, Object> paramMap) {
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        for (String key : paramMap.keySet()) {
            pairs.add(new BasicNameValuePair(key, paramMap.get(key).toString()));
        }
        URI uri = buildUri(path, pairs);
        return new HttpGet(uri);
    }

    protected HttpPost generatePostRequest(final String path, final Map<String, Object> paramMap) {
        URI uri = buildUri(path);

        String s = JSONObject.toJSONString(paramMap);

        HttpPost post = new HttpPost(uri);
        post.setEntity(new StringEntity(s, ContentType.APPLICATION_JSON));

        return post;
    }

    protected HttpPut generatePutRequest(final String path, final Map<String, Object> paramMap) {
        URI uri = buildUri(path);

        HttpPut put = new HttpPut(uri);
        if (paramMap != null) {
            String s = JSONObject.toJSONString(paramMap);
            put.setEntity(new StringEntity(s, ContentType.APPLICATION_JSON));
        }

        return put;
    }
    
    public void upload(String uri, File sourceFile, String contentType) throws IOException {
        String path = getPath(uri);

        HttpPut request = (HttpPut) setupRequest(path, PUT, null);
        request.setEntity(contentType == null ? new FileEntity(sourceFile) : new FileEntity(sourceFile, ContentType.parse(contentType)));

        performRequest(request);
    }
    
    public void download(String uri, File destFile) throws IOException {
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
    
    

    protected HttpDelete generateDeleteRequest(final String path) {
        URI uri = buildUri(path);
        return new HttpDelete(uri);
    }

    protected URI buildUri(final String path) {
        return buildUri(path, null);
    }

    protected URI buildUri(final String path, final List<NameValuePair> queryStringParams) {
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
