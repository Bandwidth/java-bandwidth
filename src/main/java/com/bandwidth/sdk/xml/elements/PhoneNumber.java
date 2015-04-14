package com.bandwidth.sdk.xml.elements;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "PhoneNumber")
public class PhoneNumber implements Elements {

    private String number;

    public PhoneNumber() {
        super();
    }

    public PhoneNumber(String number) {
        setNumber(number);
    }

    @XmlValue
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "PhoneNumber{" +
                "number='" + number + '\'' +
                '}';
    }
}
