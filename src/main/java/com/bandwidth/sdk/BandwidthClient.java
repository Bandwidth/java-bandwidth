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
import org.json.simple.JSONObject;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Helper class to abstract the HTTP interface. This class wraps the HttpClient and the HTTP methods POST, GET, PUT
 * and DELETE.
 *
 * This class also holds the credentials to the back end and includes several convenience methods for building the
 * appropriate resource URIs.
 *
 * This class is used as a singleton by the different resource classes in com.bandwidth.sdk.models.
 *
 *
 * Created by sbarstow on 10/3/14.
 */
public class BandwidthClient implements Client{

    protected String token;
    protected String secret;
    protected String apiVersion;
    protected String apiEndpoint;
    protected String usersUri;

    protected HttpClient httpClient;

    protected static BandwidthClient INSTANCE;


    /**
     * getInstance() method returns a singleton instance of the BandwidthClient. Looks for user-id, api-token and
     * api-secret as environment variables or system properties. These can also be set using the setCredentials method.
     *
     * @return
     */
    public synchronized static BandwidthClient getInstance() {
        if (INSTANCE == null) {
            Map<String, String> env = System.getenv();

            String userId = env.get(BandwidthConstants.BANDWIDTH_USER_ID);
            String apiToken = env.get(BandwidthConstants.BANDWIDTH_API_TOKEN);
            String apiSecret = env.get(BandwidthConstants.BANDWIDTH_API_SECRET);
            String apiEndpoint = env.get(BandwidthConstants.BANDWIDTH_API_ENDPOINT);
            String apiVersion = env.get(BandwidthConstants.BANDWIDTH_API_VERSION);

            if (userId == null || apiToken == null || apiSecret == null) {
                userId = System.getProperty(BandwidthConstants.BANDWIDTH_SYSPROP_USER_ID);
                apiToken = System.getProperty(BandwidthConstants.BANDWIDTH_SYSPROP_API_TOKEN);
                apiSecret = System.getProperty(BandwidthConstants.BANDWIDTH_SYSPROP_API_SECRET);
                apiEndpoint = System.getProperty(BandwidthConstants.BANDWIDTH_SYSPROP_API_ENDPOINT);
                apiVersion = System.getProperty(BandwidthConstants.BANDWIDTH_SYSPROP_API_VERSION);
            }

            INSTANCE = new BandwidthClient(userId, apiToken, apiSecret, apiEndpoint, apiVersion);
        }
        return INSTANCE;
    }


    /**
     * protected constuctor. Instances are created through getInstance() method.
     * @param userId
     * @param apiToken
     * @param apiSecret
     * @param apiEndpoint
     * @param apiVersion
     */
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

    /**
     * The setCredentials() provides a convenience method to pass the userId, api-token and api-secret after
     * a client has been instantiated.
     *
     * @param userId
     * @param apiToken
     * @param apiSecret
     */
    public void setCredentials(String userId, String apiToken, String apiSecret) {
        usersUri = String.format(BandwidthConstants.USERS_URI_PATH, userId);
        this.token = apiToken;
        this.secret = apiSecret;
    }

    /**
     * The setEndpointandVersion() method provides a convenience method to pass the apiEndpoint and apiVersion
     * after a client has been instantiated.
     *
     * @param apiEndpoint
     * @param apiVersion
     */
    public void setEndpointandVersion(String apiEndpoint, String apiVersion) {
        this.apiEndpoint = apiEndpoint;
        this.apiVersion = apiVersion;
    }


    /**
     * Convenience method to return the resource Url with the users credentials, e.g.
     *
     * String userResourceUri = client.getUserResourceUri(BandwidthConstants.BRIDGES_URI_PATH);
     *
     * returns users/{user-id}/bridges

     * @param path
     * @return
     */
    public String getUserResourceUri(String path){
        if(StringUtils.isEmpty(path))
            throw new IllegalArgumentException("Path cannot be null");
        return StringUtils.join(new String[] {getUserUri(), path}, "/");
    }

