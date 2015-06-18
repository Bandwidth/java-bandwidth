package com.bandwidth.sdk.exception;

public class MissingCredentialsException extends RuntimeException {

    private static final long serialVersionUID = 8463765824822860908L;

    public MissingCredentialsException() {
        super("Missing credentials. \n" +
                "\tThere are 3 ways to set these credentials:\n\n" +
                "\t1. Via Java VM System Properties, set as -D arguments on the VM command line:\n" +
                "\t\t-Dcom.bandwidth.userId=<myUserId>\n" +
                "\t\t-Dcom.bandwidth.apiToken=<myApiToken>\n" +
                "\t\t-Dcom.bandwidth.apiSecret=<myApiSecret>\n" +
                "\t2. Via environment variables:\n" +
                "\t\texport BANDWIDTH_USER_ID=<myUserId>\n" +
                "\t\texport BANDWIDTH_API_TOKEN=<myApiToken>\n" +
                "\t\texport BANDWIDTH_API_SECRET=<myApiSecret>\n" +
                "\t3. Directly by way of a method call on the BandwidthClient object\n" +
                "\t\tBandwidthClient.getInstance().setCredentials(<myUserId>, <myApiToken>, <myApiSecret>)\n\n" +
                "\tNotice: if credentials are not set explicitly, the sdk will first look for VM properties. " +
                "If those are not present, it will look for environments vars." +
                "\n\tTo override VM properties and env vars, use client's method as described on 3th option.\n");
    }
}
