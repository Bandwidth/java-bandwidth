package com.bandwidth.sdk.model;

import com.bandwidth.sdk.AppPlatformException;
import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.BandwidthClient;
import com.bandwidth.sdk.RestResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

/**
 * Information about message.
 *
 * @author vpotapenko
 */
public class Message extends ResourceBase {
	
    /**
     * Gets information about a previously sent or received message.
     *
     * @param id message id
     * @return information about message
     * @throws IOException
     */
    public static Message get(String id) throws Exception {
    	
        return get(BandwidthClient.getInstance(), id);
    }
    
    /**
     * Gets information about a previously sent or received message.
     *
     * @param id message id
     * @return information about message
     * @throws IOException
     */
    public static Message get(BandwidthClient client, String id) throws Exception {
    	
        String messagesUri = client.getUserResourceInstanceUri(BandwidthConstants.MESSAGES_URI_PATH, id);

        JSONObject jsonObject = toJSONObject(client.get(messagesUri, null));
        return new Message(client, jsonObject);
    }
    
    
    /**
     * Factory method for Message list, returns a list of Message objects with default page, size
     * @return
     * @throws IOException
     */
    public static ResourceList<Message> list() throws Exception {
    	
    	// default page size is 25
     	return list(0, 25);
    }
    
    /**
     * Factory method for Message list, returns a list of Message objects with page, size preference
     * @param page
     * @param size
     * @return
     * @throws IOException
     */
    public static ResourceList<Message> list(int page, int size) throws Exception {
    	
        
        return list(BandwidthClient.getInstance(), page, size);
    }
    
    /**
     * Factory method for Message list, returns a list of Message objects with page, size preference
     * @param page
     * @param size
     * @return
     * @throws IOException
     */
    public static ResourceList<Message> list(BandwidthClient client, int page, int size) throws Exception {
    	
        String messageUri = client.getUserResourceUri(BandwidthConstants.MESSAGES_URI_PATH);

        ResourceList<Message> messages = 
        			new ResourceList<Message>(page, size, messageUri, Message.class);

        messages.setClient(client);
        messages.initialize();
        
        return messages;
    }
    
    /**
     * Convenience factory method to send a message, given the to number, the from number and the text
     * @param to
     * @param from
     * @param text
     * @return
     * @throws IOException
     */
    public static Message create(String to, String from, String text) throws Exception{
    	
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("to", to);
    	params.put("from", from);
    	params.put("text", text);
    	
    	return create(params);
    }
    
    /**
     * Convenience factory method to send a message, given a set of params
     * @param params
     * @return
     * @throws IOException
     */
    public static Message create(Map<String, Object>params) throws Exception {
    	
    	return create(BandwidthClient.getInstance(), params);
    	
    }
    
    /**
     * Factory method to send a message from a params object, given a client instance
     * @param client
     * @param params
     * @return
     * @throws IOException
     */
    public static Message create(BandwidthClient client, Map<String, Object> params) throws Exception {
        String messageUri = client.getUserResourceUri(BandwidthConstants.MESSAGES_URI_PATH);
        
        RestResponse response = client.post(messageUri, params);
                
        if (response.getStatus() > 400) {
            throw new AppPlatformException(response.getResponseText());
        }
        // success here, otherwise an exception is generated


    	String messageId = response.getLocation().substring(client.getPath(messageUri).length() + 1);

    	Message message = get(client, messageId);
        
        return message;
    }
	

    public Message(BandwidthClient client, JSONObject jsonObject) {
        super(client, jsonObject);
    }

    @Override
    protected void setUp(JSONObject jsonObject) {
        this.id = (String) jsonObject.get("id");
        updateProperties(jsonObject);
    }      

    protected String getUri() {
        return client.getUserResourceInstanceUri(BandwidthConstants.MESSAGES_URI_PATH, getId());
    }

    public String getMessageId() {
        return getPropertyAsString("messageId");
    }

    public String getFrom() {
        return getPropertyAsString("from");
    }

    public String getTo() {
        return getPropertyAsString("to");
    }

    public String getState() {
        return getPropertyAsString("state");
    }

    public String getDirection() {
        return getPropertyAsString("direction");
    }

    public String getCallbackUrl() {
        return getPropertyAsString("callbackUrl");
    }

    public String getFallbackUrl() {
        return getPropertyAsString("fallbackUrl");
    }

    public String getText() {
        return getPropertyAsString("text");
    }

    public Date getTime() {
        return getPropertyAsDate("time");
    }

    public Long getCallbackTimeout() {
        return getPropertyAsLong("callbackTimeout");
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + getId() + '\'' +
                ", messageId='" + getMessageId() + '\'' +
                ", from='" + getFrom() + '\'' +
                ", to='" + getTo() + '\'' +
                ", state='" + getState() + '\'' +
                ", direction='" + getDirection() + '\'' +
                ", callbackUrl='" + getCallbackUrl() + '\'' +
                ", fallbackUrl='" + getFallbackUrl() + '\'' +
                ", text='" + getText() + '\'' +
                ", time=" + getTime() +
                ", callbackTimeout=" + getCallbackTimeout() +
                '}';
    }
}
