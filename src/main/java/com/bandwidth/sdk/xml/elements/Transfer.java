package com.bandwidth.sdk.xml.elements;

import com.bandwidth.sdk.exception.XMLInvalidAttributeException;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Transfer")
public class Transfer implements Elements {

    protected String transferTo;

    protected String transferCallerId;

    protected SpeakSentence speakSentence = null;

    public Transfer() {
        super();
    }

    public Transfer(final String transferTo, final String transferCallerId) throws XMLInvalidAttributeException {
        setTransferTo(transferTo);
        setTransferCallerId(transferCallerId);
    }

    public Transfer(final String transferTo,
                    final String transferCallerId,
                    final SpeakSentence speakSentence) throws XMLInvalidAttributeException {
        setTransferTo(transferTo);
        setTransferCallerId(transferCallerId);
        setSpeakSentence(speakSentence);
    }

    @XmlAttribute(name = "transferCallerId", required = true)
    public String getTransferCallerId() {
        return transferCallerId;
    }

    public void setTransferCallerId(final String transferCallerId) throws XMLInvalidAttributeException {
        if ((transferCallerId != null) && (!transferCallerId.trim().isEmpty())) {
            this.transferCallerId = transferCallerId;
        } else {
            throw new XMLInvalidAttributeException("transferCallerId mustn't not be empty or null");
        }
    }

    @XmlAttribute(name = "transferTo", required = true)
    public String getTransferTo() {
        return transferTo;
    }

    public void setTransferTo(final String transferTo) throws XMLInvalidAttributeException {
        if ((transferTo != null) && (!transferTo.trim().isEmpty())) {
            this.transferTo = transferTo;
        } else {
            throw new XMLInvalidAttributeException("transferTo mustn't not be empty or null");
        }
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
        return "Transfer{" +
                "transferTo='" + transferTo + '\'' +
                ", transferCallerId='" + transferCallerId + '\'' +
                ", speakSentence=" + speakSentence +
                '}';
    }
}