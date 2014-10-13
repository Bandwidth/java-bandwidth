package com.bandwidth.sdk;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author vpotapenko
 */
public class MockRestClient extends BandwidthRestClient {

    private String userId;
    private String token;
    private String secret;
    private String endpoint;
    private String version;

    public final List<RestRequest> requests = new ArrayList<RestRequest>();

    public JSONObject result;
    public JSONArray arrayResult;
    
    protected RestResponse restResponse;

    public MockRestClient(String userId, String token, String secret, String endpoint, String version){
        super(userId, token, secret, endpoint, version);
        this.userId = userId;
        this.token = token;
        this.secret = secret;
        this.endpoint = endpoint;
        this.version = version;
    }
    
    
    /**
     * HTTP get method. Returns a RestResponse object
     * @param uri
     * @param params
     * @return
     * @throws IOException
     */
    public RestResponse get(String uri, Map<String, Object> params) 
    													throws IOException {
    	
    	
        return getRestResponse();
    }
    

    // Used to compare url buildout properly
    public String getUserId(){
        return this.userId;
    }

    public MockRestClient() {
        super("userId", "", "", "", "");
    }

    @Override
    public JSONArray getArray(String uri, Map<String, Object> params) throws IOException {
        requests.add(new RestRequest("getArray", uri, params));
        return arrayResult;
    }

    @Override
    public JSONObject getObject(String uri) throws IOException {
        requests.add(new RestRequest("getObject", uri, null));
        return result;
    }

    @Override
    public JSONObject create(String uri, Map<String, Object> params) throws IOException {
        requests.add(new RestRequest("create", uri, params));
        return result;
    }

    @Override
    public RestResponse post(String uri, Map<String, Object> params) throws IOException {
        requests.add(new RestRequest("post", uri, params));
        
        return null;
    }

    @Override
    public void delete(String uri) throws IOException {
        requests.add(new RestRequest("delete", uri, null));
    }

    @Override
    public void uploadFile(String uri, File sourceFile, String contentType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("filePath", sourceFile.getPath());
        if (contentType != null) params.put("contentType", contentType);

        requests.add(new RestRequest("uploadFile", uri, params));
    }

    @Override
    public void downloadFileTo(String uri, File destFile) throws IOException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("filePath", destFile.getPath());
        requests.add(new RestRequest("downloadFileTo", uri, params));
    }
    
    public RestResponse getRestResponse() {
    	return restResponse;
    }
    
    public void setRestResponse(RestResponse restResponse) {
    	this.restResponse = restResponse;
    }
    

    public static class RestRequest {

        public final String name;
        public final String uri;
        public final Map<String, Object> params;

        public RestRequest(String name, String uri, Map<String, Object> params) {
            this.name = name;
            this.uri = uri;
            this.params = params;
        }
    }
}