    /**
     * Convenience method that returns the resource instance uri. E.g.
     *
     * String bridgesUri =  client.getUserResourceInstanceUri(BandwidthConstants.BRIDGES_URI_PATH, "124567");
     *
     * returns users/{user-id}/bridges/124567
     *
     *
     * @param path
     * @param instanceId
     * @return
     */
    public String getUserResourceInstanceUri(String path, String instanceId){
        if(StringUtils.isEmpty(path) || StringUtils.isEmpty(instanceId))
            throw new IllegalArgumentException("Path and Instance Id cannot be null");
        return getUserResourceUri(path) + "/" + instanceId;
    }

    /**
     * Convenience method that returns the base resource Uri. E.g.
     *
     * String baseResourceUri = client.getBaseResourceUri(BandwidthConstants.AVAILABLE_NUMBERS_URI_PATH);
     *
     * returns availableNumbers/
     *
     * @param path
     * @return
     */
    public String getBaseResourceUri(String path){
        if(StringUtils.isEmpty(path))
            throw new IllegalArgumentException("Path cannot be null");
        return path + "/";
    }

    /**
     *
     * Convenience method to return the full URL, including endpoint and version, to a given resource. E.g.
     *
     * String userResourceUri = client.getUserResourceUri(BandwidthConstants.BRIDGES_URI_PATH);
     * String path = client.getPath(userResourceUri);
     *
     * returns https://api.catapult.inetwork.com/v1/users/{user-id}/bridges
     *
     * @param uri
     * @return
     */
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


    /**
     * This method implements an HTTP POST. Use this method to create a new resource.
     *
     * @param uri
     * @param params
     * @return
     * @throws IOException
     */
    public RestResponse post(String uri, Map<String, Object> params) throws IOException {
        return request(getPath(uri), BandwidthConstants.POST, params);
    }

    /**
     * This method implements an HTTP GET. Use this method to retrieve a resource.
     *
     * @param uri
     * @param params
     * @return
     * @throws Exception
     */
    public RestResponse get(String uri, Map<String, Object> params) throws Exception {
        String path = getPath(uri);
        
        RestResponse response = request(path, BandwidthConstants.GET, params);
        if (response.isError()) throw new IOException(response.getResponseText());

        return response;

    }

    /**
     * This method implements an HTTP put. Use this method to update a resource.
     *
     * @param uri
     * @param params
     * @return
     * @throws IOException
     */
    public RestResponse put(String uri, Map<String, Object> params) throws IOException {
        return request(getPath(uri), BandwidthConstants.PUT, params);
    }

    /**
     * This method implements an HTTP delete. Use this method to remove a resource.
     * @param uri
     * @return
     * @throws IOException
     */
    public RestResponse delete(String uri) throws IOException {
        return request(getPath(uri), BandwidthConstants.DELETE);
    }

    /**
     * Convenience method to upload files to the server. User to upload media.
     *
     * @param uri
     * @param sourceFile
     * @param contentType
     * @throws IOException
     */
    public void upload(String uri, File sourceFile, String contentType) throws IOException {
        String path = getPath(uri);

        HttpPut request = (HttpPut) setupRequest(path, BandwidthConstants.PUT, null);
        request.setEntity(contentType == null ? new FileEntity(sourceFile) : new FileEntity(sourceFile, ContentType.parse(contentType)));

        performRequest(request);
    }

