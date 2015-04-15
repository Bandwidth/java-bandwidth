package com.bandwidth.sdk.xml.elements;

import com.bandwidth.sdk.exception.XMLInvalidAttributeException;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@XmlRootElement(name = "Transfer")
public class Transfer implements Elements {

    private static final int MAX_PHONE_NUMBERS = 7;

    protected String transferTo;
    protected String transferCallerId;
    protected SpeakSentence speakSentence = null;
    protected List<PhoneNumber> phoneNumberList;

    public Transfer() {
        super();
    }

    public Transfer(final String transferCallerId) throws XMLInvalidAttributeException {
        setTransferCallerId(transferCallerId);
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
        if ((transferCallerId == null) || (transferCallerId.trim().isEmpty())) {
            throw new XMLInvalidAttributeException("transferCallerId mustn't not be empty or null");
        }
        this.transferCallerId = transferCallerId;
    }

    @XmlAttribute(name = "transferTo", required = true)
    public String getTransferTo() {
        return transferTo;
    }

    public void setTransferTo(final String transferTo) throws XMLInvalidAttributeException {
        if(phoneNumberList == null || phoneNumberList.isEmpty()) {
            // transferTo is mandatory if no phone numbers were passed
            if ((transferTo == null) || (transferTo.trim().isEmpty())) {
                throw new XMLInvalidAttributeException("transferTo mustn't not be empty or null");
            }
        }
        this.transferTo = transferTo;
    }

    @XmlElement(name = "SpeakSentence")
    public SpeakSentence getSpeakSentence() {
        return speakSentence;
    }

    public void setSpeakSentence(final SpeakSentence speakSentence) {
        this.speakSentence = speakSentence;
    }

    @XmlElement(name = "PhoneNumber")
    public List<PhoneNumber> getPhoneNumberList() {
        if(phoneNumberList == null || phoneNumberList.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        return phoneNumberList;
    }

    public void setPhoneNumberList(List<PhoneNumber> phoneNumberList) throws XMLInvalidAttributeException {
        if(phoneNumberList != null && phoneNumberList.size() > MAX_PHONE_NUMBERS) {
            throw new XMLInvalidAttributeException(String.format("Transfer can only hold %s phone numbers", MAX_PHONE_NUMBERS));
        }
        this.phoneNumberList = phoneNumberList;
    }

    public void addPhoneNumber(String phoneNumber) throws XMLInvalidAttributeException {
        if(this.phoneNumberList == null) {
            this.phoneNumberList = new ArrayList<PhoneNumber>();
        }
        if(phoneNumberList.size() == MAX_PHONE_NUMBERS) {
            throw new XMLInvalidAttributeException(String.format("Transfer can only hold %s phone numbers", MAX_PHONE_NUMBERS));
        }
        this.phoneNumberList.add(new PhoneNumber(phoneNumber));
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "transferTo='" + transferTo + '\'' +
                ", transferCallerId='" + transferCallerId + '\'' +
                ", speakSentence=" + speakSentence +
                ", phoneNumbers=" + phoneNumberList +
                '}';
    }
}