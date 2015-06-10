package com.bandwidth.sdk.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;

import com.bandwidth.sdk.BandwidthClient;
import com.bandwidth.sdk.BandwidthConstants;

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
     * @throws IOException unexpected error.
     */
    public static Error get(final String id) throws Exception {

    	return get(BandwidthClient.getInstance(), id);
    }
    
    /**
     * Factory method for Error. Returns Error object from id.
     * @param client the client
     * @param id error id
     * @return information about one user error
     * @throws IOException unexpected error.
     */
    public static Error get(final BandwidthClient client, final String id) throws Exception {
        final String errorsUri = client.getUserResourceInstanceUri(BandwidthConstants.ERRORS_URI_PATH, id);

        final JSONObject jsonObject = toJSONObject(client.get(errorsUri, null));
        return new Error(client, errorsUri, jsonObject);
    }
    

    /**
     * Factory method for errors list. Returns a list of Error objects, default 25 per page
     * @return the list
     * @throws IOException unexpected error.
     */
    public static ResourceList<Error> list() throws IOException {
    	
    	// default page size is 25
     	return list(0, 25);
    }
    
    /**
     * Factory method for errors list. Returns a list of error objects, given the page and size preferences.
     * @param page the page
     * @param size the page size
     * @return the list
     * @throws IOException unexpected error.
     */
    public static ResourceList<Error> list(final int page, final int size) throws IOException {
    	        
        return list(BandwidthClient.getInstance(), page, size);
    }
    
    /**
     * Factory method for errors list. Returns a list of error objects, given the page and size preferences.
     * @param client the client
     * @param page the page
     * @param size the page size
     * @return the list
     * @throws IOException unexpected error.
     */
    public static ResourceList<Error> list(final BandwidthClient client, final int page, final int size) throws IOException {
    	
        final String resourceUri = client.getUserResourceUri(BandwidthConstants.ERRORS_URI_PATH);

        final ResourceList<Error> errors = 
        			new ResourceList<Error>(page, size, resourceUri, Error.class);
        
        errors.setClient(client);

        errors.initialize();
        
        return errors;
    }
    

    String parentUri;
    public Error(final BandwidthClient client, final String parentUri, final JSONObject jsonObject) {
        super(client, jsonObject);
        this.parentUri = parentUri;
    }
    
    public Error(final BandwidthClient client, final JSONObject jsonObject) {
        super(client, jsonObject);
    }
    
    @Override
    protected void setUp(final JSONObject jsonObject) {
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
        final String uri = getUri();
        final List<ErrorDetail> details = new ArrayList<ErrorDetail>();
        for (final Object obj : (List) getProperty("details")) {
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
