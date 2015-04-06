package com.bandwidth.sdk.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bandwidth.sdk.AppPlatformException;
import com.bandwidth.sdk.MockClient;

public class EndpointTest {

    private MockClient mockClient;
    
    private Domain domain;
    private String domainName;
    
    @Before
    public void setUp() throws Exception {
        mockClient = new MockClient();
        domainName = RandomStringUtils.randomAlphabetic(12);
        domain = Domain.create(domainName, "Domain Description");
    }
    
    @After
    public void tearDown() throws Exception {
        Domain.delete(domain.getId());
    }
    
    @Test
    public void shouldBeCreatedFromJson() throws ParseException {
        final JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"name\":\"domainName\", \"name\":\"domainName\"}");
        new Endpoint(mockClient, jsonObject);
    }
    
    @Test(expected = AppPlatformException.class)
    public void deleteEndpointByInvalidIds() throws Exception {
        Endpoint.delete("111111", "23323");
    }
    
    @Test(expected = AppPlatformException.class)
    public void deleteEndpointByInvalidEndpointId() throws Exception {
        Endpoint.delete(domain.getId(), "111111");
    }
    
    @Test
    public void shouldCreateAndDeleteEndpoint() throws Exception {
        final Endpoint createdEndpoint = Endpoint.create(domain.getId(), "endpointname", "123456");
        assertThat(createdEndpoint.getDomainId(), equalTo(domain.getId()));
        Endpoint.delete(domain.getId(), createdEndpoint.getId());
    }
    
    @Test
    public void shouldCreateAndDeleteActiveEndpoint() throws Exception {
        final Endpoint createdEndpoint = Endpoint.create(domain.getId(), "endpointname", "123456", true);
        assertThat(createdEndpoint.getDomainId(), equalTo(domain.getId()));
        Endpoint.delete(domain.getId(), createdEndpoint.getId());
    }
    
    @Test
    public void shouldCreateGetAndDeleteEndpoint() throws Exception {
        final Endpoint createdEndpoint = Endpoint.create(domain.getId(), "endpointname", "123456");
        assertThat(createdEndpoint.getDomainId(), equalTo(domain.getId()));
        final Endpoint getEndpoint = Endpoint.get(domain.getId(), createdEndpoint.getId());
        assertThat(createdEndpoint.getDomainId(), equalTo(domain.getId()));
        assertThat(createdEndpoint.getId(), equalTo(getEndpoint.getId()));
        Endpoint.delete(domain.getId(), createdEndpoint.getId());
    }
    
    @Test
    public void shouldCreateListDeleteAndListEndpoints() throws Exception {
        final Endpoint createdEndpoint = Endpoint.create(domain.getId(), "endpointname", "123456");
        assertThat(createdEndpoint.getDomainId(), equalTo(domain.getId()));
        ResourceList<Endpoint> endpointList = Endpoint.list(domain.getId());
        assertThat(endpointList.size(), equalTo(1));
        Endpoint.delete(domain.getId(), createdEndpoint.getId());
        endpointList = Endpoint.list(domain.getId());
        assertThat(endpointList.size(), equalTo(0));
    }
    
    @Test
    public void shouldCreateUpdateAndDeleteEndpoints() throws Exception {
        final Endpoint createdEndpoint = Endpoint.create(domain.getId(), "endpointname", "123456");
        assertThat(createdEndpoint.getDomainId(), equalTo(domain.getId()));
        Endpoint.update(domain.getId(), createdEndpoint.getId(), "123456", true);
        
        final Endpoint updateEndpoint = Endpoint.get(domain.getId(), createdEndpoint.getId());
        assertThat(updateEndpoint.getId(), equalTo(createdEndpoint.getId()));
        assertThat(updateEndpoint.getDomainId(), equalTo(createdEndpoint.getDomainId()));
        assertThat(updateEndpoint.isEnabled(), equalTo(true));

        Endpoint.delete(domain.getId(), createdEndpoint.getId());
    }
}