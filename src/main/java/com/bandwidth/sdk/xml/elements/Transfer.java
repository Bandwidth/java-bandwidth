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
<<<<<<< HEAD
    protected SpeakSentence speakSentence = null;
    protected List<PhoneNumber> phoneNumberList;
=======
    protected Integer callTimeout;
    protected Boolean recordingEnabled;
    protected String recordingCallbackUrl;
    protected String terminatingDigits;
    protected Integer maxDuration;
    protected String fileFormat;
    protected Boolean transcribe;
    protected String transcribeCallbackUrl;

    protected SpeakSentence speakSentence;
    protected PlayAudio playAudio;
>>>>>>> master

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

    @XmlAttribute(name = "callTimeout")
    public Integer getCallTimeout() {
        return callTimeout;
    }

    public void setCallTimeout(Integer callTimeout) throws XMLInvalidAttributeException {
        if ((callTimeout == null) || (callTimeout < 0)) {
            throw new XMLInvalidAttributeException("callTimeout mustn't not be null or less than zero");
        }
        this.callTimeout = callTimeout;
    }

    @XmlAttribute(name = "recordingEnabled")
    public Boolean getRecordingEnabled() {
        return recordingEnabled;
    }

    public void setRecordingEnabled(Boolean recordingEnabled) throws XMLInvalidAttributeException {
        if (recordingEnabled == null) {
            throw new XMLInvalidAttributeException("recordingEnabled mustn't not be null");
        }
        this.recordingEnabled = recordingEnabled;
    }

    @XmlAttribute(name = "recordingCallbackUrl")
    public String getRecordingCallbackUrl() {
        return recordingCallbackUrl;
    }

    public void setRecordingCallbackUrl(String recordingCallbackUrl) throws XMLInvalidAttributeException {
        if ((recordingCallbackUrl == null) || (recordingCallbackUrl.trim().isEmpty())) {
            throw new XMLInvalidAttributeException("recordingCallbackUrl mustn't not be empty or null");
        }
        this.recordingCallbackUrl = recordingCallbackUrl;
    }

    @XmlAttribute(name = "terminatingDigits")
    public String getTerminatingDigits() {
        return terminatingDigits;
    }

    public void setTerminatingDigits(String terminatingDigits) throws XMLInvalidAttributeException {
        if ((terminatingDigits == null) || (terminatingDigits.trim().isEmpty())) {
            throw new XMLInvalidAttributeException("terminatingDigits mustn't not be empty or null");
        }
        this.terminatingDigits = terminatingDigits;
    }

    @XmlAttribute(name = "maxDuration")
    public Integer getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(Integer maxDuration) throws XMLInvalidAttributeException {
        if ((maxDuration == null) || (maxDuration < 0)) {
            throw new XMLInvalidAttributeException("maxDuration mustn't not be null or less than zero");
        }
        this.maxDuration = maxDuration;
    }

    @XmlAttribute(name = "fileFormat")
    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) throws XMLInvalidAttributeException {
        if ((fileFormat == null) || (fileFormat.trim().isEmpty())) {
            throw new XMLInvalidAttributeException("fileFormat mustn't not be empty or null");
        }
        this.fileFormat = fileFormat;
    }

    @XmlAttribute(name = "transcribe")
    public Boolean getTranscribe() {
        return transcribe;
    }

    public void setTranscribe(Boolean transcribe) throws XMLInvalidAttributeException {
        if (transcribe == null) {
            throw new XMLInvalidAttributeException("transcribe mustn't not be null");
        }
        this.transcribe = transcribe;
    }

    @XmlAttribute(name = "transcribeCallbackUrl")
    public String getTranscribeCallbackUrl() {
        return transcribeCallbackUrl;
    }

    public void setTranscribeCallbackUrl(String transcribeCallbackUrl) throws XMLInvalidAttributeException {
        if ((transcribeCallbackUrl == null) || (transcribeCallbackUrl.trim().isEmpty())) {
            throw new XMLInvalidAttributeException("transcribeCallbackUrl mustn't not be empty or null");
        }
        this.transcribeCallbackUrl = transcribeCallbackUrl;
    }

    @XmlElement(name = "SpeakSentence")
    public SpeakSentence getSpeakSentence() {
        return speakSentence;
    }

    public void setSpeakSentence(final SpeakSentence speakSentence) {
        this.speakSentence = speakSentence;
    }

<<<<<<< HEAD
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
=======
    @XmlElement(name = "PlayAudio")
    public PlayAudio getPlayAudio() {
        return playAudio;
    }

    public void setPlayAudio(final PlayAudio playAudio) {
        this.playAudio = playAudio;
>>>>>>> master
    }

    @Override
    public String toString() {
<<<<<<< HEAD
        return "Transfer{" +
                "transferTo='" + transferTo + '\'' +
                ", transferCallerId='" + transferCallerId + '\'' +
                ", speakSentence=" + speakSentence +
                ", phoneNumbers=" + phoneNumberList +
                '}';
=======
        final StringBuilder sb = new StringBuilder("Transfer{");
        sb.append("transferTo='").append(transferTo).append('\'');
        sb.append(", transferCallerId='").append(transferCallerId).append('\'');
        sb.append(", callTimeout='").append(callTimeout).append('\'');
        sb.append(", recordingEnabled='").append(recordingEnabled).append('\'');
        sb.append(", recordingCallbackUrl='").append(recordingCallbackUrl).append('\'');
        sb.append(", terminatingDigits='").append(terminatingDigits).append('\'');
        sb.append(", maxDuration='").append(maxDuration).append('\'');
        sb.append(", fileFormat='").append(fileFormat).append('\'');
        sb.append(", transcribe='").append(transcribe).append('\'');
        sb.append(", transcribeCallbackUrl='").append(transcribeCallbackUrl).append('\'');
        sb.append(", speakSentence=").append(speakSentence);
        sb.append(", playAudio=").append(playAudio);
        sb.append('}');
        return sb.toString();
>>>>>>> master
    }
}