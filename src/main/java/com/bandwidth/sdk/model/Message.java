package com.bandwidth.sdk.model;

import com.bandwidth.sdk.BandwidthConstants;
import com.bandwidth.sdk.BandwidthRestClient;
import org.json.simple.JSONObject;

import java.util.Date;

/**
 * Information about message.
 *
 * @author vpotapenko
 */
public class Message extends BaseModelObject {

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
