package com.bandwidth.sdk.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.MockClient;
import com.bandwidth.sdk.RestResponse;
import com.bandwidth.sdk.TestsHelper;

public class DomainTest {

    private MockClient mockClient;
    
    
    @Before
    public void setUp() {
        mockClient = new MockClient();

    }
    
    @Test
    public void shouldBeCreatedFromJson() throws ParseException {
        final JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"name\":\"domainName\"}");

        final Domain domain = new Domain(mockClient, jsonObject);
        assertThat(domain.getName(), equalTo("domainName"));
    }
    
    @Test
    public void shouldGetDomainById() throws ParseException, Exception {
        
        final String domainName = RandomStringUtils.randomAlphabetic(12);
        
        final RestResponse response = new RestResponse();
        response.setResponseText ("{\"id\":\"id1\",\"name\":\"" + domainName + "\",\"description\":\"description of the domain\"}");
        mockClient.setRestResponse(response);
        
        final Domain domain = Domain.get(mockClient, "id1");
        assertThat(domain.getId(), equalTo("id1"));
        assertThat(domain.getName(), equalTo(domainName));
        assertThat(domain.getDescription(), equalTo("description of the domain"));
    }
    
    @Test
    public void shouldCreateDomain() throws ParseException, Exception {
        mockClient.result = (JSONObject) new JSONParser().parse("{\"id\":\"id1\",\"name\":\"domainName\",\"description\":\"description of the domain\"}");

        final RestResponse restResponse = new RestResponse();
        restResponse.setResponseText("{\"id\":\"id1\",\"name\":\"domainName\",\"description\":\"description of the domain\"}");
        
        restResponse.setContentType("application/json");
        final String mockUri = mockClient.getUserResourceUri(BandwidthConstants.DOMAINS_URI_PATH) + "/id1";
        restResponse.setLocation(mockUri);
        restResponse.setStatus(201);
         
        mockClient.setRestResponse(restResponse);
        
        final Domain bridge = Domain.create(mockClient, "id1", null);
        assertThat(bridge.getId(), equalTo("id1"));
        assertThat(bridge.getName(), equalTo("domainName"));
        assertThat(bridge.getDescription(), equalTo("description of the domain"));
    }
    
    @Test
    public void shouldGetDomainList() throws Exception {
        final RestResponse restResponse = new RestResponse();
        restResponse.setResponseText("[\n" +
                "{\"id\":\"id1\",\"name\":\"domainName\",\"description\":\"description of the domain\"},\n" +
                "{\"id\":\"id2\",\"name\":\"domainNameTwo\",\"description\":\"description of the domain2\"}]");        
        restResponse.setContentType("application/json");
        restResponse.setStatus(201);
         
        mockClient.setRestResponse(restResponse);
        
        final List<Domain> domainList = Domain.list(mockClient, 0, 5);
        assertThat(domainList.size(), equalTo(2));
        
        assertThat(domainList.get(0).getId(), equalTo("id1"));
        assertThat(domainList.get(0).getName(), equalTo("domainName"));
        assertThat(domainList.get(0).getDescription(), equalTo("description of the domain"));
        
        assertThat(domainList.get(1).getId(), equalTo("id2"));
        assertThat(domainList.get(1).getName(), equalTo("domainNameTwo"));
        assertThat(domainList.get(1).getDescription(), equalTo("description of the domain2"));

        assertThat(mockClient.requests.get(0).name, equalTo("get"));
        assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/domains"));
    }
    
    @Test
    public void shouldCleanDescription() throws Exception {
        final String domainName = RandomStringUtils.randomAlphabetic(12);
        final String domainDescription = "Domain Description";
        
        final RestResponse restResponse = new RestResponse();
        restResponse.setResponseText("{\"id\":\"id1\",\"name\":\"domainName\",\"description\":\"description of the domain\"}");        
        restResponse.setContentType("application/json");
        restResponse.setStatus(201);
         
        mockClient.setRestResponse(restResponse);
        
        final Domain createdDomain = Domain.create(mockClient, domainName, domainDescription);
        
        //Get the Domain
        final Domain domainGet = Domain.get(mockClient, createdDomain.getId());
        assertThat(createdDomain.getId(), equalTo(domainGet.getId()));
        
        //Update the domain
        restResponse.setResponseText("{\"id\":\"id1\",\"name\":\"domainName\",\"description\":\"\"}");
        mockClient.setRestResponse(restResponse);
        final Domain domainUpdate = Domain.update(mockClient, domainGet.getId(), "");
        assertThat(createdDomain.getId(), equalTo(domainUpdate.getId()));
        assertThat("", equalTo(domainUpdate.getDescription()));
    }
}