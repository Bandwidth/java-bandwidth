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

    public static String BANDWIDTH_USER_ID = "BANDWIDTH_USER_ID";
    public static String BANDWIDTH_API_TOKEN = "BANDWIDTH_API_TOKEN";
    public static String BANDWIDTH_API_SECRET = "BANDWIDTH_API_SECRET";
    public static String BANDWIDTH_API_ENDPOINT = "BANDWIDTH_API_ENDPOINT";
    public static String BANDWIDTH_API_VERSION = "BANDWIDTH_API_VERSION";

    public static String BANDWIDTH_SYSPROP_USER_ID = "com.bandwidth.userId";
    public static String BANDWIDTH_SYSPROP_API_TOKEN = "com.bandwidth.apiToken";
    public static String BANDWIDTH_SYSPROP_API_SECRET = "com.bandwidth.apiSecret";
    public static String BANDWIDTH_SYSPROP_API_ENDPOINT = "com.bandwidth.apiEndpoint";
    public static String BANDWIDTH_SYSPROP_API_VERSION = "com.bandwidth.apiSecret";

    protected static final String GET = "get";
    protected static final String POST = "post";
    protected static final String PUT = "put";
    protected static final String DELETE = "delete";

    public static final String CONTENT_TYPE_JPEG = "image/jpeg";

}
