package com.bandwidth.sdk.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.bandwidth.sdk.BandwidthClient;
import com.bandwidth.sdk.BandwidthConstants;

/**
 * Available number information.
 *
 * @author vpotapenko
 */
public class AvailableNumber extends ResourceBase {

	/**
	 * Factory method for AvailableNumber list, returns list of AvailableNumber objects with default page, size
	 * @return the list
	 * @throws IOException unexpected error.
	 */
    public static ResourceList<AvailableNumber> list() throws IOException {
    	
    	// default page size is 25
     	return list(0, 25);
    }
    
    /**
     * Factory method for AvailableNumber list, returns list of AvailableNumber objects with page, size preferences
     * @param page the page
     * @param size the page size
     * @return the list
     * @throws IOException unexpected error.
     */
    public static ResourceList<AvailableNumber> list(final int page, final int size) throws IOException {
    	        
        return list(BandwidthClient.getInstance(), page, size);
    }
    
    /**
     * Factory method for AvailableNumber list, returns list of AvailableNumber objects with page, 
     * 		size preferences, with a given client
     * @param client the client
     * @param page the page
     * @param size the page size
     * @return the list
     * @throws IOException unexpected error.
     */
    public static ResourceList<AvailableNumber> list(final BandwidthClient client, final int page, final int size) throws IOException {
    	
        final String availableNumbersUri = client.getUserResourceUri(BandwidthConstants.AVAILABLE_NUMBERS_URI_PATH);

        // TODO add new ResourceList ctor to allow arbitrary params
        final ResourceList<AvailableNumber> availableNumbers = 
        			new ResourceList<AvailableNumber>(page, size, availableNumbersUri, AvailableNumber.class);
        availableNumbers.setClient(client);
        availableNumbers.initialize();
        
        return availableNumbers;
    }
    
    
    /**
     * Convenience factory method to return tollfree numbers based on the given search criteria
     * @param params the params
     * @return the list
     * @throws IOException unexpected error.
     */
    public static List<AvailableNumber> searchTollFree(final Map<String, Object>params) throws Exception {
    	return searchTollFree(BandwidthClient.getInstance(), params);
    }
    
    /**
     * Convenience factory method to return tollfree numbers based on the given search criteria for a given client
     * @param client the client
     * @param params the params
     * @return the list
     * @throws IOException unexpected error.
     */
    public static List<AvailableNumber> searchTollFree(final BandwidthClient client, final Map<String, Object>params) 
    																							throws Exception {
    	
        final String tollFreeUri = BandwidthConstants.AVAILABLE_NUMBERS_TOLL_FREE_URI_PATH;
        final JSONArray array = toJSONArray(client.get(tollFreeUri, params));

        final List<AvailableNumber> numbers = new ArrayList<AvailableNumber>();
        for (final Object obj : array) {
            numbers.add(new AvailableNumber(client, (JSONObject) obj));
        }
        return numbers;
    }

    /**
     * Convenience factory method to return local numbers based on a given search criteria
     * @param params the params
     * @return the list
     * @throws IOException unexpected error.
     */
    public static List<AvailableNumber> searchLocal(final Map<String, Object>params) throws Exception {
    	return searchLocal(BandwidthClient.getInstance(), params);
    }
    
    /**
     * Convenience factory method to return local numbers based on a given search criteria for a given client 
     * @param client the client
     * @param params the params
     * @return the list
     * @throws IOException unexpected error.
     */
    public static List<AvailableNumber> searchLocal(final BandwidthClient client, final Map<String, Object>params) 
    																							throws Exception {
    	
        final String tollFreeUri = BandwidthConstants.AVAILABLE_NUMBERS_LOCAL_URI_PATH;
        final JSONArray array = toJSONArray(client.get(tollFreeUri, params));

        final List<AvailableNumber> numbers = new ArrayList<AvailableNumber>();
        for (final Object obj : array) {
            numbers.add(new AvailableNumber(client, (JSONObject) obj));
        }
        return numbers;
    }
    
	
    public AvailableNumber(final BandwidthClient client, final JSONObject jsonObject) {
        super(client, jsonObject);
    }

    @Override
    protected void setUp(final JSONObject jsonObject) {
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
