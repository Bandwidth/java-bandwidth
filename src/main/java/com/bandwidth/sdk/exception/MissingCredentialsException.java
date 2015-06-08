package com.bandwidth.sdk.exception;

import java.io.IOException;

public class MissingCredentialsException extends IOException {

    private static final long serialVersionUID = 8463765824822860908L;

    public MissingCredentialsException() {
        super("Missing credentials. There are 3 ways to set these credentials:\n" +
                " *\n" +
                " * 1. Via environment variables\n" +
                " * 2. Via Java VM System Properties, set as -D arguments on the VM command line.\n" +
                " * 3. Directly by way of a method call on the BandwidthClient object");
    }
}
