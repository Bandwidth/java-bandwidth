package com.bandwidth.sdk.exception;

public class XMLMarshallingException extends Exception {

    private static final long serialVersionUID = 1557359792155861255L;
    
    public XMLMarshallingException(final String s) { super(); }
    public XMLMarshallingException (final String message, final Throwable exception) {
        super(message, exception);
    }
}

