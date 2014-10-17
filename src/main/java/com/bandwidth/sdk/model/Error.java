package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.BandwidthClient;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Information about one user error
 *
 * @author vpotapenko
 */
public class Error extends ResourceBase {
	
    /**
     * Factory method for Error. Returns Error object from id.
     * @param id error id
     * @return information about one user error
     * @throws IOException
     */
    public static Error get(String id) throws Exception {

    	return get(BandwidthClient.getInstance(), id);
    }
    
    /**
     * Factory method for Error. Returns Error object from id.
     * @param id error id
     * @return information about one user error
     * @throws IOException
     */
    public static Error get(BandwidthClient client, String id) throws Exception {
        String errorsUri = client.getUserResourceInstanceUri(BandwidthConstants.ERRORS_URI_PATH, id);

        JSONObject jsonObject = toJSONObject(client.get(errorsUri, null));
        return new Error(client, errorsUri, jsonObject);
    }
    

    /**
     * Factory method for errors list. Returns a list of Error objects, default 25 per page
     * @return
     * @throws IOException
     */
    public static ResourceList<Error> list() throws IOException {
    	
    	// default page size is 25
     	return list(0, 25);
    }
    
    /**
     * Factory method for errors list. Returns a list of error objects, given the page and size preferences.
     * @param page
     * @param size
     * @return
     * @throws IOException
     */
    public static ResourceList<Error> list(int page, int size) throws IOException {
    	        
        return list(BandwidthClient.getInstance(), page, size);
    }
    
    /**
     * Factory method for errors list. Returns a list of error objects, given the page and size preferences.
     * @param page
     * @param size
     * @return
     * @throws IOException
     */
    public static ResourceList<Error> list(BandwidthClient client, int page, int size) throws IOException {
    	
        String resourceUri = client.getUserResourceUri(BandwidthConstants.ERRORS_URI_PATH);

        ResourceList<Error> errors = 
        			new ResourceList<Error>(page, size, resourceUri, Error.class);
        
        errors.setClient(client);

        errors.initialize();
        
        return errors;
    }
    

    String parentUri;
    public Error(BandwidthClient client, String parentUri, JSONObject jsonObject) {
        super(client, jsonObject);
        this.parentUri = parentUri;
    }
    
    public Error(BandwidthClient client, JSONObject jsonObject) {
        super(client, jsonObject);
    }
    
    @Override
    protected void setUp(JSONObject jsonObject) {
        this.id = (String) jsonObject.get("id");
        updateProperties(jsonObject);
    }      
    

    protected String getUri() {
        return null;
    }

    public String getMessage() {
        return getPropertyAsString("message");
    }

    public String getCategory() {
        return getPropertyAsString("category");
    }

    public String getCode() {
        return getPropertyAsString("code");
    }

    public List<ErrorDetail> getDetails() {
        String uri = getUri();
        List<ErrorDetail> details = new ArrayList<ErrorDetail>();
        for (Object obj : (List) getProperty("details")) {
            details.add(new ErrorDetail(client, uri, (JSONObject) obj));
        }
        return details;
    }

    public Date getTime() {
        return getPropertyAsDate("time");
    }

    @Override
    public String toString() {
        return "Error{" +
                "id='" + getId() + '\'' +
                ", message='" + getMessage() + '\'' +
                ", category='" + getCategory() + '\'' +
                ", code='" + getCode() + '\'' +
                ", time=" + getTime() +
                '}';
    }
}
