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
     * @param id
     * @return
     * @throws IOException
     */
    public static Application get(String id) throws Exception {
        assert(id != null);

        BandwidthClient client = BandwidthClient.getInstance();
        return Application.get(client ,id);
    }	

	/**
	 * Factory method for Application, returns Application object
	 * @param client
	 * @param id
	 * @return
	 * @throws IOException
	 */
    public static Application get(BandwidthClient client, String id) throws Exception {
        assert(id != null);
        String applicationUri = client.getUserResourceInstanceUri(BandwidthConstants.APPLICATIONS_URI_PATH, id);
        JSONObject applicationObj = toJSONObject( client.get(applicationUri, null) );
        
        Application application = new Application(client, applicationObj);
        return application;
    }
    
    /**
     * Factory method for Application list. Returns a list of Application object with default page size of 25
     * @return
     * @throws IOException
     */
    public static ResourceList<Application> list() throws IOException {
    	
    	// default page size is 25
     	return list(0, 25);
    }
    
    /**
     * Factory method for Application list. Returns a list of Application object with page and size preferences
     * @param page
     * @param size
     * @return
     * @throws IOException
     */
    public static ResourceList<Application> list(int page, int size) throws IOException {
    	
    	BandwidthClient client = BandwidthClient.getInstance();
        
        return list(client, page, size);
    }
    
    /**
     * Factory method for Application list. Returns a list of Application object with page and size preferences
     * Allow different Client implementaitons
     * @param page
     * @param size
     * @return
     * @throws IOException
     */
    public static ResourceList<Application> list(BandwidthClient client, int page, int size) throws IOException {
    	
        String applicationUri = client.getUserResourceUri(BandwidthConstants.APPLICATIONS_URI_PATH);

        ResourceList<Application> applications = 
        			new ResourceList<Application>(page, size, applicationUri, Application.class);
        applications.setClient(client);
        applications.initialize();
        
        return applications;
    }
    
    /**
     * Convenience factory method to create an Application object with a given name
     * @param name
     * @return
     * @throws Exception
     */
    public static Application create(String name) throws Exception{
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("name", name);
    	
    	return create(params);
    }
    
    /**
     * Convenience factory method to create an Application object from a set of params
     * @param params
     * @return
     * @throws Exception
     */
    public static Application create(Map<String, Object>params) throws Exception{
    	
    	return create(BandwidthClient.getInstance(), params);
    }
    
    /**
     * Convenience factory method to create an Application object from a set of params with a given client
     * @param client
     * @param params
     * @return
     * @throws Exception
     */
    public static Application create(BandwidthClient client, Map<String, Object>params) throws Exception {
    	assert(client != null);
    	
    	RestResponse response = client.post(BandwidthConstants.APPLICATIONS_URI_PATH, params);
    	
    	return new Application(client, toJSONObject(response));
    }
    
    
    public Application(BandwidthClient client, JSONObject jsonObject) {
        super(client,jsonObject);
    }
    
    @Override
    protected void setUp(JSONObject jsonObject) {
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

    public void setName(String name) {
        if (name == null) throw new IllegalArgumentException();

        putProperty("name", name);
    }

    public void setIncomingCallUrl(String incomingCallUrl) {
        putProperty("incomingCallUrl", incomingCallUrl);
    }

    public void setIncomingSmsUrl(String incomingSmsUrl) {
        putProperty("incomingSmsUrl", incomingSmsUrl);
    }

    public void setAutoAnswer(boolean autoAnswer) {
        putProperty("autoAnswer", autoAnswer);
    }

    public void setIncomingCallFallbackUrl(String incomingCallFallbackUrl) {
        putProperty("incomingCallFallbackUrl", incomingCallFallbackUrl);
    }

    public void setIncomingCallUrlCallbackTimeout(Long incomingCallUrlCallbackTimeout) {
        putProperty("incomingCallUrlCallbackTimeout", incomingCallUrlCallbackTimeout);
    }

    public void setIncomingSmsUrlCallbackTimeout(Long incomingSmsUrlCallbackTimeout) {
        putProperty("incomingSmsUrlCallbackTimeout", incomingSmsUrlCallbackTimeout);
    }

    public void setCallbackHttpMethod(String callbackHttpMethod) {
        putProperty("callbackHttpMethod", callbackHttpMethod);
    }

    /**
     * Makes changes of the application.
     *
     * @throws IOException
     */
    public void commit() throws IOException {
        Map<String, Object> params = toMap();
        params.remove("id");

        client.post(getUri(), params);
    }

    /**
     * Permanently deletes application.
     *
     * @throws IOException
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
