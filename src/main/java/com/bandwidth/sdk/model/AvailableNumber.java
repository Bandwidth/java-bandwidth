package com.bandwidth.sdk.model;

import java.io.IOException;

import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.BandwidthRestClient;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Available number information.
 *
 * @author vpotapenko
 */
public class AvailableNumber extends BaseModelObject {

	/**
	 * Factory method for AvailableNumber list, returns list of AvailableNumber objects with default page, size
	 * @return
	 * @throws IOException
	 */
    public static ResourceList<AvailableNumber> getAvailableNumbers() throws IOException {
    	
    	// default page size is 25
     	return getAvailableNumbers(0, 25);
    }
    
    /**
     * Factory method for AvailableNumber list, returns list of AvailableNumber objects with page, size preferences
     * @param page
     * @param size
     * @return
     * @throws IOException
     */
    public static ResourceList<AvailableNumber> getAvailableNumbers(int page, int size) throws IOException {
    	
        String availableNumbersUri = BandwidthRestClient.getInstance().getUserResourceUri(BandwidthConstants.AVAILABLE_NUMBERS_URI_PATH);

        // TODO add new ResourceList ctor to allow arbitrary params
        ResourceList<AvailableNumber> availableNumbers = 
        			new ResourceList<AvailableNumber>(page, size, availableNumbersUri, AvailableNumber.class);

        availableNumbers.initialize();
        
        return availableNumbers;
    }
    
    /**
     * Convenience factory method to return tollfree numbers based on the given search criteria
     * @param params
     * @return
     * @throws IOException
     */
    public static List<AvailableNumber> searchTollFree(Map<String, Object>params) throws IOException {
    	return searchTollFree(BandwidthRestClient.getInstance(), params);
    }
    
    /**
     * Convenience factory method to return tollfree numbers based on the given search criteria for a given client
     * @param client
     * @param params
     * @return
     * @throws IOException
     */
    public static List<AvailableNumber> searchTollFree(BandwidthRestClient client, Map<String, Object>params) 
    																							throws IOException {
    	
        String tollFreeUri = client.getUserResourceUri(BandwidthConstants.AVAILABLE_NUMBERS_TOLL_FREE_URI_PATH);
        JSONArray array = client.getArray(tollFreeUri, params);

        List<AvailableNumber> numbers = new ArrayList<AvailableNumber>();
        for (Object obj : array) {
            numbers.add(new AvailableNumber(client, (JSONObject) obj));
        }
        return numbers;
    }

    /**
     * Convenience factory method to return local numbers based on a given search criteria
     * @param params
     * @return
     * @throws IOException
     */
    public static List<AvailableNumber> searchLocal(Map<String, Object>params) throws IOException {
    	return searchTollFree(BandwidthRestClient.getInstance(), params);
    }
    
    /**
     * Convenience factory method to return local numbers based on a given search criteria for a given client 
     * @param client
     * @param params
     * @return
     * @throws IOException
     */
    public static List<AvailableNumber> searchLocal(BandwidthRestClient client, Map<String, Object>params) 
    																							throws IOException {
    	
        String tollFreeUri = client.getUserResourceUri(BandwidthConstants.AVAILABLE_NUMBERS_LOCAL_URI_PATH);
        JSONArray array = client.getArray(tollFreeUri, params);

        List<AvailableNumber> numbers = new ArrayList<AvailableNumber>();
        for (Object obj : array) {
            numbers.add(new AvailableNumber(client, (JSONObject) obj));
        }
        return numbers;
    }
    
	
    public AvailableNumber(BandwidthRestClient client, JSONObject jsonObject) {
        super(client, jsonObject);
    }

    @Override
    protected String getUri() {
        return client.getBaseResourceUri(BandwidthConstants.AVAILABLE_NUMBERS_URI_PATH);
    }

    public String getNumber() {
        return getPropertyAsString("number");
    }

    public String getNationalNumber() {
        return getPropertyAsString("nationalNumber");
    }

    public String getPatternMatch() {
        return getPropertyAsString("patternMatch");
    }

    public String getCity() {
        return getPropertyAsString("city");
    }

    public String getLata() {
        return getPropertyAsString("lata");
    }

    public String getRateCenter() {
        return getPropertyAsString("rateCenter");
    }

    public String getState() {
        return getPropertyAsString("state");
    }

    public Double getPrice() {
        return getPropertyAsDouble("price");
    }

    @Override
    public String toString() {
        return "Number{" +
                "number='" + getNumber() + '\'' +
                ", nationalNumber='" + getNationalNumber() + '\'' +
                ", patternMatch='" + getPatternMatch() + '\'' +
                ", city='" + getCity() + '\'' +
                ", lata='" + getLata() + '\'' +
                ", rateCenter='" + getRateCenter() + '\'' +
                ", state='" + getState() + '\'' +
                ", price=" + getPrice() +
                '}';
    }
}
