package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.BandwidthClient;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Information about your phone number.
 *
 * @author vpotapenko
 */
public class PhoneNumber extends ResourceBase {

	/**
	 * Factory method for PhoneNumber. Returns PhoneNumber object
	 * @param client
	 * @param phoneNumberId
	 * @return
	 * @throws IOException
	 */
    public static PhoneNumber get(String phoneNumberId) throws Exception {
        assert(phoneNumberId != null);

        return get(BandwidthClient.getInstance(), phoneNumberId);
    }
	
	/**
	 * Factory method for PhoneNumber. Returns PhoneNumber object
	 * @param client
	 * @param phoneNumberId
	 * @return
	 * @throws IOException
	 */
    public static PhoneNumber get(BandwidthClient client, String phoneNumberId) throws Exception {
        assert(client != null && phoneNumberId != null);
        String phoneNumberUri = client.getUserResourceInstanceUri(BandwidthConstants.PHONE_NUMBER_URI_PATH, phoneNumberId);
        JSONObject phoneNumberObj = toJSONObject(client.get(phoneNumberUri, null));
        PhoneNumber number = new PhoneNumber(client, phoneNumberObj);
        return number;
    }
        
    /**
     * Factory method to allocate a phone number given a set of params. Note that this assumes that the phone number
     * has been previously search for as an AvailableNumber
     * @param params
     * @return
     * @throws IOException
     */
    public static PhoneNumber create(Map<String, Object> params) throws Exception {
        return create(BandwidthClient.getInstance(), params);
    }
    
    /**
     * Factory method to allocate a phone number given a set of params. Note that this assumes that the phone number
     * has been previously search for as an AvailableNumber
     * @param params
     * @return
     * @throws IOException
     */
    public static PhoneNumber create(BandwidthClient client, Map<String, Object> params) throws Exception {
        String uri = client.getUserResourceUri(BandwidthConstants.PHONE_NUMBER_URI_PATH);
        JSONObject jsonObject = toJSONObject(client.post(uri, params));
        return new PhoneNumber(client, jsonObject);
    }
    
    
    /**
     * Factory method for PhoneNumber list, returns list of PhoneNumber objects with default page setting
     * @return
     * @throws IOException
     */
    public static ResourceList<PhoneNumber> list() throws IOException {
    	
    	// default page size is 25
     	return list(0, 25);
    }
    
    /**
     * Factory method for PhoneNumber list, returns list of PhoneNumber objects with page, size preferences
     * @param page
     * @param size
     * @return
     * @throws IOException
     */
    public static ResourceList<PhoneNumber> list(int page, int size) throws IOException {
    	
        
        return list(BandwidthClient.getInstance(), page, size);
    }
    
    /**
     * Factory method for PhoneNumber list, returns list of PhoneNumber objects with page, size preferences
     * @param page
     * @param size
     * @return
     * @throws IOException
     */
    public static ResourceList<PhoneNumber> list(BandwidthClient client, int page, int size) throws IOException {
    	
        String resourceUri = client.getUserResourceUri(BandwidthConstants.PHONE_NUMBER_URI_PATH);

        ResourceList<PhoneNumber> phoneNumbers = 
        			new ResourceList<PhoneNumber>(page, size, resourceUri, PhoneNumber.class);

        phoneNumbers.setClient(client);
        phoneNumbers.initialize();
        
        return phoneNumbers;
    }
    
    

    public PhoneNumber(BandwidthClient client, JSONObject jsonObject){
        super(client, jsonObject);

    }
    
    @Override
    protected void setUp(JSONObject jsonObject) {
        this.id = (String) jsonObject.get("id");
        updateProperties(jsonObject);
    }      
    

    public String getUri(){
        return client.getUserResourceInstanceUri(BandwidthConstants.PHONE_NUMBER_URI_PATH, getId());
    }


    /**
     * Makes changes to a number you have.
     *
     * @throws IOException
     */
    public void commit() throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();

        String applicationId = getPropertyAsString("applicationId");
        if (applicationId != null) params.put("applicationId", applicationId);

        String name = getName();
        if (name != null) params.put("name", name);

        String fallbackNumber = getFallbackNumber();
        if (fallbackNumber != null) params.put("fallbackNumber", fallbackNumber);

        String uri = getUri();
        client.post(uri, params);

        JSONObject object = toJSONObject(client.get(uri, null));
        updateProperties(object);
    }

    /**
     * Removes a number from your account so you can no longer make or receive calls,
     * or send or receive messages with it. When you remove a number from your account,
     * it will not immediately become available for re-use, so be careful.
     *
     * @throws IOException
     */
    public void delete() throws IOException {
        client.delete(getUri());
    }

    public String getApplication() {
        return getPropertyAsString("application");
    }

    public String getNumber() {
        return getPropertyAsString("number");
    }

    public String getNationalNumber() {
        return getPropertyAsString("nationalNumber");
    }

    public String getName() {
        return getPropertyAsString("name");
    }

    public String getCity() {
        return getPropertyAsString("city");
    }

    public String getState() {
        return getPropertyAsString("state");
    }

    public String getNumberState() {
        return getPropertyAsString("numberState");
    }

    public String getFallbackNumber() {
        return getPropertyAsString("fallbackNumber");
    }

    public Double getPrice() {
        return getPropertyAsDouble("price");
    }

    public Date getCreatedTime() {
        return getPropertyAsDate("createdTime");
    }

    public void setApplicationId(String applicationId) {
        putProperty("applicationId", applicationId);
    }

    public void setName(String name) {
        putProperty("name", name);
    }

    public void setFallbackNumber(String fallbackNumber) {
        putProperty("fallbackNumber", fallbackNumber);
    }

    @Override
    public String toString() {
        return "PhoneNumber{" +
                "id='" + getId() + '\'' +
                ", application='" + getApplication() + '\'' +
                ", number='" + getNumber() + '\'' +
                ", nationalNumber='" + getNationalNumber() + '\'' +
                ", name='" + getName() + '\'' +
                ", city='" + getCity() + '\'' +
                ", state='" + getState() + '\'' +
                ", numberState='" + getNumberState() + '\'' +
                ", fallbackNumber='" + getFallbackNumber() + '\'' +
                ", price=" + getPrice() +
                ", createdTime=" + getCreatedTime() +
                '}';
    }
}
