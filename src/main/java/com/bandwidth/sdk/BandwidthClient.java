package com.bandwidth.sdk;

import com.bandwidth.sdk.exception.MissingCredentialsException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
     * @return the BandwidthClient
     */
    public synchronized static BandwidthClient getInstance() {
        if (INSTANCE == null) {
            final Map<String, String> env = System.getenv();

            String userId = env.get(BandwidthConstants.BANDWIDTH_USER_ID);
            String apiToken = env.get(BandwidthConstants.BANDWIDTH_API_TOKEN);
            String apiSecret = env.get(BandwidthConstants.BANDWIDTH_API_SECRET);
            String apiEndpoint = env.get(BandwidthConstants.BANDWIDTH_API_ENDPOINT);
            String apiVersion = env.get(BandwidthConstants.BANDWIDTH_API_VERSION);

            if (userId == null || apiToken == null || apiSecret == null || apiEndpoint == null || apiVersion == null) {
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
     * Constructor. Instances are created through getInstance() method.
     * @param userId the user id.
     * @param apiToken the user API token.
     * @param apiSecret the user API secret.
     * @param apiEndpoint the API Endpoint.
     * @param apiVersion the API version.
     */
    protected BandwidthClient(final String userId, final String apiToken, final String apiSecret, final String apiEndpoint, final String apiVersion){
        this.usersUri = String.format(BandwidthConstants.USERS_URI_PATH, userId);
        this.token = apiToken;
        this.secret = apiSecret;

        this.apiEndpoint = apiEndpoint;
        this.apiVersion = apiVersion;

        if (apiEndpoint == null || apiVersion == null) {
            this.apiEndpoint = BandwidthConstants.API_ENDPOINT;
            this.apiVersion = BandwidthConstants.API_VERSION;
        }

        this.httpClient = new DefaultHttpClient();
    }

    /**
     * The setCredentials() provides a convenience method to pass the userId, API-token and API-secret after
     * a client has been instantiated.
     *
     * @param userId the user id.
     * @param apiToken the API token.
     * @param apiSecret the API secret.
     */
    public void setCredentials(final String userId, final String apiToken, final String apiSecret) {
        usersUri = String.format(BandwidthConstants.USERS_URI_PATH, userId);
        this.token = apiToken;
        this.secret = apiSecret;
    }

    /**
     * The setEndpointandVersion() method provides a convenience method to pass the apiEndpoint and apiVersion
     * after a client has been instantiated.
     *
     * @param apiEndpoint the API Endpoint.
     * @param apiVersion the API version.
     */
    public void setEndpointandVersion(final String apiEndpoint, final String apiVersion) {
        this.apiEndpoint = apiEndpoint;
        this.apiVersion = apiVersion;
    }


    /**
     * Convenience method to return the resource URL with the users credentials, e.g.
     *
     * @param path the path.
     * @return the joined path.
     */
    public String getUserResourceUri(final String path){
        if(StringUtils.isEmpty(path)) {
            throw new IllegalArgumentException("Path cannot be null");
        }
        return StringUtils.join(new String[] {getUserUri(), path}, "/");
    }

    /**
     * Convenience method that returns the resource instance uri. E.g.
     *
     * @param path the path.
     * @param instanceId the instance id.
     * @return The user Instance URI.
     */
    public String getUserResourceInstanceUri(final String path, final String instanceId){
        if(StringUtils.isEmpty(path) || StringUtils.isEmpty(instanceId)) {
            throw new IllegalArgumentException("Path and Instance Id cannot be null");
        }
        return getUserResourceUri(path) + "/" + instanceId;
    }

    /**
     * Convenience method that returns the base resource URI. E.g.
     *
     * @param path the path.
     * @return the base resource URI.
     */
    public String getBaseResourceUri(final String path){
        if(StringUtils.isEmpty(path)) {
            throw new IllegalArgumentException("Path cannot be null");
        }
        return path + "/";
    }

    /**
     *
     * Convenience method to return the full URL, including Endpoint and version, to a given resource. E.g.
     *
     * @param uri the URI.
     * @return the path.
     */
    public String getPath(final String uri) {
        final String[] parts = new String[]{
                apiEndpoint,
                apiVersion,
                null
        };
        
        final String base = StringUtils.join(parts, "/");

        if (!uri.contains(base)) {
            parts[2] = uri;
            return StringUtils.join(parts, "/");
        } else {
            return uri;
        }
    }
    
    /**
     * Returns API URL with userid 
     * 
     * @return usersUri
     */
    public String getUserUri() {
    	return usersUri;
    }

    /**
     * This method implements an HTTP POST. Use this method to create a new resource.
     *
     * @param uri the URI.
     * @param params the parameters.
     * @return the post response.
     * @throws IOException unexpected exception.
     */
    public RestResponse post(final String uri, final Map<String, Object> params)
            throws IOException, AppPlatformException {
        return request(getPath(uri), HttpPost.METHOD_NAME, params);
    }

    /**
     * This method implements an HTTP GET. Use this method to retrieve a resource.
     *
     * @param uri the URI.
     * @param params the parameters.
     * @return the get response.
     * @throws Exception unexpected exception.
     */
    public RestResponse get(final String uri, final Map<String, Object> params) throws Exception {
        final String path = getPath(uri);
        final RestResponse response = request(path, HttpGet.METHOD_NAME, params);
        if (response.isError()) {
            throw new IOException(response.getResponseText());
        }
        return response;
    }

    /**
     * This method implements an HTTP put. Use this method to update a resource.
     *
     * @param uri the URI
     * @param params the parameters.
     * @return the put response.
     * @throws IOException unexpected exception.
     */
    public RestResponse put(final String uri, final Map<String, Object> params) throws IOException,
            AppPlatformException {
        return request(getPath(uri), HttpPut.METHOD_NAME, params);
    }

    /**
     * This method implements an HTTP delete. Use this method to remove a resource.
     * @param uri the URI.
     * @return the response.
     * @throws IOException unexpected exception.
     */
    public RestResponse delete(final String uri) throws IOException, AppPlatformException {
        return request(getPath(uri), HttpDelete.METHOD_NAME);
    }

    /**
     * Convenience method to upload files to the server. User to upload media.
     *
     * @param uri the URI
     * @param sourceFile the source file
     * @param contentType the content type.
     * @throws IOException unexpected exception.
     */
    public void upload(final String uri, final File sourceFile, final String contentType)
            throws IOException, AppPlatformException {
        final String path = getPath(uri);
        final HttpPut request = (HttpPut) setupRequest(path, HttpPut.METHOD_NAME, null);
        request.setEntity(contentType == null ? new FileEntity(sourceFile) : new FileEntity(sourceFile, ContentType.parse(contentType)));
        performRequest(request);
    }

    /**
     * Convenience method to download files from the server. Used to retrieve media files.
     *
     * @param uri the URI.
     * @param destFile the destination file.
     * @throws IOException unexpected exception.
     */
    public void download(final String uri, final File destFile) throws IOException {
        final String path = getPath(uri);
        final HttpGet request = (HttpGet) setupRequest(path, HttpGet.METHOD_NAME, Collections.<String, Object>emptyMap());
        HttpResponse response;
        OutputStream outputStream = null;
        try {
            response = httpClient.execute(request);
            final HttpEntity entity = response.getEntity();

            final StatusLine status = response.getStatusLine();
            final int statusCode = status.getStatusCode();
            if (statusCode >= 400) {
                throw new IOException(EntityUtils.toString(entity));
            }
            outputStream = new BufferedOutputStream(new FileOutputStream(destFile));
            entity.writeTo(outputStream);
        } catch (final ClientProtocolException e1) {
            throw new IOException(e1);
        } catch (final IOException e1) {
            throw new IOException(e1);
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (final IOException ignore) {
                
            }
        }
    }



    /**
     * Helper method to build the request to the server.
     *
     * @param path the path.
     * @param method the method.
     * @return the request response.
     * @throws IOException unexpected exception.
     */
    protected RestResponse request(final String path, final String method) throws IOException, AppPlatformException {
        return request(path, method, null);
    }

    /**
     * Helper method to build the request to the server.
     * @param path the path
     * @param method the method
     * @param paramList the parameter list.
     * @return the response.
     * @throws IOException unexpected exception.
     */
    protected RestResponse request(final String path, final String method, Map<String, Object> paramList)
            throws IOException, AppPlatformException {
        if (paramList == null) {
            paramList = Collections.emptyMap();
        }

        final HttpUriRequest request = setupRequest(path, method, paramList);
        return performRequest(request);
    }

    /**
     * Helper method that executes the request on the server.
     *
     * @param request the request.
     * @return the response.
     * @throws IOException unexpected exception.
     */
    protected RestResponse performRequest(final HttpUriRequest request) throws IOException, AppPlatformException {

        if (this.usersUri == null || this.usersUri.isEmpty()
                || this.token == null || this.token.isEmpty()
                || this.secret == null || this.secret.isEmpty()) {

            throw new MissingCredentialsException();
        }

        RestResponse restResponse = RestResponse.createRestResponse(httpClient.execute(request));

        if (restResponse.getStatus() >= 400) {
            throw new AppPlatformException(restResponse.getResponseText());
        }

        return restResponse;
    }

    /**
     * Helper method to build the request to the server.
     *
     * @param path the path.
     * @param method the method.
     * @param params the parameters.
     * @return the request.
     */
    protected HttpUriRequest setupRequest(final String path, final String method, final Map<String, Object> params) {
        final HttpUriRequest request = buildMethod(method, path, params);
        request.addHeader(new BasicHeader("Accept", "application/json"));
        request.addHeader(new BasicHeader("Accept-Charset", "utf-8"));
        request.setHeader(new BasicHeader("Authorization", "Basic " + new String(Base64.encodeBase64((this.token + ":" + this.secret).getBytes()))));
        return request;
    }

    /**
     * Helper method that builds the request to the server.
     *
     * @param method the method.
     * @param path the path.
     * @param params the parameters.
     * @return the request.
     */
    protected HttpUriRequest buildMethod(final String method, final String path, final Map<String, Object> params) {
        if (StringUtils.equalsIgnoreCase(method, HttpGet.METHOD_NAME)) {
            return generateGetRequest(path, params);
        } else if (StringUtils.equalsIgnoreCase(method, HttpPost.METHOD_NAME)) {
            return generatePostRequest(path, params);
        } else if (StringUtils.equalsIgnoreCase(method, HttpPut.METHOD_NAME)) {
            return generatePutRequest(path, params);
        } else if (StringUtils.equalsIgnoreCase(method, HttpDelete.METHOD_NAME)) {
            return generateDeleteRequest(path);
        } else {
            throw new RuntimeException("Must not be here.");
        }
    }

    /**
     * Helper method to build the GET request for the server.
     *
     * @param path the path.
     * @param paramMap the parameters map.
     * @return the get object.
     */
    protected HttpGet generateGetRequest(final String path, final Map<String, Object> paramMap) {
        final List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        for (final String key : paramMap.keySet()) {
            pairs.add(new BasicNameValuePair(key, paramMap.get(key).toString()));
        }
        final URI uri = buildUri(path, pairs);
        return new HttpGet(uri);
    }

    /**
     * Helper method to build the POST request for the server.
     *
     * @param path the path.
     * @param paramMap the parameters map.
     * @return the post object.
     */
    protected HttpPost generatePostRequest(final String path, final Map<String, Object> paramMap) {
        final HttpPost post = new HttpPost(buildUri(path));
        post.setEntity(new StringEntity(JSONObject.toJSONString(paramMap), ContentType.APPLICATION_JSON));
        return post;
    }

    /**
     * Helper method to build the PUT request for the server.
     *
     * @param path the path
     * @param paramMap the parameters map.
     * @return the put object.
     */
    protected HttpPut generatePutRequest(final String path, final Map<String, Object> paramMap) {
        final HttpPut put = new HttpPut(buildUri(path));
        if (paramMap != null) {
            put.setEntity(new StringEntity(JSONObject.toJSONString(paramMap), ContentType.APPLICATION_JSON));
        }
        return put;
    }

    /**
     * Helper method to build the HTTP DELETE request for the server.
     *
     * @param path the path
     * @return the delete object.
     */
    protected HttpDelete generateDeleteRequest(final String path) {
        return new HttpDelete(buildUri(path));
    }

    /**
     * Helper method to return URI
     *
     * @param path the path.
     * @return the URI object.
     */
    protected URI buildUri(final String path) {
        return buildUri(path, null);
    }

    /**
     * Helper method to return URI query parameters
     *
     * @param path the path.
     * @param queryStringParams the query string parameters.
     * @return the URI object.
     */
    protected URI buildUri(final String path, final List<NameValuePair> queryStringParams) {
        final StringBuilder sb = new StringBuilder();
        
        sb.append(path);
        if (queryStringParams != null && queryStringParams.size() > 0) {
            sb.append("?").append(URLEncodedUtils.format(queryStringParams, "UTF-8"));
        }

        try {
            return new URI(sb.toString());
        } catch (final URISyntaxException e) {
            throw new IllegalStateException("Invalid uri", e);
        }
    }
}
