package com.bandwidth.sdk.xml.elements;

import com.bandwidth.sdk.exception.XMLInvalidAttributeException;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "SpeakSentence")
public class SpeakSentence implements Elements {
    private String voice;
    private String gender;
    private String locale;
    private String sentence;

    public SpeakSentence() {
        super();
    }

    public SpeakSentence(final String sentence,
                         final String voice,
                         final String gender,
                         final String locale) throws XMLInvalidAttributeException {
        setSentence(sentence);
        setVoice(voice);
        setGender(gender);
        setLocale(locale);
    }

    @XmlAttribute(name = "voice", required = true)
    public String getVoice() {
        return voice;
    }

    public void setVoice(final String voice) throws XMLInvalidAttributeException {
        if ((voice != null) && (!voice.trim().isEmpty())) {
            this.voice = voice;
        } else {
            throw new XMLInvalidAttributeException("voice mustn't not be empty or null");
        }
    }

    @XmlAttribute(name = "gender", required = true)
    public String getGender() {
        return gender;
    }

    public void setGender(final String gender) throws XMLInvalidAttributeException {
        if ((gender != null) && (!gender.trim().isEmpty())) {
            this.gender = gender;
        } else {
            throw new XMLInvalidAttributeException("gender mustn't not be empty or null");
        }
    }

    @XmlAttribute(name = "locale", required = true)
    public String getLocale() {
        return locale;
    }

    public void setLocale(final String locale) throws XMLInvalidAttributeException {
        if ((locale != null) && (!locale.trim().isEmpty())) {
            this.locale = locale;
        } else {
            throw new XMLInvalidAttributeException("locale mustn't not be empty or null");
        }
    }

    @XmlValue
    public String getSentence() {
        return sentence;
    }

    public void setSentence(final String sentence) throws XMLInvalidAttributeException {
        if ((sentence != null) && (!sentence.trim().isEmpty())) {
            this.sentence = sentence;
        } else {
            throw new XMLInvalidAttributeException("sentence mustn't not be empty or null");
        }
    }

    @Override
    public String toString() {
        return "SpeakSentence{" +
                "voice='" + voice + '\'' +
                ", gender='" + gender + '\'' +
                ", locale='" + locale + '\'' +
                ", sentence='" + sentence + '\'' +
                '}';
    }
}