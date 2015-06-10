package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthClient;
import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.RestResponse;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
     * @throws IOException unexpected error
     */
    public static Message get(final String id) throws Exception {
    	
        return get(BandwidthClient.getInstance(), id);
    }
    
    /**
     * Gets information about a previously sent or received message.
     * @param client the client.
     * @param id message id
     * @return information about message
     * @throws IOException unexpected error
     */
    public static Message get(final BandwidthClient client, final String id) throws Exception {
    	
        final String messagesUri = client.getUserResourceInstanceUri(BandwidthConstants.MESSAGES_URI_PATH, id);

        final JSONObject jsonObject = toJSONObject(client.get(messagesUri, null));
        return new Message(client, jsonObject);
    }
    
    
    /**
     * Factory method for Message list, returns a list of Message objects with default page, size
     * @return the list
     * @throws IOException unexpected error
     */
    public static ResourceList<Message> list() throws Exception {
    	
    	// default page size is 25
     	return list(0, 25);
    }
    
    /**
     * Factory method for Message list, returns a list of Message objects with page, size preference
     * @param page the page
     * @param size the page size
     * @return the list
     * @throws IOException unexpected error
     */
    public static ResourceList<Message> list(final int page, final int size) throws Exception {
    	
        
        return list(BandwidthClient.getInstance(), page, size);
    }
    
    /**
     * Factory method for Message list, returns a list of Message objects with page, size preference
     * @param client the client.
     * @param page the page
     * @param size the page size
     * @return the list
     * @throws IOException unexpected error
     */
    public static ResourceList<Message> list(final BandwidthClient client, final int page, final int size) throws Exception {
    	
        final String messageUri = client.getUserResourceUri(BandwidthConstants.MESSAGES_URI_PATH);

        final ResourceList<Message> messages = 
        			new ResourceList<Message>(page, size, messageUri, Message.class);

        messages.setClient(client);
        messages.initialize();
        
        return messages;
    }
    
    /**
     * Convenience factory method to send a message, given the to number, the from number and the text
     * @param to the from number
     * @param from the to number
     * @param text the text
     * @return the message
     * @throws IOException unexpected error
     */
    public static Message create(final String to, final String from, final String text) throws Exception {
    	
    	final Map<String, Object> params = new HashMap<String, Object>();
    	params.put("to", to);
    	params.put("from", from);
    	params.put("text", text);
    	
    	return create(params);
    }

    /**
     * Convenience factory method to send a message with receipt, given the to number, the from number and the text
     * @param to the from number
     * @param from the to number
     * @param text the text
     * @param receiptRequest the receipt request option
     * @return the message
     * @throws IOException unexpected error
     */
    public static Message create(final String to, final String from, final String text, final ReceiptRequest receiptRequest) throws Exception {

        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("to", to);
        params.put("from", from);
        params.put("text", text);

        if (receiptRequest != null) {
            params.put("receiptRequested", receiptRequest.toString());
        } else {
            params.put("receiptRequested", ReceiptRequest.NONE.toString());
        }
        return create(params);
    }

    /**
     * Convenience factory method to send MMS messages. Create a MediaFile object using the Media upload method
     * and pass that in.
     *
     * @param to the from number
     * @param from the to number
     * @param text the text
     * @param media the media
     * @return the message
     * @throws Exception error.
     */
    public static Message create(final String to, final String from, final String text, final MediaFile media) throws Exception {

        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("to", to);
        params.put("from", from);
        params.put("text", text);
        params.put("media", media.getContent());

        return create(params);
    }
    
    /**
     * Convenience factory method to send a message, given a set of params
     * @param params the params
     * @return the message
     * @throws IOException unexpected error
     */
    public static Message create(final Map<String, Object>params) throws Exception {
    	
    	return create(BandwidthClient.getInstance(), params);
    	
    }
    
    /**
     * Factory method to send a message from a params object, given a client instance
     * @param client the client
     * @param params the params
     * @return the message
     * @throws IOException unexpected error
     */
    public static Message create(final BandwidthClient client, final Map<String, Object> params) throws Exception {
        final String messageUri = client.getUserResourceUri(BandwidthConstants.MESSAGES_URI_PATH);
        final RestResponse response = client.post(messageUri, params);
    	final String messageId = response.getLocation().substring(client.getPath(messageUri).length() + 1);

        return get(client, messageId);
    }
	

    public Message(final BandwidthClient client, final JSONObject jsonObject) {
        super(client, jsonObject);
    }

    @Override
    protected void setUp(final JSONObject jsonObject) {
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

    public String getReceiptRequested() {
        return getPropertyAsString("receiptRequested");
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
                ", receiptRequested=" + getReceiptRequested() +
                '}';
    }
}
