package com.bandwidth.sdk.model;

import java.io.IOException;

import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.BandwidthClient;

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
public class AvailableNumber extends ResourceBase {

	/**
	 * Factory method for AvailableNumber list, returns list of AvailableNumber objects with default page, size
	 * @return
	 * @throws IOException
	 */
    public static ResourceList<AvailableNumber> list() throws IOException {
    	
    	// default page size is 25
     	return list(0, 25);
    }
    
    /**
     * Factory method for AvailableNumber list, returns list of AvailableNumber objects with page, size preferences
     * @param page
     * @param size
     * @return
     * @throws IOException
     */
    public static ResourceList<AvailableNumber> list(int page, int size) throws IOException {
    	        
        return list(BandwidthClient.getInstance(), page, size);
    }
    
    /**
     * Factory method for AvailableNumber list, returns list of AvailableNumber objects with page, 
     * 		size preferences, with a given client
     * @param page
     * @param size
     * @return
     * @throws IOException
     */
    public static ResourceList<AvailableNumber> list(BandwidthClient client, int page, int size) throws IOException {
    	
        String availableNumbersUri = client.getUserResourceUri(BandwidthConstants.AVAILABLE_NUMBERS_URI_PATH);

        // TODO add new ResourceList ctor to allow arbitrary params
        ResourceList<AvailableNumber> availableNumbers = 
        			new ResourceList<AvailableNumber>(page, size, availableNumbersUri, AvailableNumber.class);
        availableNumbers.setClient(client);
        availableNumbers.initialize();
        
        return availableNumbers;
    }
    
    
    /**
     * Convenience factory method to return tollfree numbers based on the given search criteria
     * @param params
     * @return
     * @throws IOException
     */
    public static List<AvailableNumber> searchTollFree(Map<String, Object>params) throws Exception {
    	return searchTollFree(BandwidthClient.getInstance(), params);
    }
    
    /**
     * Convenience factory method to return tollfree numbers based on the given search criteria for a given client
     * @param client
     * @param params
     * @return
     * @throws IOException
     */
    public static List<AvailableNumber> searchTollFree(BandwidthClient client, Map<String, Object>params) 
    																							throws Exception {
    	
        String tollFreeUri = BandwidthConstants.AVAILABLE_NUMBERS_TOLL_FREE_URI_PATH;
        JSONArray array = toJSONArray(client.get(tollFreeUri, params));

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
    public static List<AvailableNumber> searchLocal(Map<String, Object>params) throws Exception {
    	return searchLocal(BandwidthClient.getInstance(), params);
    }
    
    /**
     * Convenience factory method to return local numbers based on a given search criteria for a given client 
     * @param client
     * @param params
     * @return
     * @throws IOException
     */
    public static List<AvailableNumber> searchLocal(BandwidthClient client, Map<String, Object>params) 
    																							throws Exception {
    	
        String tollFreeUri = BandwidthConstants.AVAILABLE_NUMBERS_LOCAL_URI_PATH;
        JSONArray array = toJSONArray(client.get(tollFreeUri, params));

        List<AvailableNumber> numbers = new ArrayList<AvailableNumber>();
        for (Object obj : array) {
            numbers.add(new AvailableNumber(client, (JSONObject) obj));
        }
        return numbers;
    }
    
	
    public AvailableNumber(BandwidthClient client, JSONObject jsonObject) {
        super(client, jsonObject);
    }

    @Override
    protected void setUp(JSONObject jsonObject) {
        this.id = (String) jsonObject.get("id");
        updateProperties(jsonObject);
    }      
    
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
