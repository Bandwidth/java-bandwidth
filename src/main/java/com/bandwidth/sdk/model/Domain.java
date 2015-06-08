package com.bandwidth.sdk.model;

import com.bandwidth.sdk.AppPlatformException;
import com.bandwidth.sdk.BandwidthClient;
import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.RestResponse;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Information about domain.
 * @author lvivo
 */
public class Domain extends ResourceBase {

    public Domain(final BandwidthClient client, final JSONObject jsonObject) {
        super(client, jsonObject);
    }
    
    @Override
    protected void setUp(final JSONObject jsonObject) {
        this.id = (String) jsonObject.get("id");
        updateProperties(jsonObject);
    }  
    
    public static JSONObject toJSONObject(final RestResponse response) throws ParseException {
        return (JSONObject) new JSONParser().parse(response.getResponseText());
    }
    
    /**
     * Convenience factory method for Domain.
     * @param name the domain name.
     * @return the created Domain.
     * @throws AppPlatformException API Exception
     * @throws ParseException Error parsing data
     * @throws Exception error
     */
    public static Domain create(final String name) throws AppPlatformException, ParseException, Exception {
        return create(name, null);
    }
    
    /**
     * Convenience factory method for Domain.
     * @param name the domain name.
     * @param description the optional domain description.
     * @return the created Domain.
     * @throws AppPlatformException API Exception
     * @throws ParseException Error parsing data
     * @throws Exception error
     */
    public static Domain create(final String name, final String description) throws AppPlatformException, ParseException, Exception {
        return create(BandwidthClient.getInstance(), name, description);
    }
    
    /**
     * Convenience method to create a Domain.
     * @param client the client
     * @param name the domain name.
     * @param description the optional domain description.
     * @return the created domain
     * @throws AppPlatformException API Exception
     * @throws ParseException Error parsing data
     * @throws Exception error
     */
    public static Domain create(final BandwidthClient client, final String name, final String description) throws AppPlatformException, ParseException, Exception {
        assert (name != null);
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", name);
        if(!StringUtils.isBlank(description)) {
            params.put("description", description);
        }
        return create(client, params);
    }
    
    /**
     * Convenience factory method to create a Domain.
     * @param client the client.
     * @param params the request parameters.
     * @return the created domain.
     * @throws AppPlatformException API Exception
     * @throws ParseException Error parsing data
     * @throws Exception error
     */
    public static Domain create(final BandwidthClient client, final Map<String, Object>params) throws AppPlatformException, ParseException, Exception {
        assert (client!= null && params != null);
        final String domainsUri =  client.getUserResourceUri(BandwidthConstants.DOMAINS_URI_PATH);
        final RestResponse response = client.post(domainsUri, params);
        final JSONObject callObj = toJSONObject(client.get(response.getLocation(), null));
        return new Domain(client, callObj);
    }
    
    /**
     * Convenience method to get information about a specific Domain
     * @param id the domain id.
     * @return information about a specific Domain
     * @throws ParseException Error parsing data
     * @throws Exception error
     */
    public static Domain get(final String id) throws ParseException, Exception {
        final BandwidthClient client = BandwidthClient.getInstance();
        return get(client, id); 
    }
    
    /**
     * Convenience method to return a Domain
     * @param client the client.
     * @param id the domain id.
     * @return the domain object.
     * @throws ParseException Error parsing data
     * @throws Exception error
     */
    public static Domain get(final BandwidthClient client, final String id) throws ParseException, Exception {
        assert(client != null);
        final String domainsUri =  client.getUserResourceInstanceUri(BandwidthConstants.DOMAINS_URI_PATH, id);
        final JSONObject jsonObject = toJSONObject(client.get(domainsUri, null));
        return new Domain(client, jsonObject);
    }
    
    /**
     * Factory method to list the Domains.
     * @return the Domain list.
     */
    public static ResourceList<Domain> list() {
        final BandwidthClient client = BandwidthClient.getInstance();
        return list(client, 0, 20);
    }
    
    /**
     * Factory method to list the Domains.
     * @param client the user client.
     * @return the Domain list.
     */
    public static ResourceList<Domain> list(final BandwidthClient client) {
        return list(client, 0, 20);
    }
    
