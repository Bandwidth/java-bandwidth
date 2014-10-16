package com.bandwidth.sdk.model;

import java.util.Map;
import java.util.HashMap;

import com.bandwidth.sdk.MockRestClient;
import com.bandwidth.sdk.MockClient;
import com.bandwidth.sdk.RestResponse;
import com.bandwidth.sdk.TestsHelper;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ApplicationTest {
    
    private MockClient mockClient;

    @Before
    public void setUp(){
 //       mockRestClient = TestsHelper.getClient();
        
        // todo fix this to come from the TestsHelper factory
        mockClient = new MockClient();

    }
    
    @Test 
    public void shouldBeCreatedFromName() throws Exception {
    	
    	RestResponse response = new RestResponse();
    	
    	response.setResponseText("{\"id\":\"id1\",\"autoAnswer\":true,\"incomingSmsUrl\":\"http:\\/\\/sms\\/callback.json\",\"name\":\"App1\",\"incomingCallUrl\":\"http:\\/\\/call\\/callback.json\"}");
    	response.setStatus(201);
    
    	mockClient.setRestResponse(response);
    	
    	
    	Map<String, Object>params = new HashMap<String, Object>();
    	params.put("name", "App1");
    	
    	Application application = Application.create(mockClient, params);
    	
    	assert(application.getId() != null);
        assertThat(application.getId(), equalTo("id1"));
        assertThat(application.getName(), equalTo("App1"));
        assertThat(application.getIncomingCallUrl(), equalTo("http://call/callback.json"));
        assertThat(application.getIncomingSmsUrl(), equalTo("http://sms/callback.json"));
        assertThat(application.isAutoAnswer(), equalTo(true));
    	
    	
        assertThat(mockClient.getRequests().get(0).name, equalTo("post"));
    }

    @Test
    public void shouldBeCreatedFromParams() throws ParseException {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse
        		("{\"id\":\"id1\",\"autoAnswer\":true,\"incomingSmsUrl\":\"http:\\/\\/sms\\/callback.json\",\"name\":\"App1\",\"incomingCallUrl\":\"http:\\/\\/call\\/callback.json\"}");
        
        Application application = new Application(mockClient, jsonObject);

        assertThat(application.getId(), equalTo("id1"));
        assertThat(application.getName(), equalTo("App1"));
        assertThat(application.getIncomingCallUrl(), equalTo("http://call/callback.json"));
        assertThat(application.getIncomingSmsUrl(), equalTo("http://sms/callback.json"));
        assertThat(application.isAutoAnswer(), equalTo(true));
    }

    @Test
    public void shouldBeDeleted() throws ParseException, IOException {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"id\":\"id1\",\"autoAnswer\":true,\"incomingSmsUrl\":\"http:\\/\\/sms\\/callback.json\",\"name\":\"App1\",\"incomingCallUrl\":\"http:\\/\\/call\\/callback.json\"}");
        Application application = new Application(mockClient, jsonObject);

        assertThat(application.getId(), equalTo("id1"));

        application.delete();

        assertThat(mockClient.getRequests().get(0).name, equalTo("delete"));
        assertThat(mockClient.getRequests().get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/applications/id1"));
    }

    @Test
    public void shouldUpdateAttributesOnServer() throws ParseException, IOException {

        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"id\":\"id1\",\"autoAnswer\":true,\"incomingSmsUrl\":\"http:\\/\\/sms\\/callback.json\",\"name\":\"App1\",\"incomingCallUrl\":\"http:\\/\\/call\\/callback.json\"}");
        Application application = new Application(mockClient, jsonObject);

        assertThat(application.getId(), equalTo("id1"));
        application.setName("App2");
        application.setIncomingCallUrl("anotherUrl");
        application.commit();

        assertThat(mockClient.getRequests().get(0).name, equalTo("post"));
        assertThat(mockClient.getRequests().get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/applications/id1"));
        assertThat(mockClient.getRequests().get(0).params.get("name").toString(), equalTo("App2"));
    }
    
    @Test
    public void shouldGetApplicationsList() throws ParseException, IOException {
        
        RestResponse restResponse = new RestResponse();
        		
        restResponse.setResponseText("[{\"id\":\"id1\",\"incomingCallUrl\":\"https://postBack\",\"incomingSmsUrl\":\"https://message\",\"name\":\"App1\",\"autoAnswer\":false},{\"id\":\"id2\",\"incomingCallUrl\":\"http:///call/callback.json\",\"incomingSmsUrl\":\"http:///sms/callback.json\",\"name\":\"App2\",\"autoAnswer\":true}]");
        restResponse.setContentType("application/json");
        restResponse.setStatus(201);
         
        mockClient.setRestResponse(restResponse);
        
        ResourceList<Application> applicationList = Application.list(mockClient, 0, 5);
                
        Application application = applicationList.get(0);
        assertThat(applicationList.size(), equalTo(2));
        assertThat(applicationList.get(0).getId(), equalTo("id1"));
        assertThat(applicationList.get(0).isAutoAnswer(), equalTo(false));
        assertThat(applicationList.get(0).getIncomingSmsUrl(), equalTo("https://message"));
        assertThat(applicationList.get(1).getId(), equalTo("id2"));
        assertThat(applicationList.get(1).isAutoAnswer(), equalTo(true));
        assertThat(applicationList.get(1).getIncomingCallUrl(), equalTo("http:///call/callback.json"));

        assertThat(mockClient.getRequests().get(0).name, equalTo("get"));
        assertThat(mockClient.getRequests().get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/applications"));
    }

}