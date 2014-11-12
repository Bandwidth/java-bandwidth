package com.bandwidth.sdk.xml.elements;

import com.bandwidth.sdk.exception.XMLInvalidAttributeException;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Redirect")
public class Redirect implements Elements {

    private String requestUrl;
    private Integer timeout;

    public Redirect() {
        super();
    }

    public Redirect(final String requestUrl, final int timeout) throws XMLInvalidAttributeException {
        setRequestUrl(requestUrl);
        setTimeout(timeout);
    }

    @XmlAttribute(name = "requestUrl", required = true)
    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(final String requestUrl) throws XMLInvalidAttributeException {
        if ((requestUrl != null) && (!requestUrl.trim().isEmpty())) {
            this.requestUrl = requestUrl;
        } else {
            throw new XMLInvalidAttributeException("requestUrl mustn't not be empty or null");
        }
    }

    @XmlAttribute(name = "timeout", required = true)
    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(final int timeout) throws XMLInvalidAttributeException {
        if (timeout > 0) {
            this.timeout = timeout;
        } else {
            throw new XMLInvalidAttributeException("timeout must be greater than 0");
        }
    }

    @Override
    public String toString() {
        return "Redirect{" +
                "requestUrl='" + requestUrl + '\'' +
                ", timeout=" + timeout +
                '}';
    }
}