    /**
     * Factory method to list the Domains.
     * @param client The Bandwidth client
     * @param page the starting page.
     * @param size the page size.
     * @return the Domain list.
     */
    public static ResourceList<Domain> list(final BandwidthClient client, final int page, final int size) {
        final String resourceUri = client.getUserResourceUri(BandwidthConstants.DOMAINS_URI_PATH);
        final ResourceList<Domain> domains = new ResourceList<Domain>(resourceUri, Domain.class);
        domains.setClient(client);
        domains.initialize();
        return domains;
    }
    
    /**
     * Convenience method to get information about a specific Domain.
     * @param id the domain id.
     * @return information about a specific Domain.
     * @throws AppPlatformException API Exception
     * @throws ParseException Error parsing data
     * @throws IOException unexpected error
     * @throws Exception error
     */
    public static Domain update(final String id) throws AppPlatformException, ParseException, IOException, Exception {
        return update(id, null); 
    }
    
    /**
     * Convenience method to get information about a specific Domain.
     * @param id the domain id.
     * @param description the description
     * @return information about a specific Domain
     * @throws AppPlatformException API Exception
     * @throws ParseException Error parsing data
     * @throws IOException unexpected error
     * @throws Exception error
     */
    public static Domain update(final String id, final String description) throws AppPlatformException, ParseException, IOException, Exception {
        final BandwidthClient client = BandwidthClient.getInstance();
        final Map<String, Object> params = new HashMap<String, Object>();
        if(description != null) {
            params.put("description", description);
        }
        return update(client, id, params); 
    }
    
    /**
     * Convenience method to get information about a specific Domain.
     * @param client the user client.
     * @param id the domain id.
     * @param description the description
     * @return information about a specific Domain
     * @throws AppPlatformException API Exception
     * @throws ParseException Error parsing data
     * @throws IOException unexpected error
     * @throws Exception error
     */
    public static Domain update(final BandwidthClient client, final String id, final String description) throws AppPlatformException, ParseException, IOException, Exception {
        final Map<String, Object> params = new HashMap<String, Object>();
        if(description != null) {
            params.put("description", description);
        }
        return update(client, id, params); 
    }
    
    /**
     * Convenience method to return a Domain.
     * @param client the client.
     * @param id the domain id.
     * @param params the params
     * @return the domain object.
     * @throws AppPlatformException API Exception
     * @throws ParseException Error parsing data
     * @throws IOException unexpected error
     * @throws Exception error
     */
    public static Domain update(final BandwidthClient client, final String id, final Map<String, Object>params) throws AppPlatformException, ParseException, IOException, Exception {
        assert(client != null && id != null);
        final String domainsUri =  client.getUserResourceInstanceUri(BandwidthConstants.DOMAINS_URI_PATH, id);
        final RestResponse response = client.post(domainsUri, params);
        final JSONObject jsonObject = toJSONObject(client.get(domainsUri, null));
        return new Domain(client, jsonObject);
    }
    
    /**
     * Permanently deletes the Domain.
     * @param id the Domain id.
     * @throws AppPlatformException API Exception
     * @throws IOException unexpected error
     */
    public static void delete(final String id) throws AppPlatformException, IOException {
        assert(id != null);
        final BandwidthClient client = BandwidthClient.getInstance();
        client.delete(client.getUserResourceInstanceUri(BandwidthConstants.DOMAINS_URI_PATH, id));
    }
    
    /**
     * Permanently deletes the Domain.
     * @param client the client
     * @param id the Domain id.
     * @throws AppPlatformException API Exception
     * @throws IOException unexpected error
     */
    public static void delete(final BandwidthClient client, final String id) throws AppPlatformException, IOException {
        assert(id != null);
        client.delete(client.getUserResourceInstanceUri(BandwidthConstants.DOMAINS_URI_PATH, id));
    }
    
    public String getName() {
        return getPropertyAsString("name");
    }
    
    public String getDescription() {
        return getPropertyAsString("description");
    }
    
    public String getEndpointsUrl() {
        return getPropertyAsString("endpointsUrl");
    }
    
    @Override
    public String toString() {
        return "Domain{" +
                "id='" + getId() + '\'' +
                ", name=" + getName() +
                ", description='" + getDescription() + '\'' +
                '}';
    }
}
