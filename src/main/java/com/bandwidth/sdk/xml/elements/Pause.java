package com.bandwidth.sdk.xml.elements;

import com.bandwidth.sdk.exception.XMLInvalidAttributeException;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Pause")
public class Pause implements Elements {

    private int length;

    public Pause() {
    }

    public Pause(int length) throws XMLInvalidAttributeException {
        setLength(length);
    }

    @XmlAttribute(name = "length")
    public int getLength() {
        return length;
    }

    public void setLength(int length) throws XMLInvalidAttributeException {
        if (0 > length || length > 3600) {
            throw new XMLInvalidAttributeException("'length' must be between 0 and 3600 inclusive");
        }
        this.length = length;
    }

    @Override
    public String toString() {
        return "Pause{" +
                "length='" + length + '\'' +
                '}';
    }
}
