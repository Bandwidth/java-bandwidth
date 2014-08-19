package com.bandwidth.sdk.model;

import com.bandwidth.sdk.driver.IRestDriver;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author vpotapenko
 */
public class Messages extends BaseModelObject {

    public Messages(IRestDriver driver, String parentUri) {
        super(driver, parentUri, null);
    }

    @Override
    public String getUri() {
        return StringUtils.join(new String[]{
                parentUri,
                "messages"
        }, '/');
    }

    public Message getMessageById(String id) throws IOException {
        String messagesUri = getUri();
        String uri = StringUtils.join(new String[]{
                messagesUri,
                id
        }, '/');
        JSONObject jsonObject = driver.getObject(uri);
        return new Message(driver, messagesUri, jsonObject);
    }

    public NewMessageBuilder newMessageBuilder() {
        return new NewMessageBuilder();
    }

    public QueryMessagesBuilder queryMessagesBuilder() {
        return new QueryMessagesBuilder();
    }

    private List<Message> getMessages(Map<String, Object> params) throws IOException {
        String messagesUri = getUri();
        JSONArray jsonArray = driver.getArray(messagesUri, params);

        List<Message> messages = new ArrayList<Message>();
        for (Object obj : jsonArray) {
            messages.add(new Message(driver, messagesUri, (JSONObject) obj));
        }
        return messages;
    }

    private Message newMessage(Map<String, Object> params) throws IOException {
        String uri = getUri();
        JSONObject jsonObject = driver.create(uri, params);
        return new Message(driver, uri, jsonObject);
    }

    public class QueryMessagesBuilder {

        private final Map<String, Object> params = new HashMap<String, Object>();

        public QueryMessagesBuilder from(String from) {
            params.put("from", from);
            return this;
        }

        public QueryMessagesBuilder to(String to) {
            params.put("to", to);
            return this;
        }

        public QueryMessagesBuilder page(int page) {
            params.put("page", page);
            return this;
        }

        public QueryMessagesBuilder size(int size) {
            params.put("size", size);
            return this;
        }

        public List<Message> list() throws IOException {
            return getMessages(params);
        }
    }

    public class NewMessageBuilder {

        private final Map<String, Object> params = new HashMap<String, Object>();

        public NewMessageBuilder from(String from) {
            params.put("from", from);
            return this;
        }

        public NewMessageBuilder to(String to) {
            params.put("to", to);
            return this;
        }

        public NewMessageBuilder text(String text) {
            params.put("text", text);
            return this;
        }

        public NewMessageBuilder callbackUrl(String callbackUrl) {
            params.put("callbackUrl", callbackUrl);
            return this;
        }

        public NewMessageBuilder tag(String tag) {
            params.put("tag", tag);
            return this;
        }

        public Message create() throws IOException {
            return newMessage(params);
        }
    }
}
