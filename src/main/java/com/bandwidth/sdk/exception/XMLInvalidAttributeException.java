package com.bandwidth.sdk.exception;

public class XMLInvalidAttributeException extends Exception {
    
    private static final long serialVersionUID = 6829292458161743043L;
    
    public XMLInvalidAttributeException () { super(); }
    public XMLInvalidAttributeException (final String message) { super(message); }
    public XMLInvalidAttributeException (final String message, final Throwable exception) {
        super(message, exception);
    }
}
