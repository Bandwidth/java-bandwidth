package com.bandwidth.sdk.model;

import com.bandwidth.sdk.MockRestClient;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class MessagesTest {

    private MockRestClient mockRestClient;
    private Messages messages;

    @Before
    public void setUp() throws Exception {
        mockRestClient = new MockRestClient();
        messages = new Messages(mockRestClient, "parentUri");
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

        List<Message> list = messages.queryMessagesBuilder().page(5).size(10).list();
        assertThat(list.size(), equalTo(2));
        assertThat(list.get(0).getId(), equalTo("m-ckobmmd4fgqumyhssgd6lqy"));

        assertThat(mockRestClient.requests.get(0).name, equalTo("getArray"));
        assertThat(mockRestClient.requests.get(0).uri, equalTo("parentUri/messages"));
        assertThat(mockRestClient.requests.get(0).params.get("page").toString(), equalTo("5"));
        assertThat(mockRestClient.requests.get(0).params.get("size").toString(), equalTo("10"));
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

        Message message = messages.getMessage("m-ckobmmd4fgqumyhssgd6lqy");
        assertThat(message.getId(), equalTo("m-ckobmmd4fgqumyhssgd6lqy"));
        assertThat(message.getFrom(), equalTo("+number2"));
        assertThat(mockRestClient.requests.get(0).name, equalTo("getObject"));
        assertThat(mockRestClient.requests.get(0).uri, equalTo("parentUri/messages/m-ckobmmd4fgqumyhssgd6lqy"));
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

        Message message = messages.newMessageBuilder().from("from").to("to").tag("tag").text("hello").create();
        assertThat(message.getId(), equalTo("m-ckobmmd4fgqumyhssgd6lqy"));
        assertThat(mockRestClient.requests.get(0).name, equalTo("create"));
        assertThat(mockRestClient.requests.get(0).uri, equalTo("parentUri/messages"));
        assertThat(mockRestClient.requests.get(0).params.get("from").toString(), equalTo("from"));
        assertThat(mockRestClient.requests.get(0).params.get("to").toString(), equalTo("to"));
        assertThat(mockRestClient.requests.get(0).params.get("tag").toString(), equalTo("tag"));
        assertThat(mockRestClient.requests.get(0).params.get("text").toString(), equalTo("hello"));
    }
}