package com.bandwidth.sdk.exception;

public class XMLInvalidTagContentException extends Exception {
    
    private static final long serialVersionUID = 8530761862734579961L;
    
    public XMLInvalidTagContentException() { super(); }
    public XMLInvalidTagContentException(final String message) { super(message); }
    public XMLInvalidTagContentException(final String message, final Throwable exception) {
        super(message, exception);
    }
}
