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
     * Convenience factory method to create a Endpoint object from a map of parameters
     * @param client the bandwidth client configuration.
     * @param domainId the domain id.
     * @param endpointId the endpoint id.
     * @return the created token.
     * @throws AppPlatformException API Exception
     * @throws ParseException Error parsing data
     * @throws Exception error
     */
    public static EndpointToken create(final BandwidthClient client, final String domainId, final String endpointId) throws AppPlatformException, ParseException, Exception {
        assert (client!= null && domainId != null && endpointId != null);
        final String endpointTokenUri = String.format(client.getUserResourceUri(BandwidthConstants.ENDPOINTS_TOKEN), domainId, endpointId);
        final RestResponse response = client.post(endpointTokenUri, null);
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("expires", "10");
        final JSONObject callObj = toJSONObject(client.get(response.getLocation(), params));
        return new EndpointToken(client, callObj);
    }

    public String getExpires() {
        return getPropertyAsString("expires");
    }
    
    public String getToken() {
        return getPropertyAsString("token");
    }

}