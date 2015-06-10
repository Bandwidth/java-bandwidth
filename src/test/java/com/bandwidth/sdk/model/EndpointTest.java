package com.bandwidth.sdk.model;

import com.bandwidth.sdk.AppPlatformException;
import com.bandwidth.sdk.MockClient;
import com.bandwidth.sdk.RestResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class EndpointTest {

    private MockClient mockClient;

    private Domain domain;
    private String domainName;

    @Before
    public void setUp() throws Exception {
        mockClient = new MockClient();
        domainName = RandomStringUtils.randomAlphabetic(12);

        final RestResponse response = new RestResponse();
        response.setResponseText("{\"id\":\"id1\", \"name\":\"domainName\", \"name\":\"domainName\"}");
        response.setStatus(201);
        mockClient.setRestResponse(response);
        domain = Domain.create(mockClient, domainName, "Domain Description");
    }

    @Test
    public void shouldBeCreatedFromJson() throws ParseException {
        final JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"name\":\"domainName\", \"name\":\"domainName\"}");
        new Endpoint(mockClient, jsonObject);
    }

    @Test(expected = AppPlatformException.class)
    public void deleteEndpointByInvalidIds() throws Exception {
        final RestResponse response = new RestResponse();
        mockClient.setRestResponse(response);
        response.setStatus(404);

        Endpoint.delete(mockClient, "111111", "23323");
    }
    
    @Test(expected = AppPlatformException.class)
    public void deleteEndpointByInvalidEndpointId() throws Exception {
        final RestResponse response = new RestResponse();
        mockClient.setRestResponse(response);
        response.setStatus(404);
        Endpoint.delete(mockClient, domain.getId(), "111111");
    }

    @Test
    public void shouldCreateEndpoint() throws Exception {

        final RestResponse response = new RestResponse();
        response.setResponseText("{\"id\":\"id2\", \"domainId\":\"id1\",\"name\":\"endpointname\", \"password\":\"123456\", \"credentials\": { \"realm\": \"abc\", \"username\": \"xxx\"}}");
        response.setStatus(201);
        mockClient.setRestResponse(response);

        final Endpoint createdEndpoint = Endpoint.create(mockClient, domain.getId(), "endpointname", "123456");
        createdEndpoint.getCredentials();
        assertThat(createdEndpoint.getDomainId(), equalTo(domain.getId()));
    }

    @Test
    public void shouldCreateGetEndpoint() throws Exception {
        final RestResponse response = new RestResponse();
        response.setResponseText("{\"id\":\"id2\", \"domainId\":\"id1\",\"name\":\"endpointname\", \"password\":\"123456\", \"credentials\": { \"realm\": \"abc\", \"username\": \"xxx\"}}");
        response.setStatus(201);
        mockClient.setRestResponse(response);
        final Endpoint createdEndpoint = Endpoint.create(mockClient, domain.getId(), "endpointname", "123456");

        assertThat(createdEndpoint.getDomainId(), equalTo(domain.getId()));
        final Endpoint getEndpoint = Endpoint.get(mockClient, domain.getId(), createdEndpoint.getId());
        assertThat(createdEndpoint.getDomainId(), equalTo(domain.getId()));
        assertThat(createdEndpoint.getId(), equalTo(getEndpoint.getId()));
    }

    @Test
    public void shouldCreateListEndpoints() throws Exception {
        final RestResponse response = new RestResponse();
        response.setResponseText("{\"id\":\"id2\", \"domainId\":\"id1\",\"name\":\"endpointname\", \"password\":\"123456\", \"credentials\": { \"realm\": \"abc\", \"username\": \"xxx\"}}");
        response.setStatus(201);
        mockClient.setRestResponse(response);
        final Endpoint createdEndpoint = Endpoint.create(mockClient, domain.getId(), "endpointname", "123456");

        assertThat(createdEndpoint.getDomainId(), equalTo(domain.getId()));
        response.setResponseText("{\"id\":\"id2\", \"domainId\":\"id1\",\"name\":\"endpointname\", \"password\":\"123456\", \"credentials\": { \"realm\": \"abc\", \"username\": \"xxx\"}}");
        response.setStatus(201);
        mockClient.setRestResponse(response);
    }

    @Test(expected = AppPlatformException.class)
    public void shouldFailCreateEndpoint() throws Exception {
        final RestResponse response = new RestResponse();
        response.setStatus(HttpStatus.SC_BAD_REQUEST);
        mockClient.setRestResponse(response);

        Endpoint.create(mockClient, domain.getId(), "endpointname", "123456");
    }

}