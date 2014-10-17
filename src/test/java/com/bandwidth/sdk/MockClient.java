package com.bandwidth.sdk;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.bandwidth.sdk.MockRestClient.RestRequest;

public class MockClient extends BandwidthClient {

	public MockClient() {
		super(TestsHelper.TEST_USER_ID, "", "", "", "");
	}
	
	
    public final List<RestRequest> requests = new ArrayList<RestRequest>();

    public JSONObject result;
    public JSONArray arrayResult;
    
    protected RestResponse restResponse;

    
    @Override
    public RestResponse get(String uri, Map<String, Object> params) throws IOException {
        requests.add(new RestRequest("get", uri, params));
   	
        return getRestResponse();
    }    
    
    @Override
    public RestResponse post(String uri, Map<String, Object> params) throws IOException {
    	 requests.add(new RestRequest("post", uri, params));        
    	 
    	 return restResponse;
    }
    
    @Override
    public RestResponse delete(String uri) throws IOException {
        requests.add(new RestRequest("delete", uri, null));
        
        return restResponse;
    }
    
    @Override
    public void upload(String uri, File sourceFile, String contentType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("filePath", sourceFile.getPath());
        if (contentType != null) params.put("contentType", contentType);

        requests.add(new RestRequest("uploadFile", uri, params));
    }

    @Override
    public void download(String uri, File destFile) throws IOException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("filePath", destFile.getPath());
        requests.add(new RestRequest("downloadFileTo", uri, params));
    }

    


	public JSONObject getResult() {
		return result;
	}


	public void setResult(JSONObject result) {
		this.result = result;
	}


	public JSONArray getArrayResult() {
		return arrayResult;
	}


	public void setArrayResult(JSONArray arrayResult) {
		this.arrayResult = arrayResult;
	}


	public RestResponse getRestResponse() {
		return restResponse;
	}


	public void setRestResponse(RestResponse restResponse) {
		this.restResponse = restResponse;
	}


	public List<RestRequest> getRequests() {
		return requests;
	}   
    
}
