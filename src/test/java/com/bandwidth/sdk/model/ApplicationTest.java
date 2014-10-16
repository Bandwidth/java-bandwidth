package com.bandwidth.sdk.model;

import com.bandwidth.sdk.MockRestClient;
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

    private MockRestClient mockRestClient;

    @Before
    public void setUp(){
        mockRestClient = TestsHelper.getClient();

    }

    @Test
    public void shouldBeCreatedFromJson() throws ParseException {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"id\":\"id1\",\"autoAnswer\":true,\"incomingSmsUrl\":\"http:\\/\\/sms\\/callback.json\",\"name\":\"App1\",\"incomingCallUrl\":\"http:\\/\\/call\\/callback.json\"}");

        Application application = new Application(null, jsonObject);

        assertThat(application.getId(), equalTo("id1"));
        assertThat(application.getName(), equalTo("App1"));
        assertThat(application.getIncomingCallUrl(), equalTo("http://call/callback.json"));
        assertThat(application.getIncomingSmsUrl(), equalTo("http://sms/callback.json"));
        assertThat(application.isAutoAnswer(), equalTo(true));
    }

    @Test
    public void shouldBeDeleted() throws ParseException, IOException {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"id\":\"id1\",\"autoAnswer\":true,\"incomingSmsUrl\":\"http:\\/\\/sms\\/callback.json\",\"name\":\"App1\",\"incomingCallUrl\":\"http:\\/\\/call\\/callback.json\"}");
        Application application = new Application(mockRestClient, jsonObject);

        assertThat(application.getId(), equalTo("id1"));

        application.delete();

        assertThat(mockRestClient.requests.get(0).name, equalTo("delete"));
        assertThat(mockRestClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/applications/id1"));
    }

    @Test
    public void shouldUpdateAttributesOnServer() throws ParseException, IOException {

        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\"id\":\"id1\",\"autoAnswer\":true,\"incomingSmsUrl\":\"http:\\/\\/sms\\/callback.json\",\"name\":\"App1\",\"incomingCallUrl\":\"http:\\/\\/call\\/callback.json\"}");
        Application application = new Application(mockRestClient, jsonObject);

        assertThat(application.getId(), equalTo("id1"));
        application.setName("App2");
        application.setIncomingCallUrl("anotherUrl");
        application.commit();

        assertThat(mockRestClient.requests.get(0).name, equalTo("post"));
        assertThat(mockRestClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/applications/id1"));
        assertThat(mockRestClient.requests.get(0).params.get("name").toString(), equalTo("App2"));
    }
    
    @Test
    public void shouldGetApplicationsList() throws ParseException, IOException {
        mockRestClient.arrayResult = (JSONArray) new JSONParser().parse
        		("[{\"id\":\"id1\",\"incomingCallUrl\":\"https://postBack\",\"incomingSmsUrl\":\"https://message\",\"name\":\"App1\",\"autoAnswer\":false},{\"id\":\"id2\",\"incomingCallUrl\":\"http:///call/callback.json\",\"incomingSmsUrl\":\"http:///sms/callback.json\",\"name\":\"App2\",\"autoAnswer\":true}]");
        
        
        RestResponse restResponse = new RestResponse();
        		
        restResponse.setResponseText("[{\"id\":\"id1\",\"incomingCallUrl\":\"https://postBack\",\"incomingSmsUrl\":\"https://message\",\"name\":\"App1\",\"autoAnswer\":false},{\"id\":\"id2\",\"incomingCallUrl\":\"http:///call/callback.json\",\"incomingSmsUrl\":\"http:///sms/callback.json\",\"name\":\"App2\",\"autoAnswer\":true}]");
        restResponse.setContentType("application/json");
        restResponse.setStatus(201);
         
        mockRestClient.setRestResponse(restResponse);
        
        ResourceList<Application> applicationList = Application.getApplications(mockRestClient, 0, 5);
        
        Application application = applicationList.get(0);
        assertThat(applicationList.size(), equalTo(2));
        assertThat(applicationList.get(0).getId(), equalTo("id1"));
        assertThat(applicationList.get(0).isAutoAnswer(), equalTo(false));
        assertThat(applicationList.get(0).getIncomingSmsUrl(), equalTo("https://message"));
        assertThat(applicationList.get(1).getId(), equalTo("id2"));
        assertThat(applicationList.get(1).isAutoAnswer(), equalTo(true));
        assertThat(applicationList.get(1).getIncomingCallUrl(), equalTo("http:///call/callback.json"));

       // assertThat(mockRestClient.requests.get(0).name, equalTo("getArray"));
       // assertThat(mockRestClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/applications"));
       // assertThat((Integer) mockRestClient.requests.get(0).params.get("page"), equalTo(1));
    }

}