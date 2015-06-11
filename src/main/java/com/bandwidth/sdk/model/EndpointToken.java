package com.bandwidth.sdk.model;

import com.bandwidth.sdk.AppPlatformException;
import com.bandwidth.sdk.BandwidthClient;
import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.RestResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Endpoint token model.
 */
public class EndpointToken extends ResourceBase {

    public EndpointToken(final BandwidthClient client, final JSONObject jsonObject) {
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
     * Creates an EndpointToken for the domain/endpoint
     * @param client the bandwidth client.
     * @param domainId the domain id.
     * @param endpointId the endpoint id.
     * @return the created token.
     * @throws AppPlatformException API Exception
     * @throws ParseException Error parsing data
     * @throws IOException error
     */
    public static EndpointToken create(final BandwidthClient client, final String domainId, final String endpointId) throws AppPlatformException, ParseException, IOException {
        assert (client != null && domainId != null && endpointId != null);
        return createToken(client, domainId, endpointId, null);
    }

    /**
     * Creates an EndpointToken for the domain/endpoint
     * @param domainId the domain id.
     * @param endpointId the endpoint id.
     * @return the created token.
     * @throws AppPlatformException API Exception
     * @throws ParseException Error parsing data
     * @throws IOException error
     */
    public static EndpointToken create(final String domainId, final String endpointId) throws AppPlatformException, ParseException, IOException {
        assert (domainId != null && endpointId != null);
        return createToken(null, domainId, endpointId, null);
    }

    /**
     * Creates an EndpointToken for the domain/endpoint
     * @param client the bandwidth client.
     * @param domainId the domain id.
     * @param endpointId the endpoint id.
     * @param expires expiration in ms > 0.
     * @return the created token.
     * @throws AppPlatformException API Exception
     * @throws ParseException Error parsing data
     * @throws IOException error
     */
    public static EndpointToken create(final BandwidthClient client, final String domainId, final String endpointId, final Long expires) throws AppPlatformException, ParseException, IOException {
        assert (client!= null && domainId != null && endpointId != null && expires != null && expires > 0);
        return createToken(client, domainId, endpointId, expires);
    }

    /**
     * Creates an EndpointToken for the domain/endpoint
     * @param domainId the domain id.
     * @param endpointId the endpoint id.
     * @param expires expiration in ms > 0.
     * @return the created token.
     * @throws AppPlatformException API Exception
     * @throws ParseException Error parsing data
     * @throws IOException error
     */
    public static EndpointToken create(final String domainId, final String endpointId, final Long expires) throws AppPlatformException, ParseException, IOException {
        assert (domainId != null && endpointId != null && expires != null && expires > 0);
        return createToken(null, domainId, endpointId, expires);
    }

    private static EndpointToken createToken(final BandwidthClient client, final String domainId, final String endpointId, final Long expires) throws AppPlatformException, ParseException, IOException {
        BandwidthClient bandwidthClient=client;
        if (bandwidthClient == null) {
            bandwidthClient = BandwidthClient.getInstance();
        }

        RestResponse response=null;
        final String endpointTokenUri = String.format(bandwidthClient.getUserResourceUri(BandwidthConstants.ENDPOINTS_TOKEN), domainId, endpointId);
        if (expires == null) {
            // API gets empty string instead of {} in the case
            // of no parameters
            response = bandwidthClient.postPlainJson(endpointTokenUri, "");
        } else {
            final Map<String, Object> params = new HashMap<String, Object>();
            params.put("expires", expires);
            response = bandwidthClient.post(endpointTokenUri, params);
        }

        final JSONObject callObj = toJSONObject(response);
        return new EndpointToken(bandwidthClient, callObj);
    }

    /**
     * Permanently deletes the Endpoint token.
     * @param client the bandwidth client.
     * @param domainId the domain id.
     * @param endpointId the Endpoint id.
     * @param token
     * @throws AppPlatformException API Exception
     * @throws IOException unexpected error
     */
    public static void delete(final BandwidthClient client, final String domainId, final String endpointId, final String token) throws AppPlatformException, ParseException, IOException {
        assert(client != null && domainId != null && endpointId != null && endpointId != null);
        deleteToken(client, domainId, endpointId, token);
    }

    /**
     * Permanently deletes the Endpoint token.
     * @param domainId the domain id.
     * @param endpointId the Endpoint id.
     * @param token
     * @throws AppPlatformException API Exception
     * @throws IOException unexpected error
     */
    public static void delete(final String domainId, final String endpointId, final String token) throws AppPlatformException, ParseException, IOException {
        assert(domainId != null && endpointId != null && endpointId != null);
        deleteToken(null, domainId, endpointId, token);
    }

    private static void deleteToken (final BandwidthClient client, final String domainId, final String endpointId, final String token) throws AppPlatformException, ParseException, IOException {
        BandwidthClient bandwidthClient=client;
        if (bandwidthClient == null) {
            bandwidthClient = BandwidthClient.getInstance();
        }

        final String endpointTokenUri = String.format(bandwidthClient.getUserResourceUri(BandwidthConstants.ENDPOINTS_TOKEN_DELETE), domainId, endpointId, token);
        bandwidthClient.delete(endpointTokenUri);
    }

    public Long getExpires() {
        return (Long)getProperty("expires");
    }
    
    public String getToken() {
        return getPropertyAsString("token");
    }

}