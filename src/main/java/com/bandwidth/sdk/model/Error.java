package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.BandwidthRestClient;

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
public class Error extends BaseModelObject {
	
    /**
     * Factory method for Error. Returns Error object from id.
     * @param id error id
     * @return information about one user error
     * @throws IOException
     */
    public static Error getError(String id) throws IOException {
    	BandwidthRestClient client = BandwidthRestClient.getInstance();
        String errorsUri = client.getUserResourceUri(BandwidthConstants.ERRORS_URI_PATH);
        String uri = StringUtils.join(new String[]{
                errorsUri,
                id
        }, '/');
        JSONObject jsonObject = client.getObject(uri);
        return new Error(client, errorsUri, jsonObject);
    }

    /**
     * Factory method for errors list. Returns a list of Error objects, default 25 per page
     * @return
     * @throws IOException
     */
    public static ResourceList<Error> getErrors() throws IOException {
    	
    	// default page size is 25
     	return getErrors(0, 25);
    }
    
    /**
     * Factory method for errors list. Returns a list of error objects, given the page and size preferences.
     * @param page
     * @param size
     * @return
     * @throws IOException
     */
    public static ResourceList<Error> getErrors(int page, int size) throws IOException {
    	
        String resourceUri = BandwidthRestClient.getInstance().getUserResourceUri(BandwidthConstants.ERRORS_URI_PATH);

        ResourceList<Error> errors = 
        			new ResourceList<Error>(page, size, resourceUri, Error.class);

        errors.initialize();
        
        return errors;
    }

    public Error(BandwidthRestClient client, String parentUri, JSONObject jsonObject) {
        super(client, parentUri, jsonObject);
    }
    
    public Error(BandwidthRestClient client, JSONObject jsonObject) {
        super(client, jsonObject);
    }

    @Override
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
