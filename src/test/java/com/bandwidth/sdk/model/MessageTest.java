package com.bandwidth.sdk.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;

import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.MockClient;
import com.bandwidth.sdk.RestResponse;
import com.bandwidth.sdk.TestsHelper;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class MessageTest extends BaseModelTest{

	
    private MockClient mockClient;

    @Before
    public void setUp(){
    	super.setUp();
        mockClient = new MockClient();
    }


    @Test
    public void shouldBeCreatedFromJson() throws Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\n" +
                "  \"to\": \"+number1\",\n" +
                "  \"id\": \"m-ckobmmd4fgqumyhssgd6lqy\",\n" +
                "  \"time\": \"2013-10-02T12:15:41Z\",\n" +
                "  \"text\": \"Hello judith\",\n" +
                "  \"direction\": \"in\",\n" +
                "  \"state\": \"received\",\n" +
                "  \"from\": \"+number2\",\n" +
                "  \"messageId\": \"m-ckobmmd4fgqumyhssgd6lqy\",\n" +
                "  \"media\": []\n" +
                "}");
        Message message = new Message(mockClient, jsonObject);
        assertThat(message.getId(), equalTo("m-ckobmmd4fgqumyhssgd6lqy"));
        assertThat(message.getFrom(), equalTo("+number2"));
        assertThat(message.getMessageId(), equalTo("m-ckobmmd4fgqumyhssgd6lqy"));
        assertThat(message.getText(), equalTo("Hello judith"));
    }
    
    @Test
    public void shouldGetMessageList() throws Exception {
        mockClient.arrayResult = (org.json.simple.JSONArray) new JSONParser().parse("[\n" +
                "  {\n" +
                "    \"to\": \"+number1\",\n" +
                "    \"id\": \"m-ckobmmd4fgqumyhssgd6lqy\",\n" +
                "    \"time\": \"2013-10-02T12:15:41Z\",\n" +
                "    \"text\": \"Hello judith\",\n" +
                "    \"direction\": \"in\",\n" +
                "    \"state\": \"received\",\n" +
                "    \"from\": \"+number4\",\n" +
                "    \"messageId\": \"m-ckobmmd4fgqumyhssgd6lqy\",\n" +
                "    \"media\": []\n" +
                "  },\n" +
                "  {\n" +
                "    \"to\": \"+number2\",\n" +
                "    \"id\": \"m-3ieljdlvsatob4r6rii4oaq\",\n" +
                "    \"time\": \"2013-10-02T23:36:07Z\",\n" +
                "    \"text\": \"Yo.\",\n" +
                "    \"direction\": \"in\",\n" +
                "    \"state\": \"received\",\n" +
                "    \"from\": \"+number3\",\n" +
                "    \"messageId\": \"m-3ieljdlvsatob4r6rii4oaq\",\n" +
                "    \"media\": []\n" +
                "  }\n" +
                "]");
        
        RestResponse restResponse = new RestResponse();
		
        restResponse.setResponseText("[\n" +
                "  {\n" +
                "    \"to\": \"+number1\",\n" +
                "    \"id\": \"m-ckobmmd4fgqumyhssgd6lqy\",\n" +
                "    \"time\": \"2013-10-02T12:15:41Z\",\n" +
                "    \"text\": \"Hello judith\",\n" +
                "    \"direction\": \"in\",\n" +
                "    \"state\": \"received\",\n" +
                "    \"from\": \"+number4\",\n" +
                "    \"messageId\": \"m-ckobmmd4fgqumyhssgd6lqy\",\n" +
                "    \"media\": []\n" +
                "  },\n" +
                "  {\n" +
                "    \"to\": \"+number2\",\n" +
                "    \"id\": \"m-3ieljdlvsatob4r6rii4oaq\",\n" +
                "    \"time\": \"2013-10-02T23:36:07Z\",\n" +
                "    \"text\": \"Yo.\",\n" +
                "    \"direction\": \"in\",\n" +
                "    \"state\": \"received\",\n" +
                "    \"from\": \"+number3\",\n" +
                "    \"messageId\": \"m-3ieljdlvsatob4r6rii4oaq\",\n" +
                "    \"media\": []\n" +
                "  }\n" +
                "]");

        String mockUri = mockClient.getUserResourceUri(BandwidthConstants.MESSAGES_URI_PATH) + "/id1";
        restResponse.setLocation(mockUri);
        restResponse.setContentType("application/json");
        restResponse.setStatus(201);
         
        mockClient.setRestResponse(restResponse);
        
        
        List<Message> list = Message.list(mockClient, 0, 5);
        
        assertThat(list.size(), equalTo(2));
        assertThat(list.get(0).getId(), equalTo("m-ckobmmd4fgqumyhssgd6lqy"));

        assertThat(mockClient.requests.get(0).name, equalTo("get"));
        assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/messages"));
        assertThat(mockClient.requests.get(0).params.get("page").toString(), equalTo("0"));
        assertThat(mockClient.requests.get(0).params.get("size").toString(), equalTo("5"));
    }
    
    @Test
    public void shouldGetMessageById() throws Exception {
    	JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\n" +
                "  \"to\": \"+number1\",\n" +
                "  \"id\": \"m-ckobmmd4fgqumyhssgd6lqy\",\n" +
                "  \"time\": \"2013-10-02T12:15:41Z\",\n" +
                "  \"text\": \"Hello judith\",\n" +
                "  \"direction\": \"in\",\n" +
                "  \"state\": \"received\",\n" +
                "  \"from\": \"+number2\",\n" +
                "  \"messageId\": \"m-ckobmmd4fgqumyhssgd6lqy\",\n" +
                "  \"media\": []\n" +
                "}");
        
        RestResponse response = new RestResponse();
        response.setResponseText(jsonObject.toString());
        mockClient.setRestResponse(response);   
        

        Message message = Message.get(mockClient, "m-ckobmmd4fgqumyhssgd6lqy");
        assertThat(message.getId(), equalTo("m-ckobmmd4fgqumyhssgd6lqy"));
        assertThat(message.getFrom(), equalTo("+number2"));
        assertThat(mockClient.requests.get(0).name, equalTo("get"));
        assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/messages/m-ckobmmd4fgqumyhssgd6lqy"));
    }
    
    @Test
    public void shouldCreateMessage() throws Exception {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\n" +
                "  \"to\": \"+number1\",\n" +
                "  \"id\": \"m-ckobmmd4fgqumyhssgd6lqy\",\n" +
                "  \"time\": \"2013-10-02T12:15:41Z\",\n" +
                "  \"text\": \"Hello judith\",\n" +
                "  \"direction\": \"in\",\n" +
                "  \"state\": \"received\",\n" +
                "  \"from\": \"+number2\",\n" +
                "  \"messageId\": \"m-ckobmmd4fgqumyhssgd6lqy\",\n" +
                "  \"media\": []\n" +
                "}");

    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("to", "+number1");
    	params.put("from", "+number2");
    	params.put("text", "Hola Mundo!");
    	params.put("tag", "MESSAGE_TAG");
        
        RestResponse response = new RestResponse();
        response.setResponseText(jsonObject.toString());
        
        String uri = mockClient.getUserResourceUri(BandwidthConstants.MESSAGES_URI_PATH);
        String path = mockClient.getPath(uri);
        
        System.out.println(path);
        String locationLink = "http:" + path + "/m-ckobmmd4fgqumyhssgd6lqy";
        response.setLocation(locationLink);
        
        mockClient.setRestResponse(response);   

                
        Message message = Message.create(mockClient, params);
        
        assertThat(message.getId(), equalTo("m-ckobmmd4fgqumyhssgd6lqy"));
        assertThat(mockClient.requests.get(0).name, equalTo("post"));
        assertThat(mockClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/messages"));
        assertThat(mockClient.requests.get(0).params.get("from").toString(), equalTo("+number2"));
        assertThat(mockClient.requests.get(0).params.get("to").toString(), equalTo("+number1"));
        assertThat(mockClient.requests.get(0).params.get("tag").toString(), equalTo("MESSAGE_TAG"));
        assertThat(mockClient.requests.get(0).params.get("text").toString(), equalTo("Hola Mundo!"));
    }
    
    
    
}