    /**
     * Convenience method to download files from the server. Used to retrieve media files.
     *
     * @param uri
     * @param destFile
     * @throws IOException
     */
    public void download(String uri, File destFile) throws IOException {
        String path = getPath(uri);

        HttpGet request = (HttpGet) setupRequest(path, BandwidthConstants.GET, Collections.<String, Object>emptyMap());
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



    /**
     * Helper method to build the request to the server.
     *
     * @param path
     * @param method
     * @return
     * @throws IOException
     */
    protected RestResponse request(final String path, String method) throws IOException {
        return request(path, method, null);
    }

    /**
     * Helper method to build the request to the server.
     * @param path
     * @param method
     * @param paramList
     * @return
     * @throws IOException
     */
    protected RestResponse request(final String path, String method,
                                   Map<String, Object> paramList) throws IOException {
        if (paramList == null) 
        	paramList = Collections.emptyMap();

        HttpUriRequest request = setupRequest(path, method, paramList);
        return performRequest(request);
    }

    /**
     * Helper method that executes the request on the server.
     *
     * @param request
     * @return
     * @throws IOException
     */
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

    /**
     * Helper method to build the request to the server.
     *
     * @param path
     * @param method
     * @param params
     * @return
     */
    protected HttpUriRequest setupRequest(String path, String method, final Map<String, Object> params) {
        HttpUriRequest request = buildMethod(method, path, params);

        request.addHeader(new BasicHeader("Accept", "application/json"));
        request.addHeader(new BasicHeader("Accept-Charset", "utf-8"));
        String s = token + ":" + secret;
        String auth = new String(Base64.encodeBase64(s.getBytes()));
        request.setHeader(new BasicHeader("Authorization", "Basic " + auth));

        return request;
    }

    /**
     * Helper method that builds the request to the server.
     *
     * @param method
     * @param path
     * @param params
     * @return
     */
    protected HttpUriRequest buildMethod(String method, final String path, final Map<String, Object> params) {
        if (StringUtils.equals(method, BandwidthConstants.GET)) {
            return generateGetRequest(path, params);
        } else if (StringUtils.equals(method, BandwidthConstants.POST)) {
            return generatePostRequest(path, params);
        } else if (StringUtils.equals(method, BandwidthConstants.PUT)) {
            return generatePutRequest(path, params);
        } else if (StringUtils.equals(method, BandwidthConstants.DELETE)) {
            return generateDeleteRequest(path);
        } else {
            throw new RuntimeException("Must not be here.");
        }
    }

    /**
     * Helper method to build the GET request for the server.
     *
     * @param path
     * @param paramMap
     * @return
     */
    protected HttpGet generateGetRequest(final String path, final Map<String, Object> paramMap) {
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        for (String key : paramMap.keySet()) {
            pairs.add(new BasicNameValuePair(key, paramMap.get(key).toString()));
        }
        URI uri = buildUri(path, pairs);
        return new HttpGet(uri);
    }

    /**
     * Helper method to build the POST request for the server.
     *
     * @param path
     * @param paramMap
     * @return
     */
    protected HttpPost generatePostRequest(final String path, final Map<String, Object> paramMap) {
        URI uri = buildUri(path);

        String s = JSONObject.toJSONString(paramMap);

        HttpPost post = new HttpPost(uri);
        post.setEntity(new StringEntity(s, ContentType.APPLICATION_JSON));

        return post;
    }

    /**
     * Helper method to build the PUT request for the server.
     *
     * @param path
     * @param paramMap
     * @return
     */
    protected HttpPut generatePutRequest(final String path, final Map<String, Object> paramMap) {
        URI uri = buildUri(path);

        HttpPut put = new HttpPut(uri);
        if (paramMap != null) {
            String s = JSONObject.toJSONString(paramMap);
            put.setEntity(new StringEntity(s, ContentType.APPLICATION_JSON));
        }

        return put;
    }


    /**
     * Helper method to build the HTTP DELETE request for the server.
     *
     * @param path
     * @return
     */
    protected HttpDelete generateDeleteRequest(final String path) {
        URI uri = buildUri(path);
        return new HttpDelete(uri);
    }

    /**
     * Helper method to return URI
     *
     * @param path
     * @return
     */
    protected URI buildUri(final String path) {
        return buildUri(path, null);
    }

    /**
     * Helper method to return URI query params
     *
     * @param path
     * @param queryStringParams
     * @return
     */
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
