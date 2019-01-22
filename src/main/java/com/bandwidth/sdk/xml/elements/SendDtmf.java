package com.bandwidth.sdk.xml.elements;

import com.bandwidth.sdk.exception.XMLInvalidTagContentException;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "SendDtmf")
public class SendDtmf implements Elements {

    private String value;

    public SendDtmf() {
    }

    public SendDtmf(String value) throws XMLInvalidTagContentException {
        setValue(value);
    }

    @XmlValue
    public String getValue() {
        return value;
    }

    public void setValue(String value) throws XMLInvalidTagContentException {
        if (value == null || !value.matches("[,wW\\d\\*#]{1,92}")) {
            throw new XMLInvalidTagContentException("'value' contains invalid characters or wrong length");
        }

        this.value = value;
    }

    @Override
    public String toString() {
        return "SendDtmf{" +
                "value='" + value + '\'' +
                '}';
    }
}
