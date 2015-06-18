package com.bandwidth.sdk;

public class AppPlatformException extends Exception {

    private static final long serialVersionUID = 1044495525539824275L;

	private int status;

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

	public AppPlatformException(String message, int status) {
		super(message);
		this.status = status;
	}

	public int getStatus() {
		return status;
	}
}
