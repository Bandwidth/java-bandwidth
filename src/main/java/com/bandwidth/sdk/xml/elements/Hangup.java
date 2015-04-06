package com.bandwidth.sdk.xml.elements;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Hangup")
public class Hangup implements Elements {
    
    
    public Hangup() {}

    @Override
    public String toString() {
        return "Hangup{ }";
    }
}