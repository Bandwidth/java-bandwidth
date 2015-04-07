package com.bandwidth.sdk.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.bandwidth.sdk.AppPlatformException;
import com.bandwidth.sdk.BandwidthClient;
import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.RestResponse;

/**
 * Information about endpoint.
 * @author lvivo
 */
public class Endpoint extends ResourceBase {

    public Endpoint(final BandwidthClient client, final JSONObject jsonObject) {
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
     * Convenience factory method for Endpoint, returns a created Endpoint object from a name
     * @param client the bandwidth client configuration.
     * @param domainId the domain id.
     * @param name the endpoint name.
     * @param password the endpoint password
     * @return the created endpoint.
     * @throws AppPlatformException API Exception
     * @throws ParseException Error parsing data
     * @throws Exception error
     */
    public static Endpoint create(final BandwidthClient client, final String domainId, final String name, 
            final String password) throws AppPlatformException, ParseException, Exception {
        return create(client, domainId, name, password, null);
    }
    
    /**
     * Convenience factory method for Endpoint, returns a created Endpoint object from a name
     * @param client the bandwidth client configuration.
     * @param domainId the domain id.
     * @param name the endpoint name.
     * @param password the endpoint password
     * @param description the endpoint description.
     * @return the created endpoint.
     * @throws AppPlatformException API Exception
     * @throws ParseException Error parsing data
     * @throws Exception error
     */
    public static Endpoint create(final BandwidthClient client, final String domainId, final String name, 
            final String password, final String description) throws AppPlatformException, ParseException, Exception {
        return create(client, domainId, name, password, description, true);
    }
    
    /**
     * Convenience factory method for Endpoint, returns a created Endpoint object from a name
     * @param client the bandwidth client configuration.
     * @param domainId the domain id.
     * @param name the endpoint name.
     * @param password the endpoint password
     * @param enabled indicates if the endpoint is active or not.
     * @param description the endpoint description.
     * @return the created endpoint.
     * @throws AppPlatformException API Exception
     * @throws ParseException Error parsing data
     * @throws Exception error
     */
    public static Endpoint create(final BandwidthClient client, final String domainId, final String name, final String password, 
            final String description, final boolean enabled) throws AppPlatformException, ParseException, Exception {
        return create(client, domainId, name, password, description, enabled, null);
    }
    
    
    
    
    
    /**
     * Convenience factory method for Endpoint, returns a created Endpoint object from a name
     * @param domainId the domain id.
     * @param name the endpoint name.
     * @param password the endpoint password
     * @return the created endpoint.
     * @throws AppPlatformException API Exception
     * @throws ParseException Error parsing data
     * @throws Exception error
     */
    public static Endpoint create(final String domainId, final String name, final String password) 
            throws AppPlatformException, ParseException, Exception {
        return create(domainId, name, password, true);
    }
    
    /**
     * Convenience factory method for Endpoint, returns a created Endpoint object from a name
     * @param domainId the domain id.
     * @param name the endpoint name.
     * @param password the endpoint password
     * @param enabled indicates if the endpoint is active or not
     * @return the created endpoint.
     * @throws AppPlatformException API Exception
     * @throws ParseException Error parsing data
     * @throws Exception error
     */
    public static Endpoint create(final String domainId, final String name, final String password, 
            final boolean enabled) throws AppPlatformException, ParseException, Exception {
        return create(domainId, name, password, null, enabled);
    }
    
    /**
     * Convenience factory method for Endpoint, returns a created Endpoint object from a name
     * @param domainId the domain id.
     * @param name the endpoint name.
     * @param password the endpoint password
     * @param description the endpoint description.
     * @return the created endpoint.
     * @throws AppPlatformException API Exception
     * @throws ParseException Error parsing data
     * @throws Exception error
     */
    public static Endpoint create(final String domainId, final String name, final String password, 
            final String description) throws AppPlatformException, ParseException, Exception {
        return create(domainId, name, password, true, description);
    }
    
    /**
     * Convenience factory method for Endpoint, returns a created Endpoint object from a name
     * @param domainId the domain id.
     * @param name the endpoint name.
     * @param password the endpoint password
     * @param enabled indicates if the endpoint is active or not
     * @param description the endpoint description.
     * @return the created endpoint.
     * @throws AppPlatformException API Exception
     * @throws ParseException Error parsing data
     * @throws Exception error
     */
    public static Endpoint create(final String domainId, final String name, final String password, 
            final String description, final boolean enabled) throws AppPlatformException, ParseException, Exception {
        return create(domainId, name, password, true, description);
    }
    
    
    
    /**
     * Convenience factory method for Endpoint, returns a created Endpoint object from a name
     * @param domainId the domain id.
     * @param name the endpoint name.
     * @param password the endpoint password
     * @param enabled indicates if the endpoint is active or not.
     * @param description the endpoint description.
     * @return the created endpoint.
     * @throws AppPlatformException API Exception
     * @throws ParseException Error parsing data
     * @throws Exception error
     */
    public static Endpoint create(final String domainId, final String name, final String password, final boolean enabled, 
            final String description) throws AppPlatformException, ParseException, Exception {
        final BandwidthClient client = BandwidthClient.getInstance();
        return create(client, domainId, name, password, description, enabled, null);
    }
    
    /**
     * Convenience factory method for Endpoint, returns a created Endpoint object from a name
     * @param client the bandwidth client configuration.
     * @param domainId the domain id.
     * @param name the endpoint name.
     * @param password the endpoint password
     * @param enabled indicates if the endpoint is active or not
     * @param description the endpoint description.
     * @param applicationId the applicationId.
     * @return the created endpoint.
     * @throws AppPlatformException API Exception
     * @throws ParseException Error parsing data
     * @throws Exception error
     */
    public static Endpoint create(final BandwidthClient client, final String domainId, final String name, final String password, 
            final String description, final boolean enabled, final String applicationId) throws AppPlatformException, ParseException, Exception {
        assert (domainId != null);
        assert (name != null);
        assert (password != null);
        
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", name);
        params.put("enabled", Boolean.toString(enabled));
        final Map<String, String> credentials = new HashMap<String, String>();
        credentials.put("password", password);
        params.put("credentials", credentials);
        if(description != null) {
            params.put("description", description);
        }
        if(applicationId != null) {
            params.put("applicationId", applicationId);
        }
        return create(client, domainId, params);
    }
    
    /**
     * Convenience factory method to create a Endpoint object from a map of parameters
     * @param client the bandwidth client configuration.
     * @param domainId the domain id.
     * @param params the request parameters.
     * @return the created endpoint.
     * @throws AppPlatformException API Exception
     * @throws ParseException Error parsing data
     * @throws Exception error
     */
    public static Endpoint create(final BandwidthClient client, final String domainId, final Map<String, Object>params) throws AppPlatformException, ParseException, Exception {
        assert (client!= null && params != null);
        final String endpointsUri = String.format(client.getUserResourceUri(BandwidthConstants.ENDPOINTS_URI_PATH), domainId);
        final RestResponse response = client.post(endpointsUri, params);
        if (response.getStatus() > 400) {
            throw new AppPlatformException(response.getResponseText());
        }
        final JSONObject callObj = toJSONObject(client.get(response.getLocation(), null));
        return new Endpoint(client, callObj);
    }
    
    /**
     * Convenience method to get information about a specific Endpoint. Returns a Endpoint object given its id.
     * @param domainId the domain id.
     * @param endpointId the Endpoint id.
     * @return information about a specific Endpoint
     * @throws ParseException Error parsing data
     * @throws Exception error
     */
    public static Endpoint get(final String domainId, final String endpointId) throws ParseException, Exception {
        final BandwidthClient client = BandwidthClient.getInstance();
        assert(domainId != null);
        assert(endpointId != null);
        final String endpointsUri =  String.format(client.getUserResourceUri(BandwidthConstants.ENDPOINTS_URI_PATH), domainId)+ "/"+endpointId;
        final JSONObject jsonObject = toJSONObject(client.get(endpointsUri, null));
        return new Endpoint(client, jsonObject);
    }
    
    /**
     * Factory method for Endpoint list
     * @param domainId the domain id.
     * @return the Endpoint list.
     */
    public static ResourceList<Endpoint> list(final String domainId) {
        return list(domainId, 0, 20);
    }
    
    /**
     * Factory method for Endpoint list
     * @param domainId the domain id.
     * @param page the starting page.
     * @param size the page size.
     * @return the Endpoint list.
     */
    public static ResourceList<Endpoint> list(final String domainId, final int page, final int size) {
        assert(domainId != null);
        assert(page  >= 0);
        assert(size > 0);
        final BandwidthClient client = BandwidthClient.getInstance();
        final String resourceUri = String.format(client.getUserResourceUri(BandwidthConstants.ENDPOINTS_URI_PATH), domainId);
        final ResourceList<Endpoint> endpoints = new ResourceList<Endpoint>(page, size, resourceUri, Endpoint.class);
        endpoints.setClient(client);
        endpoints.initialize();
        return endpoints;
    }
    
    /**
     * Convenience method to update information about a specific Endpoint
     *
     * @param domainId the domain id.
     * @param endpointId the endpoint id.
     * @param endpointPassword the endpoint password.
     * @return information about a specific Endpoint.
     * @throws AppPlatformException API Exception
     * @throws ParseException Error parsing data
     * @throws Exception error
     */
    public static Endpoint update(final String domainId, final String endpointId, final String endpointPassword) 
            throws AppPlatformException, ParseException, Exception {
        return update(domainId, endpointId, endpointPassword, null, false);
    }
    
    /**
     * Convenience method to update information about a specific Endpoint
     *
     * @param domainId the domain id.
     * @param endpointId the endpoint id.
     * @param endpointPassword the endpoint password.
     * @param enabled indicates if the endpoint is active or not.
     * @return information about a specific Endpoint.
     * @throws AppPlatformException API Exception
     * @throws ParseException Error parsing data
     * @throws Exception error
     */
    public static Endpoint update(final String domainId, final String endpointId, final String endpointPassword, 
            final boolean enabled) throws AppPlatformException, ParseException, Exception {
        return update(domainId, endpointId, endpointPassword, null, enabled);
    }
    
    /**
     * Convenience method to update information about a specific Endpoint
     *
     * @param domainId the domain id.
     * @param endpointId the endpoint id.
     * @param endpointPassword the endpoint password.
     * @param applicationId the application id.
     * @param enabled indicates if the endpoint is active or not.
     * @return information about a specific Endpoint.
     * @throws AppPlatformException API Exception
     * @throws ParseException Error parsing data
     * @throws Exception error
     */
    public static Endpoint update(final String domainId, final String endpointId, final String endpointPassword, 
            final String applicationId, final boolean enabled) throws AppPlatformException, ParseException, Exception {
        return update(domainId, endpointId, endpointPassword, applicationId, null, enabled);
    }
    
    /**
     * Convenience method to update information about a specific Endpoint
     *
     * @param domainId the domain id.
     * @param endpointId the endpoint id.
     * @param password the endpoint password.
     * @param applicationId the application id.
     * @param description the endpoint description.
     * @param enabled indicates if the endpoint is active or not.
     * @return information about a specific Endpoint.
     * @throws AppPlatformException API Exception
     * @throws ParseException Error parsing data
     * @throws Exception error
     */
    public static Endpoint update(final String domainId, final String endpointId, final String password, 
            final String applicationId, final String description, final boolean enabled) throws AppPlatformException, ParseException, Exception {
        final BandwidthClient client = BandwidthClient.getInstance();
        assert(domainId != null);
        assert(endpointId != null);
        assert(password != null);
        
        final Map<String, Object> params = new HashMap<String, Object>();
        
        final Map<String, String> credentials = new HashMap<String, String>();
        credentials.put("password", password);
        params.put("credentials", credentials);
        
        if(applicationId != null) {
            params.put("applicationId", applicationId);
        }
        if(description != null) {
            params.put("description", description);
        }
        params.put("enabled", Boolean.toString(enabled));
        
        final String domainsUri =  String.format(client.getUserResourceUri(BandwidthConstants.ENDPOINTS_URI_PATH), domainId)+ "/"+endpointId;
        final RestResponse response = client.post(domainsUri, params);
        if (response.getStatus() > 400) {
            throw new AppPlatformException(response.getResponseText());
        }
        final JSONObject jsonObject = toJSONObject(client.get(domainsUri, null));
        return new Endpoint(client, jsonObject);
    }
    
    /**
     * Permanently deletes the Endpoint.
     * @param domainId the domain id.
     * @param endpointId the Endpoint id.
     * @throws AppPlatformException API Exception
     * @throws IOException unexpected error
     */
    public static void delete(final String domainId, final String endpointId) throws AppPlatformException, IOException {
        assert(endpointId != null);
        final BandwidthClient client = BandwidthClient.getInstance();
        final RestResponse response = client.delete(String.format(client.getUserResourceUri(BandwidthConstants.ENDPOINTS_URI_PATH), domainId)+ "/"+endpointId);
        if (response.getStatus() > 400) {
            throw new AppPlatformException(response.getResponseText());
        }
    }
    
    /**
     * Permanently deletes the Endpoint.
     * @param client the bandwidth client configuration.
     * @param domainId the domain id.
     * @param endpointId the Endpoint id.
     * @throws AppPlatformException API Exception
     * @throws IOException unexpected error
     */
    public static void delete(final BandwidthClient client, final String domainId, final String endpointId) throws AppPlatformException, IOException {
        assert(endpointId != null);
        final RestResponse response = client.delete(String.format(client.getUserResourceUri(BandwidthConstants.ENDPOINTS_URI_PATH), domainId)+ "/"+endpointId);
        if (response.getStatus() > 400) {
            throw new AppPlatformException(response.getResponseText());
        }
    }
    
    public String getName() {
        return getPropertyAsString("name");
    }
    
    public String getDomainId() {
        return getPropertyAsString("domainId");
    }
    
    public boolean isEnabled() {
        return getPropertyAsBoolean("enabled");
    }
    
    public String getSipUri() {
        return getPropertyAsString("sipUri");
    }
    
    
    public String getDescription() {
        return getPropertyAsString("description");
    }
    
    public Credentials getCredentials() {
        try {
            return new ObjectMapper().readValue(getProperty("credentials").toString(), Credentials.class);
        } catch (IOException e) {
            return null;
        }
    }
    
    @Override
    public String toString() {
        return "Endpoint{" +
                "id='" + getId() + '\'' +
                ", name=" + getName() +
                ", domainId='" + getDomainId() + '\'' +
                ", enabled='" + isEnabled() + '\'' +
                ", sipUri='" + getSipUri() + '\'' +
                ", credentials= {" + getCredentials() + '}' +
                "}";
    }
}