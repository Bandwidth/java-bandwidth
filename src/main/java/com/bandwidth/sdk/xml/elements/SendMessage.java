package com.bandwidth.sdk.xml.elements;

import com.bandwidth.sdk.exception.XMLInvalidAttributeException;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "SendMessage")
public class SendMessage implements Elements {

    private String from;
    private String to;
    private String text;
    private String requestUrl;
    private Integer requestUrlTimeout;
    private String statusCallbackUrl;

    public SendMessage() {
    }

    public SendMessage(String from, String to, String text) throws XMLInvalidAttributeException {
        setFrom(from);
        setTo(to);
        setText(text);
    }

    @XmlAttribute(name = "from", required = true)
    public String getFrom() {
        return from;
    }

    public void setFrom(final String from) throws XMLInvalidAttributeException {
        if ((from != null) && (!from.trim().isEmpty())) {
            this.from = from;
        } else {
            throw new XMLInvalidAttributeException("from must not be empty or null");
        }
    }

    @XmlAttribute(name = "to", required = true)
    public String getTo() {
        return to;
    }

    public void setTo(final String to) throws XMLInvalidAttributeException {
        if ((to != null) && (!to.trim().isEmpty())) {
            this.to = to;
        } else {
            throw new XMLInvalidAttributeException("to must not be empty or null");
        }
    }

    @XmlValue
    public String getText() {
        return text;
    }

    public void setText(String text) throws XMLInvalidAttributeException {
        if((text != null) && (!text.trim().isEmpty())) {
            this.text = text;
        } else {
            throw new XMLInvalidAttributeException("text must not be empty or null");
        }
    }

    @XmlAttribute(name = "requestUrl")
    public String getRequestUrl() {
        return requestUrl;
    }

    @XmlAttribute(name = "requestUrlTimeout")
    public Integer getRequestUrlTimeout() {
        return requestUrlTimeout;
    }

    public void setRequestUrl(String requestUrl, Integer requestUrlTimeout) throws XMLInvalidAttributeException {
        if(requestUrl != null && (!requestUrl.trim().isEmpty())) {
            this.requestUrl = requestUrl;
        } else {
            throw new XMLInvalidAttributeException("requestUrl must not be null");
        }

        this.requestUrlTimeout = requestUrlTimeout;
    }

    @XmlAttribute(name = "statusCallbackUrl")
    public String getStatusCallbackUrl() {
        return statusCallbackUrl;
    }

    public void setStatusCallbackUrl(String statusCallbackUrl) throws XMLInvalidAttributeException {
        if(statusCallbackUrl != null && (!statusCallbackUrl.trim().isEmpty())) {
            this.statusCallbackUrl = statusCallbackUrl;
        } else {
            throw new XMLInvalidAttributeException("statusCallbackUrl must not be null");
        }

        this.statusCallbackUrl = statusCallbackUrl;
    }

    @Override
    public String toString() {
        return "SendMessage{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", text='" + text + '\'' +
                ", requestUrl='" + requestUrl + '\'' +
                ", requestUrlTimeout=" + requestUrlTimeout +
                '}';
    }
}
