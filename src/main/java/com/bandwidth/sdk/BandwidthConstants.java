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
    public static final String CALLS_URI_PATH = "calls";
    public static final String PHONE_NUMBER_URI_PATH = "phoneNumbers";
    public static final String CONFERENCES_URI_PATH="conferences";
    public static final String ERRORS_URI_PATH = "errors";
    public static final String MESSAGES_URI_PATH = "messages";
    public static final String AVAILABLE_NUMBERS_URI_PATH = "availableNumbers";
    public static final String AVAILABLE_NUMBERS_TOLL_FREE_URI_PATH = "availableNumbers/tollFree";
    public static final String AVAILABLE_NUMBERS_LOCAL_URI_PATH = "availableNumbers/local";
    public static final String BRIDGES_URI_PATH = "bridges";
    public static final String RECORDINGS_URI_PATH = "recordings";
    public static final String ACCOUNT_URI_PATH = "account";
    public static final String APPLICATIONS_URI_PATH = "applications";
    public static final String MEDIA_URI_PATH = "media";
    public static final String ACCOUNT_TRANSACTIONS_URI_PATH = "account/transactions";
    public static final String GATHER_URI_PATH = "gather";

    public static final String CONTENT_TYPE_JPEG = "image/jpeg";

}
