package com.bandwidth.sdk.exception;

public class XMLInvalidTagContentException extends Exception {
    public XMLInvalidTagContentException() { super(); }
    public XMLInvalidTagContentException(String message) { super(message); }
    public XMLInvalidTagContentException(String message, Throwable exception) {
        super(message, exception);
    }
}
