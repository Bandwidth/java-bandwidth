package com.bandwidth.sdk;

import com.bandwidth.sdk.exception.MissingCredentialsException;
import com.bandwidth.sdk.model.NumberInfo;
import com.bandwidth.sdk.model.ResourceBase;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockClient extends BandwidthClient {
    private String userId;
    private String token;
    private String secret;
    private String endpoint;
    private String version;

	public MockClient() {
		super(TestsHelper.TEST_USER_ID, "", "", "", "", 200, 20);
	}

    public MockClient(final String userId,
                      final String token,
                      final String secret,
                      final String endpoint,
                      final String version,
                      final int maxConnections,
                      final int defaultMaxPerRoute){
        super(userId, token, secret, endpoint, version, maxConnections, defaultMaxPerRoute);
        this.userId = userId;
        this.token = token;
        this.secret = secret;
        this.endpoint = endpoint;
        this.version = version;
    }

    public final List<RestRequest> requests = new ArrayList<RestRequest>();

    public JSONObject result;
    public JSONArray arrayResult;
    
    protected RestResponse restResponse;

    
    @Override
    public RestResponse get(final String uri, final Map<String, Object> params) throws IOException,
            AppPlatformException {
        requests.add(new RestRequest("get", uri, params));
   	
        return getRestResponse();
    }    
    
    @Override
    public RestResponse post(final String uri, final Map<String, Object> params)
            throws IOException, AppPlatformException {
    	 requests.add(new RestRequest("post", uri, params));        
    	 
    	 return getRestResponse();
    }
    
    @Override
    public RestResponse delete(final String uri) throws IOException, AppPlatformException {
        requests.add(new RestRequest("delete", uri, null));
        
        return getRestResponse();
    }
    
    @Override
    public void upload(final String uri, final File sourceFile, final String contentType) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("filePath", sourceFile.getPath());
        if (contentType != null) params.put("contentType", contentType);

        requests.add(new RestRequest("uploadFile", uri, params));
    }

    @Override
    public void download(final String uri, final File destFile) throws IOException {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("filePath", destFile.getPath());
        requests.add(new RestRequest("downloadFileTo", uri, params));
    }


    /**
     * Returns information about this number.
     *
     * @param number searching number
     * @return information about the number
     * @throws IOException
     */
    public NumberInfo getNumberInfoByNumber(final String number) throws Exception {
        final String uri = StringUtils.join(new String[]{
                "phoneNumbers",
                "numberInfo",
                number
        }, '/');
        final JSONObject object = ResourceBase.toJSONObject(get(uri, null));
        return new NumberInfo(object);
    }


	public JSONObject getResult() {
		return result;
	}


	public void setResult(final JSONObject result) {
		this.result = result;
	}


	public JSONArray getArrayResult() {
		return arrayResult;
	}


	public void setArrayResult(final JSONArray arrayResult) {
		this.arrayResult = arrayResult;
	}


	public RestResponse getRestResponse() throws AppPlatformException, MissingCredentialsException {
        if (restResponse != null) {
            if (restResponse.getStatus() == 401) {
                if (this.usersUri == null || this.usersUri.isEmpty()
                        || this.token == null || this.token.isEmpty()
                        || this.secret == null || this.secret.isEmpty()) {

                    throw new MissingCredentialsException();
                }
            } else if (restResponse.getStatus() >= 400) {
                throw new AppPlatformException(restResponse.getResponseText());
            }
        }

		return restResponse;
	}


	public void setRestResponse(final RestResponse restResponse) {
		this.restResponse = restResponse;
	}


	public List<RestRequest> getRequests() {
		return requests;
	}

    public HttpUriRequest setupNewRequest(final String path, final String method, final Map<String, Object> params) {
        return setupRequest(path, method, params);
    }


    public static class RestRequest {

        public final String name;
        public final String uri;
        public final Map<String, Object> params;

        public RestRequest(final String name, final String uri, final Map<String, Object> params) {
            this.name = name;
            this.uri = uri;
            this.params = params;
        }
    }
    
}
