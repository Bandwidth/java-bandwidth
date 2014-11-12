package com.bandwidth.sdk.exception;

public class XMLMarshallingException extends Exception {
    public XMLMarshallingException(String s) { super(); }
    public XMLMarshallingException (String message, Throwable exception) {
        super(message, exception);
    }
}

