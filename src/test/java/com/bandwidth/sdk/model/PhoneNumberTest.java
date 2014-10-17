package com.bandwidth.sdk.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bandwidth.sdk.MockClient;
import com.bandwidth.sdk.RestResponse;
import com.bandwidth.sdk.TestsHelper;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class PhoneNumberTest extends BaseModelTest {

    private MockClient mockClient;

    @Before
    public void setUp(){
    	super.setUp();
        mockClient = new MockClient();
    }

    @Test
    public void shouldBeCreatedFromJson() throws Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\n" +
                "    \"numberState\": \"enabled\",\n" +
                "    \"id\": \"n-bdllkjjddr5vuvglfluxdwi\",\n" +
                "    \"application\": \"https://api.com/v1/users/userId/applications/a-id1\",\n" +
                "    \"price\": \"0.00\",\n" +
                "    \"createdTime\": \"2013-03-07T21:35:18Z\",\n" +
                "    \"state\": \"NC\",\n" +
                "    \"number\": \"+number\",\n" +
                "    \"nationalNumber\": \"(111) 111-8026\",\n" +
                "    \"city\": \"GREENSBORO\"\n" +
                "  }");
        PhoneNumber number = new PhoneNumber(mockClient, jsonObject);
        assertThat(number.getId(), equalTo("n-bdllkjjddr5vuvglfluxdwi"));
        assertThat(number.getNumber(), equalTo("+number"));
        assertThat(number.getCity(), equalTo("GREENSBORO"));
    }

    @Test
    public void shouldCommitOnServerProperties() throws Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\n" +
                "    \"numberState\": \"enabled\",\n" +
                "    \"id\": \"n-bdllkjjddr5vuvglfluxdwi\",\n" +
                "    \"application\": \"https://api.com/v1/users/userId/applications/a-id1\",\n" +
                "    \"price\": \"0.00\",\n" +
                "    \"createdTime\": \"2013-03-07T21:35:18Z\",\n" +
                "    \"state\": \"NC\",\n" +
                "    \"number\": \"+number\",\n" +
                "    \"nationalNumber\": \"(111) 111-8026\",\n" +
                "    \"city\": \"GREENSBORO\"\n" +
                "  }");

        PhoneNumber number = new PhoneNumber(mockClient, jsonObject);
        number.setApplicationId("appId");
        number.setName("NewName");
        
        RestResponse restResponse = new RestResponse();
		
        restResponse.setResponseText(jsonObject.toString());

        restResponse.setContentType("application/json");
        restResponse.setStatus(201);
         
        mockClient.setRestResponse(restResponse);
        
        
        number.commit();

        assertThat(mockClient.requests.get(0).name, equalTo("post"));
        assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/phoneNumbers/n-bdllkjjddr5vuvglfluxdwi"));
        assertThat(mockClient.requests.get(0).params.get("applicationId").toString(), equalTo("appId"));
        assertThat(mockClient.requests.get(0).params.get("name").toString(), equalTo("NewName"));
    }

    @Test
    public void shouldBeDeletable() throws Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\n" +
                "    \"numberState\": \"enabled\",\n" +
                "    \"id\": \"n-bdllkjjddr5vuvglfluxdwi\",\n" +
                "    \"application\": \"https://api.com/v1/users/userId/applications/a-id1\",\n" +
                "    \"price\": \"0.00\",\n" +
                "    \"createdTime\": \"2013-03-07T21:35:18Z\",\n" +
                "    \"state\": \"NC\",\n" +
                "    \"number\": \"+number\",\n" +
                "    \"nationalNumber\": \"(111) 111-8026\",\n" +
                "    \"city\": \"GREENSBORO\"\n" +
                "  }");

        mockClient.result = jsonObject;

        PhoneNumber number = new PhoneNumber(mockClient, jsonObject);
        number.delete();

        assertThat(mockClient.requests.get(0).name, equalTo("delete"));
        assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/phoneNumbers/n-bdllkjjddr5vuvglfluxdwi"));
    }
    
    @Test
    public void shouldGetNumberList() throws Exception {
        RestResponse restResponse = new RestResponse();
		
        restResponse.setResponseText("[\n" +
                "  {\n" +
                "    \"numberState\": \"enabled\",\n" +
                "    \"id\": \"n-bdllkjjddr5vuvglfluxdwi\",\n" +
                "    \"application\": \"https://api.com/v1/users/userId/applications/a-id1\",\n" +
                "    \"price\": \"0.00\",\n" +
                "    \"createdTime\": \"2013-03-07T21:35:18Z\",\n" +
                "    \"state\": \"NC\",\n" +
                "    \"number\": \"+number\",\n" +
                "    \"nationalNumber\": \"(111) 111-8026\",\n" +
                "    \"city\": \"GREENSBORO\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"numberState\": \"enabled\",\n" +
                "    \"id\": \"n-lyamatqgkgcpeycv765pf3q\",\n" +
                "    \"price\": \"0.00\",\n" +
                "    \"createdTime\": \"2013-03-18T15:54:02Z\",\n" +
                "    \"name\": \"DIDify Number\",\n" +
                "    \"number\": \"+number2\",\n" +
                "    \"nationalNumber\": \"(999) 999-6131\"\n" +
                "  }\n" +
                "]");

        restResponse.setContentType("application/json");
        restResponse.setStatus(201);
         
        mockClient.setRestResponse(restResponse);
        
        List<PhoneNumber> list = PhoneNumber.list(mockClient, 0, 5);
        
        assertThat(list.size(), equalTo(2));
        assertThat(list.get(0).getId(), equalTo("n-bdllkjjddr5vuvglfluxdwi"));

        assertThat(mockClient.requests.get(0).name, equalTo("get"));
        assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/phoneNumbers"));
    }
    
    @Test
    public void shouldGetNumberById() throws Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\n" +
                "    \"numberState\": \"enabled\",\n" +
                "    \"id\": \"n-bdllkjjddr5vuvglfluxdwi\",\n" +
                "    \"application\": \"https://api.com/v1/users/userId/applications/a-id1\",\n" +
                "    \"price\": \"0.00\",\n" +
                "    \"createdTime\": \"2013-03-07T21:35:18Z\",\n" +
                "    \"state\": \"NC\",\n" +
                "    \"number\": \"+number\",\n" +
                "    \"nationalNumber\": \"(111) 111-8026\",\n" +
                "    \"city\": \"GREENSBORO\"\n" +
                "  }");
        
        RestResponse restResponse = new RestResponse();
		
        restResponse.setResponseText(jsonObject.toString());

        restResponse.setContentType("application/json");
        restResponse.setStatus(201);
         
        mockClient.setRestResponse(restResponse);
        
        
        
        PhoneNumber number = PhoneNumber.get(mockClient, "n-bdllkjjddr5vuvglfluxdwi");
        assertThat(number.getId(), equalTo("n-bdllkjjddr5vuvglfluxdwi"));

        assertThat(mockClient.requests.get(0).name, equalTo("get"));
        assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/phoneNumbers/n-bdllkjjddr5vuvglfluxdwi"));
    }
    
    @Test
    public void shouldGetNumberByNumber() throws Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\n" +
                "    \"numberState\": \"enabled\",\n" +
                "    \"id\": \"n-bdllkjjddr5vuvglfluxdwi\",\n" +
                "    \"application\": \"https://api.com/v1/users/userId/applications/a-id1\",\n" +
                "    \"price\": \"0.00\",\n" +
                "    \"createdTime\": \"2013-03-07T21:35:18Z\",\n" +
                "    \"state\": \"NC\",\n" +
                "    \"number\": \"+number\",\n" +
                "    \"nationalNumber\": \"(111) 111-8026\",\n" +
                "    \"city\": \"GREENSBORO\"\n" +
                "  }");
        
        RestResponse restResponse = new RestResponse();
		
        restResponse.setResponseText(jsonObject.toString());

        restResponse.setContentType("application/json");
        restResponse.setStatus(201);
         
        mockClient.setRestResponse(restResponse);
        
        PhoneNumber number = PhoneNumber.get(mockClient, "+number");
        assertThat(number.getId(), equalTo("n-bdllkjjddr5vuvglfluxdwi"));

        assertThat(mockClient.requests.get(0).name, equalTo("get"));
        assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/phoneNumbers/+number"));
    }
    
    @Test
    public void shouldCreateNewNumber() throws Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\n" +
                "    \"numberState\": \"enabled\",\n" +
                "    \"id\": \"n-bdllkjjddr5vuvglfluxdwi\",\n" +
                "    \"application\": \"https://api.com/v1/users/userId/applications/a-id1\",\n" +
                "    \"price\": \"0.00\",\n" +
                "    \"createdTime\": \"2013-03-07T21:35:18Z\",\n" +
                "    \"state\": \"NC\",\n" +
                "    \"number\": \"+number\",\n" +
                "    \"nationalNumber\": \"(111) 111-8026\",\n" +
                "    \"city\": \"GREENSBORO\"\n" +
                "  }");

        RestResponse restResponse = new RestResponse();
		
        restResponse.setResponseText(jsonObject.toString());

        restResponse.setContentType("application/json");
        restResponse.setStatus(201);
         
        mockClient.setRestResponse(restResponse);

        
        Map<String, Object> params = new HashMap<String, Object>();
    	params.put("number", "+number1");
    	params.put("name", "my new number");
    	params.put("applicationId", "a-bdllkjjddr5vuvglfluxdwi");
        
        
        PhoneNumber number = PhoneNumber.create(mockClient, params);
        assertThat(number.getId(), equalTo("n-bdllkjjddr5vuvglfluxdwi"));

        assertThat(mockClient.requests.get(0).name, equalTo("post"));
        assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/phoneNumbers"));
    }
    
    
    
    
}