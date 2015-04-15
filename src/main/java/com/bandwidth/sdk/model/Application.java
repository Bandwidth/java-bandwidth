package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.BandwidthClient;
import com.bandwidth.sdk.RestResponse;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

/**
 * Information about one of your applications.
 *
 * @author vpotapenko
 */
public class Application extends ResourceBase {
	
    /**
     * Factory method for Application. Returns Application object from id
     * @param id the application id
     * @return the application
     * @throws IOException unexpected error.
     */
    public static Application get(final String id) throws Exception {
        assert(id != null);

        final BandwidthClient client = BandwidthClient.getInstance();
        return Application.get(client ,id);
    }	

	/**
	 * Factory method for Application, returns Application object
	 * @param client the client
	 * @param id the application id
	 * @return the application
	 * @throws IOException unexpected error.
	 */
    public static Application get(final BandwidthClient client, final String id) throws Exception {
        assert(id != null);
        final String applicationUri = client.getUserResourceInstanceUri(BandwidthConstants.APPLICATIONS_URI_PATH, id);
        final JSONObject applicationObj = toJSONObject( client.get(applicationUri, null) );
        
        final Application application = new Application(client, applicationObj);
        return application;
    }
    
    /**
     * Factory method for Application list. Returns a list of Application object with default page size of 25
     * @return the list
     * @throws IOException unexpected error.
     */
    public static ResourceList<Application> list() throws IOException {
    	
    	// default page size is 25
     	return list(0, 25);
    }
    
    /**
     * Factory method for Application list. Returns a list of Application object with page and size preferences
     * @param page the page
     * @param size the page size
     * @return the list
     * @throws IOException unexpected error.
     */
    public static ResourceList<Application> list(final int page, final int size) throws IOException {
    	
    	final BandwidthClient client = BandwidthClient.getInstance();
        
        return list(client, page, size);
    }
    
    /**
     * Factory method for Application list. Returns a list of Application object with page and size preferences
     * Allow different Client implementaitons
     * @param client the client
     * @param page the page
     * @param size the page size
     * @return the list
     * @throws IOException unexpected error.
     */
    public static ResourceList<Application> list(final BandwidthClient client, final int page, final int size) throws IOException {
    	
        final String applicationUri = client.getUserResourceUri(BandwidthConstants.APPLICATIONS_URI_PATH);

        final ResourceList<Application> applications = 
        			new ResourceList<Application>(page, size, applicationUri, Application.class);
        applications.setClient(client);
        applications.initialize();
        
        return applications;
    }
    
    /**
     * Convenience factory method to create an Application object with a given name
     * @param name the name
     * @return the application
     * @throws Exception error
     */
    public static Application create(final String name) throws Exception{
    	final Map<String, Object> params = new HashMap<String, Object>();
    	params.put("name", name);
    	
    	return create(params);
    }
    
    /**
     * Convenience factory method to create an Application object from a set of params
     * @param params the params
     * @return the application
     * @throws Exception error
     */
    public static Application create(final Map<String, Object>params) throws Exception{
    	
    	return create(BandwidthClient.getInstance(), params);
    }
    
    /**
     * Convenience factory method to create an Application object from a set of params with a given client
     * @param client the client
     * @param params the params
     * @return the application
     * @throws Exception error
     */
    public static Application create(final BandwidthClient client, final Map<String, Object>params) throws Exception {
    	assert(client != null);

        String uri = client.getUserResourceUri(BandwidthConstants.APPLICATIONS_URI_PATH);

        RestResponse response = client.post(uri, params);


        RestResponse getResponse = client.get(response.getLocation(), null);

        return new Application(client, toJSONObject(getResponse));
    }
    
    
    public Application(final BandwidthClient client, final JSONObject jsonObject) {
        super(client,jsonObject);
    }
    
    @Override
    protected void setUp(final JSONObject jsonObject) {
        this.id = (String) jsonObject.get("id");
        updateProperties(jsonObject);
    }
    
    protected String getUri() {
        return client.getUserResourceInstanceUri(BandwidthConstants.APPLICATIONS_URI_PATH, getId());
    }

    public String getName() {
        return getPropertyAsString("name");
    }

    public String getIncomingCallUrl() {
        return getPropertyAsString("incomingCallUrl");
    }

    public String getIncomingSmsUrl() {
        return getPropertyAsString("incomingSmsUrl");
    }

    public String getCallbackHttpMethod() {
        return getPropertyAsString("callbackHttpMethod");
    }

    public String getIncomingCallFallbackUrl() {
        return getPropertyAsString("incomingCallFallbackUrl");
    }

    public Long getIncomingCallUrlCallbackTimeout() {
        return getPropertyAsLong("incomingCallUrlCallbackTimeout");
    }

    public Long getIncomingSmsUrlCallbackTimeout() {
        return getPropertyAsLong("incomingSmsUrlCallbackTimeout");
    }

    public boolean isAutoAnswer() {
        return getPropertyAsBoolean("autoAnswer");
    }

    public void setName(final String name) {
        if (name == null) throw new IllegalArgumentException();

        putProperty("name", name);
    }

    public void setIncomingCallUrl(final String incomingCallUrl) {
        putProperty("incomingCallUrl", incomingCallUrl);
    }

    public void setIncomingSmsUrl(final String incomingSmsUrl) {
        putProperty("incomingSmsUrl", incomingSmsUrl);
    }

    public void setAutoAnswer(final boolean autoAnswer) {
        putProperty("autoAnswer", autoAnswer);
    }

    public void setIncomingCallFallbackUrl(final String incomingCallFallbackUrl) {
        putProperty("incomingCallFallbackUrl", incomingCallFallbackUrl);
    }

    public void setIncomingCallUrlCallbackTimeout(final Long incomingCallUrlCallbackTimeout) {
        putProperty("incomingCallUrlCallbackTimeout", incomingCallUrlCallbackTimeout);
    }

    public void setIncomingSmsUrlCallbackTimeout(final Long incomingSmsUrlCallbackTimeout) {
        putProperty("incomingSmsUrlCallbackTimeout", incomingSmsUrlCallbackTimeout);
    }

    public void setCallbackHttpMethod(final String callbackHttpMethod) {
        putProperty("callbackHttpMethod", callbackHttpMethod);
    }

    /**
     * Makes changes of the application.
     *
     * @throws IOException unexpected error.
     */
    public void commit() throws IOException {
        final Map<String, Object> params = toMap();
        params.remove("id");

        client.post(getUri(), params);
    }

    /**
     * Permanently deletes application.
     *
     * @throws IOException unexpected error.
     */
    public void delete() throws IOException {
        client.delete(getUri());
    }

    @Override
    public String toString() {
        return "Application{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", incomingCallUrl='" + getIncomingCallUrl() + '\'' +
                ", incomingSmsUrl='" + getIncomingSmsUrl() + '\'' +
                ", autoAnswer=" + isAutoAnswer() +
                ", incomingCallUrlCallbackTimeout='" + getIncomingCallUrlCallbackTimeout() + '\'' +
                ", incomingSmsUrlCallbackTimeout='" + getIncomingSmsUrlCallbackTimeout() + '\'' +
                ", callbackHttpMethod='" + getCallbackHttpMethod() + '\'' +
                ", incomingCallFallbackUrl='" + getIncomingCallFallbackUrl() + '\'' +
                '}';
    }
}
