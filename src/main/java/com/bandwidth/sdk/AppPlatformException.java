package com.bandwidth.sdk;

public class AppPlatformException extends Exception {

    private static final long serialVersionUID = 1044495525539824275L;

    public AppPlatformException() {
	}

	public AppPlatformException(final String message) {
		super(message);
	}

	public AppPlatformException(final Throwable cause) {
		super(cause);
	}

	public AppPlatformException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
