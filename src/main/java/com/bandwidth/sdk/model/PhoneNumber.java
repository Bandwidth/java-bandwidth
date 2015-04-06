package com.bandwidth.sdk.model;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import com.bandwidth.sdk.BandwidthClient;
import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.RestResponse;

/**
 * Information about your phone number.
 *
 * @author vpotapenko
 */
public class PhoneNumber extends ResourceBase {

    
    public PhoneNumber(final BandwidthClient client, final JSONObject jsonObject){
        super(client, jsonObject);

    }
    
    @Override
    protected void setUp(final JSONObject jsonObject) {
        this.id = (String) jsonObject.get("id");
        updateProperties(jsonObject);
    }    
    
    
	/**
	 * Factory method for PhoneNumber. Returns PhoneNumber object
	 * @param phoneNumberId the phone number id
	 * @return the PhoneNumber
	 * @throws IOException unexpected error.
	 */
    public static PhoneNumber get(final String phoneNumberId) throws Exception {
        assert(phoneNumberId != null);

        return get(BandwidthClient.getInstance(), phoneNumberId);
    }
	
	/**
	 * Factory method for PhoneNumber. Returns PhoneNumber object
	 * @param client the client
	 * @param phoneNumberId the phone number id
	 * @return the PhoneNumber
     * @throws IOException unexpected error.
	 */
    public static PhoneNumber get(final BandwidthClient client, final String phoneNumberId) throws Exception {
        assert(client != null && phoneNumberId != null);
        final String phoneNumberUri = client.getUserResourceInstanceUri(BandwidthConstants.PHONE_NUMBER_URI_PATH, phoneNumberId);
        final JSONObject phoneNumberObj = toJSONObject(client.get(phoneNumberUri, null));
        final PhoneNumber number = new PhoneNumber(client, phoneNumberObj);
        return number;
    }
        
    /**
     * Factory method to allocate a phone number given a set of params. Note that this assumes that the phone number
     * has been previously search for as an AvailableNumber
     * @param params the params
     * @return the PhoneNumber
     * @throws IOException unexpected error.
     */
    public static PhoneNumber create(final Map<String, Object> params) throws Exception {
        return create(BandwidthClient.getInstance(), params);
    }
    
    /**
     * Factory method to allocate a phone number given a set of params. Note that this assumes that the phone number
     * has been previously search for as an AvailableNumber
     * @param client the client
     * @param params the params
     * @return the PhoneNumber
     * @throws IOException unexpected error.
     */
    public static PhoneNumber create(final BandwidthClient client, final Map<String, Object> params) throws Exception {
        final String uri = client.getUserResourceUri(BandwidthConstants.PHONE_NUMBER_URI_PATH);
        final RestResponse createResponse = client.post(uri, params);
        final RestResponse getResponse = client.get(createResponse.getLocation(), null);
        final JSONObject jsonObject = toJSONObject(getResponse);
        return new PhoneNumber(client, jsonObject);
    }
    
    
    /**
     * Factory method for PhoneNumber list, returns list of PhoneNumber objects with default page setting
     * @return the PhoneNumber list.
     * @throws IOException unexpected error.
     */
    public static ResourceList<PhoneNumber> list() throws IOException {
    	// default page size is 25
     	return list(0, 25);
    }
    
    /**
     * Factory method for PhoneNumber list, returns list of PhoneNumber objects with page, size preferences
     * @param page the current page
     * @param size the page size
     * @return the PhoneNumber list
     * @throws IOException unexpected error.
     */
    public static ResourceList<PhoneNumber> list(final int page, final int size) throws IOException {
        return list(BandwidthClient.getInstance(), page, size);
    }
    
    /**
     * Factory method for PhoneNumber list, returns list of PhoneNumber objects with page, size preferences
     * @param client the client
     * @param page the current page
     * @param size the page size
     * @return the PhoneNumber list
     * @throws IOException unexpected error.
     */
    public static ResourceList<PhoneNumber> list(final BandwidthClient client, final int page, final int size) throws IOException {
    	
        final String resourceUri = client.getUserResourceUri(BandwidthConstants.PHONE_NUMBER_URI_PATH);
        final ResourceList<PhoneNumber> phoneNumbers = 
        			new ResourceList<PhoneNumber>(page, size, resourceUri, PhoneNumber.class);

        phoneNumbers.setClient(client);
        phoneNumbers.initialize();
        
        return phoneNumbers;
    }
    
    /**
     * 
     * @return the URI.
     */
    public String getUri(){
        return client.getUserResourceInstanceUri(BandwidthConstants.PHONE_NUMBER_URI_PATH, getId());
    }


    /**
     * Makes changes to a number you have.
     *
     * @throws IOException unexpected error.
     */
    public void commit() throws Exception {
        final Map<String, Object> params = new HashMap<String, Object>();

        final String applicationId = getPropertyAsString("applicationId");
        if (applicationId != null) params.put("applicationId", applicationId);

        final String name = getName();
        if (name != null) params.put("name", name);

        final String fallbackNumber = getFallbackNumber();
        if (fallbackNumber != null) params.put("fallbackNumber", fallbackNumber);

        final String uri = getUri();
        client.post(uri, params);

        final JSONObject object = toJSONObject(client.get(uri, null));
        updateProperties(object);
    }

    /**
     * Removes a number from your account so you can no longer make or receive calls,
     * or send or receive messages with it. When you remove a number from your account,
     * it will not immediately become available for re-use, so be careful.
     *
     * @throws IOException unexpected error.
     */
    public void delete() throws IOException {
        client.delete(getUri());
    }

    /**
     * 
     * @return the application.
     */
    public String getApplication() {
        return getPropertyAsString("application");
    }

    /**
     * 
     * @return the number.
     */
    public String getNumber() {
        return getPropertyAsString("number");
    }

    /**
     * 
     * @return the national number
     */
    public String getNationalNumber() {
        return getPropertyAsString("nationalNumber");
    }

    /**
     * 
     * @return the name
     */
    public String getName() {
        return getPropertyAsString("name");
    }

    /**
     * 
     * @return the city
     */
    public String getCity() {
        return getPropertyAsString("city");
    }

    /**
     * 
     * @return the state
     */
    public String getState() {
        return getPropertyAsString("state");
    }

    /**
     * 
     * @return the number state
     */
    public String getNumberState() {
        return getPropertyAsString("numberState");
    }

    /**
     * 
     * @return the fallback number
     */
    public String getFallbackNumber() {
        return getPropertyAsString("fallbackNumber");
    }

    /**
     * 
     * @return the price
     */
    public Double getPrice() {
        return getPropertyAsDouble("price");
    }

    /**
     * 
     * @return the creation time
     */
    public Date getCreatedTime() {
        return getPropertyAsDate("createdTime");
    }

    /**
     * 
     * @param applicationId the application id
     */
    public void setApplicationId(final String applicationId) {
        putProperty("applicationId", applicationId);
    }
    
    /**
     * 
     * @param name the name
     */
    public void setName(final String name) {
        putProperty("name", name);
    }

    /**
     * 
     * @param fallbackNumber the fallback number
     */
    public void setFallbackNumber(final String fallbackNumber) {
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
