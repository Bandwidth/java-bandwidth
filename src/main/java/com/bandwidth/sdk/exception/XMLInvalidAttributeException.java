package com.bandwidth.sdk.exception;

public class XMLInvalidAttributeException extends Exception {
    public XMLInvalidAttributeException () { super(); }
    public XMLInvalidAttributeException (String message) { super(message); }
    public XMLInvalidAttributeException (String message, Throwable exception) {
        super(message, exception);
    }
}
