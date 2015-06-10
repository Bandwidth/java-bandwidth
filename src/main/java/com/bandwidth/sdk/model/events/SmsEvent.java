package com.bandwidth.sdk.model.events;

import org.json.simple.JSONObject;

public class SmsEvent extends EventBase {

	public SmsEvent(final JSONObject json) {
		super(json);
	}

	public void execute(final Visitor visitor) {
		visitor.processEvent(this);
	}

    public String getDirection() {
        return getPropertyAsString("direction");
    }

    public String getFrom() {
        return getPropertyAsString("from");
    }

    public String getTo() {
        return getPropertyAsString("to");
    }

    public String getMessageId() {
        return getPropertyAsString("messageId");
    }

    public String getMessageUri() {
        return getPropertyAsString("messageUri");
    }

    public String getText() {
        return getPropertyAsString("text");
    }

    public String getApplicationId() {
        return getPropertyAsString("applicationId");
    }

    public String getState() {
        return getPropertyAsString("state");
    }

    public String getDeliveryState() {
        return getPropertyAsString("deliveryState");
    }

    public String getDeliveryCode() {
        return getPropertyAsString("deliveryCode");
    }
    public String getDeliveryDescription() {
        return getPropertyAsString("deliveryDescription");
    }

}
