package com.bandwidth.sdk.xml.elements;

import com.bandwidth.sdk.exception.XMLInvalidAttributeException;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Redirect")
public class Redirect implements Elements {

    private String requestUrl;
    private Integer requestUrlTimeout;

    public Redirect() {
        super();
    }

    public Redirect(final String requestUrl, final int requestUrlTimeout) throws XMLInvalidAttributeException {
        setRequestUrl(requestUrl);
        setRequestUrlTimeout(requestUrlTimeout);
    }

    @XmlAttribute(name = "requestUrl", required = true)
    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(final String requestUrl) throws XMLInvalidAttributeException {
        if ((requestUrl == null) || (requestUrl.trim().isEmpty())) {
            throw new XMLInvalidAttributeException("requestUrl mustn't not be empty or null");
        }
        this.requestUrl = requestUrl;
    }

    @XmlAttribute(name = "requestUrlTimeout", required = true)
    public int getRequestUrlTimeout() {
        return requestUrlTimeout;
    }

    public void setRequestUrlTimeout(final int requestUrlTimeout) throws XMLInvalidAttributeException {
        if (requestUrlTimeout <= 0) {
            throw new XMLInvalidAttributeException("requestUrlTimeout must be greater than 0");
        }
        this.requestUrlTimeout = requestUrlTimeout;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Redirect{");
        sb.append("requestUrl='").append(requestUrlTimeout).append('\'');
        sb.append(", requestUrlTimeout=").append(requestUrlTimeout);
        sb.append('}');
        return sb.toString();
    }
}