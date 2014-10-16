package com.bandwidth.sdk.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;

import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.RestResponse;
import com.bandwidth.sdk.TestsHelper;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class MessageTest extends BaseModelTest{

    @Before
    public void setUp(){
        super.setUp();
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
        Message message = new Message(mockRestClient, jsonObject);
        assertThat(message.getId(), equalTo("m-ckobmmd4fgqumyhssgd6lqy"));
        assertThat(message.getFrom(), equalTo("+number2"));
        assertThat(message.getMessageId(), equalTo("m-ckobmmd4fgqumyhssgd6lqy"));
        assertThat(message.getText(), equalTo("Hello judith"));
    }
    
    @Test
    public void shouldGetMessageList() throws Exception {
        mockRestClient.arrayResult = (org.json.simple.JSONArray) new JSONParser().parse("[\n" +
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

        String mockUri = mockRestClient.getUserResourceUri(BandwidthConstants.MESSAGES_URI_PATH) + "/id1";
        restResponse.setLocation(mockUri);
        restResponse.setContentType("application/json");
        restResponse.setStatus(201);
         
        mockRestClient.setRestResponse(restResponse);
        
        
        List<Message> list = Message.getMessages(mockRestClient, 0, 5);
        
        assertThat(list.size(), equalTo(2));
        assertThat(list.get(0).getId(), equalTo("m-ckobmmd4fgqumyhssgd6lqy"));

        assertThat(mockRestClient.requests.get(0).name, equalTo("get"));
        assertThat(mockRestClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/messages"));
        assertThat(mockRestClient.requests.get(0).params.get("page").toString(), equalTo("0"));
        assertThat(mockRestClient.requests.get(0).params.get("size").toString(), equalTo("5"));
    }
    
    @Test
    public void shouldGetMessageById() throws Exception {
        mockRestClient.result = (JSONObject) new JSONParser().parse("{\n" +
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

        Message message = Message.getMessage(mockRestClient, "m-ckobmmd4fgqumyhssgd6lqy");
        assertThat(message.getId(), equalTo("m-ckobmmd4fgqumyhssgd6lqy"));
        assertThat(message.getFrom(), equalTo("+number2"));
        assertThat(mockRestClient.requests.get(0).name, equalTo("getObject"));
        assertThat(mockRestClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/messages/m-ckobmmd4fgqumyhssgd6lqy"));
    }
    
    @Test
    public void shouldCreateMessage() throws Exception {
        mockRestClient.result = (JSONObject) new JSONParser().parse("{\n" +
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

        
       // Message message = messages.newMessageBuilder().from("from").to("to").tag("tag").text("hello").create();
        
        Message message = Message.createMessage(mockRestClient, params);
        
        assertThat(message.getId(), equalTo("m-ckobmmd4fgqumyhssgd6lqy"));
        assertThat(mockRestClient.requests.get(0).name, equalTo("create"));
        assertThat(mockRestClient.requests.get(0).uri, equalTo("users/" + TestsHelper.TEST_USER_ID + "/messages"));
        assertThat(mockRestClient.requests.get(0).params.get("from").toString(), equalTo("+number2"));
        assertThat(mockRestClient.requests.get(0).params.get("to").toString(), equalTo("+number1"));
        assertThat(mockRestClient.requests.get(0).params.get("tag").toString(), equalTo("MESSAGE_TAG"));
        assertThat(mockRestClient.requests.get(0).params.get("text").toString(), equalTo("Hola Mundo!"));
    }
    
    
    
}