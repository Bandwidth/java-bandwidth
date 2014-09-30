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
    public static final String CONFERENCES_URI_PATH="/conferences";
    public static final String ERRORS_URI_PATH = "/errors";
    public static final String MESSAGES_URI_PATH = "/messages";
    public static final String AVAILABLE_NUMBERS_URI_PATH = "/availableNumbers";
    public static final String BRIDGES_URI_PATH = "/bridges";
    public static final String RECORDINGS_URI_PATH = "/recordings";

}
