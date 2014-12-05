package com.bandwidth.sdk.xml.elements;

import com.bandwidth.sdk.exception.XMLInvalidAttributeException;
import com.bandwidth.sdk.exception.XMLInvalidTagContentException;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "Record")
public class Record implements Elements {
    private String requestUrl;
    private Integer requestUrlTimeout;
    private String terminatingDigits;
    private Integer maxDuration;
    private Boolean transcribe;
    private String transcribeCallbackUrl;

    public Record() {
        super();
    }

    public Record(final String requestUrl, final Integer requestUrlTimeout) throws XMLInvalidTagContentException, XMLInvalidAttributeException {
        setRequestUrl(requestUrl);
        setRequestUrlTimeout(requestUrlTimeout);
    }

    @XmlAttribute(name = "requestUrl", required = true)
    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) throws XMLInvalidAttributeException {
        if ((requestUrl != null) && (!requestUrl.trim().isEmpty())) {
            this.requestUrl = requestUrl;
        } else {
            throw new XMLInvalidAttributeException("requestUrl must not be empty or null");
        }
    }

    @XmlAttribute(name = "requestUrlTimeout", required = true)
    public Integer getRequestUrlTimeout() {
        return requestUrlTimeout;
    }

    public void setRequestUrlTimeout(Integer requestUrlTimeout) throws XMLInvalidAttributeException {
        if ((requestUrlTimeout != null) && (requestUrlTimeout > 0)) {
            this.requestUrlTimeout = requestUrlTimeout;
        } else {
            throw new XMLInvalidAttributeException("requestUrlTimeout must be greater than zero");
        }
    }

    @XmlAttribute(name = "terminatingDigits")
    public String getTerminatingDigits() {
        return terminatingDigits;
    }

    public void setTerminatingDigits(String terminatingDigits) throws XMLInvalidAttributeException {
        if ((terminatingDigits != null) && (!terminatingDigits.trim().isEmpty())) {
            this.terminatingDigits = terminatingDigits;
        } else {
            throw new XMLInvalidAttributeException("terminatingDigits must not be empty or null");
        }
    }

    @XmlAttribute(name = "maxDuration")
    public Integer getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(Integer maxDuration) throws XMLInvalidAttributeException {
        if ((maxDuration != null) && (maxDuration > 0)) {
            this.maxDuration = maxDuration;
        } else {
            throw new XMLInvalidAttributeException("maxDuration must not be null");
        }
    }

    @XmlAttribute(name = "transcribe")
    public Boolean getTranscribe() {
        return transcribe;
    }

    public void setTranscribe(Boolean transcribe) throws XMLInvalidAttributeException {
        if (transcribe != null) {
            this.transcribe = transcribe;
        } else {
            throw new XMLInvalidAttributeException("transcribe must not be null");
        }
    }

    @XmlAttribute(name = "transcribeCallbackUrl")
    public String getTranscribeCallbackUrl() {
        return transcribeCallbackUrl;
    }

    public void setTranscribeCallbackUrl(String transcribeCallbackUrl) throws XMLInvalidAttributeException {
        if ((transcribeCallbackUrl != null) && (!transcribeCallbackUrl.trim().isEmpty()))  {
            this.transcribeCallbackUrl = transcribeCallbackUrl;
        } else {
            throw new XMLInvalidAttributeException("transcribeCallbackUrl must not be null");
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Record{");
        sb.append("requestUrl='").append(requestUrl).append('\'');
        sb.append(", requestUrlTimeout=").append(requestUrlTimeout);
        sb.append(", terminatingDigits='").append(terminatingDigits).append('\'');
        sb.append(", maxDuration=").append(maxDuration);
        sb.append(", transcribe=").append(transcribe);
        sb.append(", transcribeCallbackUrl='").append(transcribeCallbackUrl).append('\'');
        sb.append('}');
        return sb.toString();
    }
}