package com.bandwidth.sdk;

/**
 * @author vpotapenko
 */
public abstract class BandwidthConstants {

    private BandwidthConstants() {
    }

    public static final String TRANSACTION_DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    // REST constants
    public static final String API_ENDPOINT = "https://api.catapult.inetwork.com";
    public static final String API_VERSION = "v1";

    public static final String USERS_URI_PATH = "users/%s"; // userId as a parameter
    public static final String CALLS_URI_PATH = "/calls";
    public static final String PHONE_NUMBER_URI_PATH = "/phoneNumbers";

}
