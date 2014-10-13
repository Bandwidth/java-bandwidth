package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.BandwidthRestClient;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.Date;

/**
 * Information about message.
 *
 * @author vpotapenko
 */
public class Message extends BaseModelObject {
	
    /**
     * Gets information about a previously sent or received message.
     *
     * @param id message id
     * @return information about message
     * @throws IOException
     */
    public static Message getMessage(String id) throws IOException {
    	
    	BandwidthRestClient client = BandwidthRestClient.getInstance();
        String messagesUri = client.getUserResourceUri(BandwidthConstants.MESSAGES_URI_PATH);
        String uri = StringUtils.join(new String[]{
                messagesUri,
                id
        }, '/');
        JSONObject jsonObject = client.getObject(uri);
        return new Message(client, jsonObject);
    }
    
    /**
     * Factory method for Message list, returns a list of Message objects with default page, size
     * @return
     * @throws IOException
     */
    public static ResourceList<Message> getMessages() throws IOException {
    	
    	// default page size is 25
     	return getMessages(0, 25);
    }
    
    /**
     * Factory method for Message list, returns a list of Message objects with page, size preference
     * @param page
     * @param size
     * @return
     * @throws IOException
     */
    public static ResourceList<Message> getMessages(int page, int size) throws IOException {
    	
        String messageUri = BandwidthRestClient.getInstance().getUserResourceUri(BandwidthConstants.MESSAGES_URI_PATH);

        ResourceList<Message> messages = 
        			new ResourceList<Message>(page, size, messageUri, Message.class);

        messages.initialize();
        
        return messages;
    }
    
	

    public Message(BandwidthRestClient client, JSONObject jsonObject) {
        super(client, jsonObject);
    }

    @Override
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
