package com.bandwidth.sdk;

/**
 * @author vpotapenko
 */
public interface BandwidthConstants {

    String TRANSACTION_DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    // REST constants
    String API_ENDPOINT = "https://api.catapult.inetwork.com";
    String API_VERSION = "v1";

    //Endpoint constants
    String USERS_URI_PATH = "users/%s"; // userId as a parameter
    String CALLS_URI_PATH = "calls";
    String PHONE_NUMBER_URI_PATH = "phoneNumbers";
    String CONFERENCES_URI_PATH="conferences";
    String ERRORS_URI_PATH = "errors";
    String MESSAGES_URI_PATH = "messages";
    String AVAILABLE_NUMBERS_URI_PATH = "availableNumbers";
    String AVAILABLE_NUMBERS_TOLL_FREE_URI_PATH = "availableNumbers/tollFree";
    String AVAILABLE_NUMBERS_LOCAL_URI_PATH = "availableNumbers/local";
    String BRIDGES_URI_PATH = "bridges";
    String DOMAINS_URI_PATH = "domains";
    String ENDPOINTS_URI_PATH = "domains/%s/endpoints"; //endpoint id as parameter
    String RECORDINGS_URI_PATH = "recordings";
    String ACCOUNT_URI_PATH = "account";
    String APPLICATIONS_URI_PATH = "applications";
    String MEDIA_URI_PATH = "media";
    String ACCOUNT_TRANSACTIONS_URI_PATH = "account/transactions";
    String GATHER_URI_PATH = "gather";

    // Http connection constants
    String HTTP_MAX_TOTAL_CONNECTIONS = "200";
    String HTTP_MAX_DEFAULT_CONNECTIONS_PER_ROUTE = "20";

    String BANDWIDTH_USER_ID = "BANDWIDTH_USER_ID";
    String BANDWIDTH_API_TOKEN = "BANDWIDTH_API_TOKEN";
    String BANDWIDTH_API_SECRET = "BANDWIDTH_API_SECRET";
    String BANDWIDTH_API_ENDPOINT = "BANDWIDTH_API_ENDPOINT";
    String BANDWIDTH_API_VERSION = "BANDWIDTH_API_VERSION";

    String BANDWIDTH_SYSPROP_USER_ID = "com.bandwidth.userId";
    String BANDWIDTH_SYSPROP_API_TOKEN = "com.bandwidth.apiToken";
    String BANDWIDTH_SYSPROP_API_SECRET = "com.bandwidth.apiSecret";
    String BANDWIDTH_SYSPROP_API_ENDPOINT = "com.bandwidth.apiEndpoint";
    String BANDWIDTH_SYSPROP_API_VERSION = "com.bandwidth.apiVersion";

    String CONTENT_TYPE_JPEG = "image/jpeg";

    String BANDWIDTH_HTTP_MAX_TOTAL_CONNECTIONS = "BANDWIDTH_MAX_TOTAL_CONNECTIONS";
    String BANDWIDTH_HTTP_MAX_DEFAULT_CONNECTIONS_PER_ROUTE = "BANDWIDTH_MAX_DEFAULT_CONNECTIONS_PER_ROUTE";

    String BANDWIDTH_SYSPROP_HTTP_MAX_TOTAL_CONNECTIONS = "com.bandwidth.http.maxtotalconnections";
    String BANDWIDTH_SYSPROP_HTTP_MAX_DEFAULT_CONNECTIONS_PER_ROUTE = "com.bandwidth.http.maxdefaultconnectionsperroute";

}
