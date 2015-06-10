package com.bandwidth.sdk.xml.elements;

import com.bandwidth.sdk.exception.XMLInvalidAttributeException;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Gather")
public class Gather implements Elements {

    protected String requestUrl;

    protected Integer requestUrlTimeout;

    protected String terminatingDigits;

    protected Integer maxDigits;

    protected Integer interDigitTimeout;

    protected String bargeable;

    protected SpeakSentence speakSentence = null;

    protected PlayAudio playAudio = null;

    public Gather() {
        super();
    }

    public Gather(final String requestUrl,
                  final Integer requestUrlTimeout,
                  final String terminatingDigits,
                  final Integer maxDigits,
                  final Integer interDigitTimeout,
                  final String bargeable) throws XMLInvalidAttributeException {
        setRequestUrl(requestUrl);
        setRequestUrlTimeout(requestUrlTimeout);
        setTerminatingDigits(terminatingDigits);
        setMaxDigits(maxDigits);
        setInterDigitTimeout(interDigitTimeout);
        setBargeable(bargeable);

    }

    public Gather(final String requestUrl,
                  final Integer requestUrlTimeout,
                  final String terminatingDigits,
                  final Integer maxDigits,
                  final Integer interDigitTimeout,
                  final String bargeable,
                  final SpeakSentence speakSentence) throws XMLInvalidAttributeException {
        setRequestUrl(requestUrl);
        setRequestUrlTimeout(requestUrlTimeout);
        setTerminatingDigits(terminatingDigits);
        setMaxDigits(maxDigits);
        setInterDigitTimeout(interDigitTimeout);
        setBargeable(bargeable);
        setSpeakSentence(speakSentence);
    }

    public Gather(final String requestUrl,
                  final Integer requestUrlTimeout,
                  final String terminatingDigits,
                  final Integer maxDigits,
                  final Integer interDigitTimeout,
                  final String bargeable,
                  final PlayAudio playAudio) throws XMLInvalidAttributeException {
        setRequestUrl(requestUrl);
        setRequestUrlTimeout(requestUrlTimeout);
        setTerminatingDigits(terminatingDigits);
        setMaxDigits(maxDigits);
        setInterDigitTimeout(interDigitTimeout);
        setBargeable(bargeable);
        setPlayAudio(playAudio);
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
        if (requestUrlTimeout > 0) {
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

    @XmlAttribute(name = "maxDigits")
    public Integer getMaxDigits() {
        return maxDigits;
    }

    public void setMaxDigits(Integer maxDigits) throws XMLInvalidAttributeException {
        if (maxDigits > 0) {
            this.maxDigits = maxDigits;
        } else {
            throw new XMLInvalidAttributeException("maxDigits must be greater than zero");
        }
    }

    @XmlAttribute(name = "interDigitTimeout")
    public Integer getInterDigitTimeout() {
        return interDigitTimeout;
    }

    public void setInterDigitTimeout(Integer interDigitTimeout) throws XMLInvalidAttributeException {
        if (interDigitTimeout > 0) {
            this.interDigitTimeout = interDigitTimeout;
        } else {
            throw new XMLInvalidAttributeException("interDigitTimeout must be greater than zero");
        }
    }

    @XmlAttribute(name = "bargeable")
    public String getBargeable() {
        return bargeable;
    }

    public void setBargeable(String bargeable) throws XMLInvalidAttributeException {
        if ((bargeable != null) && (!bargeable.trim().isEmpty())) {
            this.bargeable = bargeable;
        } else {
            throw new XMLInvalidAttributeException("bargeable must not be empty or null");
        }
    }

    @XmlElement(name = "PlayAudio")
    public PlayAudio getPlayAudio() {
        return playAudio;
    }

    public void setPlayAudio(final PlayAudio playAudio) {
        this.playAudio = playAudio;
    }

    @XmlElement(name = "SpeakSentence")
    public SpeakSentence getSpeakSentence() {
        return speakSentence;
    }

    public void setSpeakSentence(final SpeakSentence speakSentence) {
        this.speakSentence = speakSentence;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Gather{");
        sb.append("requestUrl='").append(requestUrl).append('\'');
        sb.append(", requestUrlTimeout=").append(requestUrlTimeout);
        sb.append(", terminatingDigits='").append(terminatingDigits).append('\'');
        sb.append(", maxDigits=").append(maxDigits);
        sb.append(", interDigitTimeout=").append(interDigitTimeout);
        sb.append(", bargeable='").append(bargeable).append('\'');
        sb.append(", speakSentence=").append(speakSentence);
        sb.append(", playAudio=").append(playAudio);
        sb.append('}');
        return sb.toString();
    }
}